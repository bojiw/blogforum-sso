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
	 * 通过用户名或邮箱或手机号获取用户 如果checkPassword为true 还需要对比下密码是否一样 不一样获取用户为空
	 * @param user
	 * @param checkPassword
	 * @return
	 * @author: wwd
	 * @time: 2018年5月13日
	 */
	User getUserByNameOREmailORIphoneAndPwd(User user,Boolean checkPassword);
	
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
	
	/**
	 * 获取所有状态用户
	 * @param id
	 * @return
	 * @author: wwd
	 * @time: 2018年3月25日
	 */
	User getAllStatus(String id);
	
	
}
