package com.blogforum.sso.facade.model;

import java.util.Map;

import com.blogforum.sso.facade.enums.UserStatusEnum;

public class SsoUserPageRequest extends BaseVO {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -6535783461492595137L;

	/**
	 * 请求页数 默认查询第一页
	 */
	private Integer				pageNo				= 1;

	/**
	 * 请求页数大小 默认查询20条
	 */
	private Integer				pageSize			= 20;

	private UserStatusEnum		status;

	/**
	 * 扩展字段
	 */
	private Map<String, Object>	parameter;

	public SsoUserPageRequest() {
	}

	public SsoUserPageRequest(Integer pageNo, Integer pageSize) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Map<String, Object> getParameter() {
		return parameter;
	}

	public void setParameter(Map<String, Object> parameter) {
		this.parameter = parameter;
	}

	public UserStatusEnum getStatus() {
		return status;
	}

	public void setStatus(UserStatusEnum status) {
		this.status = status;
	}

}
