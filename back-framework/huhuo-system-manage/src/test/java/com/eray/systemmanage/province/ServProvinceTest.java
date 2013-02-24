package com.eray.systemmanage.province;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.eray.systemmanage.BaseTest;
import com.huhuo.integration.db.mysql.Condition;
import com.huhuo.integration.db.mysql.Page;

public class ServProvinceTest extends BaseTest {
	@Resource(name="smServProvince")
	private IServProvince servProvince;
	
	@Test
	public void add(){
		ModelProvince p = new ModelProvince();
		p.setId(0L);
		p.setHasMetaData(false);
		p.setMapAreaId("ss");
		p.setOrderNo(1);
		p.setName("添加省");
		p.setSpelling("tianjia");
	
		System.out.println("-->add:"+servProvince.add(p));
	}
	
	@Test
	public void find(){
		System.out.println("-->find:" + servProvince.find(10101));
	}
	
	@Test
	public void update(){
		ModelProvince p = servProvince.find(0L);
		System.out.println("-->find:" + p);
		p.setHasMetaData(true);
		p.setMapAreaId("update" + p.getMapAreaId());
		p.setOrderNo(p.getOrderNo()+1);
		p.setName("update" + p.getName());
		p.setSpelling("update" + p.getSpelling());
		
		System.out.println("-->update:" + servProvince.update(p));
	}
	
	@Test
	public void delete(){
		ModelProvince p = servProvince.find(0L);
		System.out.println("-->delete:" + servProvince.delete(p));
	}
	
	@Test
	public void findCountByCondition(){
		Condition<ModelProvince> condition = new Condition<ModelProvince>();
		ModelProvince t = new ModelProvince();
		t.setId(1010L);
		condition.setT(t);
		
		Page p = new Page();
		p.setStart(0);
		p.setLimit(5);
		condition.setPage(p);
		
		List<ModelProvince> list = servProvince.findByCondition(condition);
		for(ModelProvince ele: list)
			System.out.println(ele);
		System.out.println(servProvince.countByCondition(condition));
	}
	
}
