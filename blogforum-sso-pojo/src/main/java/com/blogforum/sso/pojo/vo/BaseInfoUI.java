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
	 * 省列表
	 */
	private List<City>			citys;

	/**
	 * 所在城市列表
	 */
	private List<City>			cityLocations;

	/**
	 * 出生城市列表
	 */
	private List<City>			birthCitys;

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

	public List<City> getCityLocations() {
		return cityLocations;
	}

	public void setCityLocations(List<City> cityLocations) {
		this.cityLocations = cityLocations;
	}

	public List<City> getBirthCitys() {
		return birthCitys;
	}

	public void setBirthCitys(List<City> birthCitys) {
		this.birthCitys = birthCitys;
	}

}
