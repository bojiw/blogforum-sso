package com.blogforum.sso.dao.mapper;



import com.blogforum.sso.pojo.entity.User;
import com.blogforum.sso.pojo.vo.UserDateIn;

public interface UserMapper extends CrudMapper<User> {
	/**
	 * 根据用户名密码获取用户
	 * @param user
	 * @return
	 * @author: wwd
	 * @time: 2017年11月8日
	 */
	User getUserByPwdName(User user);
	
	/**
	 * 根据邮件或者手机号获取用户
	 * @param uesr
	 * @return
	 * @author: wwd
	 * @time: 2017年11月8日
	 */
	User getUserByEmailORIphone(User uesr);
	
	
	/**
	 * 根据用户名邮件或者手机号获取用户
	 * @param uesr
	 * @return
	 * @author: wwd
	 * @time: 2017年11月8日
	 */
	User getUserByNameOREmailORIphone(User uesr);
	
	

	/**
	 * 支持用户名 邮箱 手机号登录 只要密码和上面中的其中一个对的上就可以获取到用户
	 * @param user
	 * @return
	 * @author: wwd
	 * @time: 2018年3月1日
	 */
	User getUserByPwdNameOREmailORIphone(User user);
	
	/**
	 * 根据用户名获取用户
	 * @param user
	 * @return
	 * @author: wwd
	 * @time: 2017年11月8日
	 */
	User getUserByName(User user);
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 * @author: wwd
	 * @time: 2018年2月20日
	 */
	User getById(String id);
	
	
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
	 * 获取对应状态用户总数
	 * 
	 * @return 总数
	 * @author: wwd
	 * @time: 2018年3月3日
	 */
	Integer getCount(Integer status);
	
	/**
	 * 获取指定时间段的用户数
	 * 
	 * @param userDateIn
	 * @return
	 * @author: wwd
	 * @time: 2018年3月3日
	 */
	Integer getDateInUser(UserDateIn userDateIn);
	
	/**
	 * 修改状态
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
