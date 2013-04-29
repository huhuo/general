package com.huhuo.integration.service;

import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;

import com.huhuo.integration.web.Message;

public interface IServHttpClient {
	/**
	 * get method
	 * 
	 * @param url request url
	 * @param respEncoding response String's encoding
	 * @param isForceCloseConnection
	 *            true: indicate server close current connection when finish the
	 *            request. isForceCloseConnection is true, together with
	 *            http.protocol.expect-continue is false, can solve problem
	 *            about 'Too many open files' in linux when create many
	 *            {@link ServHttpClient} instance. <br>false: the connection is
	 *            managed by HttpConnectionManager, not ensure it is closed.
	 * 
	 * @return {@link Message} object
	 */
	public Message<String> get(String url, String respEncoding, boolean isForceCloseConnection);
	
	/**
	 * See {@link #get(String, String, boolean)}.
	 * 
	 * @param url
	 * @param isForceCloseConnection
	 * @return
	 */
	public Message<String> get(String url, boolean isForceCloseConnection);
	
	/**
	 * See {@link #get(String, String, boolean)}, isForceCloseConnection is false.
	 * 
	 * @param url
	 * @param respEncoding
	 * @return
	 */
	public Message<String> get(String url, String respEncoding);
	
	/**
	 * See {@link #get(String, String)}
	 * 
	 * @param url
	 * @return
	 */
	public Message<String> get(String url);
	
	/**
	 * See {@link #get(String, String, boolean)}, difference is response data,
	 * i.e. {@link Message#getData()}, is bytes
	 * 
	 * @param url
	 * @param respEncoding
	 * @param isForceCloseConnection
	 * @return
	 */
	public Message<byte[]> getRtnBytes(String url, boolean isForceCloseConnection);
	
	/**
	 * See {@link #getRtnBytes(String, boolean)}, isForceCloseConnection is false.
	 * 
	 * @param url
	 * @return
	 */
	public Message<byte[]> getRtnBytes(String url);
	
	/**
	 * See {@link #get(String, String, boolean)}, difference is using post and able to use parameters for request.
	 * 
	 * @param url
	 * @param respEncoding
	 * @param isForceCloseConnection
	 * @param params
	 *            parameters for the post request
	 * @return
	 */
	public Message<String> post(String url, String respEncoding, 
			boolean isForceCloseConnection, NameValuePair... params);
	
	/**
	 * See {@link #post(String, String, boolean, NameValuePair...)}, isForceCloseConnection is false.
	 * 
	 * @param url
	 * @param respEncoding
	 * @param params
	 * @return
	 */
	public Message<String> post(String url, String respEncoding, NameValuePair... params);
	
	/**
	 * See {@link #post(String, String, boolean, NameValuePair...)}
	 * 
	 * @param url
	 * @param isForceCloseConnection
	 * @param params
	 * @return
	 */
	public Message<String> post(String url, boolean isForceCloseConnection, NameValuePair... params);
	
	/**
	 * See {@link #post(String, boolean, NameValuePair...)}, isForceCloseConnection is false.
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public Message<String> post(String url, NameValuePair... params);

	/**
	 * See {@link #getRtnBytes(String, String, boolean)}, difference is using post and able to use parameters for request.
	 * method of Http.
	 * 
	 * @param url
	 * @param respEncoding
	 * @param isForceCloseConnection
	 * @param params
	 *            parameters for the post request
	 * @return
	 */
	public Message<byte[]> postRtnBytes(String url, boolean isForceCloseConnection, 
			NameValuePair... params);
	
	/**
	 * See {@link #postRtnBytes(String, boolean, NameValuePair...)}, isForceCloseConnection is false.
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public Message<byte[]> postRtnBytes(String url, NameValuePair... params);
	
	/**
	 * Send request to url by post method.
	 * 
	 * @param url
	 *            request url
	 * @param msg
	 *            write msg into streaming to url
	 * @param reqEncoding
	 *            request encoding for msg
	 * @param isForceCloseConnection
	 *            see {@link #get(String, String, boolean)}
	 * @param respEncoding
	 *            response String's encoding
	 * @return
	 */
	public Message<String> postAsStream(String url, String msg, String reqEncoding, 
			boolean isForceCloseConnection, String respEncoding);
	
