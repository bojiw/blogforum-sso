package com.blogforum.sso.facade.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * rpc传输用户类
 * 
 * @author wwd
 *
 */
public class UserVO extends BaseVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2700217512116572732L;
	/**
	 * 编号
	 */
	private String	id;
	/**
	 * 登录用户名
	 */
	private String	username;

	/**
	 * 角色
	 */
	private int		role;
	/**
	 * Email邮件
	 */
	private String	email;
	/**
	 * 手机号
	 */
	private String	iphone;

	/**
	 * 最后登录时间
	 */
	private Date	loginDate;
	/**
	 * 最后登录ip
	 */
	private String	loginIp;
	/**
	 * 状态 0为正常 1为无法登录 2为删除状态
	 */
	private int		status	= 0;
	/**
	 * 头像路径
	 */
	private String	image;
	/**
	 * 备注信息
	 */
	private String	remarks;
	/**
	 * 所在省份
	 */
	private String	provinceLocation;
	/**
	 * 所在城市
	 */
	private String	cityLocation;
	/**
	 * 出生省份
	 */
	private String	birthProvince;
	/**
	 * 出生城市
	 */
	private String	birthCity;
	
	// 创建时间
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createDate; 

	// 最后修改时间
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateDate; 
	// 最后修改人
	private String updateUser; 

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIphone() {
		return iphone;
	}

	public void setIphone(String iphone) {
		this.iphone = iphone;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getProvinceLocation() {
		return provinceLocation;
	}

	public void setProvinceLocation(String provinceLocation) {
		this.provinceLocation = provinceLocation;
	}

	public String getCityLocation() {
		return cityLocation;
	}

	public void setCityLocation(String cityLocation) {
		this.cityLocation = cityLocation;
	}

	public String getBirthProvince() {
		return birthProvince;
	}

	public void setBirthProvince(String birthProvince) {
		this.birthProvince = birthProvince;
	}

	public String getBirthCity() {
		return birthCity;
	}

	public void setBirthCity(String birthCity) {
		this.birthCity = birthCity;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	
	


}
