package com.huhuo.componentweb.core;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.huhuo.componentweb.HuhuoComponentWebBaseTest;

public class ServHcwHttpClientTest extends HuhuoComponentWebBaseTest {

	@Resource(name = "componentwebCoreHttpClientServ")
	private IServHcwHttpClient iServHcwHttpClient;
	
	private String baseUrl =
			"http://61.4.184.65:80/dataService"
//			"http://192.168.1.181:8080/mobile-service/" 
//			"http://localhost:8080/mobile-service/"
			;
	
	@Test
	public void postAsStream() {
		String url = baseUrl + "/smartWeather";
		List<String> paramList = new ArrayList<String>();
		paramList.add("{'method':'geo','param':{'gps':{'xy':'120.2512728,23.1416985'}}}");	// 
		paramList.add("{'method':'geo','param':{'gps':{'xy':'128.485,35.847'}}}");	// 
		paramList.add("{'method':'geo','param':{'gps':{'xy':'128.485,35.847'}}}");	// DAQIU city KOREA
		List<String> resultList = new ArrayList<String>();
		for(String param : paramList) {
			resultList.add(iServHcwHttpClient.postAsStream(url, param, true, true, true).getData());
		}
		print(resultList);
	}
	
}