	/**
	 * See {@link #postAsStream(String, String, String, boolean, String)}.
	 * 
	 * @param url
	 * @param msg
	 * @param isForceCloseConnection
	 * @return
	 */
	public Message<String> postAsStream(String url, String msg, boolean isForceCloseConnection);
	
	/**
	 * See {@link #postAsStream(String, String, boolean)}, isForceCloseConnection is false.
	 * 
	 * @param url
	 * @param msg
	 * @return
	 */
	public Message<String> postAsStream(String url, String msg);
	/**
	 * See {@link #postAsStream(String, String, String, boolean, String)}.
	 * 
	 * @param url
	 * @param msg
	 * @param isForceCloseConnection
	 * @param respEncoding
	 * @return
	 */
	public Message<String> postAsStream(String url, byte[] msg, 
			boolean isForceCloseConnection, String respEncoding);
	
	/**
	 * See {@link #postAsStream(String, byte[], boolean, String)}, isForceCloseConnection is false.
	 * 
	 * @param url
	 * @param msg
	 * @param respEncoding
	 * @return
	 */
	public Message<String> postAsStream(String url, byte[] msg, String respEncoding);
	
	/**
	 * See {@link #postAsStream(String, byte[], boolean, String)}.
	 * 
	 * @param url
	 * @param msg
	 * @param isForceCloseConnection
	 * @return
	 */
	public Message<String> postAsStream(String url, byte[] msg, boolean isForceCloseConnection);
	
	/**
	 * See {@link #postAsStream(String, byte[], boolean)}, isForceCloseConnection is false.
	 * 
	 * @param url
	 * @param msg
	 * @return
	 */
	public Message<String> postAsStream(String url, byte[] msg);
	
	/**
	 * See {@link #postAsStream(String, String, String, boolean, String)},
	 * difference is the reponse data, i.e. {@link Message#getData()}, is bytes
	 * 
	 * @param url
	 * @param msg
	 * @param reqEncoding
	 * @param isForceCloseConnection
	 * @return
	 */
	public Message<byte[]> postAsStreamRtnBytes(String url, String msg, String reqEncoding, 
			boolean isForceCloseConnection);
	
	/**
	 * See {@link #postAsStreamRtnBytes(String, String, String, boolean)}, isForceCloseConnection is false.
	 * 
	 * @param url
	 * @param msg
	 * @param reqEncoding
	 * @return
	 */
	public Message<byte[]> postAsStreamRtnBytes(String url, String msg, String reqEncoding);
	
	/**
	 * See {@link #postAsStreamRtnBytes(String, String, String, boolean)}
	 * 
	 * @param url
	 * @param msg
	 * @param isForceCloseConnection
	 * @return
	 */
	public Message<byte[]> postAsStreamRtnBytes(String url, String msg, boolean isForceCloseConnection);
	
	/**
	 * See {@link #postAsStreamRtnBytes(String, String, boolean)}, isForceCloseConnection is false.
	 * 
	 * @param url
	 * @param msg
	 * @return
	 */
	public Message<byte[]> postAsStreamRtnBytes(String url, String msg);
	
	/**
	 * See {@link #postAsStreamRtnBytes(String, String, String, boolean)}
	 * 
	 * @param url
	 * @param msg
	 * @param isForceCloseConnection
	 * @return
	 */
	public Message<byte[]> postAsStreamRtnBytes(String url, byte[] msg, boolean isForceCloseConnection);
	
	/**
	 * See {@link #postAsStreamRtnBytes(String, byte[], boolean)}, isForceCloseConnection is false.
	 * 
	 * @param url
	 * @param msg
	 * @return
	 */
	public Message<byte[]> postAsStreamRtnBytes(String url, byte[] msg);
	
	public Message<byte[]> executeMethodBytes(HttpMethod method, boolean isForceCloseConnection);
	
	public Message<String> executeMethod(HttpMethod method, boolean isForceCloseConnection, 
			String respEncoding);
}
