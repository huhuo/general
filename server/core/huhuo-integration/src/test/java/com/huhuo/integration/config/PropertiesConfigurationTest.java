package com.huhuo.integration.config;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.huhuo.integration.algorithm.HuhuoEncryptUtils;

@ContextConfiguration(locations="classpath:config/app-context-util.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class PropertiesConfigurationTest {
	
	@Test
	public void getPropertiesConfiguration() {
		System.out.println(HuhuoEncryptUtils.getKey());
		System.out.println(HuhuoEncryptUtils.getEncoding());
	}
}
