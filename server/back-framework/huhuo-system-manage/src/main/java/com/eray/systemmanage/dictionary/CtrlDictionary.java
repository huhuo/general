package com.eray.systemmanage.dictionary;

import java.io.OutputStream;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eray.systemmanage.constant.Dictionary.GroupName;
import com.huhuo.integration.base.BaseCtrl;
import com.huhuo.integration.exception.HuhuoException;
import com.huhuo.integration.util.CheckUtils;
import com.huhuo.integration.util.ExtUtils;
import com.huhuo.integration.web.Message;
import com.huhuo.integration.web.Message.Status;


@Controller("smCtrlDictionary")
@RequestMapping(value="/sm/dictionary")
public class CtrlDictionary extends BaseCtrl {
	@Resource(name = "smServDictionary")
	private IServDictionary servDictionary;
	private String PAGE_PATH = "system-manage/dictionary/";
	
	@RequestMapping(value="/index.do")
	public String index(){
		return PAGE_PATH + "index";
	}
	
	//====================================
		/**
		 * 模块管理主页面
		 * @param aa
		 * @return
		 */	
		
		@RequestMapping(value = "/dictModuleGrid")
		public String dictModuleGrid(String aa){
			return PAGE_PATH + "/dictModuleGrid";
		}
	
	@RequestMapping(value="/add.do")
	public void add(ModelDictionary dictionary, OutputStream out) {
		try { 
			logger.debug("获取参数：dictionary = {}", dictionary);
			
			int r =0;
			Message<String> msg = validDictionary(dictionary);
			if(msg.getStatus()==Status.SUCCESS) {
				try {
					r = servDictionary.add(dictionary);
				} catch (Exception e) {
					logger.debug("debug exception: " + ExceptionUtils.getFullStackTrace(e));
				}
			}
			if(r>0) {
				msg.setStatus(Status.SUCCESS);
				msg.setMsg("字典添加成功");
			}else {
				msg.setStatus(Status.FAILURE);
				msg.setMsg("字典添加失败");
			}
			write(msg, out);
		} catch(Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			write(new Message<String>(Status.ERROR, e.getMessage()), out);
		} 
	}
	
	@RequestMapping(value = "/update.do")
	public void update(OutputStream out, ModelDictionary dictionary){
		try { 
			logger.debug("获取参数：dictionary = {}", dictionary);
			
			int r =0;
			Message<String> msg = validDictionary(dictionary);
			if(msg.getStatus()==Status.SUCCESS){
				try {
					r = servDictionary.update(dictionary);
				} catch (Exception e) {
					logger.debug("debug exception: " + ExceptionUtils.getFullStackTrace(e));
				}
			}
			if(r==1){
				msg.setStatus(Status.SUCCESS);
				msg.setMsg("字典更新成功");
			}else{
				msg.setStatus(Status.FAILURE);
				msg.setMsg("字典更新失败");
			}
			write(msg, out);
		} catch(Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			write(new Message<String>(Status.ERROR, e.getMessage()), out);
		} 
	}
	
	@RequestMapping(value="/delete.do")
	public void delete(OutputStream out, ModelDictionary dictionary){
		try { 
			logger.debug("server receive: dictionary={}", dictionary);
			
			int r = 0;
			Message<String> msg = validDictionary(dictionary);
			if(dictionary.getId() != null) {// 数据合格
				try {
					r = servDictionary.delete(dictionary);
				} catch (Exception e) {
					logger.debug("debug exception: " + ExceptionUtils.getFullStackTrace(e));
				}
			} 
			if(r > 0) {
				msg.setStatus(Status.SUCCESS);
				msg.setMsg("删除成功");
			}else {
				msg.setStatus(Status.FAILURE);
				msg.setMsg("删除失败");
			}
			write(msg, out);
		} catch(Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			write(new Message<String>(Status.ERROR, e.getMessage()), out);
		} 
	}
	
	
	
	@RequestMapping(value = "/getByGroupName.do")
	public void getByGroupName(OutputStream out, String groupName){
		try { 
			logger.debug("server receive: groupName={}", groupName);
			if(groupName == null || groupName == ""){
				groupName = null;
			}else{
				groupName = groupName.toUpperCase();
			}
			List<ModelDictionary> records = servDictionary.findByGroupNameOrdByDictKeyAsc(GroupName.valueOf(groupName));
			write(ExtUtils.getJsonStore(records, (long)records.size()), out);
		} catch(HuhuoException e) {
			logger.warn(e.getMessage());
			write(new Message<String>(Status.FAILURE, e.getMessage()), out);
		} 
	}
	
