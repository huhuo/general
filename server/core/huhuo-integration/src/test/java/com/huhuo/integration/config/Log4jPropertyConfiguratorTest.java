package com.huhuo.integration.config;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Log4jPropertyConfiguratorTest {
	private static Logger logger ;
	
	@Test
	public void printMsg(){
		logger = LoggerFactory.getLogger(Log4jPropertyConfiguratorTest.class);
		System.out.println("core start");
		ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext("coreContext-main.xml");
		logger.debug("Initialize applicationContext.xml......"+appContext);
		System.out.println("core end");
	}
}
