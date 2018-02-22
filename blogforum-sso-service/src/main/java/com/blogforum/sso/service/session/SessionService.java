package com.blogforum.sso.service.session;

import com.blogforum.sso.pojo.entity.User;

public interface SessionService {

	/**
	 * 获取session中的用户 并且更新session过期时间
	 * @param token
	 * @return
	 * @author: wwd
	 * @time: 2018年2月20日
	 */
	User getSessionUser(String token);
	
}
