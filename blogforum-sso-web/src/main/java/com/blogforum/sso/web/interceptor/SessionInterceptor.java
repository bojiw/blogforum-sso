package com.blogforum.sso.web.interceptor;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.blogforum.common.tools.CookieUtils;
import com.blogforum.common.tools.LoggerUtil;
import com.blogforum.sso.enums.SessionExceptionUrlEnum;
import com.blogforum.sso.pojo.entity.User;
import com.blogforum.sso.service.session.SessionService;

/**
 * 登录检测拦截器
 * @author wwd
 *
 */
@Component
public class SessionInterceptor implements HandlerInterceptor {
	
	private final static Logger	logger	= LoggerFactory.getLogger(SessionInterceptor.class);
	
	
	/** session开头key */
	@Value("${myValue.session_key}")
	protected String			SESSION_KEY;

	@Value("${myValue.ssoServerUrl}")
	protected String			ssoUrl;

	@Autowired
	private SessionService		sessionService;


	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
						throws Exception {

		//如果不在拦截例外列表里 则判断用户是否登录
		String url = request.getServletPath();
		if (!(SessionExceptionUrlEnum.isException(url))) {
			//获取cookie中的token
			String token = CookieUtils.getCookie(request, "COOKIE_TOKEN");
			//获取redis中保存的session信息
			User user = sessionService.getSessionUser(token);
			//如果用户未登录 跳转到登录页面
			if (null == user) {
				
				// 判断是否ajax请求
				if (!(request.getHeader("accept").indexOf("application/json") > -1 || (request
									.getHeader("X-Requested-With") != null && request.getHeader(
														"X-Requested-With").indexOf("XMLHttpRequest") > -1))) {
					//页面请求返回跳转提示
					loginAgain(request, response);
				}else {
					ajaxLoginAgain(response);
				}
				
				return false;
			}
			request.setAttribute("user", user);

		}
	
		//不进行拦截
		return true;
	}
	
	/**
	 * 返回登录页面地址 前端直接跳转
	 * 
	 * @author: wwd
	 * @time: 2018年2月24日
	 */
	private void ajaxLoginAgain(HttpServletResponse response){
		//ajax请求返回登录地址
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(ssoUrl);
		} catch (IOException e) {
			LoggerUtil.error(logger, e, "跳转登录页面异常");
		}
	}
	
	
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
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
						ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
						throws Exception {
		// TODO Auto-generated method stub

	}

}
