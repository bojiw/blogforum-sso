package com.blogforum.sso.service.loginregistration;

import org.springframework.stereotype.Component;

import com.blogforum.common.tools.blogforumResult;
import com.blogforum.sso.pojo.entity.User;

@Component
public class SendSmsRegister extends AbstractLoginRegister {
	

	@Override
	public blogforumResult execute(LoginRegisterContext context) {
		User user = context.getUser();
		checkSmsValue(user, "0000");
		verificationCodeSend.SendIphoneVerificationCode(user.getIphone());
		return blogforumResult.ok();
	}

}
