package com.blogforum.sso.service.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogforum.sso.dao.mapper.CityMapper;
import com.blogforum.sso.pojo.entity.City;
import com.blogforum.sso.service.base.CrudService;
import com.blogforum.sso.service.dao.CityService;

@Service
public class CityServiceImpl extends CrudService<City> implements CityService  {

	@Autowired
	private CityMapper cityMapper;
	
	
	@Override
	public List<City> queryProvinceAll() {
		List<City> citys = cityMapper.queryProvinceAll();
		return citys;
	}

	@Override
	public List<City> queryByParentId(String parentId) {
		List<City> citys = cityMapper.queryByParentId(parentId);
		return citys;
	}

	@Override
	public List<City> getByNames(String[] names) {
		return cityMapper.getByNames(names);
	}


}
