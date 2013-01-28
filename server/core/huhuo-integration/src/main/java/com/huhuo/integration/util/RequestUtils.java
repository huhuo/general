package com.huhuo.integration.util;

/**
 * Title:
 * Description:
 * Company:易游科技
 * 
 * @author century
 * @version 1.0
 *          Date: 2006-5-9
 */
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * Parameter extraction methods, for an approach distinct from data binding, in which
 * parameters of specific types are required. This is very useful for simple submissions.
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @author Jean-Pierre Pawlak
 */
public class RequestUtils {

	/**
	 * Throw a ServletException if the given HTTP request method should be rejected.
	 * @param request request to check
	 * @param method method (such as "GET") which should be rejected
	 * @throws ServletException if the given HTTP request is rejected
	 */
	public static void rejectRequestMethod(HttpServletRequest request, String method)
			throws ServletException {
		if (request.getMethod().equals(method)) {
			throw new ServletException("This resource does not support request method '" + method + "'");
		}
	}

	/**
	 * Get an int parameter, with a fallback value. Never throws an exception.
	 * Can pass a distinguished value as default to enable checks of whether it was supplied.
	 */
	public static int getIntParameter(HttpServletRequest request, String name, int defaultVal) {
		try {
			return getRequiredIntParameter(request, name);
		}
		catch (Exception ex) {
			return defaultVal;
		}
	}

	/**
	 * Get an int parameter, throwing an exception if it isn't found or isn't a number.
	 * @throws Exception: subclass of ServletException,
	 * so it doesn't need to be caught
	 */
	public static int getRequiredIntParameter(HttpServletRequest request, String name)
			throws Exception {
		String s = request.getParameter(name);
		if (s == null) {
			throw new Exception("Required int parameter '" + name + "' was not supplied");
		}
		if ("".equals(s)) {
			throw new Exception("Required int parameter '" + name + "' contained no value");
		}
		try {
			return Integer.parseInt(s);
		}
		catch (NumberFormatException ex) {
				throw new Exception("Required int parameter '" + name + "' value of '" + s +
																								 "' was not a valid number");
		}
	}

	/**
	 * Get an int parameter, with a fallback value. Never throws an exception.
	 * Can pass a distinguished value as default to enable checks of whether it was supplied.
	 */
	public static long getLongParameter(HttpServletRequest request, String name, long defaultVal) {
		try {
			return getRequiredLongParameter(request, name);
		}
		catch (Exception ex) {
			return defaultVal;
		}
	}

	/**
	 * Get a long parameter, throwing an exception if it isn't found or isn't a number.
	 * @throws Exception: subclass of ServletException,
	 * so it doesn't need to be caught
	 */
	public static long getRequiredLongParameter(HttpServletRequest request, String name)
			throws Exception {
		String s = request.getParameter(name);
		if (s == null) {
			throw new Exception("Required long parameter '" + name + "' was not supplied");
		}
		if ("".equals(s)) {
			throw new Exception("Required long parameter '" + name + "' contained no value");
		}
		try {
			return Long.parseLong(s);
		}
		catch (NumberFormatException ex) {
				throw new Exception("Required long parameter '" + name + "' value of '" + s +
																								 "' was not a valid number");
		}
	}

	/**
	 * Get a double parameter, with a fallback value. Never throws an exception.
	 * Can pass a distinguished value as default to enable checks of whether it was supplied.
	 */
	public static float getFloatParameter(HttpServletRequest request, String name, float defaultVal) {
		try {
			return getRequiredFloatParameter(request, name);
		}
		catch (Exception ex) {
			return defaultVal;
		}
	}

	/**
	 * Get a double parameter, throwing an exception if it isn't found or isn't a number.
	 * @throws Exception: subclass of ServletException,
	 * so it doesn't need to be caught
	 */
	public static float getRequiredFloatParameter(HttpServletRequest request, String name)
			throws Exception {
		String s = request.getParameter(name);
		if (s == null) {
			throw new Exception("Required float parameter '" + name + "' was not supplied");
		}
		if ("".equals(s)) {
			throw new Exception("Required float parameter '" + name + "' contained no value");
		}
		try {
			return Float.parseFloat(s);
		}
		catch (NumberFormatException ex) {
			throw new Exception("Required float parameter '" + name + "' value of '" +
																							 s + "' was not a valid number");
		}
	}

	/**
	 * Get a double parameter, with a fallback value. Never throws an exception.
	 * Can pass a distinguished value as default to enable checks of whether it was supplied.
	 */
	public static double getDoubleParameter(HttpServletRequest request, String name, double defaultVal) {
		try {
			return getRequiredDoubleParameter(request, name);
		}
		catch (Exception ex) {
			return defaultVal;
		}
	}

	/**
	 * Get a double parameter, throwing an exception if it isn't found or isn't a number.
	 * @throws Exception: subclass of ServletException,
	 * so it doesn't need to be caught
	 */
	public static double getRequiredDoubleParameter(HttpServletRequest request, String name)
			throws Exception {
		String s = request.getParameter(name);
		if (s == null) {
			throw new Exception("Required double parameter '" + name + "' was not supplied");
		}
		if ("".equals(s)) {
			throw new Exception("Required double parameter '" + name + "' contained no value");
		}
		try {
			return Double.parseDouble(s);
		}
		catch (NumberFormatException ex) {
			throw new Exception("Required double parameter '" + name + "' value of '" + s +
																							 "' was not a valid number");
		}
	}

	/**
	 * Get a boolean parameter, with a fallback value. Never throws an exception.
	 * Can pass a distinguished value as default to enable checks of whether it was supplied.
	 */
	public static boolean getBooleanParameter(HttpServletRequest request, String name, boolean defaultVal) {
		try {
			return getRequiredBooleanParameter(request, name);
		}
		catch (Exception ex) {
			return defaultVal;
		}
	}

	/**
	 * Get a boolean parameter, throwing an exception if it isn't found or isn't a boolean.
	 * True is "true" or "yes" or "on" (ignoring the case) or "1".
	 * @throws Exception: subclass of ServletException,
	 * so it doesn't need to be caught
	 */
	public static boolean getRequiredBooleanParameter(HttpServletRequest request, String name)
			throws Exception {
		String s = request.getParameter(name);
		if (s == null) {
			throw new Exception("Required boolean parameter '" + name + "' was not supplied");
		}
		if ("".equals(s)) {
			throw new Exception("Required boolean parameter '" + name + "' contained no value");
		}
		return s.equalsIgnoreCase("true") || s.equalsIgnoreCase("yes") || s.equalsIgnoreCase("on") || s.equals("1");
	}

	/**
	 * Get a string parameter, with a fallback value. Never throws an exception.
	 * Can pass a distinguished value to default to enable checks of whether it was supplied.
	 */
	public static String getStringParameter(HttpServletRequest request, String name, String defaultVal) {
		try {
			return getRequiredStringParameter(request, name);
		}
		catch (Exception ex) {
			return defaultVal;
		}
	}

	/**
	 * Get a string parameter, throwing an exception if it isn't found or is empty.
	 * @throws Exception: subclass of ServletException,
	 * so it doesn't need to be caught
	 */
	public static String getRequiredStringParameter(HttpServletRequest request, String name)
			throws Exception {
		String s = request.getParameter(name);
		if (s == null) {
			throw new Exception("Required string parameter '" + name + "' was not supplied");
		}
		if ("".equals(s)) {
			throw new Exception("Required string parameter '" + name + "' contained no value");
		}
		return s;
	}

}
