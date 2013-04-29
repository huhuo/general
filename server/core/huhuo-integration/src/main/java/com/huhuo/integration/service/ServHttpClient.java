package com.huhuo.integration.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huhuo.integration.config.GlobalConstant;
import com.huhuo.integration.web.Message;
import com.huhuo.integration.web.Message.Status;

/**
 * Provide service for HTTP client. Default encoding is UTF-8 for request data or reponse data.<br>
 * 
 * @author wuyuxuan
 *
 */
public class ServHttpClient implements IServHttpClient {
	protected HttpClient httpClient;
	protected String defaultEncoding = GlobalConstant.Encoding.UTF8;
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * See {@link #HttpClientServ(HttpClientParams, HttpConnectionManager)}
	 */
	public ServHttpClient() {
		this((HttpClientParams)null, null);
	}
	
	/**
	 * See {@link #HttpClientServ(Map, HttpConnectionManager)}
	 * 
	 * @param params
	 */
	public ServHttpClient(Map<String, Object> params){
		this(params, null);
	}
	
	/**
	 * See {@link #HttpClientServ(HttpClientParams, HttpConnectionManager)}
	 * 
	 * @param params
	 *            used to set parameters of HttpClientParams
	 * @param httpConnectionManager
	 */
	public ServHttpClient(Map<String, Object> params, HttpConnectionManager httpConnectionManager) {
		this((HttpClientParams)null, httpConnectionManager);
		Iterator<String> it = params.keySet().iterator();
		while(it.hasNext()){
			String key = it.next();
			this.setParameter(key, params.get(key));
		}
	}
	
	/**
	 * See {@link #HttpClientServ(HttpClientParams, HttpConnectionManager)}
	 * 
	 * @param params
	 */
	public ServHttpClient(HttpClientParams params){
		this(params, null);
	}
	
	/**
	 * Creates an instance of HttpClientServ using a user specified.
	 * {@link HttpClientParams}, {@link HttpConnectionManager}
	 * @param params
	 * @param httpConnectionManager
	 */
	public ServHttpClient(HttpClientParams params, HttpConnectionManager httpConnectionManager){
		if(params==null && httpConnectionManager==null){
			httpClient = new HttpClient();
		}else if(params==null){
			httpClient = new HttpClient(httpConnectionManager);
		}else if(httpConnectionManager==null){
			httpClient = new HttpClient(params);
		}else{
			httpClient = new HttpClient(params, httpConnectionManager);
		}
	}
	
	/**
	 * See {@link #HttpClientServ(HttpClientParams, HttpConnectionManager)}
	 * 
	 * @param httpConnectionManager
	 */
	public ServHttpClient(HttpConnectionManager httpConnectionManager){
		this((HttpClientParams)null, httpConnectionManager);
	}
	
	/**
	 * Assigns the value to the parameter with the given name. <a
	 * href="http://hc.apache.org/httpclient-3.x/preference-api.html">See
	 * official document, address is http://hc.apache.org/httpclient-3.x/preference-api.html#Supported_parameters</a>
	 * 
	 * @param name
	 *            parameter name.
	 * @param value
	 *            parameter value
	 */
	@SuppressWarnings("unchecked")
	public void setParameter(String name, Object value){
		HttpClientParams params = httpClient.getParams();
		if(HttpConnectionManagerParams.MAX_HOST_CONNECTIONS.equals(name)){
			Map<Object,Object> map = (Map<Object, Object>)params.getParameter(name);
			if(map==null){
				map = new HashMap<Object, Object>();
			}
			map.put(httpClient.getHostConfiguration(), value);
			value = map;
		}
		params.setParameter(name, value);
	}
	
	public Object getParameter(String name){
		HttpClientParams params = httpClient.getParams();
		return params.getParameter(name);
	}
	
	public HttpConnectionManager getHttpConnectionManager(){
		return httpClient.getHttpConnectionManager();
	}
	
	public HostConfiguration getHostConfiguration(){
		return httpClient.getHostConfiguration();
	}

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
	public Message<String> get(String url, String respEncoding, boolean isForceCloseConnection) {
		GetMethod method = new GetMethod(url);
		return executeMethod(method, isForceCloseConnection, respEncoding);
	}
	
	/**
	 * See {@link #get(String, String, boolean)}.
	 * 
	 * @param url
	 * @param isForceCloseConnection
	 * @return
	 */
	public Message<String> get(String url, boolean isForceCloseConnection) {
		GetMethod method = new GetMethod(url);
		return executeMethod(method, isForceCloseConnection, defaultEncoding);
	}
	
	/**
	 * See {@link #get(String, String, boolean)}, isForceCloseConnection is false.
	 * 
	 * @param url
	 * @param respEncoding
	 * @return
	 */
	public Message<String> get(String url, String respEncoding){
		return get(url, respEncoding, false);
	}
	
	/**
	 * See {@link #get(String, String)}
	 * 
	 * @param url
	 * @return
	 */
	public Message<String> get(String url){
		return get(url, defaultEncoding);
	}
	
	/**
	 * See {@link #get(String, String, boolean)}, difference is response data,
	 * i.e. {@link Message#getData()}, is bytes
	 * 
	 * @param url
	 * @param respEncoding
	 * @param isForceCloseConnection
	 * @return
	 */
	public Message<byte[]> getRtnBytes(String url, boolean isForceCloseConnection){
		GetMethod method = new GetMethod(url);
		return executeMethodBytes(method, isForceCloseConnection);
	}
	
	/**
	 * See {@link #getRtnBytes(String, boolean)}, isForceCloseConnection is false.
	 * 
	 * @param url
	 * @return
	 */
	public Message<byte[]> getRtnBytes(String url){
		return getRtnBytes(url, false);
	}
	
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
	public Message<String> post(String url, String respEncoding, boolean isForceCloseConnection, NameValuePair... params){
		PostMethod method = new PostMethod(url);
		method.setRequestBody(params);
		return executeMethod(method, isForceCloseConnection, respEncoding);
	}
	
	/**
	 * See {@link #post(String, String, boolean, NameValuePair...)}, isForceCloseConnection is false.
	 * 
	 * @param url
	 * @param respEncoding
	 * @param params
	 * @return
	 */
	public Message<String> post(String url, String respEncoding, NameValuePair... params){
		return post(url, respEncoding, false, params);
	}
	
	/**
	 * See {@link #post(String, String, boolean, NameValuePair...)}
	 * 
	 * @param url
	 * @param isForceCloseConnection
	 * @param params
	 * @return
	 */
	public Message<String> post(String url, boolean isForceCloseConnection, NameValuePair... params){
		return post(url, defaultEncoding, isForceCloseConnection, params);
	}
	
	/**
	 * See {@link #post(String, boolean, NameValuePair...)}, isForceCloseConnection is false.
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public Message<String> post(String url, NameValuePair... params){
		return post(url, false, params);
	}

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
	public Message<byte[]> postRtnBytes(String url, boolean isForceCloseConnection, NameValuePair... params){
		PostMethod method = new PostMethod(url);
		method.setRequestBody(params);
		return executeMethodBytes(method, isForceCloseConnection);
	}
	
	/**
	 * See {@link #postRtnBytes(String, boolean, NameValuePair...)}, isForceCloseConnection is false.
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public Message<byte[]> postRtnBytes(String url, NameValuePair... params){
		return postRtnBytes(url, false, params);
	}
	
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
	public Message<String> postAsStream(String url, String msg, String reqEncoding, boolean isForceCloseConnection, String respEncoding){
		try {
			byte[] b = msg.getBytes(reqEncoding);
			return postAsStream(url, b, isForceCloseConnection, respEncoding);
		} catch (UnsupportedEncodingException e) {
			logger.debug(ExceptionUtils.getFullStackTrace(e));
			return new Message<String>(Status.ERROR, "function internal error");
		}
	}
	
	/**
	 * See {@link #postAsStream(String, String, String, boolean, String)}.
	 * 
	 * @param url
	 * @param msg
	 * @param isForceCloseConnection
	 * @return
	 */
	public Message<String> postAsStream(String url, String msg, boolean isForceCloseConnection){
		return postAsStream(url, msg, defaultEncoding, isForceCloseConnection, defaultEncoding);
	}
	
	/**
	 * See {@link #postAsStream(String, String, boolean)}, isForceCloseConnection is false.
	 * 
	 * @param url
	 * @param msg
	 * @return
	 */
	public Message<String> postAsStream(String url, String msg){
		return postAsStream(url, msg, false);
	}
	/**
	 * See {@link #postAsStream(String, String, String, boolean, String)}.
	 * 
	 * @param url
	 * @param msg
	 * @param isForceCloseConnection
	 * @param respEncoding
	 * @return
	 */
	public Message<String> postAsStream(String url, byte[] msg, boolean isForceCloseConnection, String respEncoding){
		InputStreamRequestEntity reqEntity = new InputStreamRequestEntity(new ByteArrayInputStream(msg), msg.length);
		PostMethod method = new PostMethod(url);
		method.setRequestEntity(reqEntity);
		return executeMethod(method, isForceCloseConnection, respEncoding);
	}
	
	/**
	 * See {@link #postAsStream(String, byte[], boolean, String)}, isForceCloseConnection is false.
	 * 
	 * @param url
	 * @param msg
	 * @param respEncoding
	 * @return
	 */
	public Message<String> postAsStream(String url, byte[] msg, String respEncoding){
		return postAsStream(url, msg, false, respEncoding);
	}
	
	/**
	 * See {@link #postAsStream(String, byte[], boolean, String)}.
	 * 
	 * @param url
	 * @param msg
	 * @param isForceCloseConnection
	 * @return
	 */
	public Message<String> postAsStream(String url, byte[] msg, boolean isForceCloseConnection){
		return postAsStream(url, msg, isForceCloseConnection, defaultEncoding);
	}
	
	/**
	 * See {@link #postAsStream(String, byte[], boolean)}, isForceCloseConnection is false.
	 * 
	 * @param url
	 * @param msg
	 * @return
	 */
	public Message<String> postAsStream(String url, byte[] msg){
		return postAsStream(url, msg, false);
	}
	
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
	public Message<byte[]> postAsStreamRtnBytes(String url, String msg, String reqEncoding, boolean isForceCloseConnection){
		try {
			byte[] b = msg.getBytes(reqEncoding);
			return postAsStreamRtnBytes(url, b, isForceCloseConnection);
		} catch (UnsupportedEncodingException e) {
			logger.debug(ExceptionUtils.getFullStackTrace(e));
			return new Message<byte[]>(Status.ERROR, "function internal error");
		}
	}
	
	/**
	 * See {@link #postAsStreamRtnBytes(String, String, String, boolean)}, isForceCloseConnection is false.
	 * 
	 * @param url
	 * @param msg
	 * @param reqEncoding
	 * @return
	 */
	public Message<byte[]> postAsStreamRtnBytes(String url, String msg, String reqEncoding){
		return postAsStreamRtnBytes(url, msg, reqEncoding, false);
	}
	
	/**
	 * See {@link #postAsStreamRtnBytes(String, String, String, boolean)}
	 * 
	 * @param url
	 * @param msg
	 * @param isForceCloseConnection
	 * @return
	 */
	public Message<byte[]> postAsStreamRtnBytes(String url, String msg, boolean isForceCloseConnection){
		return postAsStreamRtnBytes(url, msg, defaultEncoding, isForceCloseConnection);
	}
	
	/**
	 * See {@link #postAsStreamRtnBytes(String, String, boolean)}, isForceCloseConnection is false.
	 * 
	 * @param url
	 * @param msg
	 * @return
	 */
	public Message<byte[]> postAsStreamRtnBytes(String url, String msg){
		return postAsStreamRtnBytes(url, msg, false);
	}
	
	/**
	 * See {@link #postAsStreamRtnBytes(String, String, String, boolean)}
	 * 
	 * @param url
	 * @param msg
	 * @param isForceCloseConnection
	 * @return
	 */
	public Message<byte[]> postAsStreamRtnBytes(String url, byte[] msg, boolean isForceCloseConnection){
		InputStreamRequestEntity reqEntity = new InputStreamRequestEntity(new ByteArrayInputStream(msg), msg.length);
		PostMethod method = new PostMethod(url);
		method.setRequestEntity(reqEntity);
		return executeMethodBytes(method, isForceCloseConnection);
	}
	
	/**
	 * See {@link #postAsStreamRtnBytes(String, byte[], boolean)}, isForceCloseConnection is false.
	 * 
	 * @param url
	 * @param msg
	 * @return
	 */
	public Message<byte[]> postAsStreamRtnBytes(String url, byte[] msg){
		return postAsStreamRtnBytes(url, msg, false);
	}
	
	public Message<byte[]> executeMethodBytes(HttpMethod method, boolean isForceCloseConnection){
		if(isForceCloseConnection){
			forceCloseConnection(method);
		}
		try {
			int status = httpClient.executeMethod(method);
			logger.debug("send request：{} --> response code:{}", method.getURI().toString(), status);
			if(status==HttpStatus.SC_OK){
				InputStream in = method.getResponseBodyAsStream();
				return new Message<byte[]>(Status.SUCCESS, "Http method success", getBytes(in));
			}else{
				return new Message<byte[]>(Status.FAILURE, "Http method failure response code:"+status);
			}
		} catch (Exception e) {
			logger.debug(ExceptionUtils.getFullStackTrace(e));
			return new Message<byte[]>(Status.ERROR, "function internal error");
		}finally{
			method.releaseConnection();
		}
	}
	
	public Message<String> executeMethod(HttpMethod method, boolean isForceCloseConnection, String respEncoding){
		if(isForceCloseConnection){
			forceCloseConnection(method);
		}
		try {
			int status = httpClient.executeMethod(method);
			
			logger.debug("send request：{} --> response code:{}", method.getURI().toString(), status);
			if(status==HttpStatus.SC_OK){
				InputStream in = method.getResponseBodyAsStream();
				return new Message<String>(Status.SUCCESS, "Http method success", getString(in, respEncoding));
			}else{
				return new Message<String>(Status.FAILURE, "Http method failure response code:"+status);
			}
		} catch (Exception e) {
			logger.debug(ExceptionUtils.getFullStackTrace(e));
			return new Message<String>(Status.ERROR, "function internal error");
		}finally{
			method.releaseConnection();
		}
	}
	
	protected ByteArrayOutputStream convertToByteArrayOutputStream(InputStream in) throws IOException{
		byte[] b = new byte[1024];
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int len = 0;
		while((len=in.read(b))!=-1){
			out.write(b, 0, len);
		}
		return out;
	}
	
	protected String getString(InputStream in, String charsetName) throws IOException{
		ByteArrayOutputStream out = convertToByteArrayOutputStream(in);
		return out.toString(charsetName);
	}

	
	protected byte[] getBytes(InputStream in) throws IOException{
		ByteArrayOutputStream out = convertToByteArrayOutputStream(in);
		return out.toByteArray();
	}
	
	protected void forceCloseConnection(HttpMethod method){
		method.setRequestHeader("Connection", "close");
	}

}
