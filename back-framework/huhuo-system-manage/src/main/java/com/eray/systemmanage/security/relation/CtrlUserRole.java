package com.eray.systemmanage.security.relation;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eray.systemmanage.security.role.ModelRole;
import com.huhuo.integration.base.BaseCtrl;
import com.huhuo.integration.util.ExtUtils;
import com.huhuo.integration.web.Message;
import com.huhuo.integration.web.Message.Status;

@Controller("smCtrlUserRole")
@RequestMapping(value="/sm/security/userrole")
public class CtrlUserRole extends BaseCtrl {
	@Resource(name="smServUserRole")
	private IServUserRole servUserRole;
	
	@RequestMapping(value="/unrelated-roles.do")
	public void unrelatedRoles(OutputStream out, Long userId, Long start, Long limit){
		logger.debug("server receive: userId={}, start={}, limit={}", new Object[]{userId, start, limit});
		try{
			List<ModelRole> records = servUserRole.unrelatedRoles(userId, start, limit);
			Long count = servUserRole.countUnrelatedRoles(userId);
			
			write(ExtUtils.getJsonStore(records, count), out);
		}catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			write(new Message<String>(Status.ERROR, e.getMessage()), out);
		}
		
	}
	
	@RequestMapping(value="/related-roles.do")
	public void relatedroles(OutputStream out, Long userId){
		logger.debug("server receive: userId={}", userId);
		try{
			List<ModelRole> records = servUserRole.relatedRoles(userId, null, null);
			Long count = servUserRole.countRelatedRoles(userId);
			
			write(ExtUtils.getJsonStore(records, count), out);
		}catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			write(new Message<String>(Status.ERROR, e.getMessage()), out);
		}
	}
	
	@RequestMapping(value="/relate-roles.do")
	public void relateroles(OutputStream out, Long userId, Long[] roleIds){
		try{
			List<Long> roleList = new ArrayList<Long>();
			if(roleIds!=null){
				roleList = Arrays.asList(roleIds);
			}
			logger.debug("server receive: userId={}", userId, roleList);
			//delete old relation
			servUserRole.deleteByUserId(userId);
			//add new relation
			Integer r = servUserRole.addCollection(userId, roleList);
			
			write(new Message<String>(Status.SUCCESS, "关联成功，关联记录数："+ r), out);
		}catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			write(new Message<String>(Status.ERROR, e.getMessage()), out);
		}
	}
}
