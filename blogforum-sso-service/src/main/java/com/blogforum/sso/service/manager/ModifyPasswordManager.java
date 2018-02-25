package com.blogforum.sso.service.manager;

public interface ModifyPasswordManager {

	/**
	 * 发送手机验证码
	 * @param iphone
	 * @param user
	 * @author: wwd
	 * @time: 2018年2月24日
	 */
	void sendIphoneVerfication(String iphone);
	
	/**
	 * 发送短信验证码
	 * @param email
	 * @param user
	 * @author: wwd
	 * @time: 2018年2月24日
	 */
	void sendEmailVerfication(String email);
	
	
	/**
	 * 通过手机号修改密码
	 * @param key
	 * @param verfication
	 * @param password
	 * @param user
	 * @author: wwd
	 * @time: 2018年2月24日
	 */
	void updateIphonePassword(String iphone,String verfication,String password);
	
	/**
	 * 通过邮箱修改密码
	 * @param key
	 * @param verfication
	 * @param password
	 * @param user
	 * @author: wwd
	 * @time: 2018年2月24日
	 */
	void updateEamilPassword(String email,String verfication,String password);
	
	
}
