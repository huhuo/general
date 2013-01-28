package com.huhuo.integration.config;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;


public abstract class PropertiesConfigurationTemplate implements Configuration{

	protected abstract PropertiesConfiguration getConfig();
	
	@Override
	public Configuration subset(String prefix) {
		return getConfig().subset(prefix);
	}

	@Override
	public boolean isEmpty() {
		return getConfig().isEmpty();
	}

	@Override
	public boolean containsKey(String key) {
		return getConfig().containsKey(key);
	}

	@Override
	public void addProperty(String key, Object value) {
		getConfig().addProperty(key, value);
	}

	@Override
	public void setProperty(String key, Object value) {
		getConfig().setProperty(key, value);
	}

	@Override
	public void clearProperty(String key) {
		getConfig().clearProperty(key);
	}

	@Override
	public void clear() {
		getConfig().clear();
	}

	@Override
	public Object getProperty(String key) {
		return getConfig().getProperty(key);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Iterator getKeys(String prefix) {
		return getConfig().getKeys(prefix);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Iterator getKeys() {
		return getConfig().getKeys();
	}

	@Override
	public Properties getProperties(String key) {
		return getConfig().getProperties(key);
	}

	@Override
	public boolean getBoolean(String key) {
		return getConfig().getBoolean(key);
	}

	@Override
	public boolean getBoolean(String key, boolean defaultValue) {
		return getConfig().getBoolean(key, defaultValue);
	}

	@Override
	public Boolean getBoolean(String key, Boolean defaultValue) {
		return getConfig().getBoolean(key, defaultValue);
	}

	@Override
	public byte getByte(String key) {
		return getConfig().getByte(key);
	}

	@Override
	public byte getByte(String key, byte defaultValue) {
		return getConfig().getByte(key, defaultValue);
	}

	@Override
	public Byte getByte(String key, Byte defaultValue) {
		return getConfig().getByte(key, defaultValue);
	}

	@Override
	public double getDouble(String key) {
		return getConfig().getDouble(key);
	}

	@Override
	public double getDouble(String key, double defaultValue) {
		return getConfig().getDouble(key, defaultValue);
	}

	@Override
	public Double getDouble(String key, Double defaultValue) {
		return getConfig().getDouble(key, defaultValue);
	}

	@Override
	public float getFloat(String key) {
		return getConfig().getFloat(key);
	}

	@Override
	public float getFloat(String key, float defaultValue) {
		return getConfig().getFloat(key, defaultValue);
	}

	@Override
	public Float getFloat(String key, Float defaultValue) {
		return getConfig().getFloat(key, defaultValue);
	}

	@Override
	public int getInt(String key) {
		return getConfig().getInt(key);
	}

	@Override
	public int getInt(String key, int defaultValue) {
		return getConfig().getInt(key, defaultValue);
	}

	@Override
	public Integer getInteger(String key, Integer defaultValue) {
		return getConfig().getInteger(key, defaultValue);
	}

	@Override
	public long getLong(String key) {
		return getConfig().getLong(key);
	}

	@Override
	public long getLong(String key, long defaultValue) {
		return getConfig().getLong(key, defaultValue);
	}

	@Override
	public Long getLong(String key, Long defaultValue) {
		return getConfig().getLong(key, defaultValue);
	}

	@Override
	public short getShort(String key) {
		return getConfig().getShort(key);
	}

	@Override
	public short getShort(String key, short defaultValue) {
		return getConfig().getShort(key, defaultValue);
	}

	@Override
	public Short getShort(String key, Short defaultValue) {
		return getConfig().getShort(key, defaultValue);
	}

	@Override
	public BigDecimal getBigDecimal(String key) {
		return getConfig().getBigDecimal(key);
	}

	@Override
	public BigDecimal getBigDecimal(String key, BigDecimal defaultValue) {
		return getConfig().getBigDecimal(key, defaultValue);
	}

	@Override
	public BigInteger getBigInteger(String key) {
		return getConfig().getBigInteger(key);
	}

	@Override
	public BigInteger getBigInteger(String key, BigInteger defaultValue) {
		return getConfig().getBigInteger(key, defaultValue);
	}

	@Override
	public String getString(String key) {
		return getConfig().getString(key);
	}

	@Override
	public String getString(String key, String defaultValue) {
		return getConfig().getString(key, defaultValue);
	}

	@Override
	public String[] getStringArray(String key) {
		return getConfig().getStringArray(key);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getList(String key) {
		return getConfig().getList(key);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getList(String key, List defaultValue) {
		return getConfig().getList(key, defaultValue);
	}
	
}
