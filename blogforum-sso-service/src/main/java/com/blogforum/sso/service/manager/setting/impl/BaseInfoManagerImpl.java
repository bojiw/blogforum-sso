package com.blogforum.sso.service.manager.setting.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.blogforum.common.tools.LoggerUtil;
import com.blogforum.common.tools.ObjectUtils;
import com.blogforum.common.tools.blogforumResult;
import com.blogforum.sso.common.exception.SSOBusinessException;
import com.blogforum.sso.dao.redis.RedisClient;
import com.blogforum.sso.pojo.entity.City;
import com.blogforum.sso.pojo.entity.User;
import com.blogforum.sso.pojo.vo.BaseInfoUI;
import com.blogforum.sso.service.dao.CityService;
import com.blogforum.sso.service.dao.UserService;
import com.blogforum.sso.service.manager.setting.BaseInfoManager;
import com.google.common.collect.Lists;

@Component
public class BaseInfoManagerImpl implements BaseInfoManager {
	
	private final static Logger	logger	= LoggerFactory.getLogger(BaseInfoManagerImpl.class);

	@Autowired
	private CityService cityService;
	
	@Autowired
	private UserService userService;
	
	/** session过期时间 */
	@Value("${myValue.session_time}")
	protected int			SESSION_TIME;
	
	/** session开头key */
	@Value("${myValue.session_key}")
	protected String		SESSION_KEY;
	
	/** redis客户端 */
	@Autowired
	protected RedisClient	redisClient;
	
	@Override
	public blogforumResult getBaseInfo(String userId) {
		User user = userService.getById(userId);
		List<City> provinceAll = cityService.queryProvinceAll();
		BaseInfoUI infoUI = new BaseInfoUI(user);
		infoUI.setCitys(provinceAll);
		
		return blogforumResult.ok(infoUI);
	}

	@Override
	public blogforumResult updateBaseInfo(BaseInfoUI baseInfoUI) {
		checkBaseInfo(baseInfoUI);
		User user = userService.getById(baseInfoUI.getId());
		if (ObjectUtils.isObjAllNull(user)) {
			throw new SSOBusinessException("找不到对应用户");
		}
		updateUserBaseInfo(user, baseInfoUI);
		userService.updateBaseInfo(user);
		updateSession(baseInfoUI, user);
		return blogforumResult.ok();
	}
	
	/**
	 * 更新session信息
	 * @param baseInfoUI
	 * @param user
	 * @author: wwd
	 * @time: 2018年2月20日
	 */
	private void updateSession(BaseInfoUI baseInfoUI,User user){
		StringBuffer newToken = new StringBuffer();
		newToken.append(SESSION_KEY).append(":").append(baseInfoUI.getToken());
		redisClient.setExpire(newToken.toString(), user, SESSION_TIME);
	}
	
	/**
	 * 效验基本信息
	 * @param baseInfoUI
	 * @author: wwd
	 * @time: 2018年2月20日
	 */
	private void checkBaseInfo(BaseInfoUI baseInfoUI){
		String[] temp = {baseInfoUI.getProvinceLocation(), baseInfoUI.getBirthCity(),
						baseInfoUI.getBirthProvince(),baseInfoUI.getCityLocation()};
		if (StringUtils.isBlank(baseInfoUI.getUsername())) {
			throw new SSOBusinessException("用户名不能为空");
		}
		
		
		List<String> names = Lists.newArrayList();
		//把不为空的省市名称放入到names中查询
		for (String name : temp) {
			if (StringUtils.isNotEmpty(name)) {
				names.add(name);
			}
		}
		//如果省市都没填 则不用对省市进行效验
		if (names.size() == 0 ) {
			return;
		}
		
		//判断前端传入的省市名数量和能在数据库中查询到的记录数是否一样 如果不一样则代表前端传入的数据有问题
		List<City> citys = cityService.getByNames(names.toArray(new String[0]));
		if (citys.size() != names.size()) {
			LoggerUtil.error(logger, "数据库查询出来的数据,citys:{0},前端传入数据,names:{1}",JSON.toJSONString(citys),JSON.toJSONString(names));
			throw new SSOBusinessException("传入城市信息错误");
		}
		
	}
	

	/**
	 * 填充新的基本信息
	 * @param user
	 * @param baseInfoUI
	 * @author: wwd
	 * @time: 2018年2月20日
	 */
	private void updateUserBaseInfo(User user,BaseInfoUI baseInfoUI){
		user.setUsername(baseInfoUI.getUsername());
		user.setIphone(baseInfoUI.getIphone());
		user.setEmail(baseInfoUI.getEmail());
		user.setImage(baseInfoUI.getImage());
		user.setRemarks(baseInfoUI.getRemarks());
		user.setProvinceLocation(baseInfoUI.getProvinceLocation());
		user.setCityLocation(baseInfoUI.getCityLocation());
		user.setBirthProvince(baseInfoUI.getBirthProvince());
		user.setBirthCity(baseInfoUI.getBirthCity());
	}

	@Override
	public blogforumResult getCitys(String cityParentId) {
		List<City> citys = cityService.queryByParentId(cityParentId);
		return blogforumResult.ok(citys);
	}

}
