package com.huhuo.integration.service;

import javax.annotation.Resource;

import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.huhuo.integration.config.GlobalConstant;
import com.huhuo.integration.service.ZHttpClientServ;

@ContextConfiguration(locations="classpath:config/app-context-service.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ZHttpClientServTest {
	@Resource(name="zHttpClientServ")
	private ZHttpClientServ zHttpClientServ;
	String url = "http://localhost:8080/mobile-service/monitorService";
	@Test
	public void printServ() {
		System.out.println(zHttpClientServ.getParameter(HttpClientParams.USE_EXPECT_CONTINUE));
		System.out.println(zHttpClientServ.getParameter(HttpConnectionManagerParams.MAX_HOST_CONNECTIONS));
		System.out.println(zHttpClientServ.getParameter(HttpConnectionParams.CONNECTION_TIMEOUT));
		System.out.println(zHttpClientServ.getParameter(HttpClientParams.SO_TIMEOUT));
		System.out.println(zHttpClientServ.getParameter(HttpClientParams.HTTP_CONTENT_CHARSET));
		System.out.println(zHttpClientServ.getParameter(HttpClientParams.HTTP_ELEMENT_CHARSET));
		
		System.out.println(zHttpClientServ.getParameter(HttpConnectionManagerParams.MAX_TOTAL_CONNECTIONS));
		System.out.println(zHttpClientServ.getHttpConnectionManager().getParams().getParameter(HttpConnectionManagerParams.MAX_TOTAL_CONNECTIONS));
	}
	
	@Test
	public void get(){
		String url = "http://m.weather.com.cn/mobile/FcAndSk/101010100.html";
		System.out.println(zHttpClientServ.get(url, GlobalConstant.Encoding.UTF8, false));
	}
	
	@Test
	public void getRtnBytes(){
		String url = "http://m.weather.com.cn/mobile/FcAndSk/101010100.html";
		System.out.println(zHttpClientServ.getRtnBytes(url, true));
	}
	
	@Test
	public void post(){
		System.out.println(zHttpClientServ.post(url, GlobalConstant.Encoding.UTF8, true));
	}

	@Test
	public void postByStream(){
		String b= zHttpClientServ.postAsStream(url, "{'method':'connMemcache'}", GlobalConstant.Encoding.UTF8, true, GlobalConstant.Encoding.UTF8).getData();
		System.out.println(b);
	}
	
	@Test
	public void postAsStreamRtnBytes(){
		byte[] b= zHttpClientServ.postAsStreamRtnBytes(url, "{'method':'connMemcache'}", GlobalConstant.Encoding.UTF8, true).getData();
		System.out.println(new String(b));
	}
	
}
