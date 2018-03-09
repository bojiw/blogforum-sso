package com.blogforum.sso.facade.user;


import java.util.Date;

import com.blogforum.common.model.Result;
import com.blogforum.sso.facade.enums.UserStatusEnum;
import com.blogforum.sso.facade.model.SsoPage;
import com.blogforum.sso.facade.model.SsoUserPageRequest;
import com.blogforum.sso.facade.model.UserVO;

/**
 * 用户接口
 * @author wwd
 *
 */
public interface UserServerFacade {
	
	/**
	 * 根据userId获取用户
	 * @param token
	 * @return
	 * @author: wwd
	 * @time: 2017年11月4日
	 */
	Result<UserVO>  getUserByUserId (String userId);
	
	

	/**
	 * 分页获取用户
	 * @param request
	 * @return
	 * @author: wwd
	 * @time: 2018年3月3日
	 */
	Result<SsoPage<UserVO>> queryAllUserPage(SsoUserPageRequest request);
	
	

	/**
	 * 获取对应状态用户总数或者所有用户总数
	 * @param delFlag 如果为空获取所有状态用户总数
	 * @return
	 * @author: wwd
	 * @time: 2018年3月3日
	 */
	Result<Integer> getAllUserCount(UserStatusEnum status);
	
	
	/**
	 * 获取指定时间段的用户数
	 * @param staterDate 开始时间
	 * @param endDate 结束时间
	 * @param delFlag 如果为空获取所有在线和删除用户总数
	 * @return
	 * @author: wwd
	 * @time: 2018年3月3日
	 */
	Result<Integer> getDateInUser(Date startDate,Date endDate,UserStatusEnum status);
	
	
}
