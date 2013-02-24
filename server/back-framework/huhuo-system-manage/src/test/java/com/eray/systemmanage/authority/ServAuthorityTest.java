package com.eray.systemmanage.authority;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.eray.systemmanage.BaseTest;
import com.eray.systemmanage.security.authority.IServAuthority;
import com.eray.systemmanage.security.authority.ModelAuthority;
import com.huhuo.integration.db.mysql.Condition;
import com.huhuo.integration.db.mysql.Page;

public class ServAuthorityTest extends BaseTest {
	@Resource(name="smServAuthority")
	private IServAuthority servAuthority;
	
	@Test
	public void add(){
		ModelAuthority t = new ModelAuthority();
		t.setName("testName");
		t.setComment("test comment");
		
		System.out.println("-->add:" + servAuthority.add(t));
	}
	
	@Test
	public void update(){
		ModelAuthority t = servAuthority.find(1L);
		t.setComment(t.getComment()+"update");
		t.setName(t.getName()+"update");
		
		System.out.println("-->update:" + servAuthority.update(t));
	}
	
	@Test
	public void delete(){
		ModelAuthority t = new ModelAuthority();
		t.setId(1L);
		
		System.out.println("-->delete:" + servAuthority.delete(t));
	}
	
	@Test
	public void findCountByCondition(){
		Condition<ModelAuthority> condition = new Condition<ModelAuthority>();
		
		ModelAuthority t = new ModelAuthority();
		//t.setId(1L);
		t.setName("test");
		condition.setT(t);
		
		Page page = new Page(0, 1);
		condition.setPage(page);
		
		System.out.println("-->findByCondition:");
		List<ModelAuthority> list = servAuthority.findByCondition(condition);
		print(list);
		
		System.out.println("-->countByCondition:" + servAuthority.countByCondition(condition));
	}
}
