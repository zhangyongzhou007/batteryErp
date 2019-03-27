package com.batteryErp.utils;

import com.star.gate.GateService;
import com.batteryErp.exception.MException;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
	private static Logger logger = LoggerFactory.getLogger(HttpRequest.class);
	private String reqUrl = "";
	private Map<String,String> queryMap = new HashMap<String,String>(); 
	private String jsonBody = "";
	private int socketTimeout = 5000;
	private int connectTimeout = 5000;
	
	public String getJsonBody() {
		return jsonBody;
	}
	public void setJsonBody(String jsonBody) {
		this.jsonBody = jsonBody;
	}
	public void setJSONObjectBody(JSONObject jsonBody) {
		this.jsonBody = jsonBody.toString();
	}
	/**
	 * @return the socketTimeout
	 */
	public int getSocketTimeout() {
		return socketTimeout;
	}
	/**
	 * @param socketTimeout the socketTimeout to set
	 */
	public void setSocketTimeout(int socketTimeout) {
		this.socketTimeout = socketTimeout;
	}
	/**
	 * @return the connectTimeout
	 */
	public int getConnectTimeout() {
		return connectTimeout;
	}
	/**
	 * @param connectTimeout the connectTimeout to set
	 */
	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}
	public HttpRequest(String reqUrl) {
		this.reqUrl = reqUrl;
	}
	public void setQuery(String name,String value){
		queryMap.put(name, value);
	}
	public void setQuery(String name,int value){
		queryMap.put(name, String.valueOf(value));
	}
	public void setQuery(Map<String,String> map){
		queryMap.putAll(map);
	}

	
	public JSONObject get() throws MException {
		
		JSONObject json = null;
		CloseableHttpClient httpClient = null;
		String url = reqUrl;
		if (queryMap.size() != 0){
			StringBuilder sb = new StringBuilder();
			sb.append(reqUrl).append("?");
			int qIndex = 0;
			for(Map.Entry<String, String> entry:queryMap.entrySet()){
				if (qIndex != 0){
					sb.append("&");
				}
				sb.append(entry.getKey()).append("=").append(entry.getValue());
				qIndex++;
			}
			url = sb.toString();
		}
		
		logger.info("get ===>>> "+url);
		
		CloseableHttpResponse response = null;
		try {
			httpClient = HttpClients.createDefault();
			HttpGet get = new HttpGet(url);
			RequestConfig config = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
			get.setConfig(config);
			get.setHeader("token", GateService.getInstance().getToken());
//			System.out.println(com.star.common.context.StarContextHolder.getInstance().getAppId());
			if (!StringUtils.isNull(com.star.common.context.StarContextHolder.getInstance().getAppId()))
			{

				get.setHeader("app-id", com.star.common.context.StarContextHolder.getInstance().getAppId());
			}
			
			response = httpClient.execute(get);
			
			int status = response.getStatusLine().getStatusCode();
			HttpEntity resEntity = response.getEntity();
			String responseContent = "";
			if(resEntity != null) {
				responseContent = EntityUtils.toString(resEntity, "UTF-8");
			}
			logger.info("get response,status="+status +","+ responseContent);
			if (200 != status)
			{
				throw MException.create500("get "+get.toString()+", "+status + ","+responseContent);
			}
			// 获取响应对象
			if(resEntity != null) {
				json = JSONObject.fromObject(responseContent);
			}
			// 销毁
			EntityUtils.consume(resEntity);
		} 
		catch(MException e){
			throw e;
		}
		catch(Exception e){
			throw MException.create500(e.getMessage());
		}
		finally {
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
		
		
		return json;
	}
	
	
	
	public JSONObject post() throws MException {
		logger.info("post "+this.toString());
		
		JSONObject json = null;
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		try {
			httpClient = HttpClients.createDefault();
			HttpPost post = new HttpPost(reqUrl);
			RequestConfig config = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
			post.setConfig(config);
			StringEntity entity = new StringEntity(jsonBody,"utf-8");//解决中文乱码问题
			entity.setContentEncoding("UTF-8");
			entity.setContentType("application/json");
			post.setEntity(entity);
			logger.info("post request:\r\n"+reqUrl+"\r\n"+jsonBody);
			System.out.println("post request:\r\n"+reqUrl+"\r\n"+jsonBody);
			post.setHeader("token", GateService.getInstance().getToken());
			System.out.println("app-id: "+com.star.common.context.StarContextHolder.getInstance().getAppId());
			if (!StringUtils.isNull(com.star.common.context.StarContextHolder.getInstance().getAppId()))
			{
				post.setHeader("app-id", com.star.common.context.StarContextHolder.getInstance().getAppId());

			}
			
			response = httpClient.execute(post);

			int status = response.getStatusLine().getStatusCode();
			HttpEntity resEntity = response.getEntity();
			String responseContent = "";
			if(resEntity != null) {
				responseContent = EntityUtils.toString(resEntity, "UTF-8");
			}
			logger.info("post response,status="+status +","+ responseContent);
			if (200 != status)
			{
				throw MException.create500("post "+post.toString()+", "+status + ","+responseContent);
			}

			// 获取响应对象
			if (resEntity != null) {
				json = JSONObject.fromObject(responseContent);
			}
			System.out.println("result: "+json);
			// 销毁
			EntityUtils.consume(resEntity);

		} 
		catch(MException e){
			throw e;
		}
		catch(Exception e){
			throw MException.create500(e.getMessage());
		}
		finally {
			try{
				if (response != null){
					response.close();
				}
				if (httpClient != null){
					httpClient.close();
				}
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		return json;
	}
	
	
//	public JSONObject get() throws Exception {
//		JSONObject json = null;
//		
//		CloseableHttpClient httpClient = null;
//		String url = reqUrl;
//		if (queryMap.size() != 0){
//			StringBuilder sb = new StringBuilder();
//			sb.append(reqUrl).append("?");
//			int qIndex = 0;
//			for(Map.Entry<String, String> entry:queryMap.entrySet()){
//				if (qIndex != 0){
//					sb.append("&");
//				}
//				sb.append(entry.getKey()).append("=").append(entry.getValue());
//				qIndex++;
//			}
//			url = sb.toString();
//		}
//		
//		CloseableHttpResponse response = null;
//		try {
//			httpClient = HttpClients.createDefault();
//			HttpGet get = new HttpGet(url);
//			RequestConfig config = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).build();
//			get.setConfig(config);
//			
//			logger.info("http url：" + get.toString());
//			
//			response = httpClient.execute(get);
//			
//			int status = response.getStatusLine().getStatusCode();
//			
//			if (200 != status)
//			{
//				logger.error("get request failed, status : " + status);
//				return null;
//			}
//			
//			// 获取响应对象
//			HttpEntity resEntity = response.getEntity();
//			if(resEntity != null) {
//				String responseContent = EntityUtils.toString(resEntity, "UTF-8");
//				
//				logger.debug("responseContent: " + responseContent);
//				json = JSONObject.fromObject(responseContent);
//				//json = JSONObject.parseObject(responseContent);
//			}
//			
//			// 销毁
//			EntityUtils.consume(resEntity);
//		} finally {
//			try
//			{
//				if (response != null)
//				{
//					response.close();
//				}
//			} catch (IOException e)
//			{
//				e.printStackTrace();
//			}
//
//			try
//			{
//				if (httpClient != null)
//				{
//					httpClient.close();
//				}
//			} catch (IOException e)
//			{
//				e.printStackTrace();
//			}
//		}
//		
//		
//		return json;
//	}
//	
//	
//	
//	public JSONObject post() throws Exception {
//		//JSONObject paraJsonObj = JSONObject.fromObject(jsonBody);
//		JSONObject json = null;
//		CloseableHttpClient httpClient = null;
//		CloseableHttpResponse response = null;
//		try {
//			httpClient = HttpClients.createDefault();
//			HttpPost method = new HttpPost(reqUrl);
//			RequestConfig config = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).build();
//			method.setConfig(config);
//			logger.info("request body:"+jsonBody);
//			StringEntity entity = new StringEntity(jsonBody,"utf-8");//解决中文乱码问题
//			entity.setContentEncoding("UTF-8");
//			entity.setContentType("application/json");
//			method.setEntity(entity);
//			response = httpClient.execute(method);
//
//			int status = response.getStatusLine().getStatusCode();
//			if (200 != status) {
//				logger.error("http request failed, status : " + status);
//				return null;
//			}
//
//			// 获取响应对象
//			HttpEntity resEntity = response.getEntity();
//			if (resEntity != null) {
//				String responseContent = EntityUtils.toString(resEntity, "UTF-8");
//
//				logger.debug("responseContent: " + responseContent);
//
//				json = JSONObject.fromObject(responseContent);
//			}
//
//			// 销毁
//			EntityUtils.consume(resEntity);
//
//		} catch (ClientProtocolException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			try{
//				if (response != null){
//					response.close();
//				}
//				if (httpClient != null){
//					httpClient.close();
//				}
//			} catch (Exception e){
//				e.printStackTrace();
//			}
//		}
//		return json;
//	}
	
	
}
