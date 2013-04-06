package com.huhuo.integration.util;


public class StringUtils extends org.apache.commons.lang.StringUtils {
	
	public static String join(String separator, Object...objects){
		return join(objects, separator);
	}
	
}
