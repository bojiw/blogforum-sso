package com.blogforum.sso.service.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blogforum.sso.common.enums.SSOBizError;
import com.blogforum.sso.common.utils.Preconditions;
import com.blogforum.sso.pojo.entity.User;
import com.blogforum.sso.service.dao.UserService;
import com.blogforum.sso.service.manager.ModifyPasswordManager;
import com.blogforum.sso.service.verification.VerificationCodeSend;

@Component
public class ModifyPasswordManagerImpl implements ModifyPasswordManager {

	@Autowired
	private VerificationCodeSend verificationCodeSend;
	
	/** 用户表dao */
	@Autowired
	protected UserService	userService;
	
	@Override
	public void sendIphoneVerfication(String iphone) {
		//验证手机号
		Preconditions.checkNotNull(iphone, SSOBizError.IPHONE_NOTNULL);
		User user = new User();
		user.setIphone(iphone);
		User newuser = userService.getUserByNameOREmailORIphoneAndPwd(user,false);
		Preconditions.checkNotNull(newuser, "没有找到该手机号注册用户");
		//发送短信验证码
		verificationCodeSend.SendIphoneVerificationCode(iphone);

	}

	@Override
	public void sendEmailVerfication(String email) {
		//验证手机号
		Preconditions.checkNotNull(email, SSOBizError.EMAIL_NOTNULL);
		User user = new User();
		user.setEmail(email);
		User newuser = userService.getUserByNameOREmailORIphoneAndPwd(user,false);
		Preconditions.checkNotNull(newuser, "没有找到该邮箱注册用户");
		//发送验证码
		verificationCodeSend.SendEmailVerificationCode(email, "修改密码");

	}


	@Override
	public void updateIphonePassword(String iphone, String verfication, String password) {
		Preconditions.checkNotNull(iphone, SSOBizError.IPHONE_NOTNULL);
		//效验验证码
		verificationCodeSend.checkRegisterKey(iphone, verfication);
		//效验手机号
		User user = new User();
		user.setIphone(iphone);
		User newuser = userService.getUserByNameOREmailORIphoneAndPwd(user,false);
		Preconditions.checkNotNull(newuser, "没有找到该手机号注册用户");
		newuser.setPassword(password);
		//设置更新用户
		newuser.setUpdateUser(newuser.getUsername());
		//更新密码
		userService.updatePwd(newuser);
		
	}

	@Override
	public void updateEamilPassword(String email, String verfication, String password) {
		Preconditions.checkNotNull(email, SSOBizError.EMAIL_NOTNULL);
		//效验验证码
		verificationCodeSend.checkRegisterKey(email, verfication);
		//效验手机号
		User user = new User();
		user.setEmail(email);
		User newuser = userService.getUserByNameOREmailORIphoneAndPwd(user,false);
		Preconditions.checkNotNull(newuser, "没有找到该邮箱注册用户");
		newuser.setPassword(password);
		//设置更新用户
		newuser.setUpdateUser(newuser.getUsername());
		//更新密码
		userService.updatePwd(newuser);
		
	}

}
