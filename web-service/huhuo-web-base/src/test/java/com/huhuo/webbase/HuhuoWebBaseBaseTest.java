package com.huhuo.webbase;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.huhuo.integration.base.BaseTest;

@ContextConfiguration(locations={"classpath:conf/huhuo-web-base/app-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class HuhuoWebBaseBaseTest extends BaseTest {
	
}
