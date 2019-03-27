package com.batteryErp.utils;

import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class OfficialWebUtils {
	private static Logger logger = LoggerFactory.getLogger(OfficialWebUtils.class);
	
	public static Map<String, Object> requestHttp(String url, Map<String, String> tree) throws Exception {
		Map<String, Object> resultMap = null;
		
		String param = getParamStr(tree);
		
		CloseableHttpClient httpClient = null;
		
		CloseableHttpResponse response = null;
		try {
			httpClient = HttpClients.createDefault();
			HttpGet get = new HttpGet(url + "?" + param);
			
			response = httpClient.execute(get);
			
			int status = response.getStatusLine().getStatusCode();
			
			if (200 != status)
			{
				logger.error("query video list from edu failed, status : " + status);
				return null;
			}
			
			// 获取响应对象
			HttpEntity resEntity = response.getEntity();
			if(resEntity != null) {
				String responseContent = EntityUtils.toString(resEntity, "UTF-8");
				
				logger.debug("responseContent: " + responseContent);
				
				ObjectMapper mapper = new ObjectMapper();
				resultMap = mapper.readValue(responseContent, Map.class);
			}
			
			// 销毁
			EntityUtils.consume(resEntity);
		} finally {
			try
			{
				if (response != null)
				{
					response.close();
				}
			} catch (IOException e)
			{
				e.printStackTrace();
			}

			try
			{
				if (httpClient != null)
				{
					httpClient.close();
				}
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
		
		return resultMap;
	}
	
	/**
     * 发送get请求
     * @param url    路径
     * @return
     */
    public static JSONObject httpGet(String url, Map<String, String> tree){
        //get请求返回结果
        JSONObject jsonResult = new JSONObject();
        String param = getParamStr(tree);
        try {
        	HttpClient client =  HttpClients.createDefault();
            //发送get请求
            HttpGet request = new HttpGet(url + "?" + param);
   		 
            logger.info(" Request URL："+url);
            
            HttpResponse response = client.execute(request);
 
            logger.info(" Response result："+response);
            
            int status = response.getStatusLine().getStatusCode();
            
            /**请求发送成功，并得到响应**/
            if (status == 200) {
                /**读取服务器返回过来的json字符串数据**/
                String strResult = EntityUtils.toString(response.getEntity());
                /**把json字符串转换成json对象**/
                jsonResult = JSONObject.fromObject(strResult);
                url = URLDecoder.decode(url, "UTF-8");
            } else {
                logger.error("get请求提交失败:" + url);
            }
            closeHttp(client, response);
        } catch (IOException e) {
            logger.error("get请求提交失败:" + url, e);
        }
        return jsonResult;
    }
    
    /**
	 * 关闭HTTP协议协议
	 * @Title: closeHttp 
	 * @Description: 关闭HTTP协议协议 
	 * @param httpClient
	 * @param response void
	 * @throws
	 */
	public static void closeHttp(HttpClient httpClient, HttpResponse response) {
		try {
			if(response != null) {
				if(response instanceof CloseableHttpResponse) {
					((CloseableHttpResponse) response).close();	
				} 
			}
		} catch (IOException e) {
			logger.error("response is failure to close.", e);
		}
		
		try {
			if(httpClient != null) {
				if(httpClient instanceof CloseableHttpClient) {
					((CloseableHttpClient) httpClient).close();	
				} 
			}
		} catch (IOException e) {
			logger.debug("httpClient i failure to close", e);
			
		}
		
	}
	
	
	@SuppressWarnings("rawtypes")
	public static String getParamStr(Map<String, String> param)
	{
		StringBuffer sb = new StringBuffer();
		Iterator titer = param.entrySet().iterator();
		while (titer.hasNext())
		{
			Map.Entry item = (Map.Entry)titer.next();
			String key = item.getKey().toString();
			Object value = item.getValue();
			if (null == value)
			{
				logger.error("param is null , key : " + key);
				continue;
			}
			sb.append(key + "=" + value.toString() + "&");
		}
		return sb.toString();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map<String, String> getParameters (HttpServletRequest request){
		  Map<String, String> normalMap = new TreeMap<String, String>();
		
		   Map<String, String[]> paramMap = request.getParameterMap();
		   Iterator iter = paramMap.entrySet().iterator();
	       while(iter.hasNext()) {
	    	   Map.Entry element=(Map.Entry)iter.next();
	    	 //key值  
	    	   String key = element.getKey().toString();
	    	 //value,数组形式
	    	   String[] value = (String[])element.getValue();
	    	   if (value.length ==1 && !key.endsWith("[]")) {
	    		   normalMap.put(key, value[0]);
	    	   }
	    	   else {
	    		   key = key.substring(0, key.length()-2);
	    		   normalMap.put(key, Arrays.toString(value));
	    	   }

	       }
	       return normalMap;
	       
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map<String, Object> getObjectParameters (HttpServletRequest request){
		  Map<String, Object> normalMap = new TreeMap<String, Object>();
		
		   Map<String, String[]> paramMap = request.getParameterMap();
		   Iterator iter = paramMap.entrySet().iterator();
	       while(iter.hasNext()) {
	    	   Map.Entry element=(Map.Entry)iter.next();
	    	 //key值  
	    	   String key = element.getKey().toString();
	    	 //value,数组形式
	    	   String[] value = (String[])element.getValue();
	    	   if (value.length ==1 && !key.endsWith("[]")) {
	    		   normalMap.put(key, value[0]);
	    	   }
	    	   else {
	    		   key = key.substring(0, key.length()-2);
	    		   normalMap.put(key, value);
	    	   }

	       }
	       return normalMap;
	       
	}



	public static String generateSign(String paramStr, String secret)
	{
		String sign = null;
		StringBuffer sb = new StringBuffer();
		sb.append(paramStr).append("secret=").append(secret);
		sign = DigestUtils.md5Hex(sb.toString());
		return sign;
	}

	public static String getParamStr(TreeMap<String, String> param)
	{
		StringBuffer sb = new StringBuffer();
		Iterator titer = param.entrySet().iterator();
		while (titer.hasNext())
		{
			Map.Entry item = (Map.Entry)titer.next();
			String key = item.getKey().toString();
			Object value = item.getValue();
			if (null == value)
			{
				logger.error("param is null , key : " + key);
				continue;
			}
			sb.append(key + "=" + value.toString() + "&");
		}
		return sb.toString();
	}
	
	/**
	 * json转化为字符串
	 * @Title: readJSONString 
	 * @Description:  
	 * @param request
	 * @return String
	 * @throws
	 */
	public static String readJSONString(HttpServletRequest request){
	   StringBuffer json = new StringBuffer();
	   String line = null;
	   try {
		   BufferedReader reader = request.getReader();
		   while((line = reader.readLine()) != null) {
			   json.append(line);
		   }
	   }
	   catch(Exception e) {
		   logger.error("request.getReader() is error", e);
	   }
	   return json.toString();
	}
	

}
