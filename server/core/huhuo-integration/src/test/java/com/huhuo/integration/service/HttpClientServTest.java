package com.huhuo.integration.service;

import javax.annotation.Resource;

import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import com.huhuo.integration.HuhuoIntegrationTest;
import com.huhuo.integration.config.GlobalConstant;

@ContextConfiguration(locations="classpath:conf/app-context-service.xml")
public class HttpClientServTest extends HuhuoIntegrationTest {
	@Resource(name="huhuointegrationHttpClientServ")
	private HttpClientServ httpClientServ;
	String url = "http://localhost:8080/back-framework/cmcar/car/";
	@Test
	public void printServ() {
		System.out.println(httpClientServ.getParameter(HttpClientParams.USE_EXPECT_CONTINUE));
		System.out.println(httpClientServ.getParameter(HttpConnectionManagerParams.MAX_HOST_CONNECTIONS));
		System.out.println(httpClientServ.getParameter(HttpConnectionParams.CONNECTION_TIMEOUT));
		System.out.println(httpClientServ.getParameter(HttpClientParams.SO_TIMEOUT));
		System.out.println(httpClientServ.getParameter(HttpClientParams.HTTP_CONTENT_CHARSET));
		System.out.println(httpClientServ.getParameter(HttpClientParams.HTTP_ELEMENT_CHARSET));
		
		System.out.println(httpClientServ.getParameter(HttpConnectionManagerParams.MAX_TOTAL_CONNECTIONS));
		System.out.println(httpClientServ.getHttpConnectionManager().getParams().getParameter(HttpConnectionManagerParams.MAX_TOTAL_CONNECTIONS));
	}
	
	@Test
	public void get(){
		String url = "http://m.weather.com.cn/mobile/FcAndSk/101010100.html";
		System.out.println(httpClientServ.get(url, GlobalConstant.Encoding.UTF8, false));
	}
	
	@Test
	public void getRtnBytes(){
		String url = "http://m.weather.com.cn/mobile/FcAndSk/101010100.html";
		System.out.println(httpClientServ.getRtnBytes(url, true));
	}
	
	@Test
	public void post(){
		System.out.println(httpClientServ.post(url, GlobalConstant.Encoding.UTF8, true));
	}

	@Test
	public void postAsStream(){
		String b= httpClientServ.postAsStream(url, "{'method':'connMemcache'}", GlobalConstant.Encoding.UTF8, true, GlobalConstant.Encoding.UTF8).getData();
		System.out.println(b);
	}
	
	@Test
	public void postAsStreamRtnBytes(){
		byte[] b= httpClientServ.postAsStreamRtnBytes(url, "{'method':'connMemcache'}", GlobalConstant.Encoding.UTF8, true).getData();
		System.out.println(new String(b));
	}
	
}
