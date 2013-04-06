package com.huhuo.integration.util;

import java.util.Iterator;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.text.StrBuilder;

import com.alibaba.fastjson.JSON;
import com.huhuo.integration.config.GlobalConstant.DateFormat;


public class StringUtils extends org.apache.commons.lang.StringUtils {
	
	
	public static String join(String separator, Object...objects){
		return join(objects, separator);
	}
	
	/**
     * <p>Joins the elements of the provided <code>Iterator</code> into
     * a single String containing the provided elements.</p>
     *
     * <p>No delimiter is added before or after the list.
     * A <code>null</code> separator is the same as an empty String ("").</p>
     *
     * <p>See the examples here: {@link #join(Object[],String)}. </p>
     *
     * @param iterator  the <code>Iterator</code> of values to join together, may be null
     * @param separator  the separator character to use, null treated as ""
     * @return the joined String, <code>null</code> if null iterator input
     */
	public static <E> String join(Iterator<E> iterator, String separator) {

		// handle null, zero and one elements before building a buffer
		if (iterator == null) {
			return null;
		}
		if (!iterator.hasNext()) {
			return EMPTY;
		}
		System.out.println(JSON.toJSONStringWithDateFormat(iterator, DateFormat.LONG_FORMAT));
		Object first = iterator.next();

		// serialize value in iterator
		first = JSON.toJSONStringWithDateFormat(first, DateFormat.LONG_FORMAT);

		if (!iterator.hasNext()) {
			return ObjectUtils.toString(first);
		}

		// two or more elements
		StrBuilder buf = new StrBuilder(256); // Java default is 16, probably
												// too small
		if (first != null) {
			buf.append(first);
		}

		while (iterator.hasNext()) {
			if (separator != null) {
				buf.append(separator);
			}
			Object obj = iterator.next();

			// serialize value in iterator
			obj = JSON.toJSONStringWithDateFormat(obj, DateFormat.LONG_FORMAT);

			if (obj != null) {
				buf.append(obj);
			}
		}
		return buf.toString();
	}
	
}