	@RequestMapping(value = "/get.do")
	public void getDictModulePage(OutputStream out, Integer page, Integer start, Integer limit,
			ModelDictionary dictionary){
		logger.debug("server receive: limit={},dictionary={}", limit, dictionary);
		try {
			List<ModelDictionary> records = servDictionary.selectByAsPage(dictionary, start, limit);
			write(ExtUtils.getJsonStore(records, (long)records.size()), out);
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			write(new Message<String>(Status.FAILURE, e.getMessage()), out);
		}
	}
	
	
	protected Message<String> validDictionary(ModelDictionary dictionary){
		String msg = "";
		Status status = Status.FAILURE;
		if(!CheckUtils.isNoNull(dictionary.getGroupName(), dictionary.getDictKey(), dictionary.getDictDisplayName())){
			msg = "groupName,dictKey,dictValue不能为空";
		}
		else if(!CheckUtils.validLength(1, 30, dictionary.getGroupName())){
			msg = "groupName字段长度应该在1-30个字符之间";			
		}else if(!CheckUtils.validLength(0, 50, dictionary.getGroupDisplayName())){
			msg = "groupDisplayName字段长度应该在0-50个字符之间";
		}else if(!CheckUtils.validLength(1, 50, dictionary.getDictKey())){
			msg = "dictKey字段长度应该在1-50个字符之间";	
		}else if(!CheckUtils.validLength(0, 100, dictionary.getDictValue())){
			msg = "dictValue字段长度应该在0-100个字符之间";
		}else if(!CheckUtils.validLength(1, 50, dictionary.getDictDisplayName())){
			msg = "dictDisplayName字段长度应该在1-50个字符之间";
		}else if(!CheckUtils.validLength(0, 1000, dictionary.getOrderNo())){
			msg = "orderNo字段长度应该在0-1000个字符之间";
		}else if(!CheckUtils.validLength(0, 1000, dictionary.getComment())) {
			msg = "comment字段长度应该在0-1000个字符之间";
		}else{
			status = Status.SUCCESS;
		}
		return new Message<String>(status, msg);
	}
	
	
	
	
	
	
	
	/**
	 * 测试进度   相关字典项数据源
	 *//*
	@RequestMapping(value="/dicManageStore")
	public void dicManageStore(OutputStream out){
		List<SysDictionary> records = null;
		Long total = 0L;
		try { 
			List<String> groupNameList = new ArrayList<String>();
			groupNameList.add("biz_report_priority");
			groupNameList.add("biz_report_bugType");
			groupNameList.add("biz_report_moduleBelonged");
			records = dictionaryService.findByGroupNames(groupNameList);
			total = dictionaryService.countByGroupNames(groupNameList);
			writeJson(out, new JsonStore<SysDictionary>(records, total));
		} catch(Exception e) {
			logger.error("appDataDevStore ERROR --> {}", ExceptionUtils.getStackTrace(e));
			writeJson(out, new Message<String>(Status.FAILURE, "服务器内部错误"));
		} 
	}
	*//**
	 *  测试进度    字典表  添加数据 
	 *//*
	@RequestMapping(value="/dicManageAdd")
	public void dicManageAdd(OutputStream out, SysDictionary sysDictionary){
		Message<String> message = null;
		try { 
			String groupName = sysDictionary.getGroupName();
			Long dictKey = dictionaryService.findMaxKey(groupName)+1L;
			List<String> groupNameList = new ArrayList<String>();
			groupNameList.add(groupName);
			List<SysDictionary> list = dictionaryService.findByGroupNames(groupNameList);
			sysDictionary.setDictKey(dictKey.toString());
			sysDictionary.setGroupDisplayName(list.get(0).getGroupDisplayName());
			message = validDict(sysDictionary);
			if(message.getStatus()==Status.SUCCESS){
				int i = dictionaryService.add(sysDictionary);
				if(i==1){
					message.setMsg("数据添加成功");
					message.setStatus(Status.SUCCESS);
				}else{
					message.setMsg("数据添加失败");
				}
			}
			logger.debug("sysDictController/dicManageAdd = {}", message);
			writeJson(out, message);
		} catch(Exception e) {
			logger.error("sysDictController/dicManageAdd ERROR --> {}", ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
			writeJson(out, new Message<String>(Status.FAILURE, "服务器内部错误"));
		} 
	}
	*//**
	 * 测试进度  字典表 修改数据
	 *//*
	@RequestMapping(value="/dicManageUpdate")
	public void dicManageUpdate(OutputStream out, SysDictionary sysDictionary){
		Message<String> message = null;
		try { 
			String groupName = sysDictionary.getGroupName();
			//Long dictKey = dictionaryService.findMaxKey(groupName)+1L;
			List<String> groupNameList = new ArrayList<String>();
			groupNameList.add(groupName);
			List<SysDictionary> list = dictionaryService.findByGroupNames(groupNameList);
			//sysDictionary.setDictKey(dictKey.toString());
			sysDictionary.setGroupName(groupName);
			sysDictionary.setGroupDisplayName(list.get(0).getGroupDisplayName());
			message = validDict(sysDictionary);
			if(message.getStatus()==Status.SUCCESS){
				int i = dictionaryService.update(sysDictionary);
				if(i==1){
					message.setMsg("数据更新成功");
					message.setStatus(Status.SUCCESS);
				}else{
					message.setMsg("数据更新失败");
				}
			}
			logger.debug("sysDictController/dicManageUpdate = {}", message);
			writeJson(out, message);
		} catch(Exception e) {
			logger.error("sysDictController/dicManageUpdate ERROR --> {}", ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
			writeJson(out, new Message<String>(Status.FAILURE, "服务器内部错误"));
		} 
	}
	*//**
	 * 测试进度   相关字典项数据源
	 *//*
	@RequestMapping(value="/dictDevProveStore")
	public void dictDevProveStore(OutputStream out){
		List<SysDictionary> records = null;
		Long total = 0L;
		try { 
			List<String> groupNameList = new ArrayList<String>();
			groupNameList.add("biz_report_taskSort");
			groupNameList.add("report_progress_stat");
			records = dictionaryService.findByGroupNames(groupNameList);
			total = dictionaryService.countByGroupNames(groupNameList);
			writeJson(out, new JsonStore<SysDictionary>(records, total));
		} catch(Exception e) {
			logger.error("dictDevProveStore ERROR --> {}", ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
			writeJson(out, new Message<String>(Status.FAILURE, "服务器内部错误"));
		} 
	}*/
	
	
	
	
	
}
