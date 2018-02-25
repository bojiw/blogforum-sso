package com.blogforum.sso.service.verification;

public interface VerificationCodeSend {

	/**
	 * 发送短信验证码
	 * @param iphone
	 * @author: wwd
	 * @time: 2018年2月24日
	 */
	void SendIphoneVerificationCode(String iphone);
	
	/**
	 * 发送邮箱验证码
	 * @param email
	 * @param subject 邮件主题
	 * @author: wwd
	 * @time: 2018年2月24日
	 */
	void SendEmailVerificationCode(String email, String subject);
	
	
	/**
	 * 获取验证码并setEx值到redis
	 * 
	 * @param user
	 * @return
	 */
	String getVerificationCodeAndSetExRedis(String key);
	
	/**
	 * 效验验证码是否正确
	 * 
	 * @param key
	 * @author: wwd
	 * @time: 2017年11月20日
	 */
	void checkRegisterKey(String iphoneOrmail, String verificationcode);
	
	
}
