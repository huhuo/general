package com.eray.systemmanage.security.role;

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

@Controller("smCtrlRole")
@RequestMapping(value="/sm/security/role")
public class CtrlRole extends BaseCtrl {
	private String basePath = "system-manage/security/role/";
	
	@Resource(name="smServRole")
	private IServRole servRole;
	
	@RequestMapping(value="/index.do")
	public String index(){
		return basePath + "index";
	}
	
	@RequestMapping(value="/get.do")
	public void get(ModelRole role, Condition<ModelRole> condition, OutputStream out){
		logger.debug("server receive: condition={}, role={}", condition, role);
		try{
			condition.setT(role);
			List<ModelRole> list = servRole.findByCondition(condition);
			Long count = servRole.countByCondition(condition);
			write(ExtUtils.getJsonStore(list, count), out);
		}catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			write(new Message<String>(Status.ERROR, e.getMessage()), out);
		}
	}
	
	@RequestMapping(value="/save.do")
	public void save(ModelRole role, OutputStream out){
		logger.debug("server receive: role={}", role);
		Message<String> r = null;
		try{
			r = valid(role);
			if(role.getId()==null){	// add
				if(r.getStatus()==Status.SUCCESS){
					int row = servRole.add(role);
					if(row>0){
						r = new Message<String>(Status.SUCCESS, "添加成功");
					}else{
						r = new Message<String>(Status.FAILURE, "添加失败");
					}
				}
			}else{					// update
				if(r.getStatus()==Status.SUCCESS){
					int row = servRole.update(role);
					if(row>0){
						r = new Message<String>(Status.SUCCESS, "更新成功");
					}else{
						r = new Message<String>(Status.FAILURE, "更新失败");
					}
				}
			}
		}catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			r = new Message<String>(Status.ERROR, e.getMessage());
		}
		write(r, out);
	}
	
	@RequestMapping(value="/delete.do")
	public void delete(ModelRole role, OutputStream out){
		logger.debug("server receive: role={}", role);
		Message<String> r = null;
		try{
			if(role.getId()!=null){
				int row = servRole.delete(role);
				if(row>0){
					r = new Message<String>(Status.SUCCESS, "删除成功");
				}else{
					r = new Message<String>(Status.FAILURE, "删除失败");
				}
			}else{
				r = new Message<String>(Status.ERROR, "删除失败，用户id为空");
			}
			
		}catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			r = new Message<String>(Status.ERROR, e.getMessage());
		}
		write(r, out);
	}
	
	protected Message<String> valid(ModelRole role){
		Message<String> r = new Message<String>();
		if(CheckUtils.isNull(role.getName())){
			r.setMsg("角色名不能为空");
			r.setStatus(Status.FAILURE);
		}else{
			r.setStatus(Status.SUCCESS);
		}
		return r;
	}
}
