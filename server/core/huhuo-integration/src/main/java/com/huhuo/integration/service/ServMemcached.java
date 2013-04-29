package com.huhuo.integration.service;

import org.apache.commons.lang.StringUtils;

import net.rubyeye.xmemcached.MemcachedClient;

import com.huhuo.integration.algorithm.MD5Utils;

public class ServMemcached implements IServMemcached {
	private MemcachedClient memcachedClient;
	private boolean isOn = true;
	private int defaultExpiration = 24*60*60;
	private long defaultTimeout = 60000;
	
	public String getFullKey(String region, String key){
		StringBuffer keySb = new StringBuffer();
		//keySb.append(region).append("_").append(key);
		keySb.append(region);
		if(!StringUtils.isBlank(region)) //如果分区为空串，那么按照无前缀处理。为与之前无按前缀分区的旧版本做兼容，如全部按照分区管理则删除此行
			keySb.append("_");
		keySb.append(key);
		return keySb.toString();
	}
	
	public String getCompressKey(String key){
		return MD5Utils.encodeHex(key);
	}
	
	@Override
	public <T> T get(String region, String key) {
		return get(region, key, false);
	}

	@Override
	public <T> T get(String region, String key, long timeout) {
		return get(region, key, false, timeout);
	}
	
	@Override
	public <T> T get(String region, String key, boolean isCompressKey) {
		return get(region, key, isCompressKey, defaultTimeout);
	}
	
	@Override
	public <T> T get(String region, String key, boolean isCompressKey, long timeout) {
		if (isOn) {
			if (isCompressKey) {
				key = getCompressKey(key);
			}
			try {
				return memcachedClient.get(getFullKey(region, key), timeout);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	@Override
	public boolean set(String region, String key, Object value) {
		return set(region, key, value, false);
	}
	
	@Override
	public boolean set(String region, String key, Object value, boolean isCompressKey) {
		return set(region, key, value, isCompressKey, defaultExpiration, defaultTimeout);
	}
	
	@Override
	public boolean set(String region, String key, Object value, int exp,
			long timeout) {
		return set(region, key, value, false, exp, timeout);
	}
	
	@Override
	public boolean set(String region, String key, Object value, boolean isCompressKey, int exp, long timeout) {
		if (isOn) {
			if (isCompressKey) {
				key = getCompressKey(key);
			}
			try {
				return memcachedClient.set(getFullKey(region, key), exp, value, timeout);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	@Override
	public boolean delete(String region, final String key) {
		return delete(region, key, false);
	}
	
	@Override
	public boolean delete(String region, String key,
			boolean isCompressKey) {
		if(isOn){
			if(isCompressKey){
				key = getCompressKey(key);
			}
			try {
				return memcachedClient.delete(getFullKey(region, key));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	@Override
	public void flushAll() {
		try {
			if(isOn){
				memcachedClient.flushAll();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public boolean getIsOn() {
		return isOn;
	}

	@Override
	public void setIsOn(boolean isOn) {
		this.isOn = isOn;
		
	}
	
	@Override
	public MemcachedClient getMemcachedClient() {
		return memcachedClient;
	}
	
	@Override
	public void setMemcachedClient(MemcachedClient memcachedClient) {
		this.memcachedClient = memcachedClient;
	}

	@Override
	public long getDefaultTimeout() {
		return defaultTimeout;
	}

	@Override
	public int getDefaultExpiration() {
		return defaultExpiration;
	}

	@Override
	public void setDefaultTimeout(long timeout) {
		defaultTimeout = timeout;
	}

	@Override
	public void setDefalutExpiration(int exp) {
		defaultExpiration = exp;
	}

}
