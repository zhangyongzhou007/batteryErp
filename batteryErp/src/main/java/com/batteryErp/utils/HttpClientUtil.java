package com.batteryErp.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URLDecoder;

public class HttpClientUtil {
	
	private static Logger logger = Logger.getLogger(HttpClientUtil.class);

	/**
	 * 提交JSON格式请求参数，返回响应JSON数据
	 * @param jsonParam
	 * @param url
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static JSONObject JsonSubmit(JSONObject jsonParam,String url) throws ClientProtocolException, IOException{
		HttpPost httpPost = new HttpPost(url);
		HttpClient client = HttpClients.createDefault();
		StringEntity entity = new StringEntity(jsonParam.toString(),"utf-8");//解决中文乱码问题    
		entity.setContentEncoding("UTF-8");    
		entity.setContentType("application/json");    
		httpPost.setEntity(entity);
		RequestConfig config = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000).build();
		httpPost.setConfig(config);
		HttpResponse response =  client.execute(httpPost);
		String responseBody = EntityUtils.toString(response.getEntity());
		logger.info("responseBody="+responseBody);
		closeHttp(client, response);
		JSONObject result = JSONObject.fromObject(responseBody);
		return result;
	}
	public static JSONObject JsonSubmit(JSONArray jsonParam,String url) throws ClientProtocolException, IOException{
		HttpPost httpPost = new HttpPost(url);
		HttpClient client = HttpClients.createDefault();
		StringEntity entity = new StringEntity(jsonParam.toString(),"utf-8");//解决中文乱码问题    
		entity.setContentEncoding("UTF-8");    
		entity.setContentType("application/json");    
		httpPost.setEntity(entity);
		RequestConfig config = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000).build();
		httpPost.setConfig(config);
		HttpResponse response =  client.execute(httpPost);
		String responseBody = EntityUtils.toString(response.getEntity());
		logger.info("responseBody="+responseBody);
		closeHttp(client, response);
		JSONObject result = JSONObject.fromObject(responseBody);
		return result;
	}
	
	
	/**
     * 发送get请求ZZZ
     * @param url    路径
     * @return
     */
    public static JSONObject httpGet(String url){
        //get请求返回结果
        JSONObject jsonResult = null;
        try {
        	HttpClient client =  HttpClients.createDefault();
            //发送get请求
            HttpGet request = new HttpGet(url);
   		 
            RequestConfig config = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000).build();
            
            request.setConfig(config);
    		
            logger.info(" Request URL："+url);
            
            HttpResponse response = client.execute(request);
 
            logger.info(" Response result："+response);
            
            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
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


}
