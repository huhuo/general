package com.huhuo.componentweb.core;


import com.huhuo.integration.service.IServHttpClient;
import com.huhuo.integration.web.Message;

public interface IServHcwHttpClient extends IServHttpClient {
	
	/**
	 * post data with encrypt and compress logic
	 * @param url
	 * @param msg
	 * @param reqEncoding
	 * @param respEncoding
	 * @param isForceCloseConnection
	 * @param isEncrypted
	 * @param isDecrypted
	 * @param isCompressed
	 * @param unCompressed
	 * @return
	 */
	public Message<String> postAsStream(String url, String msg,
			String reqEncoding, String respEncoding,
			boolean isForceCloseConnection, boolean isEncrypted,
			boolean isDecrypted, boolean isCompressed, boolean unCompressed);
	/**
	 * @see #postAsStream(String, String, String, String, boolean, boolean, boolean, boolean, boolean)
	 */
	public Message<String> postAsStream(String url, String msg, String reqEncoding, 
			String respEncoding, boolean isForceCloseConnection);
	/**
	 * @see #postAsStream(String, String, String, String, boolean, boolean, boolean, boolean, boolean)
	 */
	public Message<String> postAsStream(String url, String msg, boolean isForceCloseConnection, boolean isEncrypted,
			boolean isDecrypted);
	/**
	 * @see #postAsStream(String, String, String, String, boolean, boolean, boolean, boolean, boolean)
	 */
	public Message<String> postAsStream(String url, String msg, boolean isForceCloseConnection, boolean isEncrypted,
			boolean isDecrypted, boolean isCompressed, boolean unCompressed);
}
