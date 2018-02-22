package com.blogforum.sso.web.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.blogforum.sso.service.session.SessionService;
import com.blogforum.sso.web.filter.SessionFilter;
@Configuration
@EnableAutoConfiguration
public class MyIndexAdapter extends WebMvcConfigurerAdapter {

	/** session开头key */
	@Value("${myValue.session_key}")
	protected String			SESSION_KEY;

	@Value("${myValue.ssoServerUrl}")
	protected String			ssoUrl;

	@Autowired
	private SessionService		sessionService;
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController( "/" ).setViewName( "forward:/login.html" );
        registry.setOrder( Ordered.HIGHEST_PRECEDENCE );
        super.addViewControllers( registry );
	}
	
	
	
	@Bean
	public SessionFilter sessionFilter(){
		SessionFilter sessionFilter = new SessionFilter();
		sessionFilter.setSESSION_KEY(SESSION_KEY);
		sessionFilter.setSessionService(sessionService);
		sessionFilter.setSsoUrl(ssoUrl);
		return sessionFilter;
	}
	
	
	
}
