package com.blogforum.sso.service.dao;

import com.blogforum.sso.facade.model.SsoPage;
import com.blogforum.sso.facade.model.SsoUserPageRequest;
import com.blogforum.sso.facade.model.UserVO;
import com.blogforum.sso.pojo.entity.User;
import com.blogforum.sso.service.base.BaseService;

public interface UserService extends BaseService<User> {

	void createUser(User user);
	
	/**
	 * 更新用户基本信息
	 * @param user
	 * @author: wwd
	 * @time: 2018年2月20日
	 */
	void updateBaseInfo(User user);
	
	/**
	 * 更新用户密码
	 * @param user
	 * @author: wwd
	 * @time: 2018年2月20日
	 */
	void updatePwd(User user);
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 * @author: wwd
	 * @time: 2018年2月20日
	 */
	User getById(String id);
	
	/**
	 * 根据邮件或者手机号获取用户
	 * @param uesr
	 * @return
	 * @author: wwd
	 * @time: 2017年11月8日
	 */
	User getUserByEmailORIphone(User uesr);
	
	/**
	 * 分页查询用户
	 * @param request
	 * @return
	 * @author: wwd
	 * @time: 2018年3月3日
	 */
	SsoPage<UserVO> queryAllUserPage(SsoUserPageRequest request);
	
	/**
	 * 更新用户信息
	 * @param user
	 * @author: wwd
	 * @time: 2018年3月24日
	 */
	void updateStatus(User user);
	
	
	
}
