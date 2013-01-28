package com.huhuo.integration.config;

import java.io.InputStream;
import java.util.Hashtable;
import java.util.Map;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 管理properties文件的类
 * 
 * @author shifengxuan
 *
 */
public class PropertiesConfigurationManager {
	private Map<String, PropertiesConfiguration> configMap;
	private Logger logger = LoggerFactory.getLogger(PropertiesConfigurationManager.class);
	private static PropertiesConfigurationManager instance = new PropertiesConfigurationManager();
	
	private PropertiesConfigurationManager(){
		configMap = new Hashtable<String, PropertiesConfiguration>();
	}
	
	protected Map<String, PropertiesConfiguration> getConfigMap(){
		return configMap;
	}
	
	public static PropertiesConfigurationManager getInstance(){
		return instance;
	}
	
	/**
	 * 获取PropertiesConfiguration
	 * 
	 * @param path properties文件的路径
	 * @return PropertiesConfiguration的对象，如果文件不存在返回new PropertiesConfiguration();
	 */
	public PropertiesConfiguration getPropertiesConfiguration(String path){
		PropertiesConfiguration config = configMap.get(path);
		try {
			if(config==null){
				InputStream configFile = getClass().getResourceAsStream(path);
				config = new PropertiesConfiguration();
				config.load(configFile);
				configMap.put(path, config);
			}
		} catch (Exception e) {
			logger.debug("no such properties file {}", path);
		}
		return config;
	}
}
