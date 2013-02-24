package com.eray.systemmanage.authority;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.eray.systemmanage.BaseTest;
import com.eray.systemmanage.security.relation.IServUserRole;

public class ServUserRoleTest extends BaseTest {
	@Resource(name="smServUserRole")
	private IServUserRole servUserRole;
	
	@Test
	public void addCollection(){
		List<Long> userIds = new ArrayList<Long>();
		userIds.add(1L);
		userIds.add(2L);
		List<Long> roleIds = new ArrayList<Long>();
		roleIds.add(3L);
		roleIds.add(4L);
		
		System.out.println("-->addCollection:" + servUserRole.addCollection(userIds, roleIds));
	}
	
	@Test
	public void deleteByUserId(){
		Long userId = 1L;
		
		System.out.println("-->deleteByUserId:" + servUserRole.deleteByUserId(userId));
	}
	
	@Test
	public void deleteByRoleId(){
		Long roleId = 3L;
		
		System.out.println("-->deleteByRoleId:" + servUserRole.deleteByRoleId(roleId));
	}
	
}
