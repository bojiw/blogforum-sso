package com.blogforum.sso.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogforum.common.tools.blogforumResult;
import com.blogforum.sso.service.manager.ModifyPasswordManager;

@RestController
@RequestMapping("/modify")
public class ModifyPasswordController {
	
	@Autowired
	private ModifyPasswordManager modifyPasswordManager;
	
	@PostMapping("/sendIphoneVerification")
	public blogforumResult sendIphoneVerification(String iphone){
		modifyPasswordManager.sendIphoneVerfication(iphone);
		return blogforumResult.ok();
	}
	
	@PostMapping("/sendEmailVerification")
	public blogforumResult sendEmailVerification(String email){
		modifyPasswordManager.sendEmailVerfication(email);
		return blogforumResult.ok();
	}
	
	@PostMapping("/updateIphonePassword")
	public blogforumResult updateIphonePassword(String iphone,String verfication,String password){
		modifyPasswordManager.updateIphonePassword(iphone, verfication, password);
		return blogforumResult.ok();
	}

	@PostMapping("/updateEmailPassword")
	public blogforumResult updateEmailPassword(String email,String verfication,String password){
		modifyPasswordManager.updateEamilPassword(email, verfication, password);
		return blogforumResult.ok();
	}
	
	
	
	

}
