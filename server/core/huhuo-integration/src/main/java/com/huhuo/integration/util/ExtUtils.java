package com.huhuo.integration.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.huhuo.integration.web.JsonStore;

public class ExtUtils {
	
	/**
	 * @see #getJsonStore(List, Long)
	 * @param list
	 * @param count
	 * @return
	 */
	public static <E> Map<String, Object> getMap(List<E> list, Long count){
		Map<String, Object> r = new LinkedHashMap<String, Object>();
		r.put("records", list);
		r.put("total", count);
		return r;
	}
	
	/**
	 * construct a JSON store object(used frequently in ExtJs)
	 * @param records
	 * @param total
	 * @return
	 */
	public static <T> JsonStore<T> getJsonStore(List<T> records, Long total){
		JsonStore<T> store = new JsonStore<T>(records, total);
		return store;
	}
	/**
	 * @see #getJsonStore(List, Long)
	 */
	public static <T> JsonStore<T> getJsonStore(List<T> records, Integer total){
		JsonStore<T> store = new JsonStore<T>(records, total);
		return store;
	}
}
