package com.blogforum.sso.service.dao;

import com.blogforum.sso.pojo.entity.User;
import com.blogforum.sso.service.base.BaseService;

public interface UserService extends BaseService<User> {

	public void createUser(User user);
	
	/**
	 * 更新用户基本信息
	 * @param user
	 * @author: wwd
	 * @time: 2018年2月20日
	 */
	public void updateBaseInfo(User user);
	
	/**
	 * 更新用户密码
	 * @param user
	 * @author: wwd
	 * @time: 2018年2月20日
	 */
	public void updatePwd(User user);
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 * @author: wwd
	 * @time: 2018年2月20日
	 */
	public User getById(String id);
	
	/**
	 * 根据邮件或者手机号获取用户
	 * @param uesr
	 * @return
	 * @author: wwd
	 * @time: 2017年11月8日
	 */
	public User getUserByEmailORIphone(User uesr);
	
	
	
}
