package com.huhuo.integration.util;

public class CheckUtils {

	/**
	 * obj 是否为null值
	 * @param obj
	 * @return
	 */
	public static boolean isNull(Object obj) {
		return obj == null;
	}
	
	/**
	 * objs是否全为null值
	 * @param objs
	 * @return
	 */
	public static boolean isAllNull(Object... objs) {
		boolean isAllNull = true;
		for(Object obj : objs) {
			isAllNull = isAllNull && isNull(obj);
		}
		return isAllNull;
	}
	
	/**
	 * objs是否全不为null值
	 * @param objs
	 * @return
	 */
	public static boolean isNoNull(Object... objs) {
		boolean r = true;
		for(Object obj : objs) {
			r = r && !isNull(obj);
		}
		return r;
	}
	
	public static boolean validLength(int min, int max, Object obj){
		if(obj!=null){
			String r = obj.toString();
			int len = r.length();
			if(len>=min && len<=max){
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean validLength(int min, int max, Object...objs){
		boolean r = true;
		for(Object obj: objs){
			r = r && validLength(min, max, obj);
		}
		
		return r;
	}
}
