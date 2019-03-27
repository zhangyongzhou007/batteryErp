package com.batteryErp.mq;

import com.star.common.error.ServiceErrorFactory;
import org.dom4j.Element;
import org.xmpp.packet.IQ;
import org.xmpp.packet.Packet;

public abstract class AbstractOperator
{
	public abstract String getName();
	
	public abstract Packet handle(Element packet);
	
	public static IQ generalError(Packet packet, String errorCode)
	{
		IQ error = IQ.createResultIQ((IQ) packet);
		error.setType(IQ.Type.error);
		error.getElement().add(ServiceErrorFactory.getInstance().getErrorCode(errorCode).toXML());
		return error;
	}
}
