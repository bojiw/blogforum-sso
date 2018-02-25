package com.blogforum.sso.common.utils;

import org.apache.commons.lang3.StringUtils;

import com.blogforum.common.enums.BizErrorEnum;
import com.blogforum.sso.common.enums.SSOBizError;
import com.blogforum.sso.common.exception.SSOBusinessException;
/**
 * 包装谷歌的Preconditions类返回自定义异常
 * @author Administrator
 *
 */
public class Preconditions {
	
	
	/**
	 * 检查是否为空 为空则抛异常
	 * @param obj
	 * @param msg
	 */
	public static void checkNotNull(Object obj,String msg){
		
		try {
			checkNotNull(obj);
		} catch (Exception e) {
			throw new SSOBusinessException(msg);
		}
	}
	
	
	private static void checkNotNull(Object obj){
		if (obj instanceof String) {
			if (StringUtils.isBlank(obj.toString())) {
				throw new RuntimeException();
			}
		}else {
			com.google.common.base.Preconditions.checkNotNull(obj);
		}
	}
	
	/**
	 * 检查是否为空 为空则抛异常
	 * @param obj
	 * @param msg
	 */
	public static void checkNotNull(Object obj,BizErrorEnum bizError){
		
		try {
			checkNotNull(obj);
		} catch (Exception e) {
			throw new SSOBusinessException(bizError);
		}
	}
	/**
	 * 检查是否为空 为空则抛异常
	 * @param obj
	 * @param msg
	 */
	public static void checkNotNull(Object obj,SSOBizError bizError){
		try {
			checkNotNull(obj);
		} catch (Exception e) {
			throw new SSOBusinessException(bizError);
		}
	}
	
	/**
	 * 检查是否为空 为空则抛异常
	 * @param obj
	 * @param msg
	 */
	public static void checkNull(Object obj,String msg){
		try {
			checkNull(obj);
		} catch (Exception e) {
			throw new SSOBusinessException(msg);
		}
	}
	
	

	/**
	 * 检查是否为空 不为空则抛异常
	 * @param obj
	 * @param msg
	 */
	public static void checkNull(Object obj,BizErrorEnum bizError){
		try {
			checkNull(obj);
		} catch (Exception e) {
			throw new SSOBusinessException(bizError);
		}
	}

	/**
	 * 检查是否为空 不为空则抛异常
	 * @param obj
	 * @param msg
	 */
	public static void checkNull(Object obj,SSOBizError bizError){
		try {
			checkNull(obj);
		} catch (Exception e) {
			throw new SSOBusinessException(bizError);
		}
	}
	
	
	private static void checkNull(Object obj){
		if (obj instanceof String) {
			if (StringUtils.isNotBlank(obj.toString())) {
				throw new RuntimeException();
			}
		}else {
			if (obj != null) {
				throw new RuntimeException();
			}
		}
	}
	
}
