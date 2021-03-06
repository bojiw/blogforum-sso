package com.blogforum.sso;

import java.util.Properties;

import javax.sql.DataSource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageHelper;

@SpringBootApplication
@ServletComponentScan
@EnableTransactionManagement //开启事务
@MapperScan("com.blogforum.sso.dao.mapper")
public class Starter extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(Starter.class, args);
	}

	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(Starter.class);
	}
	
	/**
	 * druid连接数据库的密码进行了加密 为了解密 需要在这里设置 不然直接返回new DruidDataSource();就可以了
	 * @return
	 */
	@Bean
	@ConfigurationProperties(prefix="spring.datasource")
	public DataSource druidDataSource(){
		DruidDataSource dataSource = new DruidDataSource();
		//设置密码解密
		dataSource.setConnectionProperties("config.decrypt=true");
		return dataSource;
	}
	
	//配置mybatis的分页插件pageHelper
    @Bean
    public PageHelper pageHelper(){
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("offsetAsPageNum","true");
        properties.setProperty("rowBoundsWithCount","true");
        properties.setProperty("reasonable","true");
        properties.setProperty("dialect","mysql");    //配置mysql数据库的方言
        pageHelper.setProperties(properties);
        return pageHelper;
    }
	
	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer() {

	    return new EmbeddedServletContainerCustomizer() {
	        @Override
	        public void customize(ConfigurableEmbeddedServletContainer container) {

	            ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/401.html");
	            ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404.html");
	            ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500.html");

	            container.addErrorPages(error401Page, error404Page, error500Page);
	        }
	    };
	}
	
}
