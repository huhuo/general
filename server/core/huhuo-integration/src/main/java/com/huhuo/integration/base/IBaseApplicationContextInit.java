package com.huhuo.integration.base;

import org.springframework.context.ApplicationContext;

import com.huhuo.integration.service.ApplicationContextAwareServ;

/**
 * All subclass of {@link #BaseApplicationContextInit} should implements the
 * method {@link #init(ApplicationContext)}.If you configure the bean of
 * {@link ApplicationContextAwareServ}, this method will be called when
 * applicationcontext has been initialized.
 * 
 * @author shifengxuan
 * 
 */
public interface IBaseApplicationContextInit {
	boolean init(ApplicationContext applicationContext);
}
