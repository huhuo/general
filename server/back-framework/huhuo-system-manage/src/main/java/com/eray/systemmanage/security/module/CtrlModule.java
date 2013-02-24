package com.eray.systemmanage.security.module;

import java.io.OutputStream;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.huhuo.integration.base.BaseCtrl;
import com.huhuo.integration.db.mysql.Condition;
import com.huhuo.integration.util.CheckUtils;
import com.huhuo.integration.util.ExtUtils;
import com.huhuo.integration.web.FormMessage;
import com.huhuo.integration.web.Message;
import com.huhuo.integration.web.Message.Status;

@Controller("smCtrlModule")
@RequestMapping(value="/sm/security/module")
public class CtrlModule extends BaseCtrl {
	private String basePath = "system-manage/security/module/";
	@Resource(name = "smServModule")
	private IServModule servModule;
	
	
	@RequestMapping(value="/index.do")
	public String index(){
		return basePath + "index";
	}
	
	@RequestMapping(value="/platform.do")
	public String platform(){
		return basePath + "platform";
	}
	
	@RequestMapping(value="/module.do")
	public String module(){
		return basePath + "module";
	}
	
	@RequestMapping(value="/menu.do")
	public String menu(){
		return basePath + "menu";
	}
	
	@RequestMapping(value="/get-children.do")
	public void get(String node, OutputStream out){
		logger.debug("server receive: node={}", node);
		try{
			Long parentId = null;
			if("root".equals(node)){
				parentId = 0L;
			}else{
				parentId = Long.valueOf(node);
			}
			List<ModelModule> records = servModule.findByParentId(parentId);
			long total = records.size();
			write(ExtUtils.getJsonStore(records, total), out);
		}catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			write(new Message<String>(Status.ERROR, e.getMessage()), out);
		}
	}
	
	@RequestMapping(value="/get-module.do")
	public void getModule(ModelModule module, Condition<ModelModule> condition, OutputStream out){
		logger.debug("server receive: module={}, condition={}", module, condition);
		try{
			module.setLevel(1);
			condition.setT(module);
			get(condition, out);
		}catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			write(new Message<String>(Status.ERROR, e.getMessage()), out);
		}
	}
	
	@RequestMapping(value="/get/{id}.do")
	public void getById(@PathVariable("id") String id, OutputStream out){
		logger.debug("server receive: moduleId={}", id);
		FormMessage<ModelModule> m = new FormMessage<ModelModule>();
		try{
			ModelModule r = servModule.find(id);
			m.setData(r);
			m.setStatus(Status.SUCCESS);
			if(r==null){
				m.setStatus(Status.FAILURE);
			}
		}catch (Exception e) {
			m.setStatus(Status.ERROR);
			m.setMsg("操作失败");
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		write(m, out);
	}
	
	public void get(Condition<ModelModule> condition, OutputStream out){
		List<ModelModule> records = servModule.findByCondition(condition);
		Long total = servModule.countByCondition(condition);
		write(ExtUtils.getJsonStore(records, total), out);
	}
	
	@RequestMapping(value="/add.do")
	public void add(ModelModule module, OutputStream out){
		logger.debug("server receive: module={}", module);
		try{
			Message<String> r = valid(module);
			if(r.getStatus()==Status.SUCCESS){
				if(servModule.find(module.getId())!=null){
					r.setMsg("添加失败，id重复");
					r.setStatus(Status.FAILURE);
				}else{
					int row = servModule.add(module);
					if(row>0){
						r.setMsg("添加成功");
					}else{
						r.setMsg("添加失败");
						r.setStatus(Status.FAILURE);
					}
				}
			}
			write(r, out);
		}catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			write(new FormMessage<String>(Status.ERROR, e.getMessage()), out);
		}
	}
	
	@RequestMapping(value="/update.do")
	public void update(ModelModule module, OutputStream out){
		logger.debug("server receive: module={}", module);
		try{
			Message<String> r = valid(module);
			if(r.getStatus()==Status.SUCCESS){
				int row = servModule.update(module);
				if(row>0){
					r.setMsg("更新成功");
				}else{
					r.setMsg("更新失败");
					r.setStatus(Status.FAILURE);
				}
			}
			write(r, out);
		}catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			write(new FormMessage<String>(Status.ERROR, e.getMessage()), out);
		}
	}
	
	
	@RequestMapping(value="/delete.do")
	public void delete(ModelModule module, OutputStream out){
		logger.debug("server receive: module={}", module);
		try{
			int row = servModule.delete(module);
			if(row>0){
				write(new FormMessage<String>(Status.SUCCESS, "删除成功"), out);
			}else{
				write(new FormMessage<String>(Status.FAILURE, "删除失败"), out);
			}
			
		}catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			write(new FormMessage<String>(Status.ERROR, e.getMessage()), out);
		}
	}
	
	
	protected Message<String> valid(ModelModule module){
		Status status = Status.FAILURE;
		String msg = null;
		if(module.getId()==null){
			msg="id字段不能为空";
		}else if(!CheckUtils.validLength(1, 100, module.getName())){
			msg="name字段长度应该在1-100个字符之间";
		}else if(module.getOrderNo()==null){
			msg = "orderNo字符不能为空";
		}else if(module.getParentId()==null){
			msg ="parenetId字段不能为空";
		}else{
			status = Status.SUCCESS;
		}
		
		return new Message<String>(status, msg);
	}
}
