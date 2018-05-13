package com.blogforum.sso.service.dao.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogforum.common.tools.BaseConverter;
import com.blogforum.sso.common.enums.SsoMsgExchangeNameEnum;
import com.blogforum.sso.common.utils.MD5SaltUtils;
import com.blogforum.sso.dao.mapper.UserMapper;
import com.blogforum.sso.facade.enums.UserStatusEnum;
import com.blogforum.sso.facade.model.SsoPage;
import com.blogforum.sso.facade.model.SsoUserPageRequest;
import com.blogforum.sso.facade.model.UserVO;
import com.blogforum.sso.pojo.entity.User;
import com.blogforum.sso.service.base.CrudService;
import com.blogforum.sso.service.dao.UserService;
import com.blogforum.sso.service.rabbitmq.producer.SendMqMessage;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;


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
		// 给用户的密码进行加密
		String salt = MD5SaltUtils.randomCreateSalt();
		String encodePWD = MD5SaltUtils.encode(user.getPassword(), salt);
		user.setSalt(salt);
		user.setPassword(encodePWD);
		user.updateDate();
		user.setUpdateUser(user.getUpdateUser());
		userMapper.updatePwd(user);
		
	}

	@Override
	public User getById(String id) {
		User user = userMapper.getById(id);
		return user;
	}


	@Override
	public SsoPage<UserVO> queryAllUserPage(SsoUserPageRequest request) {
		Integer pageNo = request.getPageNo();
		Integer pageSize = request.getPageSize();
		PageHelper.startPage(pageNo, pageSize);
		User user = new User();
		UserStatusEnum status = request.getStatus();
		Integer userstatus =  (status == null) ? null : status.getValue();
		user.setStatus(userstatus);
		if (StringUtils.isNotEmpty(request.getKeyword())) {
			user.setUsername(request.getKeyword());
		}
		
		List<User> users = userMapper.queryList(user);
		Page<User> pageUser = (Page<User>)users;
		BaseConverter<User, UserVO> converter = new BaseConverter<>();
		List<UserVO> convertList = converter.convertList(users, UserVO.class);
		SsoPage<UserVO> ssoPage = new SsoPage<>(pageNo, pageSize, pageUser.getTotal());
		ssoPage.setList(convertList);
		return ssoPage;
	}

	@Override
	public void updateStatus(User user) {
		user.updateDate();
		userMapper.updateStatus(user);
		
	}

	@Override
	public User getAllStatus(String id) {
		return userMapper.getAllStatus(id);
	}


	/**
	 * 数据库的密码和前端传过来的密码是否一样
	 * @param dbUser
	 * @param uiUser
	 * @param checkPassword 为false代表不对密码做效验
	 * @return
	 * @author: wwd
	 * @time: 2018年5月13日
	 */
	private Boolean passwordEquals(User dbUser,User uiUser,Boolean checkPassword){
		if (checkPassword) {
			//使用salt对用户上传的密码进行加密再重新设置
			String encodePWD = MD5SaltUtils.encode(uiUser.getPassword(), dbUser.getSalt());
			return StringUtils.equals(dbUser.getPassword(), encodePWD);
		}
		return true;
		
	}

	@Override
	public User getUserByNameOREmailORIphoneAndPwd(User user, Boolean checkPassword) {
		User userByName = userMapper.getUserByName(user);
		if (userByName != null && passwordEquals(userByName, user,checkPassword)) {
			return userByName;
		}
		User userByIphone = userMapper.getUserByIphone(user);
		if (userByIphone != null && passwordEquals(userByName, user,checkPassword)) {
			return userByIphone;
		}
		User userByEmail = userMapper.getUserByEmail(user);
		if (userByEmail != null && passwordEquals(userByName, user,checkPassword)) {
			return userByEmail;
		}
		return null;
	}

}
