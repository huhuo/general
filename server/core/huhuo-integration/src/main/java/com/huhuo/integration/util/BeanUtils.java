package com.huhuo.integration.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.springframework.aop.framework.Advised;
import org.springframework.util.ReflectionUtils;

import com.huhuo.integration.base.IBaseModel;
import com.huhuo.integration.exception.HuhuoException;
import com.huhuo.integration.exception.UtilException;

public class BeanUtils extends org.apache.commons.beanutils.BeanUtils {
	
	public static class GetterSetter{
		public String propertyName;
		public Method getter;
		public Method setter;
	}
	
	private static Map<Class<?>, GetterSetter[]> getterSetterMap = new HashMap<Class<?>, GetterSetter[]>();
	
	public static <T> boolean isProxy(final IBaseModel<T> modelBean){
		return modelBean instanceof Advised;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> boolean isTransientField(Class<T> clazz, final String propertyName) {
		Field propertyField = null;
		while (true) {
			try {
				propertyField = clazz.getDeclaredField(propertyName);
				break;
			} catch (final NoSuchFieldException nsfe) {
				clazz = (Class<T>) clazz.getSuperclass();
				if (clazz.equals(Object.class))
					break;
				continue;
			}
		}
		if (propertyField != null) {
			return Modifier.isTransient(propertyField.getModifiers());
		}
		return false;
	}
	
	/**
	 * Return true if one of more than one of propertyName's annotations in annotationClazzList, return false if other case.
	 * 
	 * @param clazz
	 * @param propertyName
	 * @param annotationClazzList
	 * @return
	 */
	public static <T> boolean isAnnotaionIn(Class<T> clazz, final String propertyName, List<Class<? extends Annotation>> annotationClazzList){
		Field f = getDeclaredField(clazz, propertyName);
		if(f!=null && annotationClazzList!=null){
			for(Class<? extends Annotation> annotaionClazz: annotationClazzList){
				boolean isAnnotation = f.isAnnotationPresent(annotaionClazz);
				if(isAnnotation) return true;
			}
		}
		
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> Field getDeclaredField(Class<T> clazz, final String propertyName){
		Field propertyField = null;
		while (true) {
			try {
				propertyField = clazz.getDeclaredField(propertyName);
				break;
			} catch (final NoSuchFieldException nsfe) {
				clazz = (Class<T>) clazz.getSuperclass();
				if (clazz.equals(Object.class))
					break;
				continue;
			}
		}
		return propertyField;
	}
	
	/**
	 * 
	 * @param beanClazz whose property info(getter, setter) will be returned. 
	 * @param excludeAnnotationClazzList none of property's annotations is in excludeAnnotationClazzList.
	 * @return {@link GetterSetter} array of beanClazz
	 * @throws UtilException-if {@link Introspector#getBeanInfo(Class)} throws IntrospectionException
	 */
	public static <T> GetterSetter[] getGetterSetter(final Class<T> beanClazz, List<Class<? extends Annotation>> excludeAnnotationClazzList){
		GetterSetter[] getterSetterArr = getterSetterMap.get(beanClazz);
		if(getterSetterArr!=null){
			return getterSetterArr;
		}
		BeanInfo beanInfo = null;
		try{
//			if(BaseModel.class.isAssignableFrom(beanClazz))
//				beanInfo = Introspector.getBeanInfo(beanClazz, BaseModel.class);
//			else
//				beanInfo = Introspector.getBeanInfo(beanClazz);
			beanInfo = Introspector.getBeanInfo(beanClazz);
		} catch (final IntrospectionException ex) {
			throw new UtilException(ex);
		}
		final PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();	
		Method setter, getter;
		final List<GetterSetter> ret = new ArrayList<GetterSetter>();
		for(final PropertyDescriptor element : pds){
			final String propertyName = element.getName();
			if(isTransientField(beanClazz, propertyName) || isAnnotaionIn(beanClazz, propertyName, excludeAnnotationClazzList)){
				continue;
			}
			getter = element.getReadMethod();
			setter = element.getWriteMethod();
			if((getter!=null)&&(setter!=null)&& (getter.getParameterTypes().length==0)&&(setter.getParameterTypes().length==1)){
				final GetterSetter getterSetter = new GetterSetter();
				getterSetter.getter = getter;
				getterSetter.setter = setter;
				getterSetter.propertyName = propertyName;
				ret.add(getterSetter);
			}
		}
		getterSetterMap.put(beanClazz, getterSetterArr=ret.toArray(new GetterSetter[ret.size()])); 
		return getterSetterArr;
	}
	
	public static <T> GetterSetter[] getGetterSetter(final Class<T> beanClazz, Class<? extends Annotation> excludeAnnotationClazz){
		List<Class<? extends Annotation>> exAnnotationClazzList = new ArrayList<Class<? extends Annotation>>();
		exAnnotationClazzList.add(excludeAnnotationClazz);
		return getGetterSetter(beanClazz, exAnnotationClazzList);
	}
	public static Object invokeMethod(final Method method, final Object target) {
		return ReflectionUtils.invokeMethod(method, target);
	}

	public static Object invokeMethod(final Method method, final Object target,
			final Object[] args) {
		return ReflectionUtils.invokeMethod(method, target, args);
	}
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

