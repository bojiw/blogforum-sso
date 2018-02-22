package com.blogforum.sso.integration.docking.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blogforum.common.model.Result;
import com.blogforum.common.tools.rpc.BaseInvocation;
import com.blogforum.common.tools.rpc.ServiceTemplate;
import com.blogforum.docking.facade.qiniuyun.QiNiuYunFacade;
import com.blogforum.sso.integration.docking.QiNiuYunFacadeClick;

@Component
public class QiNiuYunFacadeClickImpl implements QiNiuYunFacadeClick {


	@Autowired
	private QiNiuYunFacade qiNiuYunFacade;
	
	
	@Override
	public String getUpToken() {
		
		return ServiceTemplate.invoke(new BaseInvocation<String>() {
			@Override
			public Result<String> execute() {
				return qiNiuYunFacade.getUpToken();
			}
		});
	}

}
