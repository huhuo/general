package com.huhuo.webbase.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.huhuo.integration.exception.HuhuoException;
import com.huhuo.integration.web.Message;
import com.huhuo.integration.web.Message.Status;
import com.huhuo.webbase.constant.Constant.GeneralPage;

@Service("huhuowebbaseHuhuoExceptionHandler")
public class HuhuoExceptionHandler implements HandlerExceptionResolver {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private GeneralPage generalPage = GeneralPage.EXCEPTION_PAGE;

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		ModelAndView modelAndView = new ModelAndView(generalPage.getLocation());
		if (ex instanceof HuhuoException) {
			logger.warn(ex.getMessage());
			modelAndView.addObject(generalPage.getAttrName(), new Message<String>(Status.FAILURE, ex.getMessage()));
		} else {
			logger.error(ExceptionUtils.getFullStackTrace(ex));
			modelAndView.addObject(generalPage.getAttrName(), new Message<String>(Status.ERROR, ex.getMessage()));
		}
		return modelAndView;
	}

}
