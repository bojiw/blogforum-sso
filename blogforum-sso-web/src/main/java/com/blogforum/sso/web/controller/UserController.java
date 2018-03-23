package com.blogforum.sso.web.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogforum.common.tools.blogforumResult;
import com.blogforum.sso.facade.model.SsoPage;
import com.blogforum.sso.facade.model.SsoUserPageRequest;
import com.blogforum.sso.facade.model.UserVO;
import com.blogforum.sso.pojo.entity.User;
import com.blogforum.sso.service.dao.UserService;
import com.blogforum.sso.service.loginregistration.LoginRegisterContext;
import com.blogforum.sso.service.loginregistration.LoginRegisterFactory;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private LoginRegisterFactory	loginRegisterFactory;

	@PostMapping("/loginregister")
	public blogforumResult loginregister(String cmCode, User user, String verificationCode,
						HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		//设置上下文
		LoginRegisterContext context = new LoginRegisterContext();
		context.setUser(user);
		context.setVerificationCode(verificationCode);
		context.setHttpServletRequest(httpServletRequest);
		context.setHttpServletResponse(httpServletResponse);
		return loginRegisterFactory.getManager(cmCode).execute(context);
	}
	
	@Autowired
	private UserService userService;
	

	@GetMapping("getCount")
	public blogforumResult getCount(){
		//测试
//        Calendar c = Calendar.getInstance();
//        
//        //过去七天
//        c.setTime(new Date());
//        c.add(Calendar.DATE, - 306);
//        Date startDate = c.getTime();
//		UserDateIn userDateIn = new UserDateIn(startDate,null, 0);
		SsoUserPageRequest request = new SsoUserPageRequest();
		request.setPageSize(1);
		SsoPage<UserVO> queryAllUserPage = userService.queryAllUserPage(request);
		
		return blogforumResult.ok(queryAllUserPage);
		
	}

}
