package com.eray.systemmanage.city;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import com.eray.systemmanage.BaseTest;
import com.huhuo.integration.db.mysql.Condition;

public class ServSysCityTest extends BaseTest{
	@Resource(name="smServSysCity")
	private IServSysCity servSysCity;
	
	@Test
	public void add(){
		ModelSysCity city = new ModelSysCity();
		city.setId(0L);
		city.setName("添加市");
		city.setSpelling("TJS");
		city.setZipCode("072750");
		city.setLevel(1);
		city.setOrderNo(1);
		city.setProvinceId(10109L);
		System.out.println("-->add:"+servSysCity.add(city));
	}
	
	@Test
	public void find(){
		System.out.println("-->find:" + servSysCity.find(101010100));
	}
	
	@Test
	public void delete(){
		ModelSysCity city = new ModelSysCity();
		city.setId(0L);
		System.out.println("-->delete:"+servSysCity.delete(city));
	}
	
	@Test
	public void update(){
		ModelSysCity city = servSysCity.find(0L);
		System.out.println("-->city:"+city);
		if(city!=null){
			city.setSpelling("TJS+update");
			city.setName("update" + city.getName());
			city.setOrderNo(city.getOrderNo()+1);
			city.setProvinceId(10L);
			city.setSpelling("update"+city.getSpelling());
			city.setZipCode("update"+city.getZipCode());
			city.setLevel(city.getLevel()+1);
			System.out.println("-->update success:"+servSysCity.update(city));
			
			city.setId(null);
			System.out.println("-->update fail:"+servSysCity.update(city));
		}else
		{
			System.out.println("-->update fail:"+servSysCity.update(city));
		}
	}
	
	@Test
	public void findCountByCondition(){
		Condition<ModelSysCity> c = new Condition<ModelSysCity>();
		Map<String, Object> opt = new HashMap<String, Object>();
		
		List<Integer> provinceIds = new ArrayList<Integer>();
		provinceIds.add(10101);
		provinceIds.add(10102);
		opt.put(ModelSysCity.OPTKEY_PROVINCE_IDS, provinceIds);
		c.setOpt(opt);
		
		ModelSysCity city = new ModelSysCity();
		city.setName("山");
		c.setT(city);
		
		List<ModelSysCity> list = servSysCity.findByCondition(c);
		Long count = servSysCity.countByCondition(c);
		
		System.out.println("-->findByCondition:" + count);
		for (ModelSysCity ele : list) {
			System.out.println(ele);
		}
	}
}
