package com.blogforum.sso.service.loginregistration;

import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.blogforum.common.enums.BizErrorEnum;
import com.blogforum.common.tools.CookieUtils;
import com.blogforum.common.tools.UUIDCreateUtils;
import com.blogforum.sso.common.enums.SSOBizError;
import com.blogforum.sso.common.utils.MD5SaltUtils;
import com.blogforum.sso.common.utils.Preconditions;
import com.blogforum.sso.dao.mapper.UserMapper;
import com.blogforum.sso.dao.redis.RedisClient;
import com.blogforum.sso.pojo.entity.User;
import com.blogforum.sso.service.constant.ServiceConstant;
import com.blogforum.sso.service.dao.UserService;
import com.blogforum.sso.service.verification.VerificationCodeSend;

public abstract class AbstractLoginRegister implements LoginRegister {

	/** redis客户端 */
	@Autowired
	protected RedisClient	redisClient;

	/** 用户表dao */
	@Autowired
	protected UserMapper	userMapper;
	/** 用户服务 */
	@Autowired
	protected UserService	userService;

	/** session过期时间 */
	@Value("${myValue.session_time}")
	protected int			SESSION_TIME;

	/** 注册时保存到redis的值 */
	@Value("${myValue.register_key}")
	protected String		REGISTER_KEY;

	/** session开头key */
	@Value("${myValue.session_key}")
	protected String		SESSION_KEY;

	@Value("${myValue.domain}")
	protected String		DOMAIN;
	
	/** 发送验证码服务 */
	@Autowired
	protected VerificationCodeSend verificationCodeSend;

	/** note笔记系统地址 */
	@Value("${myValue.noteServerUrl}")
	protected String		noteServerUrl;
	
	/**用户默认头像路径*/
	@Value("${myValue.defaulUserImageUrl}")
	private String			defaulUserImageUrl;


	/**
	 * 效验用户名密码是否为空
	 * 
	 * @param user
	 * @author: Administrator
	 * @time: 2017年7月16日
	 */
	protected void checkUserPwd(User user) {
		Preconditions.checkNotNull(user.getUsername(), BizErrorEnum.NULL_USERPWD);
		Preconditions.checkNotNull(user.getPassword(), BizErrorEnum.NULL_USERPWD);

	}

	private void checkUserAndVeCode(User user, String verificationcode) {
		Preconditions.checkNotNull(verificationcode, SSOBizError.VECODE_NOTNULL);
		User newUser = userMapper.getUserByName(user);
		Preconditions.checkNull(newUser, SSOBizError.USER_ISREGERSTER);
	}

	/**
	 * 效验通过邮件注册的参数
	 * 
	 * @param user
	 * @author: Administrator
	 * @time: 2017年7月16日
	 */
	protected void checkMailValue(User user, String verificationcode) {
		checkUserPwd(user);
		checkUserAndVeCode(user, verificationcode);
		Preconditions.checkNotNull(user.getEmail(), SSOBizError.EMAIL_NOTNULL);
		User newuser = userService.getUserByNameOREmailORIphoneAndPwd(user,false);
		Preconditions.checkNull(newuser, SSOBizError.EMAIL_ISREGISTER);
	}

	/**
	 * 效验通过手机注册的参数
	 * 
	 * @param user
	 * @author: Administrator
	 * @time: 2017年7月16日
	 */
	protected void checkSmsValue(User user, String verificationcode) {
		checkUserPwd(user);
		checkUserAndVeCode(user, verificationcode);
		Preconditions.checkNotNull(user.getIphone(), SSOBizError.IPHONE_NOTNULL);
		User newUser = userService.getUserByNameOREmailORIphoneAndPwd(user,false);
		Preconditions.checkNull(newUser, SSOBizError.IPHONE_ISREGISTER);
	}

	/**
	 * 填充用户并且设置token
	 * 
	 * @param user
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 * @author: wwd
	 * @time: 2017年7月16日
	 */
	protected void buildUserAndSessionCookie(User user, HttpServletResponse httpServletResponse) {

		user.setId(UUIDCreateUtils.getUUID());
		// 给用户的密码进行加密
		String salt = MD5SaltUtils.randomCreateSalt();
		String encodePWD = MD5SaltUtils.encode(user.getPassword(), salt);
		user.setSalt(salt);
		user.setPassword(encodePWD);
		user.setCreateDate(new Date());
		user.setUpdateDate(new Date());
		user.setImage(defaulUserImageUrl);
		user.setRole(0);
		SessionCookie(user, httpServletResponse);
	}

	protected void SessionCookie(User user, HttpServletResponse httpServletResponse) {
		String token = UUIDCreateUtils.getUUID();
		StringBuffer newToken = new StringBuffer();
		newToken.append(SESSION_KEY).append(":").append(token);
		redisClient.setExpire(newToken.toString(), user, SESSION_TIME);
		CookieUtils.setCookie(httpServletResponse, ServiceConstant.cookieToken, token, "/", DOMAIN);
	}

}
