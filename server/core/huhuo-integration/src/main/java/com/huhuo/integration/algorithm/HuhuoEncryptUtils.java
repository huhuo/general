package com.huhuo.integration.algorithm;

import java.io.UnsupportedEncodingException;

import com.huhuo.integration.config.GlobalConstant;

/**
 * 自定义加解密字节数据的加密方法
 * @author wuyuxuan
 *
 */
public class HuhuoEncryptUtils {
	
	private static String key = "Crackers and the thief will suffer misfortune";
	private static String encoding = GlobalConstant.Encoding.UTF8;

	public static String getKey() {
		return key;
	}

	public static void setKey(String key) {
		HuhuoEncryptUtils.key = key;
	}

	public static String getEncoding() {
		return encoding;
	}

	public static void setEncoding(String encoding) {
		HuhuoEncryptUtils.encoding = encoding;
	}
	
	/**
	 * 加密算法
	 * @param buffer 要加密的字节数组
	 * @return 加密后的字节数据
	 */
	public static byte[] encrypt(byte[] buffer) {
		int pos, keylen;
		pos = 0;
		byte[] KEYVALUE = null;
		try {
			KEYVALUE = key.getBytes(encoding);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		keylen = KEYVALUE.length;
		for (int i = 0; i < buffer.length; i++) {
			buffer[i] ^= KEYVALUE[pos];
			pos++;
			if (pos == keylen)
				pos = 0;
		}
		return buffer;
	}

	/**
	 * 解密算法
	 * @param buffer 解密的字节数组
	 * @return 解密后的字节数组
	 */
	public static byte[] decrypt(byte[] buffer) {
		return encrypt(buffer);
	}
	
	/**
	 * 使用默认的编码格式（UTF-8）将buffer转化成字符串
	 * @param buffer 要编码的字节数组
	 * @return 如果出现异常，则返回null
	 */
	public static String getString(byte[] buffer) {
		try {
			return new String(buffer, encoding);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 使用默认的编码格式（UTF-8）将str转化成字节数组
	 * @param str 
	 * @return  如果出现异常，则返回null
	 */
	public static byte[] getByteArray(String str) {
		try {
			return str.getBytes(encoding);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
