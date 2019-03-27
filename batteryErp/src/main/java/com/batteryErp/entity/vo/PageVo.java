package com.batteryErp.entity.vo;

public class PageVo {
	//每页显示多少条
	private int pageSize = 15;
	//显示第几页
	private int currPage = 1;
	//一共多少页
	private int totalPage = 1;
	//一共多少条
	private int totalRow=0;
	
	/**
	 * 前一页：p  后一页：n  跳页：s
	 */
	private String direction;
	
	/**
	 * 当前页的的第一条记录号
	 */
	private String firstnum;
	
	/**
	 * 当前页的的最后一条记录
	 */
	private String lastnum;
	
	public int getPageSize() {
		return pageSize;
	}
	public int getCurrPage() {
		return currPage;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public int getTotalRow() {
		return totalRow;
	}
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
		
		this.totalPage = this.totalRow == 0 ? 1 : (this.totalRow % this.pageSize == 0 ? 
				this.totalRow / this.pageSize :  this.totalRow / this.pageSize + 1);	
	}
	public void setCurrPage(int currPage) {
		if(currPage < 1){
			currPage = 1;
		}//else if(currPage > this.totalPage){
			//currPage = this.totalPage;
		//}	
		this.currPage = currPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public void setTotalRow(int totalRow) {
		this.totalRow = totalRow;
		
		this.totalPage = this.totalRow == 0 ? 1 : (this.totalRow % this.pageSize == 0 ? 
				this.totalRow / this.pageSize :  this.totalRow / this.pageSize + 1);
		
		if(this.currPage > this.totalPage) {
			this.currPage = this.totalPage;
		}
	}
	
	
	/** 
	* 第一页 
	* 
	* @return int 
	*/ 
	public int first() { 
		return 1; 
	} 
	/** 
	* 最后一页 
	* 
	* @return int 
	*/ 
	public int last() { 
		return this.totalPage; 
	} 
	/** 
	* 上一页 
	* 
	* @return int 
	*/ 
	public int previous() { 
		return (this.currPage - 1 < 1) ? 1 : this.currPage - 1; 
	} 
	/** 
	* 下一页 
	* 
	* @return int 
	*/ 
	public int next() { 
		return (this.currPage + 1 > this.totalPage) ? this.totalPage : this.currPage + 1; 
	} 
	
	
	/** 
	* 第一页 
	* 
	* @return boolean 
	*/ 
	public boolean isFirst() { 
		return (this.currPage == 1) ? true : false; 
	} 
	/** 
	* 最后一页 
	* 
	* @return boolean 
	*/ 
	public boolean isLast() { 
		return (this.currPage == this.totalPage) ? true : false; 
	}
	public String getDirection() {
		return direction;
	}
	public String getFirstnum() {
		return firstnum;
	}
	public String getLastnum() {
		return lastnum;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public void setFirstnum(String firstnum) {
		this.firstnum = firstnum;
	}
	public void setLastnum(String lastnum) {
		this.lastnum = lastnum;
	}

}
