package com.blogforum.sso.facade.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 用户状态枚举
 * @author wwd
 *
 */
public enum RoleEnum {
	
	/**普通用户*/
	USUAL_USER(0,"普通用户"),
	/**会员用户*/
	VIP_USER(1,"会员用户");
	private Integer	value;
	private String	chinese;

	private RoleEnum(Integer value, String chinese) {
		this.value = value;
		this.chinese = chinese;
	}
	
	public static RoleEnum getRoleByValue(Integer value){
		for (RoleEnum roleEnum : values()) {
			if (roleEnum.getValue() == value) {
				return roleEnum;
			}
		}
		return null;
	}
	
	
	public static String getChinesByValue(Integer value){
		for (RoleEnum roleEnum : values()) {
			if (value == roleEnum.getValue()) {
				return roleEnum.getChinese();
			}
		}
		return "";
	}
	
	public static String getChinesByChinese(String chinese){
		for (RoleEnum roleEnum : values()) {
			if (StringUtils.equals(chinese, roleEnum.getChinese())) {
				return roleEnum.getChinese();
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
