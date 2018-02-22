package com.blogforum.sso.service.manager.setting;

import com.blogforum.common.tools.blogforumResult;
import com.blogforum.sso.pojo.vo.BaseInfoUI;

public interface BaseInfoManager {

	/**
	 * 获取个人设置的基本信息
	 * @return
	 * @author: wwd
	 * @time: 2018年2月19日
	 */
	blogforumResult getBaseInfo(String userId);
	
	/**
	 * 更新个人设置的基本信息
	 * @param baseInfoUI
	 * @return
	 * @author: wwd
	 * @time: 2018年2月19日
	 */
	blogforumResult updateBaseInfo(BaseInfoUI baseInfoUI);
	
	/**
	 * 获取当前省id对应的城市
	 * @param cityParentId
	 * @return
	 * @author: wwd
	 * @time: 2018年2月19日
	 */
	blogforumResult getCitys(String cityParentId);
	
}
