package com.blogforum.sso.web.aspect;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.blogforum.common.tools.IpUtils;
import com.blogforum.common.tools.LoggerUtil;
/**
 * aop打印http日志类
 * @author wwd
 *
 */
@Aspect
@Component
public class HttpAspect {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(HttpAspect.class);

	@Pointcut("execution(public * com.blogforum.sso.web.controller.*.*(..))")
	public void log(){
	}
	
	/**
	 * 获取请求日志
	 */
	@Before("log()")
	public void doBefore(){
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		//获取真实ip
		String ip = IpUtils.getIp(request);
		LoggerUtil.info(LOGGER, "请求ip地址为：{0}",ip);
		LoggerUtil.info(LOGGER, "入参为：{0}",JSON.toJSONString(request.getParameterMap()));
		LoggerUtil.info(LOGGER, "请求路径为：{0}",request.getRequestURI());
	}
	

	/**
	 * 获取返回日志
	 */
	@AfterReturning(returning = "object",pointcut = "log()")
	public void doAfterReturning(Object object){
		String jsonString = JSON.toJSONString(object);
		LoggerUtil.info(LOGGER, "调用成功,返回参数:{0}",jsonString.length() < 600 ? jsonString : "返回参数长度大于600");
	}
	
}
