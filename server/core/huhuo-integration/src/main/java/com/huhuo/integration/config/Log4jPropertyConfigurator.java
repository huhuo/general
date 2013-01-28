package com.huhuo.integration.config;

import org.apache.log4j.xml.DOMConfigurator;

public class Log4jPropertyConfigurator extends DOMConfigurator {
	private String log4jConfigLocation;

	public String getLog4jConfigLocation() {
		return log4jConfigLocation;
	}

	public void setLog4jConfigLocation(String log4jConfigLocation) {
		this.log4jConfigLocation = log4jConfigLocation;
		configure(ClassLoader.getSystemResource(log4jConfigLocation));
	}
}
