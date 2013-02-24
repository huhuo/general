package com.eray.systemmanage.city;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eray.systemmanage.province.ModelProvince;
import com.huhuo.integration.base.BaseCtrl;
import com.huhuo.integration.db.mysql.Condition;
import com.huhuo.integration.exception.HuhuoException;
import com.huhuo.integration.util.CheckUtils;
import com.huhuo.integration.util.ExtUtils;
import com.huhuo.integration.web.Message;
import com.huhuo.integration.web.Message.Status;


@Controller("smCtrlSysCity")
@RequestMapping(value="/sm/syscity")
public class CtrlSysCity extends BaseCtrl {
	@Resource(name="smServSysCity")
	protected IServSysCity servSysCity;
	
	/**
	 * 根据参数city的属性搜索符合条件的城市列表，如果city.getProvinceId()==0,则视所有城市的provinceId都符合此项条件
	 * 
	 * @param city
	 *            搜索的城市属性满足此参数的相关非空属性
	 * @param condition
	 *            包含分页及排序
	 * @param out
	 * @throws IOException
	 */
	
	@RequestMapping(value="/get.do")
	public void get(ModelSysCity city, Condition<ModelSysCity> condition, OutputStream out){
		try{
			logger.debug("server receive: city={},condition={}", city, condition);
			
			if (ModelProvince.ALL_PROVINCE_ID.equals(city.getProvinceId())) {
				city.setProvinceId(null);
			}
			condition.setT(city);
			List<ModelSysCity> records = servSysCity.findByCondition(condition);
			Long total = servSysCity.countByCondition(condition);
			write(ExtUtils.getJsonStore(records, total), out);
		} catch (HuhuoException e) {
			logger.warn(e.getMessage());
			write(new Message<String>(Status.FAILURE, e.getMessage()), out);
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			write(new Message<String>(Status.ERROR, e.getMessage()), out);
		}
	}
	
	/**
	 * 根据参数搜索符合条件的城市，如果provinceIds只有一个元素且值为
	 * {@link ModelProvince#ALL_PROVINCE_ID}， 仅返回城市id为
	 * {@link ModelSysCity#ALL_CITY_ID}的城市；
	 * 
	 * @param provinceIds
	 *            搜索的城市所属省份必须是此列表中的一个
	 * @param includeAll
	 *            true：结果包括Id=={@link ModelSysCity#ALL_CITY_ID}的城市；false：不包括
	 * @param condition
	 *            包含分页及排序
	 * @param out
	 * @throws IOException
	 */
	@RequestMapping(value="/getbypids.do")
	public void getbypids(Long[] provinceIds, boolean includeAll,
			Condition<ModelSysCity> condition, OutputStream out) {
		try {
			logger.debug(
					"server receive: provinceIds={},includeAll={},condition={}",
					new Object[] { Arrays.toString(provinceIds), includeAll,
							condition });

			List<Long> pidList = setProvinceIds(condition, provinceIds);
			boolean searchDb = isSearchDb(pidList);

			List<ModelSysCity> records = null;
			long total = 0;
			if (searchDb) {
				records = servSysCity.findByCondition(condition);
				total = servSysCity.countByCondition(condition);
			} else {
				records = new ArrayList<ModelSysCity>();
			}

			if (includeAll) {
				records.add(0, getModelCityNamedAll());
				total++;
			}
			write(ExtUtils.getJsonStore(records, total), out);
		} catch (HuhuoException e) {
			logger.warn(e.getMessage());
			write(new Message<String>(Status.FAILURE, e.getMessage()), out);
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			write(new Message<String>(Status.ERROR, e.getMessage()), out);
		}
	}
	
	protected List<Long> setProvinceIds(Condition<ModelSysCity> condition, Long[] provinceIds){
		if(provinceIds!=null && provinceIds.length>0){
			Map<String, Object> opt = new HashMap<String, Object>();

			List<Long> pidList = Arrays.asList(provinceIds);
			opt.put(ModelSysCity.OPTKEY_PROVINCE_IDS, pidList);
			condition.setOpt(opt);
			return pidList;
		}
		
		return null;
	}
	
