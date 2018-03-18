package com.blogforum.sso.facade.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 用户状态枚举
 * @author wwd
 *
 */
public enum UserStatusEnum {
	
	/**正常状态*/
	ACTIVE(0,"正常状态"),
	/**无法登录状态*/
	NO_LOGIN(1,"无法登录状态"),
	/**删除状态 */
	DEL(2,"删除状态");
	private Integer	value;
	private String	chinese;

	private UserStatusEnum(Integer value, String chinese) {
		this.value = value;
		this.chinese = chinese;
	}
	
	public static UserStatusEnum getStatusByValue(Integer value){
		for (UserStatusEnum statusEnum : values()) {
			if (statusEnum.getValue() == value) {
				return statusEnum;
			}
		}
		return null;
	}
	
	public static String getChineseByValue(Integer value){
		for (UserStatusEnum statusEnum : values()) {
			if (statusEnum.getValue() == value) {
				return statusEnum.getChinese();
			}
		}
		return "";
	}
	
	public static String getChineseByChinese(String chinese){
		for (UserStatusEnum statusEnum : values()) {
			if (StringUtils.equals(chinese, statusEnum.getChinese())) {
				return statusEnum.getChinese();
			}
		}
		return "";
	}
	

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getChinese() {
		return chinese;
	}

	public void setChinese(String chinese) {
		this.chinese = chinese;
	}
	
	
	

}
