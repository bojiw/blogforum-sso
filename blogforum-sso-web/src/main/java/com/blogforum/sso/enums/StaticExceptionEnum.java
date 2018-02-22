package com.blogforum.sso.enums;

public enum StaticExceptionEnum {

	JS(".js"),
	CSS(".css"),
	JPG(".jpg"),
	ICON(".icon");
	private String url;

	private StaticExceptionEnum(String url) {
		this.url = url;
	}

	public static boolean isException(String url) {
		for (StaticExceptionEnum staticEnum : values()) {
			if (url.contains(staticEnum.getUrl())) {
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
