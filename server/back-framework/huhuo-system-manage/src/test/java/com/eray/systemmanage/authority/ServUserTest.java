package com.eray.systemmanage.authority;

import javax.annotation.Resource;

import org.junit.Test;

import com.eray.systemmanage.BaseTest;
import com.eray.systemmanage.constant.EResourceType;
import com.eray.systemmanage.security.user.IServUser;
import com.eray.systemmanage.security.user.ModelUser;
import com.huhuo.integration.db.mysql.Condition;
import com.huhuo.integration.db.mysql.Page;

public class ServUserTest extends BaseTest {
	@Resource(name="smServUser")
	private IServUser servUser;
	
	@Test
	public void add(){
		ModelUser t = new ModelUser();
		t.setUsername("user2");
		t.setPassword("weather");
		t.setCellphone("13025507926");
		t.setContactPhone("0311-3712298");
		t.setDepartmentId(1L);
		t.setEmail("weather@126.com");
		t.setProvinceId(10101L);
		t.setActiveStatus(false);
	    t.setRemark("comment kk");
		
	    System.out.println("-->add:" + servUser.add(t));
	}
	
	@Test
	public void update(){
		ModelUser t = servUser.find(1L);
		t.setUsername("tom");
		t.setPassword("weatherupdate");
		t.setCellphone("13025507927");
		t.setContactPhone("0311-3712299");
		t.setDepartmentId(2L);
		t.setEmail("weather@163.com");
		t.setProvinceId(10102L);
		t.setRemark("comment aa");
		
		System.out.println("-->update:" + servUser.update(t));
	}
	
	@Test
	public void delete(){
		ModelUser t = new ModelUser();
		t.setId(1L);
		System.out.println("-->update:" + servUser.update(t));
	}
	
	@Test
	public void findCountByCondition(){
		Condition<ModelUser> condition = new Condition<ModelUser>();
		ModelUser t = new ModelUser();
		/*t.setId(2L);
		t.setDepartmentId(1L);
		t.setProvinceId(10101L);*/
//		t.setCellphone("130");
//		t.setContactPhone("371");
//		t.setEmail("weather");
//		t.setUsername("user");
		t.setActiveStatus(true);
		condition.setT(t);
		
		Page page = new Page(0,1);
		condition.setPage(page);
		
		System.out.println("-->findByCondition:");
		print(servUser.findByCondition(condition));
		
		System.out.println("-->countByCondition:" + servUser.countByCondition(condition));
	}
	
	@Test
	public void getResource(){
		ModelUser user = new ModelUser();
//		user.setUsername(ModelUser.ADMIN);
		user.setId(1L);
		
		EResourceType type = 
//				EResourceType.MODULE
//				EResourceType.ELEMENT
				EResourceType.PROVINCE
				;
		
		System.out.println("-->getResource:");
		print(servUser.getResource(user, type));
	}
}
