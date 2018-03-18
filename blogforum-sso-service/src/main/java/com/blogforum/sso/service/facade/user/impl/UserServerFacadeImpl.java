package com.blogforum.sso.service.facade.user.impl;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.blogforum.common.enums.BizErrorEnum;
import com.blogforum.common.model.ErrorContext;
import com.blogforum.common.model.Result;
import com.blogforum.common.tools.BaseConverter;
import com.blogforum.sso.dao.mapper.UserMapper;
import com.blogforum.sso.facade.enums.UserStatusEnum;
import com.blogforum.sso.facade.model.SsoPage;
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
		User user = userService.getById(userId);
		BaseConverter<User, UserVO> converter = new BaseConverter<>();
		UserVO userVO = converter.convert(user, UserVO.class);
		return new Result<UserVO>(true, userVO);
	}

	@Override
	public Result<SsoPage<UserVO>> querySearchAllUserPage(SsoUserPageRequest request) {
		SsoPage<UserVO> page = userService.queryAllUserPage(request);
		Result<SsoPage<UserVO>> result = new Result<SsoPage<UserVO>>(true, page);
		return result;
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
			return new Result<Integer>(false, new ErrorContext(BizErrorEnum.ILLEGAL_PARAMETER, "status不可为空"), 0);
		}
		UserDateIn userDateIn = new UserDateIn(startDate, endDate, status.getValue());
		
		Integer count = userMapper.getDateInUser(userDateIn);
		
		Result<Integer> result = new Result<Integer>(true, count);
		return result;
	}

}
