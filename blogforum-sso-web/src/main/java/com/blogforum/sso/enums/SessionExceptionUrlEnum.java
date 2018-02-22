package com.blogforum.sso.enums;

import org.apache.commons.lang3.StringUtils;

public enum SessionExceptionUrlEnum {

	Index("/"),
	REGISTER("/register.html"),
	MAILREGISTER("/mailregister.html"),
	ERROR("/404.html"),
	LOGINREGISTER("/user/loginregister");
	private String url;

	private SessionExceptionUrlEnum(String url) {
		this.url = url;
	}

	public static boolean isException(String url) {
		for (SessionExceptionUrlEnum session : values()) {
			if (StringUtils.equals(session.getUrl(), url)) {
				return true;
			}
		}
		return false;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
