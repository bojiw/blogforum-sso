package com.blogforum.sso.pojo.entity;

public class City extends DataEntity<City> {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	/** id */
	private int					id;
	/** 行政代码 */
	private String				code;
	/** 名称 */
	private String				name;
	/** 父id */
	private int					parentId;
	/** 首字母 */
	private String				firstLetter;
	/** 城市等级0 1 2 */
	private int					level;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getFirstLetter() {
		return firstLetter;
	}

	public void setFirstLetter(String firstLetter) {
		this.firstLetter = firstLetter;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}
