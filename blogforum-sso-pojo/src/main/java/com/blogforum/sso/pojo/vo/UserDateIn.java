package com.blogforum.sso.pojo.vo;

import java.util.Date;

public class UserDateIn {

	/**
	 * 开始时间
	 */
	Date	startDate;
	/**
	 * 结束时间
	 */
	Date	endDate;
	/**
	 * 用户状态
	 */
	Integer	status;
	
	public UserDateIn() {
	}

	public UserDateIn(Date startDate,Date endDate,Integer status) {
		this.endDate = endDate;
		this.startDate  = startDate;
		this.status = status;
	}


	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
