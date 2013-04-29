package com.huhuo.componentweb.core;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;

import com.huhuo.componentweb.HuhuoComponentWebBaseTest;

public class ServHcwMemcachedTest extends HuhuoComponentWebBaseTest {

	@Resource(name = "componentwebMemcachedService")
	private IServHcwMemcached iServHcwMemcached;
	
	@Test
	public void crud() {
		String region = "region";
		String key = "haha";
		String value = "撒的发来的";
		print(iServHcwMemcached.set(region, key, value));
		Object object = iServHcwMemcached.get(region, key);
		print("retrieve object in memecached -->" + object);
		Assert.assertEquals("failed to add object to memcached!!!", value, object);
		print(iServHcwMemcached.delete(region, key));
		object = iServHcwMemcached.get(region, key);
		print("retrieve object in memecached -->" + object);
		Assert.assertNull("failed to delete object in memcached!!!", object);
	}
	
}
