package com.batteryErp.mq;

import com.star.common.utils.StringUtils;
import com.star.kafkamq.MQContext;
import com.star.kafkamq.MQContextFactory;
import com.star.kafkamq.utils.KafkaGeneralUtils;
import com.star.kafkamq.utils.MQMessage;
import com.star.kafkamq.utils.MQPullListner;
import com.star.kafkamq.utils.MessageType;
import org.dom4j.Element;
import org.dom4j.io.XMPPPacketReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmpp.packet.JID;
import org.xmpp.packet.Packet;

import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 
 * @ClassName: RouterManager 
 * @Description: 路由模块
 * @author scp
 * @date 2017年1月16日 下午7:47:29
 */
public class RouterManager implements MQPullListner
{
	private static final Logger log = LoggerFactory.getLogger(RouterManager.class);

	private static RouterManager instance = new RouterManager();

	private MQContext mqcontext;
	private String from;
	private String setNo;
	private String domain;
	private Map<String, AbstractOperator> operMap = new HashMap<String, AbstractOperator>();

	private RouterManager()
	{
		//init("conf/mqConfig.xml");
	}

	public static RouterManager getInstance()
	{
		return instance;
	}

	public void init(String mqConfFile)
	{
		mqcontext = MQContextFactory.getNewMQContext(mqConfFile);
		mqcontext.setPushPullListener(this);

		from = KafkaGeneralUtils.getInstance().getXMLConfigProperties().getProperty("jid");

		setNo = from.split("@")[1].split("\\.")[0];
		domain = "y";
	}

	/**
	 * 收到消息时回调该接口
	 * @Title: messageRecived 
	 * @Description: TODO 
	 * @param mqMessage void
	 * @throws
	 */
	@Override
	public void messageRecived(MQMessage mqMessage)
	{
		// 1、解析消息体
		String content = mqMessage.getContent();

		if (StringUtils.isNull(content))
		{
			log.error("message content is null.");
			return;
		}

		log.debug("receive msg:" + content);
		
		// 2、将消息转为Element
		XMPPPacketReader reader = new XMPPPacketReader();
		Element doc = null;
		try
		{
			doc = reader.read(new StringReader(content)).getRootElement();
		} catch (Exception e)
		{
			log.error("parse content exception, content: " + content, e);
			return;
		}

		if (doc == null)
		{
			log.error("parse content error, content: " + content);
			return;
		}

		// 3、将element转为packet
		String tag = doc.getName();
		if (!"iq".equals(tag))
		{
			log.error("unsupport message tag:{}", tag);
			return;
		}

		@SuppressWarnings("unchecked")
		List<Element> elementList = doc.elements();
		if (null == elementList || 0 == elementList.size())
		{
			log.error("parser doc elements failed.{}", doc);
			return;
		}

		// 解析oper
		String oper = null;
		try
		{
			Element childEle = (Element) doc.elements().get(0);
			oper = childEle.attributeValue("oper");
		} catch (Exception e)
		{
			log.error("parse oper exception.", e);
		}

		// 获取对应的处理类
		AbstractOperator operHandle = operMap.get(oper);
		if (null == operHandle)
		{
			log.warn("can't find operHandle,will drop the message. oper:" + oper);
			return;
		}

		try
		{
			Packet response = operHandle.handle(doc);
			if (response != null)
			{
				route(response.toXML(), response.getTo().getNode());
			}
			else
			{
				log.error("handle result is null." + content);
			}
		} catch (Exception e)
		{
			log.error("handle exception.", e);
			return;
		}
	}

	public void route(String response, String toServer)
	{
		MQMessage mqMsg = new MQMessage(from, toServer + "@" + setNo + ".y", MessageType.PUSH);

		mqMsg.setId(UUID.randomUUID().toString());
		mqMsg.setContent(response);

		log.debug("router: push id:{}, to:{} ", mqMsg.getId(), mqMsg.getTo());
		mqcontext.push(mqMsg);
	}
	
	
	public void routeReturn(String response, String toServer)
	{
		MQMessage mqMsg = new MQMessage(from, toServer + "@" + setNo + ".y", MessageType.PUSH);

		mqMsg.setId(UUID.randomUUID().toString());
		mqMsg.setContent(response);

		log.debug("router: push id:{}, to:{} ", mqMsg.getId(), mqMsg.getTo());
		mqcontext.push(mqMsg);
	}

	public void putOperator(AbstractOperator operator)
	{
		operMap.put(operator.getName(), operator);
	}
	
	public MQMessage createMessage(JID to, MessageType type, String id)
	{
		return mqcontext.createMQMessage(to, type, id);
	}
	
	public MQMessage createMQMessage(JID to, MessageType type, String id) {

		MQMessage result = new MQMessage(from, to.toString(), type);
		if (id == null) {
			result.setId(KafkaGeneralUtils.getInstance().generateMID());
		} else
			result.setId(id);

		return result;

	}

	public MQContext getMqcontext() {
		return mqcontext;
	}


}
