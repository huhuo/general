package com.eray.systemmanage.authority;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.eray.systemmanage.BaseTest;
import com.eray.systemmanage.security.relation.IServRoleAuthority;

public class ServRoleAuthorityTest extends BaseTest {
	@Resource(name="smServRoleAuthority")
	private IServRoleAuthority servRoleAuthority;
	
	@Test
	public void addCollection(){
		List<Long> roleIds = new ArrayList<Long>();
		roleIds.add(1L);
		roleIds.add(2L);
		List<Long> authorityIds = new ArrayList<Long>();
		authorityIds.add(3L);
		authorityIds.add(4L);
		System.out.println("-->addCollection:" + servRoleAuthority.addCollection(roleIds, authorityIds));
	}
	
	@Test
	public void deleteByRoleId(){
		Long roleId = 1L;
		System.out.println("-->deleteByRoleId:" + servRoleAuthority.deleteByRoleId(roleId));
	}
	
	@Test
	public void deleteByAuthorityId(){
		Long authorityId = 3L;
		System.out.println("-->deleteByAuthorityId:" + servRoleAuthority.deleteByAuthorityId(authorityId));
	}
}
