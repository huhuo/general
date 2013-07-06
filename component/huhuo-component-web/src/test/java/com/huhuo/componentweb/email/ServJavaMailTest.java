package com.huhuo.componentweb.email;

import java.util.Properties;

import javax.annotation.Resource;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.huhuo.componentweb.HuhuoComponentWebBaseTest;

public class ServJavaMailTest extends HuhuoComponentWebBaseTest {

	@Resource(name = "componentwebCoreProps")
	private Properties componentwebCoreProps;
	
	@Resource(name = "huhuoAdminServJavaMail")
	private IServJavaMail huhuoAdminServJavaMail;
	
	@Test
	public void huhuoAdminSend() {
		System.out.println(JSON.toJSONString(componentwebCoreProps));
		ModelJavaMail javamail = new ModelJavaMail();
//		javamail.setReceiver("743791095@qq.com");
		javamail.setReceiver("binhong@eraymobile.com");
		javamail.setContent("Testing only \n\n Hello Spring 12345，来自chinaweatherServJavaMail");
		javamail.setTitle("Testing123");
		huhuoAdminServJavaMail.send(javamail);
	}
	
}
