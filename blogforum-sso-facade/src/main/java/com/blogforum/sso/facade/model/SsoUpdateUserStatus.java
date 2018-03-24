package com.blogforum.sso.facade.model;

import com.blogforum.sso.facade.enums.UserStatusEnum;

public class SsoUpdateUserStatus extends BaseVO {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 9128505996817622190L;

	/**
	 * 用户id
	 */
	private String				userId;

	/**
	 * 用户状态
	 */
	private UserStatusEnum		status;

	/**
	 * 操作用户
	 */
	private String				updateUser;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public UserStatusEnum getStatus() {
		return status;
	}

	public void setStatus(UserStatusEnum status) {
		this.status = status;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

}
