package com.blogforum.sso.pojo.vo;

import java.util.List;

import com.blogforum.sso.pojo.entity.City;
import com.blogforum.sso.pojo.entity.User;

public class BaseInfoUI extends User {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1181096429459888190L;

	/**
	 * 城市信息
	 */
	private List<City>			citys;

	/**
	 * token
	 */
	private String				token;

	public BaseInfoUI() {
	}

	public BaseInfoUI(User user) {
		this.setId(user.getId());
		this.setUsername(user.getUsername());
		this.setEmail(user.getEmail());
		this.setIphone(user.getIphone());
		this.setRemarks(user.getRemarks());
		this.setImage(user.getImage());
		this.setProvinceLocation(user.getProvinceLocation());
		this.setCityLocation(user.getCityLocation());
		this.setBirthProvince(user.getBirthProvince());
		this.setBirthCity(user.getBirthCity());
	}

	public List<City> getCitys() {
		return citys;
	}

	public void setCitys(List<City> citys) {
		this.citys = citys;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
