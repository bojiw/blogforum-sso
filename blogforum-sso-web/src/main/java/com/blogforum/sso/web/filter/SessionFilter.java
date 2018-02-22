package com.blogforum.sso.web.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.blogforum.common.tools.CookieUtils;
import com.blogforum.common.tools.LoggerUtil;
import com.blogforum.sso.enums.SessionExceptionUrlEnum;
import com.blogforum.sso.enums.StaticExceptionEnum;
import com.blogforum.sso.pojo.entity.User;
import com.blogforum.sso.service.session.SessionService;

/**
 * 访问过滤器
 * 
 * @author wwd
 *
 */
@WebFilter(urlPatterns = "/*", filterName = "sessionFilter")
@Component
public class SessionFilter extends OncePerRequestFilter {

	private final static Logger	logger	= LoggerFactory.getLogger(SessionFilter.class);

	/** session开头key */
	@Value("${myValue.session_key}")
	protected String			SESSION_KEY;

	@Value("${myValue.ssoServerUrl}")
	protected String			ssoUrl;

	@Autowired
	private SessionService		sessionService;



	/**
	 * 返回用戶為登錄提醒 跳轉到登錄頁面
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @author wwd
	 * @date 2017年3月12日上午12:50:39
	 * @version V1.0
	 */
	private void loginAgain(HttpServletRequest request, HttpServletResponse response) {
		String loginPage = ssoUrl;
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			StringBuilder builder = new StringBuilder();
			// 因为请求直接拦截 浏览器默认是GBK 所以需要设置返回页面的编码为utf-8作统一 防止有些浏览器GBK 有些UTF-8
			builder.append("<html>");
			builder.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
			builder.append("<title>Insert title here</title>");
			builder.append("<script>");
			builder.append("alert('网页过期，请重新登录！');");
			builder.append("window.location.href='");
			builder.append(loginPage);
			builder.append("';");
			builder.append("</script>");
			builder.append("</html>");
			out.print(builder.toString());
		} catch (IOException e) {
			LoggerUtil.error(logger, e, "跳转登录页面异常");
		}

	}

	@Override
	protected void doFilterInternal(HttpServletRequest httpRequest, HttpServletResponse httpResponse, FilterChain filterChain)
						throws ServletException, IOException {
		//如果不在拦截例外列表里或者不在静态列表里 则判断用户是否登录
		String url = httpRequest.getServletPath();
		if (!(SessionExceptionUrlEnum.isException(url) ||
						StaticExceptionEnum.isException(url))) {
			//获取cookie中的token
			String token = CookieUtils.getCookie(httpRequest, "COOKIE_TOKEN");
			LoggerUtil.error(logger, "--------------------------------");
			LoggerUtil.error(logger, "url" + ssoUrl);
			LoggerUtil.error(logger, "session" + SESSION_KEY);
			if (sessionService == null ) {
				LoggerUtil.error(logger, "sessionService为空");
			}
			
			//获取redis中保存的session信息
			User user = sessionService.getSessionUser(token);
			//如果用户未登录 跳转到登录页面
			if (null == user) {
				loginAgain(httpRequest, httpResponse);
				return;
			}
			httpRequest.setAttribute("user", user);

		}
		//执行业务逻辑
		filterChain.doFilter(httpRequest, httpResponse);
		
	}


}
