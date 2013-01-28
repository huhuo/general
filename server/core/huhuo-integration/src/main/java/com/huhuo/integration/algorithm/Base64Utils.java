package com.huhuo.integration.algorithm;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;

import com.huhuo.integration.config.GlobalConstant.Encoding;
import com.huhuo.integration.exception.UtilException;

public class Base64Utils extends Base64{
	
	public static String defaultEncoding = Encoding.UTF8;
	
	/**
	 * encode String src to base64 String encoding with UTF-8
	 * @see #encodeBase64(byte[])
	 * @param src
	 * @return
	 */
	public static String encodeByte(byte[] src) {
		try {
			byte[] encodeBase64 = encodeBase64(src);
			String encodeStr = new String(encodeBase64, defaultEncoding);
			return encodeStr;
		} catch (Exception e) {
			throw new UtilException(e);
		}
	}
	/**
	 *@see #encode(byte[])
	 */
	public static String encodeStr(String src) {
		try {
			return encodeByte(src.getBytes(defaultEncoding));
		} catch (Exception e) {
			throw new UtilException(e);
		}
	}
	/**
	 * encode String src to base64 String encoding with UTF-8
	 * @see #encodeBase64(byte[])
	 * @param src
	 * @return
	 */
	public static String decodeByte(byte[] src) throws UnsupportedEncodingException {
		try {
			byte[] decodeBase64 = decodeBase64(src);
			String decodeStr = new String(decodeBase64, defaultEncoding);
			return decodeStr;
		} catch (Exception e) {
			throw new UtilException(e);
		}
	}
	/**
	 * @see #decodeByte(byte[])
	 */
	public static String decodeStr(String src) throws UnsupportedEncodingException {
		try {
			return decodeByte(src.getBytes(defaultEncoding));
		} catch (Exception e) {
			throw new UtilException(e);
		}
	}

}
