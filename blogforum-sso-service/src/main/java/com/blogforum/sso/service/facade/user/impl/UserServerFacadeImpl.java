package com.blogforum.sso.service.facade.user.impl;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.blogforum.common.enums.BizErrorEnum;
import com.blogforum.common.model.ErrorContext;
import com.blogforum.common.model.Result;
import com.blogforum.common.tools.BaseConverter;
import com.blogforum.common.tools.ObjectUtils;
import com.blogforum.sso.common.exception.SSOBusinessException;
import com.blogforum.sso.dao.mapper.UserMapper;
import com.blogforum.sso.facade.enums.UserStatusEnum;
import com.blogforum.sso.facade.model.SsoPage;
import com.blogforum.sso.facade.model.SsoUpdateUserPwd;
import com.blogforum.sso.facade.model.SsoUpdateUserStatus;
import com.blogforum.sso.facade.model.SsoUserPageRequest;
import com.blogforum.sso.facade.model.UserVO;
import com.blogforum.sso.facade.user.UserServerFacade;
import com.blogforum.sso.pojo.entity.User;
import com.blogforum.sso.pojo.vo.UserDateIn;
import com.blogforum.sso.service.dao.UserService;

public class UserServerFacadeImpl implements UserServerFacade {
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserService userService;
	
	@Override
	public Result<UserVO> getUserByUserId(String userId) {
		User user = userService.getAllStatus(userId);
		BaseConverter<User, UserVO> converter = new BaseConverter<>();
		UserVO userVO = converter.convert(user, UserVO.class);
		return new Result<UserVO>(true, userVO);
	}

	@Override
	public Result<SsoPage<UserVO>> querySearchAllUserPage(SsoUserPageRequest request) {
		checkPageRequest(request);
		SsoPage<UserVO> page = userService.queryAllUserPage(request);
		Result<SsoPage<UserVO>> result = new Result<SsoPage<UserVO>>(true, page);
		return result;
	}
	
	/**
	 * 对分页请求参数做效验
	 * @param request
	 * @author: wwd
	 * @time: 2018年3月23日
	 */
	private void checkPageRequest(SsoUserPageRequest request){
		if (ObjectUtils.isObjAllNull(request)) {
			throw new SSOBusinessException(BizErrorEnum.ILLEGAL_PARAMETER,"请求对象为null");
		}
		if (ObjectUtils.isObjAllNull(request.getPageSize(),request.getPageNo())) {
			throw new SSOBusinessException(BizErrorEnum.ILLEGAL_PARAMETER,"分页参数不能为null");
		}
		
	}

	@Override
	public Result<Integer> getAllUserCount(UserStatusEnum delFlag) {
		Integer count = userMapper.getCount((delFlag ==null) ? null : delFlag.getValue());
		Result<Integer> result = new Result<Integer>(true, count);
		return result;
	}



	@Override
	public Result<Integer> getDateInUser(Date startDate, Date endDate, UserStatusEnum status) {
		if (status == null) {
			return new Result<Integer>(false, new ErrorContext(BizErrorEnum.ILLEGAL_PARAMETER, "获取指定时间段用户status不可为空"), 0);
		}
		UserDateIn userDateIn = new UserDateIn(startDate, endDate, status.getValue());
		
		Integer count = userMapper.getDateInUser(userDateIn);
		
		Result<Integer> result = new Result<Integer>(true, count);
		return result;
	}

	@Override
	public Result<Boolean> updateUserPwd(SsoUpdateUserPwd updatePwd) {
		if (updatePwd == null) {
			throw new SSOBusinessException(BizErrorEnum.ILLEGAL_PARAMETER,"更新用户密码入参updatePwd不可为空");
		}
		
		if (ObjectUtils.isStringAllNull( updatePwd.getNewPassword(),updatePwd.getUserId(),updatePwd.getUpdateUser())) {
			throw new SSOBusinessException(BizErrorEnum.ILLEGAL_PARAMETER,"更新用户密码入参字段不可为空");
		}
		
		User user = new User();
		user.setId(updatePwd.getUserId());
		user.setPassword(updatePwd.getNewPassword());
		user.setUpdateUser(updatePwd.getUpdateUser());
		userService.updatePwd(user);
		return new Result<Boolean>(true, true);
	}

	@Override
	public Result<Boolean> updateUserStatus(SsoUpdateUserStatus updateStatus) {
		if (updateStatus == null) {
			throw new SSOBusinessException(BizErrorEnum.ILLEGAL_PARAMETER,"更新用户状态入参updateStatus不可为空");
		}
		if (updateStatus.getStatus() == null) {
			throw new SSOBusinessException(BizErrorEnum.ILLEGAL_PARAMETER,"更新用户状态入参字段status不可为空");

		}
		if (ObjectUtils.isStringAllNull(updateStatus.getUserId(),updateStatus.getUserId())) {
			throw new SSOBusinessException(BizErrorEnum.ILLEGAL_PARAMETER,"更新用户状态入参字段不可为空");
		}
		
		User user = new User();
		user.setId(updateStatus.getUserId());
		user.setUpdateUser(updateStatus.getUpdateUser());
		user.setStatus(updateStatus.getStatus().getValue());
		userService.updateStatus(user);
		
		return new Result<Boolean>(true, true);
	}

}
