package com.blogforum.sso.service.loginregistration;

import org.springframework.stereotype.Component;

import com.blogforum.common.tools.blogforumResult;
import com.blogforum.sso.pojo.entity.User;

@Component
public class SendMailRegister extends AbstractLoginRegister {


	@Override
	public blogforumResult execute(LoginRegisterContext context) {
		User user = context.getUser();
		//效验参数
		checkMailValue(user, "0000");
		verificationCodeSend.SendEmailVerificationCode(user.getEmail(), "欢迎注册博记系统账户");
		
		return blogforumResult.ok();

	}

}
