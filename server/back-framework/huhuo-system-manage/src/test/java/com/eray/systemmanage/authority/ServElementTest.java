package com.eray.systemmanage.authority;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.eray.systemmanage.BaseTest;
import com.eray.systemmanage.security.element.IServElement;
import com.eray.systemmanage.security.element.ModelElement;
import com.huhuo.integration.db.mysql.Condition;
import com.huhuo.integration.db.mysql.Page;

public class ServElementTest extends BaseTest {
	@Resource(name = "smServElement")
	private IServElement servElement;

	@Test
	public void add() {
		ModelElement ele = new ModelElement();
		ele.setComment("test comment");
		ele.setName("testButton");
		ele.setLocation("sm_province_index");

		System.out.println("-->add:" + servElement.add(ele));
	}

	@Test
	public void find() {
		Long id = 1L;

		System.out.println("-->find:" + servElement.find(id));
	}
	
	@Test
	public void update(){
		ModelElement ele = servElement.find(1L);
		ele.setComment(ele.getComment()+"update");
		ele.setName(ele.getName()+"update");
		ele.setLocation(ele.getLocation() + "update");
		
		System.out.println("-->update:" + servElement.update(ele));
	}
	
	@Test
	public void delete(){
		ModelElement ele = new ModelElement();
		ele.setId(1L);
		
		System.out.println("-->delete:" + servElement.delete(ele));
	}
	
	
	@Test
	public void findCountByCondition(){
		Condition<ModelElement> condition = new Condition<ModelElement>();
		ModelElement t = new ModelElement();
		t.setName("test");
		condition.setT(t);
		
		Page page = new Page(0, 10);
		condition.setPage(page);
		
		System.out.println("-->findByCondition:");
		List<ModelElement> list = servElement.findByCondition(condition);
		print(list);
		System.out.println("-->countByCondition:" + servElement.countByCondition(condition));
	}
}
