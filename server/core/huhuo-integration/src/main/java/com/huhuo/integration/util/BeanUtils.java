package com.huhuo.integration.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.huhuo.integration.exception.HuhuoException;

public class BeanUtils extends org.apache.commons.beanutils.BeanUtils {
	
	/**
	 * Convert map to bean of type.
	 * 
	 * @param type
	 * @param map
	 * @return java bean.
	 */
	@SuppressWarnings("rawtypes")
	public static <T> T toBean(Class<T> type, Map map) {
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(type);
			T obj = type.newInstance();

			PropertyDescriptor[] propertyDescriptors = beanInfo
					.getPropertyDescriptors();
			for (int i = 0; i < propertyDescriptors.length; i++) {
				PropertyDescriptor descriptor = propertyDescriptors[i];
				String propertyName = descriptor.getName();

				if (map.containsKey(propertyName)) {
					Object value = map.get(propertyName);

					Object[] args = new Object[1];
					if(descriptor.getPropertyType().isAssignableFrom(Boolean.class)){
						if(value instanceof Integer){
							if(!value.equals(0)){
								value = true;
							}else{
								value = false;
							}
						}
					}
					args[0] = value;

					descriptor.getWriteMethod().invoke(obj, args);
				}
			}
			return obj;
		} catch (Exception e) {
			throw new HuhuoException(e);//TODO
		}
	}
	
	/**
	 * Convert every element in list to bean of type. 
	 * 
	 * @param type
	 * @param list
	 * @return list of bean.
	 */
	public static <T> List<T> toBean(Class<T> type, List<Map<String, Object>> list){
		List<T> r = new ArrayList<T>();
		for (Map<String, Object> map: list) {
			r.add(toBean(type, map));
		}
		
		return r;
	}
	
}