	/**
	 * 如果pidList长度为1，并且元素值为{@link ModelProvince#ALL_PROVINCE_ID}则返回false；否则返回true
	 * 
	 * @param pidList
	 * @return
	 */
	protected boolean isSearchDb(List<Long> pidList){
		if(pidList!=null && pidList.size()==1){
			Long pid = pidList.get(0);
			if(ModelProvince.ALL_PROVINCE_ID.equals(pid)){
				return false;
			}
		}
		return true;
	}
	
	
	public ModelSysCity getModelCityNamedAll(){
		ModelSysCity c = new ModelSysCity();
		c.setName(ModelSysCity.ALL_CITY_NAME);
		c.setId(ModelSysCity.ALL_CITY_ID);
		return c;
	}
	
	
	public ModelSysCity getModelCityNamedNo(){
		ModelSysCity c = new ModelSysCity();
		c.setName(ModelSysCity.NO_CITY_NAME);
		c.setId(ModelSysCity.NO_CITY_ID);
		return c;
	}
	
	
	@RequestMapping(value = "/add.do")
	public void add(ModelSysCity city, OutputStream out) {
		try {
			logger.debug("server receive: city={}", city);

			int r = 0;
			Message<String> msg = validCity(city);
			if (msg.getStatus() == Status.SUCCESS) {
				try {
					r = servSysCity.add(city);
				} catch (Exception e) {
					logger.debug("debug exception: "
							+ ExceptionUtils.getFullStackTrace(e));
				}
			}
			if (r > 0) {
				msg.setStatus(Status.SUCCESS);
				msg.setMsg("城市添加成功");
			} else {
				msg.setStatus(Status.FAILURE);
				msg.setMsg("城市添加失败");
			}

			write(msg, out);
		} catch (HuhuoException e) {
			logger.warn(e.getMessage());
			write(new Message<String>(Status.FAILURE, e.getMessage()), out);
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			write(new Message<String>(Status.ERROR, e.getMessage()), out);
		}
	}
	
	@RequestMapping(value = "/update.do")
	public void update(ModelSysCity city, OutputStream out) {
		try {
			logger.debug("server receive: city={}", city);

			int r = 0;
			Message<String> msg = validCity(city);
			if (msg.getStatus() == Status.SUCCESS) {
				try {
					r = servSysCity.update(city);
				} catch (Exception e) {
					logger.debug("debug exception: "
							+ ExceptionUtils.getFullStackTrace(e));
				}
			}
			if (r > 0) {
				msg.setStatus(Status.SUCCESS);
				msg.setMsg("城市更新成功");
			} else {
				msg.setStatus(Status.FAILURE);
				msg.setMsg("城市更新失败");
			}

			write(msg, out);
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			write(new Message<String>(Status.ERROR, e.getMessage()), out);
		}
	}
	
	@RequestMapping(value="/delete.do")
	public void delete(ModelSysCity city, OutputStream out){
		try{
			logger.debug("server receive: city={}", city);
			int r = 0;
			if(city.getId()!=null){
				try{
					r = servSysCity.delete(city);
				}catch (Exception e) {
					logger.debug("debug exception: "+ExceptionUtils.getFullStackTrace(e));
				}
			}
			Message<String> msg = new Message<String>(Status.FAILURE, "城市删除失败");
			if(r>0){
				msg.setStatus(Status.SUCCESS);
				msg.setMsg("城市删除成功");
			}
			
			write(msg, out);
		}catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			write(new Message<String>(Status.ERROR, e.getMessage()), out);
		}
	}
	
	

	protected Message<String> validCity(ModelSysCity city){
		String msg = "";
		Status status = Status.FAILURE;
		if(!CheckUtils.isAllNull(city.getId(),city.getOrderNo(),city.getLevel())) {
			msg = "id,orderNo,cityLevel不能为空";
		}
		else if(!CheckUtils.validLength(1, 11, city.getId())){
			msg = "id字段长度应该在1-11个字符之间";			
		}else if(!CheckUtils.validLength(1, 20, city.getName(),city.getSpelling(),city.getZipCode())){
			msg = "cityName,spelling,zipCode字段长度应该在1-20个字符之间";	
		}else if(!CheckUtils.isNull(city.getProvinceId()))
		{
			msg = "provinceName不能为空";
		} else{
			status = Status.SUCCESS;
		}
		return new Message<String>(status, msg);
	}
}
