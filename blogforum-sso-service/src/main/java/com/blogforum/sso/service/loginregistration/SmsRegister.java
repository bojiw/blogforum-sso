package com.blogforum.sso.service.loginregistration;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.blogforum.common.tools.blogforumResult;
import com.blogforum.sso.pojo.entity.User;
@Component
public class SmsRegister extends AbstractLoginRegister {

	@Override
	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public blogforumResult execute(LoginRegisterContext context) {
		User user = context.getUser();
		//效验数据
		this.checkSmsValue(user, context.getVerificationCode());
		//填充用户并且设置cookie和session
		buildUserAndSessionCookie(user, context.getHttpServletResponse());
		//保存用户
		userService.createUser(user);
		return blogforumResult.ok(noteServerUrl);
	}

	@Override
	protected void checkSmsValue(User user, String verificationcode) {
		super.checkSmsValue(user, verificationcode);
		verificationCodeSend.checkRegisterKey(user.getIphone(), verificationcode);
	}
	
}
