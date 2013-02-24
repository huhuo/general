package com.eray.systemmanage.security.relation;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eray.systemmanage.constant.EResourceType;
import com.huhuo.integration.base.BaseCtrl;
import com.huhuo.integration.exception.HuhuoException;
import com.huhuo.integration.util.ExtUtils;
import com.huhuo.integration.web.Message;
import com.huhuo.integration.web.Message.Status;

@Controller("smCtrlAuthorityResource")
@RequestMapping(value="/sm/security/authorityresource")
public class CtrlAuthorityResource extends BaseCtrl {
	@Resource(name="smServAuthorityResource")
	private IServAuthorityResource servAuthorityResource;
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/related-res.do")
	public void relatedRes(Long type, Long authorityId, Long start, Long limit, OutputStream out){
		logger.debug("server receive: type={}, authorityId={}, start={}, limit={}", new Object[]{type, authorityId, start, limit});
		try{
			EResourceType t = EResourceType.valueOf(type);
			List list = servAuthorityResource.relatedResources(authorityId, t, start, limit);
			Long count = servAuthorityResource.countRelatedResources(authorityId, t);
			write(ExtUtils.getJsonStore(list, count), out);
		}catch (HuhuoException e) {
			logger.warn(ExceptionUtils.getFullStackTrace(e));
			write(new Message<String>(Status.FAILURE, e.getMessage()), out);
		}catch (Exception e){
			logger.error(ExceptionUtils.getFullStackTrace(e));
			write(new Message<String>(Status.ERROR, e.getMessage()), out);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/unrelated-res.do")
	public void unrelatedRes(Long type, Long authorityId, OutputStream out){
		logger.debug("server receive: type={}, authorityId={}=", new Object[]{type, authorityId});
		try{
			EResourceType t = EResourceType.valueOf(type);
			List list = servAuthorityResource.unrelatedResources(authorityId, t, null, null);
			Long count = servAuthorityResource.countUnrelatedResources(authorityId, t);
			write(ExtUtils.getJsonStore(list, count), out);
		}catch (HuhuoException e) {
			logger.warn(ExceptionUtils.getFullStackTrace(e));
			write(new Message<String>(Status.FAILURE, e.getMessage()), out);
		}catch (Exception e){
			logger.error(ExceptionUtils.getFullStackTrace(e));
			write(new Message<String>(Status.ERROR, e.getMessage()), out);
		}
	}
	
	@RequestMapping(value="/relate-res.do")
	public void relateRes(Long type, Long authorityId, Long[] resourceIds, OutputStream out){
		List<Long> resourceList = null;
		if(resourceIds!=null){
			resourceList= Arrays.asList(resourceIds);
		}else{
			resourceList = new ArrayList<Long>(0);
		}
		logger.debug("server receive: type={}, authorityId={}, resourceIds={}", new Object[]{type, authorityId, resourceList});
		try{
			EResourceType t = EResourceType.valueOf(type);
			
			servAuthorityResource.deleteByAuthorityId(authorityId, t);
			int row = servAuthorityResource.addCollection(authorityId, resourceList, t);
			
			write(new Message<String>(Status.SUCCESS, "关联成功，关联记录数："+ row), out);
		}catch (Exception e){
			logger.error(ExceptionUtils.getFullStackTrace(e));
			write(new Message<String>(Status.ERROR, e.getMessage()), out);
		}
	}
	
}
