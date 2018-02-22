package com.blogforum.sso.service.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogforum.common.tools.BaseConverter;
import com.blogforum.sso.dao.mapper.UserMapper;
import com.blogforum.sso.facade.enums.SsoMsgExchangeNameEnum;
import com.blogforum.sso.facade.model.UserVO;
import com.blogforum.sso.pojo.entity.User;
import com.blogforum.sso.service.base.CrudService;
import com.blogforum.sso.service.dao.UserService;
import com.blogforum.sso.service.rabbitmq.producer.SendMqMessage;


@Service
public class UserServiceImpl  extends CrudService<User> implements UserService {

	private final static Logger LOGGER =  LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private SendMqMessage sendMqMessage;
	
	@Override
	public void createUser(User user) {
		//1、保存用户信息到user表中
		userMapper.save(user);
		//2、删除密码相关
		user.setPassword(null);
		user.setSalt(null);
		//3、发送用户创建消息到消息中心 
		try {
			BaseConverter<User, UserVO> converter = new BaseConverter<>();
			UserVO userVO = converter.convert(user, UserVO.class);
			sendMqMessage.sendMsg(userVO,SsoMsgExchangeNameEnum.SSO_FANOUT_INIT_USER);
		} catch (Exception e) {
			LOGGER.error("发送消息到消息中间件失败,user：" + user.toString(),e);
		}
		
	}

	@Override
	public void updateBaseInfo(User user) {
		userMapper.updateBaseInfo(user);
		
	}

	@Override
	public void updatePwd(User user) {
		userMapper.updatePwd(user);
		
	}

	@Override
	public User getById(String id) {
		User user = userMapper.getById(id);
		return user;
	}


}
