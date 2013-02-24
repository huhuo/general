package com.eray.systemmanage.province;

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

@Controller("smCtrlProvince")
@RequestMapping(value="/sm/province")
public class CtrlProvince extends BaseCtrl {
	@Resource(name="smServProvince")
	private IServProvince servProvince;
	private String basePath = "system-manage/province/";
	
	@RequestMapping(value="/index.do")
	public String index(){
		return basePath + "index";
	}
	
	@RequestMapping(value="/get.do")
	public void get(ModelProvince province, Condition<ModelProvince> condition, boolean includeAll, OutputStream out){
		try{
			logger.debug("server receive: province={},condition={}", province, condition);
			
			if(ModelProvince.ALL_PROVINCE_ID.equals(province.getId())){
				province.setId(null);
			}
			condition.setT(province);
			List<ModelProvince> list = servProvince.findByCondition(condition);
			long c = servProvince.countByCondition(condition);
			if(includeAll){
				list.add(0, getModelProvinceNamedAll());
				c ++;
			}
			write(ExtUtils.getJsonStore(list, c), out);
		}catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			write(new Message<String>(Status.ERROR, e.getMessage()), out);
		}
	}
	
	public ModelProvince getModelProvinceNamedAll(){
		ModelProvince p = new ModelProvince();
		p.setId(ModelProvince.ALL_PROVINCE_ID);
		p.setName(ModelProvince.ALL_PROVINCE_NAME);
		return p;
	}
	
	@RequestMapping(value="/add.do")
	public void add(ModelProvince province, OutputStream out){
		try{
			logger.debug("server receive: province={}", province);
			
			int r = 0;
			Message<String> msg = validProvince(province);
			if(msg.getStatus()==Status.SUCCESS){
				if(servProvince.find(province.getId())!=null){
					msg.setStatus(Status.FAILURE);
					msg.setMsg("省份添加失败，id重复");
				}else{
					r = servProvince.add(province);
					if(r>0){
						msg.setStatus(Status.SUCCESS);
						msg.setMsg("省份添加成功");
					}else{
						msg.setStatus(Status.FAILURE);
						msg.setMsg("省份添加失败");
					}
				}
			}
			
			write(msg, out);
		}catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			write(new Message<String>(Status.ERROR, e.getMessage()), out);
		}
	}
	
	@RequestMapping(value="/update.do")
	public void update(ModelProvince province, OutputStream out){
		try{
			logger.debug("server receive: province={}", province);
			
			int r = 0;
			Message<String> msg = validProvince(province);
			if(msg.getStatus()==Status.SUCCESS){
				try{
					r = servProvince.update(province);
				}catch(Exception e){
					logger.debug("debug exception: "+ExceptionUtils.getFullStackTrace(e));
				}
			}
			if(r>0){
				msg.setStatus(Status.SUCCESS);
				msg.setMsg("省份更新成功");
			}else{
				msg.setStatus(Status.FAILURE);
				msg.setMsg("省份更新失败");
			}
			
			write(msg, out);
		}catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			write(new Message<String>(Status.ERROR, e.getMessage()), out);
		}
	}
	
	@RequestMapping(value="/delete.do")
	public void delete(ModelProvince province, OutputStream out){
		try{
			logger.debug("server receive: province={}", province);
			int r = 0;
			if(province.getId()!=null){
				try{
					r = servProvince.delete(province);
				}catch (Exception e) {
					logger.error(ExceptionUtils.getFullStackTrace(e));
				}
			}
			Message<String> msg = new Message<String>(Status.FAILURE, "省份删除失败");
			if(r>0){
				msg.setStatus(Status.SUCCESS);
				msg.setMsg("省份删除成功");
			}
			
			write(msg, out);
		}catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			write(new Message<String>(Status.ERROR, e.getMessage()), out);
		}
	}
	
	protected Message<String> validProvince(ModelProvince province){
		String msg = "";
		Status status = Status.FAILURE;
		if(!CheckUtils.isNoNull(province.getId(),province.getOrderNo())) {
			msg = "id,orderNo不能为空";
		}
		else if(!CheckUtils.validLength(1, 11, province.getId().toString())){
			msg = "id字段长度应该在1-11个字符之间";			
		}else if(!CheckUtils.validLength(1, 50, province.getName(),province.getSpelling())){
			msg = "provinceName,spelling字段长度应该在1-50个字符之间";	
		}else if(!CheckUtils.validLength(0, 20, province.getMapAreaId()))
		{
			msg = "mapAreaId字段长度应该在1-20个字符之间";
		} else{
			status = Status.SUCCESS;
		}
		return new Message<String>(status, msg);
	}
}
