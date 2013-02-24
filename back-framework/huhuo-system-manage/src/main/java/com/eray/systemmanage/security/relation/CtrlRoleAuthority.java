package com.eray.systemmanage.security.relation;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eray.systemmanage.security.authority.ModelAuthority;
import com.huhuo.integration.base.BaseCtrl;
import com.huhuo.integration.util.ExtUtils;
import com.huhuo.integration.web.Message;
import com.huhuo.integration.web.Message.Status;

@Controller("smCtrlRoleAuthority")
@RequestMapping(value="/sm/security/roleauthority")
public class CtrlRoleAuthority extends BaseCtrl {
	@Resource(name="smServRoleAuthority")
	private IServRoleAuthority servRoleAuthority;
	
	@RequestMapping(value="/related-authorities.do")
	public void relatedAuthorities(Long roleId, Long start, Long limit, OutputStream out){
		logger.debug("server receive: roleId={}, start={}, limit={}", new Object[]{roleId, start, limit});
		try{
			List<ModelAuthority> records = servRoleAuthority.relatedAuthorities(roleId, start, limit);
			Long count = servRoleAuthority.countRelatedAuthorities(roleId);
			write(ExtUtils.getJsonStore(records, count), out);
		}catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			write(new Message<String>(Status.ERROR, e.getMessage()), out);
		}
	}
	
	@RequestMapping(value="/unrelated-authorities.do")
	public void unrelatedAuthorities(Long roleId, OutputStream out){
		logger.debug("server receive: roleId={}");
		try{
			List<ModelAuthority> records = servRoleAuthority.unrelatedAuthorities(roleId, null, null);
			Long count = servRoleAuthority.countUnrelatedAuthorities(roleId);
			write(ExtUtils.getJsonStore(records, count), out);
		}catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			write(new Message<String>(Status.ERROR, e.getMessage()), out);
		}
	}
	
	@RequestMapping(value="/relate-authorities.do")
	public void relateAuthorities(Long roleId, Long[] authorityIds, OutputStream out){
		List<Long> authList = null;
		if(authorityIds!=null){
			authList = Arrays.asList(authorityIds);
		}else{
			authList = new ArrayList<Long>();
		}
		logger.debug("server receive: roleId={}, authorityIds={}", roleId, authList);
		try{
			//delete old relation
			servRoleAuthority.deleteByRoleId(roleId);
			//add new relation
			int row = servRoleAuthority.addCollection(roleId, authList);
			write(new Message<String>(Status.SUCCESS, "关联成功，关联记录数："+ row), out);
		}catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			write(new Message<String>(Status.ERROR, e.getMessage()), out);
		}
	}
}
