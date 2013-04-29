package com.huhuo.componentweb.core;


import java.util.Iterator;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.lang.exception.ExceptionUtils;

import com.huhuo.integration.algorithm.GzipUtils;
import com.huhuo.integration.algorithm.HuhuoEncryptUtils;
import com.huhuo.integration.service.ServHttpClient;
import com.huhuo.integration.web.Message;
import com.huhuo.integration.web.Message.Status;

public class ServHcwHttpClient extends ServHttpClient implements IServHcwHttpClient {

	/**
	 * See {@link #ZHttpClientServ(HttpClientParams, HttpConnectionManager)}
	 */
	public ServHcwHttpClient() {
		this((HttpClientParams)null, null);
	}
	
	/**
	 * See {@link #ZHttpClientServ(Map, HttpConnectionManager)}
	 * 
	 * @param params
	 */
	public ServHcwHttpClient(Map<String, Object> params){
		this(params, null);
	}
	
	/**
	 * See {@link #ZHttpClientServ(HttpClientParams, HttpConnectionManager)}
	 * 
	 * @param params
	 *            used to set parameters of HttpClientParams
	 * @param httpConnectionManager
	 */
	public ServHcwHttpClient(Map<String, Object> params, HttpConnectionManager httpConnectionManager) {
		this((HttpClientParams)null, httpConnectionManager);
		Iterator<String> it = params.keySet().iterator();
		while(it.hasNext()){
			String key = it.next();
			this.setParameter(key, params.get(key));
		}
	}
	
	/**
	 * See {@link #ZHttpClientServ(HttpClientParams, HttpConnectionManager)}
	 * 
	 * @param params
	 */
	public ServHcwHttpClient(HttpClientParams params){
		this(params, null);
	}
	
	/**
	 * Creates an instance of ZHttpClientServ using a user specified.
	 * {@link HttpClientParams}, {@link HttpConnectionManager}
	 * @param params
	 * @param httpConnectionManager
	 */
	public ServHcwHttpClient(HttpClientParams params, HttpConnectionManager httpConnectionManager){
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
	 * See {@link #ZHttpClientServ(HttpClientParams, HttpConnectionManager)}
	 * 
	 * @param httpConnectionManager
	 */
	public ServHcwHttpClient(HttpConnectionManager httpConnectionManager){
		this((HttpClientParams)null, httpConnectionManager);
	}
	
	
	public Message<String> postAsStream(String url, String msg,
			String reqEncoding, String respEncoding,
			boolean isForceCloseConnection, boolean isEncrypted,
			boolean isDecrypted, boolean isCompressed, boolean unCompressed) {
		
		Message<String> message = new Message<String>();
		try{
			byte[] param = msg.getBytes(reqEncoding);
			if(isCompressed){
				param = GzipUtils.compress(param);
			}
			if(isEncrypted){
				param = HuhuoEncryptUtils.encrypt(param);
			}
			Message<byte[]> r = postAsStreamRtnBytes(url, param, isForceCloseConnection);
			byte[] d = r.getData();
			if(d!=null){
				if(isDecrypted){
					d = HuhuoEncryptUtils.decrypt(d);
				}
				if(unCompressed){
					d = GzipUtils.uncompress(d);
				}
			}
			if(d!=null)
				message.setData(new String(d, respEncoding));
		}catch (Exception e) {
			logger.debug(ExceptionUtils.getFullStackTrace(e));
			message.setStatus(Status.ERROR);
			message.setMsg("function internal error");
		}
		
		return message;
		
	}
	
	public Message<String> postAsStream(String url, String msg, String reqEncoding, String respEncoding, boolean isForceCloseConnection){
		return postAsStream(url, msg, reqEncoding, respEncoding, isForceCloseConnection, false, false, false, false);
	}
	
	public Message<String> postAsStream(String url, String msg, boolean isForceCloseConnection, boolean isEncrypted,
			boolean isDecrypted){
		return postAsStream(url, msg, defaultEncoding, defaultEncoding, isForceCloseConnection, isEncrypted, isDecrypted, false, false);
	}
	
	public Message<String> postAsStream(String url, String msg, boolean isForceCloseConnection, boolean isEncrypted,
			boolean isDecrypted, boolean isCompressed, boolean unCompressed){
		return postAsStream(url, msg, defaultEncoding, defaultEncoding, isForceCloseConnection, isEncrypted, isDecrypted, isCompressed, unCompressed);
	}
	
}
