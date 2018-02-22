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
	
	
	
}
