package com.huhuo.integration.algorithm;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MessageDigestFactory {
	/**
	 * see {@link MessageDigest#getInstance(String)}
	 * 
	 * @param algorithm
	 * @return
	 */
	public static MessageDigest getMessageDigest(String algorithm){
		MessageDigest digest=null;
		try {
			digest = MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			throw new com.huhuo.integration.exception.NoSuchAlgorithmException(e);
		}
		return digest;
	}
	
	
}
