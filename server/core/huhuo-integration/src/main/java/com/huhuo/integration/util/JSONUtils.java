package com.huhuo.integration.util;

import java.util.Iterator;

import com.alibaba.fastjson.JSONObject;

public class JSONUtils {
	
	/**
	 * get iterator of the JSONObject param
	 * 
	 * @param obj
	 * @return
	 */
	public static Iterator<String> getKeyIterator(JSONObject obj){
		return obj.keySet().iterator();
	}
	
	/**
	 * get first key of the JSONObject param
	 * 
	 * @param obj
	 *            JSONObject type
	 * @return first key if has; null if not
	 */
	public static String getFirstKey(JSONObject obj) {
		Iterator<String> it = getKeyIterator(obj);
		return it.hasNext() ? it.next() : null;
	}
}
