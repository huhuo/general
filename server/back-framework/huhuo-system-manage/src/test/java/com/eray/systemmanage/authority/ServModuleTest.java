package com.eray.systemmanage.authority;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import com.eray.systemmanage.BaseTest;
import com.eray.systemmanage.security.module.IServModule;
import com.eray.systemmanage.security.module.ModelModule;
import com.huhuo.integration.db.mysql.Condition;
import com.huhuo.integration.db.mysql.Page;

public class ServModuleTest extends BaseTest{
	@Resource(name="smServModule")
	private IServModule servModule;
	@Test
	public void findAll(){
		List<ModelModule> list = servModule.findAll();
		for(ModelModule ele: list)
			System.out.println(ele.getParent());
	}
	
	@Test
	public void test(){
		System.out.println(servModule.find(5));
		System.out.println(servModule.find(5));
	}
	
	@Test
	public void getModuleGroupedByParentId(){
		 Map<ModelModule, List<ModelModule>> r = servModule.getModuleGroupedByParentId();
		 Iterator<ModelModule> it = r.keySet().iterator();
		 while(it.hasNext()){
			 ModelModule m = it.next();
			 System.out.println("====================");
			 System.out.println(m);
			 System.out.println("====================");
			 List<ModelModule> list = r.get(m);
			 print(list);
		 }
	}
	
	
	@Test
	public void add() {
		ModelModule t = new ModelModule();
		t.setIcon("res/image/icon/module.png");
		t.setLevel(1);
		t.setLeaf(false);
		t.setName("module1");
		t.setOrderNo(1);
		t.setParentId(1L);
		t.setUrl("test/url");
		t.setVisible(false);
		

		System.out.println("-->add:" + servModule.add(t));
	}
	
	@Test
	public void find(){
		Long id = 1L;
		System.out.println("-->find:" + servModule.find(id));
	}
	
	@Test
	public void update(){
		ModelModule t = servModule.find(8L);
		System.out.println("-->value:" + t);
		t.setIcon(t.getIcon()+"update");
		t.setLeaf(true);
		t.setLevel(t.getLevel()+1);
		t.setName(t.getName()+"update");
		t.setOrderNo(t.getOrderNo()+1);
		t.setParentId(t.getParentId()+1);
		t.setUrl(t.getUrl()+"update");
		t.setVisible(true);
		
		System.out.println("-->update:" + servModule.update(t));
	}
	
	@Test
	public void delete(){
		ModelModule t = new ModelModule();
		t.setId(8L);
		
		System.out.println("-->delete:" + servModule.delete(t));
	}
	
	@Test
	public void findCountByCondition(){
		Condition<ModelModule> condition = new Condition<ModelModule>();
		ModelModule t = new ModelModule();
		t.setName("管理");
		t.setVisible(true);
		condition.setT(t);
		
		Page page = new Page(0, 1);
		condition.setPage(page);
		
		System.out.println("-->findByCondition:");
		List<ModelModule> list = servModule.findByCondition(condition);
		print(list);
		
		System.out.println("-->countByCondition:"+ servModule.countByCondition(condition));
		
	}
}
