package com.blogforum.sso.facade.model;

/**
 * 更新用户密码
 * @author wwd
 *
 */
public class SsoUpdateUserPwd extends BaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3656899258071926600L;

	
	/**用户id*/
	private String userId;
	
	/**
	 * 新密码
	 */
	private String newPassword;
	
	
	/**
	 * 操作用户
	 */
	private String updateUser;


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getNewPassword() {
		return newPassword;
	}


	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}


	public String getUpdateUser() {
		return updateUser;
	}


	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	
	
	
	
	
	
}
