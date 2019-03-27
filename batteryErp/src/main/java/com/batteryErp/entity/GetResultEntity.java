package com.batteryErp.entity;


import com.batteryErp.entity.vo.PageVo;
import com.batteryErp.sdk.ListWithPage;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import java.util.*;

public class GetResultEntity<T> {
	public static final int STATUS_CODE_SUCCESS = 10000200;
	int statusCode = 0;
	String info = "";
	T data = null;
	PageVo pageVo = null;
	
//	Map<String,String> valueMap = new HashMap<String,String>();

	static public <T> GetResultEntity<T> create200(){
		GetResultEntity<T> result = new GetResultEntity<T>();
		result.statusCode = 10000200;
		result.info = "成功";
		return result;
	}
	
	static public <T> GetResultEntity<T> create404(){
		GetResultEntity<T> result = new GetResultEntity<T>();
		result.statusCode = 10000404;
		result.info = "没有查询到数据";
		return result;
	}

	static public <T> GetResultEntity<T> create201(){
		GetResultEntity<T> result = new GetResultEntity<T>();
		result.statusCode = 10000201;
		result.info = "关键参数为空！";
		return result;
	}

	static public  <T> GetResultEntity<T>  resultEntity(int statusCode , String info){
		GetResultEntity<T> result = new GetResultEntity<T>();
		result.statusCode = statusCode;
		result.info = info;
		return result;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

//	public void put(String name,String value){
//		valueMap.put(name, value);
//	}
	
	public PageVo getPageVo() {
		return pageVo;
	}

	public void setPageVo(PageVo pageVo) {
		this.pageVo = pageVo;
	}

	public JSONObject toJSONObject(){
		if (data instanceof ListWithPage){
			return toListJSONObject();
		}

		JSONObject obj = new JSONObject();
		obj.put("statusCode", statusCode);
		obj.put("info", info);
//		for(Map.Entry<String, String> e: valueMap.entrySet()){
//			obj.put(e.getKey(), e.getValue());
//		}
		if (data != null){
			obj.put("data", (T)data);
		}
		if (pageVo != null){
			obj.put("pageVo", pageVo);
		}
		//JSONObject obj = JSONObject.fromObject(this);
		return obj;
	}
	
	public JSONObject toListJSONObjectWithNo(){
		if (!(data instanceof ListWithPage)){
			return toJSONObject();
		}
		
		JSONObject obj = new JSONObject();
		obj.put("statusCode", statusCode);
		obj.put("info", info);
//		for(Map.Entry<String, String> e: valueMap.entrySet()){
//			obj.put(e.getKey(), e.getValue());
//		}
		if (data != null){
			ListWithPage<T> lwp = (ListWithPage<T>)data;
			obj.put("data", lwp.toJSONObjectWithNo().getJSONArray("data"));
			PageVo pageVo = new PageVo();
			pageVo.setCurrPage(lwp.getCurrPage());
			pageVo.setPageSize(lwp.getPageSize());
			pageVo.setTotalPage(lwp.getTotalPage());
			pageVo.setTotalRow(lwp.getTotalRow());
			obj.put("pageVo", pageVo);
		}

		//JSONObject obj = JSONObject.fromObject(this);
		return obj;
	}

	private JSONObject toListJSONObject(){
		if (!(data instanceof ListWithPage)){
			return toJSONObject();
		}

		JSONObject obj = new JSONObject();
		obj.put("statusCode", statusCode);
		obj.put("info", info);
//		for(Map.Entry<String, String> e: valueMap.entrySet()){
//			obj.put(e.getKey(), e.getValue());
//		}
		if (data != null){
			ListWithPage<T> lwp = (ListWithPage<T>)data;
			obj.put("data", lwp.toJSONObject().getJSONArray("data"));
			PageVo pageVo = new PageVo();
			pageVo.setCurrPage(lwp.getCurrPage());
			pageVo.setPageSize(lwp.getPageSize());
			pageVo.setTotalPage(lwp.getTotalPage());
			pageVo.setTotalRow(lwp.getTotalRow());
			obj.put("pageVo", pageVo);
		}

		//JSONObject obj = JSONObject.fromObject(this);
		return obj;
	}
	
	public JSONObject toJSONObject(String[] includeFields,String[] excludeFields){
		JSONObject obj = new JSONObject();
		obj.put("statusCode", statusCode);
		obj.put("info", info);
//		for(Map.Entry<String, String> e: valueMap.entrySet()){
//			obj.put(e.getKey(), e.getValue());
//		}
		if (data != null){
			obj.put("data", (T)data);
		}
		if (pageVo != null){
			obj.put("pageVo", pageVo);
		}
		if(data!=null){
			if(includeFields!=null && includeFields.length>0){
				JsonConfig config = new JsonConfig();
				String[] excludeFieldsArray = null;			
				Object dataObj = obj.get("data");
				if(dataObj instanceof JSONArray){
					JSONArray jsonArray = (JSONArray) dataObj;
					for(int i=0;i<jsonArray.size();i++){
						JSONObject temp = jsonArray.getJSONObject(i);
						if(excludeFieldsArray == null){
							excludeFieldsArray = getExcludeFieldsJSONObject(temp, includeFields);
							config.setExcludes(excludeFieldsArray);
						}
						JSONObject newJson = JSONObject.fromObject(temp, config);
						jsonArray.set(i, newJson);
					}
				}
				if(dataObj instanceof JSONObject){
					JSONObject jsonObj = (JSONObject) dataObj;
					if(excludeFieldsArray == null){
						excludeFieldsArray = getExcludeFieldsJSONObject(jsonObj, includeFields);
						config.setExcludes(excludeFieldsArray);
					}
					JSONObject newJson = JSONObject.fromObject(jsonObj, config);
					obj.put("data", newJson);
				}
			}
			if(excludeFields!=null && excludeFields.length>0){
				JsonConfig config = new JsonConfig();
				config.setExcludes(excludeFields);
				Object dataObj = obj.get("data");
				if(dataObj instanceof JSONArray){
					JSONArray jsonArray = (JSONArray) dataObj;
					for(int i=0;i<jsonArray.size();i++){
						JSONObject temp = jsonArray.getJSONObject(i);
						JSONObject newJson = JSONObject.fromObject(temp, config);
						jsonArray.set(i, newJson);
					}
				}
				if(dataObj instanceof JSONObject){
					JSONObject jsonObj = (JSONObject) dataObj;
					JSONObject newJson = JSONObject.fromObject(jsonObj, config);
					obj.put("data", newJson);
				}
			}
			
		}
		return obj;
	}

	@Override
	public String toString() {
		JSONObject obj = this.toJSONObject();
		return obj.toString();
	}
	
	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
	private String[] getExcludeFieldsJSONObject(JSONObject json ,String[] includeFields){
		List<String> inLists = Arrays.asList(includeFields);
		List<String> exLists = new ArrayList<String>();
		for(Object str : json.keySet()){
			if(str instanceof String){
				String field = (String) str;
				if(!inLists.contains(field)){
					exLists.add(field);
				}
			}
		}
		if(exLists.size() == 0){
			return new String[]{};
		}
		String[] exArray = new String[exLists.size()];
		exLists.toArray(exArray);
		return exArray;
	}
}
