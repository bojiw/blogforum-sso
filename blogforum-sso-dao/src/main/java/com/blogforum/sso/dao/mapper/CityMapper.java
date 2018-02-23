package com.blogforum.sso.dao.mapper;

import java.util.List;

import com.blogforum.sso.pojo.entity.City;

public interface CityMapper extends CrudMapper<City> {

	/**
	 * 查询所有省
	 * @return
	 * @author: wwd
	 * @time: 2018年2月19日
	 */
	List<City>  queryProvinceAll();

	/**
	 * 根据省id查询该省市级地址
	 * @param parentId
	 * @return
	 * @author: wwd
	 * @time: 2018年2月19日
	 */
	List<City> queryByParentId(Integer parentId);
	
	
	/**
	 * 根据数组名称查询
	 * 
	 * @param name
	 * @return
	 * @author: wwd
	 * @time: 2018年2月20日
	 */
	List<City> getByNames(String[] names);

}
