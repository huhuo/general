package com.huhuo.integration.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.huhuo.integration.service.MemcachedServ;

@ContextConfiguration(locations = "classpath:config/app-context-memcached.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class MemcachedServTest {
	@Resource(name = "memcachedService")
	private MemcachedServ memServ;
	String region = "region1";
	String key = "key1";
	String value = "hello1";

	@Test
	public void setGetNormal() {
		/**
		 * normal test
		 */

		memServ.set(region, key, value);
		System.out.println("get value-->" + memServ.get(region, key));
	}

	@Test
	public void getSetLongKey() {
		/**
		 * key too long test
		 */
		key = "key1kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk"+
		 "kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk"+
				"kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk";
		boolean r = memServ.set(region, key, value);
		if (!r) {
			System.out.println(memServ.get(region, key));
			System.out.println("key too long exception-->maxlen = 250");

			/**
			 * compress key if key too long
			 */
			memServ.set(region, key, value, true);
			System.out.println(memServ.get(region, key, true));
		}
	}

	@Test
	public void getSetExpiration() throws InterruptedException {
		/**
		 * expiration test
		 */
		memServ.set(region, key, value, 1, 5);
		System.out.println("not until deadline-->" + memServ.get(region, key));
		Thread.sleep(1000);
		System.out.println("until deadline-->" + memServ.get(region, key));
	}
}
