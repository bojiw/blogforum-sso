package com.blogforum.sso.facade.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SsoPage<T> extends BaseVO{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -643047177793399967L;
	
	private Integer pageNo; // 当前页码
	private Integer pageSize;// 分页大小
	private long count;// 总记录数
	private Integer countNum;//总页数

	private boolean firstPage = false;// 是否是第一页
	private boolean lastPage = false;// 是否是最后一页

	//返回对象
	private List<T> list = new ArrayList<T>();

	//扩展函数
	private Map<String,Object> params=new HashMap<String, Object>();

	public SsoPage(Integer pageNo,Integer pageSize,long count) {
		
		this.pageSize = pageSize;
		this.count = count;
		this.pageNo = pageNo;
        if (count <= 0 ) {
        	this.countNum = 1;
        	this.firstPage = true;
        	this.lastPage = true;
        }else {
        	countNum = (int) Math.ceil((double) count / pageSize);
        	if (countNum == pageNo) {
        		
        		this.lastPage = true;
			}
        	if (pageNo == 1) {
        		this.firstPage = true;
			}
		}
        
	}
	
	
	
	
	public int getCountNum() {
		return countNum;
	}




	public void setCountNum(int countNum) {
		this.countNum = countNum;
	}




	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public boolean isFirstPage() {
		return firstPage;
	}

	public void setFirstPage(boolean firstPage) {
		this.firstPage = firstPage;
	}

	public boolean isLastPage() {
		return lastPage;
	}

	public void setLastPage(boolean lastPage) {
		this.lastPage = lastPage;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
	


}
