package com.eray.systemmanage;

import java.util.List;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations="classpath:conf/system-manage/app-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class BaseTest {
	
	protected <T> void print(List<T> list){
		if(list!=null){
			for(T ele: list){
				System.out.println(ele);
			}
		}
	}
	
}
