package com.blogforum.sso.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogforum.common.tools.blogforumResult;
import com.blogforum.sso.integration.docking.QiNiuYunFacadeClick;

@RestController
@RequestMapping("/qiniuyun")
public class QiNiuYunController {

	@Autowired
	private QiNiuYunFacadeClick qiNiuYunFacadeClick;
	
	
	@GetMapping("/getUpToken")
	public blogforumResult getBaseInfo(HttpServletRequest request) {
		String upToken = qiNiuYunFacadeClick.getUpToken();
		return blogforumResult.ok(upToken);
	}
	
}
