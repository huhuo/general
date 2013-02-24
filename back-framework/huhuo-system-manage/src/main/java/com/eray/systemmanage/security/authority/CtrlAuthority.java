package com.eray.systemmanage.security.authority;

import java.io.OutputStream;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.huhuo.integration.base.BaseCtrl;
import com.huhuo.integration.db.mysql.Condition;
import com.huhuo.integration.util.CheckUtils;
import com.huhuo.integration.util.ExtUtils;
import com.huhuo.integration.web.Message;
import com.huhuo.integration.web.Message.Status;

@Controller("smCtrlAuthority")
@RequestMapping(value="/sm/security/authority")
public class CtrlAuthority extends BaseCtrl {
	private String basePath = "system-manage/security/authority/";
	@Resource(name="smServAuthority")
	private IServAuthority servAuthority;
	
	@RequestMapping(value="/index.do")
	public String index(){
		return basePath + "index";
	}
	
	
	@RequestMapping(value="/get.do")
	public void get(ModelAuthority authority, Condition<ModelAuthority> condition, OutputStream out){
		logger.debug("server receive: authority={}, condition={}", authority, condition);
		try{
			condition.setT(authority);
			List<ModelAuthority> records = servAuthority.findByCondition(condition);
			Long count = servAuthority.countByCondition(condition);
			write(ExtUtils.getJsonStore(records, count), out);
		}catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			write(new Message<String>(Status.ERROR, e.getMessage()), out);
		}
	}
	
	@RequestMapping(value="/save.do")
	public void save(ModelAuthority authority, OutputStream out){
		logger.debug("server receive: authority={}", authority);
		Message<String> r = null;
		try{
			if(authority.getId()==null){	// add
				r =valid(authority);
				if(r.getStatus() == Status.SUCCESS){
					int row = servAuthority.add(authority);
					if(row>0){
						r = new Message<String>(Status.SUCCESS, "添加成功");
					}else{
						r = new Message<String>(Status.FAILURE, "添加失败");
					}
				}
			}else{							// update
				r =valid(authority);
				if(r.getStatus() == Status.SUCCESS){
					int row = servAuthority.update(authority);
					if(row>0){
						r = new Message<String>(Status.SUCCESS, "更新成功");
					}else{
						r = new Message<String>(Status.FAILURE, "更新失败");
					}
				}
			}
			
		}catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			r =new Message<String>(Status.ERROR, e.getMessage());
		}
		write(r, out);
	}
	
	@RequestMapping(value="/delete.do")
	public void delete(ModelAuthority authority, OutputStream out){
		logger.debug("server receive: authority={}", authority);
		Message<String> r = null;
		try{
			int row = servAuthority.delete(authority);
			if(row>0){
				r = new Message<String>(Status.SUCCESS, "删除成功");
			}else{
				r = new Message<String>(Status.FAILURE, "删除失败");
			}
		}catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			r = new Message<String>(Status.ERROR, e.getMessage());
		}
		write(r, out);
	}
	
	protected Message<String> valid(ModelAuthority authority){
		String msg = null;
		Status status = Status.FAILURE;
		if(!CheckUtils.validLength(1, 255, authority.getName())){
			msg = "name字段长度应该在1-255个字符之间";
		}else if(!CheckUtils.validLength(0, 255, authority.getComment())){
			msg = "comment字段长度不应超过255个字符";
		}else{
			status = Status.SUCCESS;
		}
		
		return new Message<String>(status, msg);
	}
	
}
