package com.blogforum.sso.service.manager.impl;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.blogforum.sso.common.enums.SSOBizError;
import com.blogforum.sso.common.exception.SSOBusinessException;
import com.blogforum.sso.common.utils.Preconditions;
import com.blogforum.sso.dao.redis.RedisClient;
import com.blogforum.sso.pojo.entity.City;
import com.blogforum.sso.pojo.entity.User;
import com.blogforum.sso.pojo.vo.BaseInfoUI;
import com.blogforum.sso.service.dao.CityService;
import com.blogforum.sso.service.dao.UserService;
import com.blogforum.sso.service.manager.BaseInfoManager;
import com.google.common.collect.Lists;

@Component
public class BaseInfoManagerImpl implements BaseInfoManager {

	private final static Logger	logger	= LoggerFactory.getLogger(BaseInfoManagerImpl.class);

	@Autowired
	private CityService			cityService;

	@Autowired
	private UserService			userService;

	/** session过期时间 */
	@Value("${myValue.session_time}")
	protected int				SESSION_TIME;

	/** session开头key */
	@Value("${myValue.session_key}")
	protected String			SESSION_KEY;

	/** redis客户端 */
	@Autowired
	protected RedisClient		redisClient;

	@Override
	public blogforumResult getBaseInfo(String userId) {
		User user = userService.getById(userId);
		List<City> provinceAll = cityService.queryProvinceAll();
		BaseInfoUI infoUI = new BaseInfoUI(user);
		infoUI.setCitys(provinceAll);
		//如果所在省不为空 则把对应的城市返回给前端
		List<City> cityLocations = buildCitys(user.getProvinceLocation(), infoUI);
		infoUI.setCityLocations(cityLocations);
		//如果出生省不为空 则把对应的城市返回给前端
		List<City> birthCitys = buildCitys(user.getBirthProvince(), infoUI);
		infoUI.setBirthCitys(birthCitys);
		return blogforumResult.ok(infoUI);
	}
	
	
	/**
	 * 通过省名获取对应的城市列表
	 * @param province
	 * @param infoUI
	 * @return
	 * @author: wwd
	 * @time: 2018年2月24日
	 */
	private List<City> buildCitys(String province,BaseInfoUI infoUI){
		List<City> cityLocations = Lists.newArrayList();
		if (StringUtils.isNotBlank(province)) {
			List<City> provinces = cityService.getByNames(province);
			//因为省排在第一个 所以获取第一个
			cityLocations = cityService.queryByParentId(provinces.get(0).getId());
		}
		return cityLocations;
	}
	
	

	@Override
	public blogforumResult updateBaseInfo(BaseInfoUI baseInfoUI) {
		User user = userService.getById(baseInfoUI.getId());
		if (ObjectUtils.isObjAllNull(user)) {
			throw new SSOBusinessException("找不到对应用户");
		}
		checkBaseInfo(baseInfoUI,user);
		updateUserBaseInfo(user, baseInfoUI);
		userService.updateBaseInfo(user);
		updateSession(baseInfoUI, user);
		return blogforumResult.ok();
	}

	/**
	 * 更新session信息
	 * 
	 * @param baseInfoUI
	 * @param user
	 * @author: wwd
	 * @time: 2018年2月20日
	 */
	private void updateSession(BaseInfoUI baseInfoUI, User user) {
		StringBuffer newToken = new StringBuffer();
		newToken.append(SESSION_KEY).append(":").append(baseInfoUI.getToken());
		redisClient.setExpire(newToken.toString(), user, SESSION_TIME);
	}

	/**
	 * 效验基本信息
	 * 
	 * @param baseInfoUI
	 * @author: wwd
	 * @time: 2018年2月20日
	 */
	private void checkBaseInfo(BaseInfoUI baseInfoUI,User user) {
		Preconditions.checkNotNull(baseInfoUI.getUsername(), "用户名不能为空");
		//对前端传的所在地省市进行效验
		checkCity(baseInfoUI.getProvinceLocation(), baseInfoUI.getCityLocation());
		//对前端传的出生省市进行效验
		checkCity(baseInfoUI.getBirthProvince(), baseInfoUI.getBirthCity());
		String iphone = baseInfoUI.getIphone();
		String email = baseInfoUI.getEmail();
		if (StringUtils.isNotEmpty(iphone)) {
			checkIphone(iphone,user.getIphone());
		}
		if (StringUtils.isNotEmpty(email)) {
			checkEmail(email,user.getEmail());
		}

	}
	
	
	/**
	 * 对手机号进行效验
	 * @param iphone
	 * @author: wwd
	 * @time: 2018年2月25日
	 */
	private void checkIphone(String iphone,String userIphone){
		//效验手机号
		String regExp = "^1[34578]\\d{9}$";  
		Pattern p = Pattern.compile(regExp);  
		Matcher m = p.matcher(iphone); 
		if (!m.matches()) {
			LoggerUtil.error(logger, "手机号:{0}", iphone);
			throw new SSOBusinessException("手机号格式不对");
		}
		//如果前端传过来的手机号和数据库中的用户手机号一样 代表没有修改 不用效验
		if (StringUtils.equals(userIphone, iphone)) {
			return;
		}
		User user = new User();
		user.setIphone(iphone);
		User newUser = userService.getUserByNameOREmailORIphoneAndPwd(user,false);
		Preconditions.checkNull(newUser, SSOBizError.IPHONE_ISREGISTER);
	}
	
	/**
	 * 对邮箱进行效验
	 * @param iphone
	 * @author: wwd
	 * @time: 2018年2月25日
	 */
	private void checkEmail(String email,String userEmail){
		//效验手机号
		String regExp = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";  
		Pattern p = Pattern.compile(regExp);  
		Matcher m = p.matcher(email); 
		if (!m.matches()) {
			LoggerUtil.error(logger, "邮箱:{0}", email);
			throw new SSOBusinessException("邮箱格式不对");
		}
		//如何前端传过来的邮箱号和数据库中的邮箱号一样 代表邮箱号没有换不需要做验证
		if (StringUtils.equals(userEmail, email)) {
			return;
		}
		User user = new User();
		user.setEmail(email);
		User newUser = userService.getUserByNameOREmailORIphoneAndPwd(user,false);
		Preconditions.checkNull(newUser, SSOBizError.EMAIL_ISREGISTER);
	}
	
	
	/**
	 * 验证省市是否正确
	 * @param province
	 * @param city
	 * @author: wwd
	 * @time: 2018年2月23日
	 */
	private void checkCity(String province ,String city){
		if (StringUtils.isNotEmpty(province)) {
			List<City> citys = cityService.getByNames(province, city);
			//因为数据库中存在相同名 比如北京市有两个 所以只要大于等于2条数据就可以
			if (citys.size() < 2) {
				LoggerUtil.error(logger, "数据库查询出来的数据,citys:{0},前端传入数据,names:{1}", JSON.toJSONString(citys),
									province, city);
				throw new SSOBusinessException("传入城市信息错误");
			}
		}
	}
	
	

	/**
	 * 填充新的基本信息
	 * 
	 * @param user
	 * @param baseInfoUI
	 * @author: wwd
	 * @time: 2018年2月20日
	 */
	private void updateUserBaseInfo(User user, BaseInfoUI baseInfoUI) {
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
	public blogforumResult getCitys(Integer cityParentId) {
		List<City> citys = cityService.queryByParentId(cityParentId);
		return blogforumResult.ok(citys);
	}

}
