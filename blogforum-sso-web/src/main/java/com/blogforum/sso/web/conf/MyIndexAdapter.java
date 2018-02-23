package com.blogforum.sso.web.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.blogforum.sso.web.interceptor.SessionInterceptor;
@Configuration
public class MyIndexAdapter extends WebMvcConfigurerAdapter {

	@Autowired
	private SessionInterceptor sessionInterceptor;
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController( "/" ).setViewName( "forward:/login.html" );
        registry.setOrder( Ordered.HIGHEST_PRECEDENCE );
        super.addViewControllers( registry );
	}
	
	//注册过滤器
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		super.addInterceptors(registry);
		registry.addInterceptor(sessionInterceptor).addPathPatterns("/**");
	}
	
	
}
