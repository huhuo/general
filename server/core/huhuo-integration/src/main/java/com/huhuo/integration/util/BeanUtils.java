package com.huhuo.integration.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.PropertyUtilsBean;

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
	/**
	 * <p>Copy property values from the origin bean to the destination bean
     * for all cases where the property names of orig is not null</p>
     * <p>For more details see <code>BeanUtilsBean</code>.</p>
     * @see #copyProperties(Object, Object)
	 * @param dest
	 * @param orig
	 * @param nullOverride
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings("rawtypes")
	public static void copyProperties(Object dest, Object orig, boolean nullOverride)
			throws IllegalAccessException, InvocationTargetException {
		if(nullOverride) {
			copyProperties(dest, orig);
		} else {
			// Validate existence of the specified beans
			PropertyUtilsBean propertyUtils = BeanUtilsBean.getInstance().getPropertyUtils();
	        if (dest == null) {
	            throw new IllegalArgumentException("No destination bean specified");
	        }
	        if (orig == null) {
	            throw new IllegalArgumentException("No origin bean specified");
	        }
	        // Copy the properties, converting as necessary
	        if (orig instanceof DynaBean) {
	            DynaProperty origDescriptors[] = ((DynaBean) orig).getDynaClass().getDynaProperties();
	            for (int i = 0; i < origDescriptors.length; i++) {
	                String name = origDescriptors[i].getName();
	                if (propertyUtils.isWriteable(dest, name)) {
	                    Object value = ((DynaBean) orig).get(name);
	                    if(value != null) {
	                    	copyProperty(dest, name, value);
	                    }
	                }
	            }
	        } else if (orig instanceof Map) {
				Iterator names = ((Map) orig).keySet().iterator();
	            while (names.hasNext()) {
	                String name = (String) names.next();
	                if (propertyUtils.isWriteable(dest, name)) {
	                    Object value = ((Map) orig).get(name);
	                    if(value != null) {
	                    	copyProperty(dest, name, value);
	                    }
	                }
	            }
	        } else /* if (orig is a standard JavaBean) */ {
	            PropertyDescriptor origDescriptors[] =
	            		propertyUtils.getPropertyDescriptors(orig);
	            for (int i = 0; i < origDescriptors.length; i++) {
	                String name = origDescriptors[i].getName();
	                if ("class".equals(name)) {
	                    continue; // No point in trying to set an object's class
	                }
	                if (propertyUtils.isReadable(orig, name) &&
	                		propertyUtils.isWriteable(dest, name)) {
	                    try {
	                        Object value = propertyUtils.getSimpleProperty(orig, name);
	                        if(value != null) {
		                    	copyProperty(dest, name, value);
		                    }
	                    } catch (NoSuchMethodException e) {
	                        ; // Should not happen
	                    }
	                }
	            }
	        }
		}
	}
	
}

