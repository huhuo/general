package com.huhuo.integration.service;

import java.util.Iterator;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.huhuo.integration.base.IBaseApplicationContextInit;

/**
 * ApplicationContext service is used for:<br>
 * 1.call method {@link IBaseApplicationContextInit#init(ApplicationContext)}
 * after if get the instance of applicationContext<br>
 * 2.get the instance of applicationContext through call {@link #getApplicationContext()}
 * 
 * @author shifengxuan
 * 
 */
public class ApplicationContextAwareServ implements ApplicationContextAware {
	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		ApplicationContextAwareServ.applicationContext = applicationContext;
		init();
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	protected void init(){
		Map<String, IBaseApplicationContextInit> map = applicationContext.getBeansOfType(IBaseApplicationContextInit.class);
		Iterator<String> iter = map.keySet().iterator();
		while(iter.hasNext()){
			map.get(iter.next()).init(applicationContext);
		}
	}
}
