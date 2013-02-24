package com.eray.systemmanage.authority;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.eray.systemmanage.BaseTest;
import com.eray.systemmanage.security.role.IServRole;
import com.eray.systemmanage.security.role.ModelRole;
import com.huhuo.integration.db.mysql.Condition;
import com.huhuo.integration.db.mysql.Page;

public class ServRoleTest extends BaseTest {
	@Resource(name="smServRole")
	private IServRole servRole;
	
	@Test
	public void add(){
		ModelRole t = new ModelRole();
		t.setName("test");
		t.setComment("test comment");
		
		System.out.println("-->add:" + servRole.add(t));
	}
	
	@Test
	public void update(){
		ModelRole t = servRole.find(1L);
		t.setName(t.getName()+"update");
		t.setComment(t.getComment()+"update");
		
		System.out.println("-->update:" + servRole.update(t));
	}
	
	@Test
	public void delete(){
		ModelRole t = new ModelRole();
		t.setId(1L);
		
		System.out.println("-->delete:" + servRole.delete(t));
	}
	
	@Test
	public void findCountByCondition(){
		Condition<ModelRole> condition = new Condition<ModelRole>();
		ModelRole t = new ModelRole();
		//t.setId(1L);
		t.setName("test");
		condition.setT(t);
		
		Page page = new Page(0,1);
		condition.setPage(page);
		
		System.out.println("-->findByCondition:");
		List<ModelRole> list = servRole.findByCondition(condition);
		print(list);
		System.out.println("-->countByCondition:" + servRole.countByCondition(condition));
	}
	
}
