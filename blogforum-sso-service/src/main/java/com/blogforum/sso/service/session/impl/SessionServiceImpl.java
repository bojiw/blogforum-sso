package com.blogforum.sso.service.session.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.blogforum.sso.dao.redis.RedisClient;
import com.blogforum.sso.pojo.entity.User;
import com.blogforum.sso.service.session.SessionService;

@Service
public class SessionServiceImpl implements SessionService {
	
	/** redis客户端 */
	@Autowired
	protected RedisClient redisClient;

	/** session开头key */
	@Value("${myValue.session_key}")
	protected String			SESSION_KEY;
	
	/** session过期时间 */
	@Value("${myValue.session_time}")
	protected int			SESSION_TIME;

	@Override
	public User getSessionUser(String token) {
		//获取redis中保存的session信息
		StringBuffer session = new StringBuffer(SESSION_KEY).append(":").append(token);
		String userString = redisClient.get(session.toString());
		if (StringUtils.isEmpty(userString)) {
			return null;
		}
		User user = JSON.parseObject(userString, User.class);
		//更新session过期时间
		redisClient.expire(session.toString(), SESSION_TIME);
		return user;
	}

}
