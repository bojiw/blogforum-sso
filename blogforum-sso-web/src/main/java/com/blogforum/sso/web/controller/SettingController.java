package com.blogforum.sso.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogforum.common.tools.CookieUtils;
import com.blogforum.common.tools.blogforumResult;
import com.blogforum.sso.pojo.entity.User;
import com.blogforum.sso.pojo.vo.BaseInfoUI;
import com.blogforum.sso.service.manager.setting.BaseInfoManager;

@RestController
@RequestMapping("/setting")
public class SettingController {

	@Autowired
	private BaseInfoManager			baseInfoManager;
	
	@GetMapping("/getBaseInfo")
	public blogforumResult getBaseInfo(HttpServletRequest request) {
		User user = (User) request.getAttribute("user");
		blogforumResult baseInfo = baseInfoManager.getBaseInfo(user.getId());
		return baseInfo;
	}
	
	@GetMapping("/getCitys")
	public blogforumResult getCitys(HttpServletRequest request,String cityParentId) {
		blogforumResult citys = baseInfoManager.getCitys(Integer.valueOf(cityParentId));
		return citys;
	}
	
	@PostMapping("/updateBaseInfo")
	public blogforumResult updateBaseInfo(HttpServletRequest request,BaseInfoUI baseInfoUI) {
		User user = (User) request.getAttribute("user");
		baseInfoUI.setId(user.getId());
		baseInfoUI.setToken(CookieUtils.getCookie(request, "COOKIE_TOKEN"));
		blogforumResult citys = baseInfoManager.updateBaseInfo(baseInfoUI);
		return citys;
	}
	
	
	
	
}
