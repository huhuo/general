package com.huhuo.integration.base;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.huhuo.integration.algorithm.GzipUtils;
import com.huhuo.integration.algorithm.HuhuoEncryptUtils;
import com.huhuo.integration.config.GlobalConstant;


public class BaseCtrl {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	private String encoding = GlobalConstant.Encoding.UTF8;
	private String dateFormat = GlobalConstant.DateFormat.LONG_FORMAT;
	
	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public String parseReqParam(InputStream in, boolean isDecrypt) throws IOException{
		ByteArrayOutputStream param = new ByteArrayOutputStream();
		byte[] b = new byte[1024];
		int len = 0;
		while((len=in.read(b)) != -1) {
			param.write(b, 0, len);
		}
		if(isDecrypt){
			b = HuhuoEncryptUtils.decrypt(param.toByteArray());
		}else{
			b = param.toByteArray();
		}
		return new String(b, encoding);
	}
	
	public String parseReqParam(InputStream in) throws IOException{
		return parseReqParam(in, false);
	}
	
	protected void write(InputStream data, OutputStream out){
		try{
			byte[] b = new byte[1024];
			int len = 0;
			while((len=data.read(b))!=-1){
				out.write(b, 0, len);
			}
		}catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
	}
	
	protected void write(Object obj, OutputStream out){
		write(obj, out, false, false);
	}
	
	protected void write(Object obj, OutputStream out, boolean encrypted){
		write(obj, out, encrypted, false);
	}
	
	/**
	 * 将对象obj转化成json字符串后输出至客户端
	 * @param obj 输出结果
	 * @param out
	 * @param encrypted 输出结果是否需要加密
	 * @param compressed 输出结果是否需要压缩
	 * @throws IOException
	 */
	protected void write(Object obj, OutputStream out, boolean encrypted,
			boolean compressed) {
		try {
			String content = null;
			if (obj instanceof String) {
				content = String.valueOf(obj);
			} else {
				content = JSONObject
						.toJSONStringWithDateFormat(obj, dateFormat);
			}

			byte[] response;

			response = content.getBytes(encoding);

			if (compressed) {
				response = GzipUtils.compress(response);
			}
			if (encrypted) {
				response = HuhuoEncryptUtils.decrypt(response);
			}

			out.write(response);
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
	}

}
