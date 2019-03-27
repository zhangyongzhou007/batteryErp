package com.batteryErp.sdk;

import java.util.ArrayList;
import java.util.List;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class ListWithPage<T> {
	//显示第几页
	private int currPage = 1;
	//每页显示多少条
	private int pageSize = 20;
	//一共多少页
	private int totalPage = 0;
	//一共多少条
	private int totalRow = 0;
	
	private String sortBy = "";
	private String sortOrder = "";
	
	List<T> data = null;
	


	public List<T> getData() {
		return data;
	}

	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	/**
	 * @return the sortBy
	 */
	public String getSortBy() {
		return sortBy;
	}

	/**
	 * @param sortBy the sortBy to set
	 */
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	/**
	 * @return the sortOrder
	 */
	public String getSortOrder() {
		return sortOrder;
	}

	/**
	 * @param sortOrder the sortOrder to set
	 */
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public int getTotalRow() {
		return totalRow;
	}

	public void setTotalRow(int totalRow) {
		this.totalRow = totalRow;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public int getSkip(){
		int skip = (currPage-1)* pageSize;
		return skip;
	}
	public int getLimit(){
		return pageSize;
	}
	public void setCount(long count){
		this.totalRow = (int)count;
		this.totalPage = this.totalRow == 0 ? 1 : (this.totalRow % this.pageSize == 0 ? 
				this.totalRow / this.pageSize :  this.totalRow / this.pageSize + 1);
	}

	static public <T> ListWithPage<T> create(JSONObject jsonObj){
		ListWithPage<T> page = new ListWithPage<T>();
		int pCurrPage = 1;
		int pPageSize = 20;
		String pSortBy = "createTime";
		String pSortOrder = "-1";
        if (jsonObj.containsKey("currPage")){
            pCurrPage = jsonObj.getInt("currPage");
        }
        if (jsonObj.containsKey("pageSize")){
        	pPageSize = jsonObj.getInt("pageSize");
        }
    	if (pCurrPage < 1){
    		pCurrPage = 1;
    	}
        if (jsonObj.containsKey("sortBy")){
        	pSortBy = jsonObj.getString("sortBy");
        }
        if (jsonObj.containsKey("sortOrder")){
        	pSortOrder = jsonObj.getString("sortOrder");
        }
    	page.currPage = pCurrPage;
    	page.pageSize = pPageSize;
    	page.sortBy = pSortBy;
    	page.sortOrder = pSortOrder;
    	return page;
	}

	//
	public <T1> ListWithPage<T1> cloneExcludeData(){
		ListWithPage<T1> page = new ListWithPage<T1>();
		page.currPage = this.currPage;
		page.pageSize = this.pageSize;
		page.totalPage = this.totalPage;
		page.totalRow = this.totalRow;
		page.sortBy = this.sortBy;
		page.sortOrder = this.sortOrder;
		return page;
	}


	public ListWithPage<T> slice(){
		ListWithPage<T> ts = new ListWithPage<T>();
		ts.currPage = this.currPage;
		ts.pageSize = this.pageSize;
		ts.totalPage = this.totalPage;
		ts.totalRow = this.totalRow;
		ts.sortBy = this.sortBy;
		ts.sortOrder = this.sortOrder;

		List<T> list = new ArrayList<T>();
		ts.data = list;
		int startIndex = (currPage-1)*pageSize;
		int endIndex = startIndex + pageSize -1;
		if (pageSize ==0 || startIndex>data.size()-1){
			return ts;
		}
		if (endIndex > data.size()-1){
			endIndex = data.size() -1;
		}
		for(int i=startIndex;i<=endIndex;i++){
			list.add(data.get(i));
		}
		return ts;
	}

	public JSONObject toJSONObjectWithNo(){
		JSONObject obj = new JSONObject();
		if (data != null){
			obj.put("data", (List<T>)data);
			JSONArray ts  = obj.getJSONArray("data");
			for (int i=0;i<ts.size();i++){
				int no = (currPage-1)*pageSize + i+1;
				ts.getJSONObject(i).put("no", no);
			}
		}
		obj.put("currPage", currPage);
		obj.put("pageSize", pageSize);
		obj.put("totalPage", totalPage);
		obj.put("totalRow", totalRow);
		return obj;
	}

	public JSONObject toJSONObject(){
		JSONObject obj = new JSONObject();
		if (data != null){
			obj.put("data", (List<T>)data);
		}
		obj.put("currPage", currPage);
		obj.put("pageSize", pageSize);
		obj.put("totalPage", totalPage);
		obj.put("totalRow", totalRow);
		return obj;
	}

	@Override
	public String toString() {
		JSONObject obj = this.toJSONObject();
		return obj.toString();
	}
	
	
}
