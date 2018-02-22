package com.blogforum.sso.service.base;

import java.util.List;

/**
 * Service层基类
 * 
 * @author Edward
 * 
 * @param <T>
 * @param <ID>
 */
public interface BaseService<T> {
	void save(T t);

	T getById(T t);
	
	T get(T t);
	
	List<T> queryList(T t);
	
	void update(T t);

	public void delete(T t);
	
}
