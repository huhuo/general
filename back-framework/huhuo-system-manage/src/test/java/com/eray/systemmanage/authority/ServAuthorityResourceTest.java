package com.eray.systemmanage.authority;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.eray.systemmanage.BaseTest;
import com.eray.systemmanage.constant.EResourceType;
import com.eray.systemmanage.security.relation.IServAuthorityResource;

public class ServAuthorityResourceTest extends BaseTest {
	@Resource(name="smServAuthorityResource")
	private IServAuthorityResource servAuthorityResource;
	
	@Test
	public void addCollection(){
		List<Long> authorityIds = new ArrayList<Long>();
		authorityIds.add(1L);
		authorityIds.add(2L);
		
		List<Long> resourceIds = new ArrayList<Long>();
		resourceIds.add(1L);
		resourceIds.add(2L);
		
		EResourceType type = EResourceType.MODULE;
		
		System.out.println("-->addCollection:" + servAuthorityResource.addCollection(authorityIds, resourceIds, type));
	}
	
	@Test
	public void deleteByAuthorityId(){
		Long authorityId = 1L;
		System.out.println("-->deleteByAuthorityId:" + servAuthorityResource.deleteByAuthorityId(authorityId));
	}
	
	@Test
	public void deleteByResourcedId(){
		Long resourceId = 1L;
		EResourceType type = EResourceType.MODULE;
		System.out.println("-->deleteByResourcedId:" + servAuthorityResource.deleteByResourcedId(resourceId, type));
	}
	
	@Test
	public void relatedResources(){
		System.out.println("-->relatedResources:");
		print(servAuthorityResource.relatedResources(3L, EResourceType.MODULE, null, null));
		System.out.println("-->countRelatedResources:" + servAuthorityResource.countRelatedResources(3L, EResourceType.ELEMENT));
		
	}
	
	@Test
	public void unrelatedResources(){
		System.out.println("-->unrelatedResources:");
		print(servAuthorityResource.unrelatedResources(3L, EResourceType.ELEMENT, null, null));
		System.out.println("-->countUnrelatedResources:" + servAuthorityResource.countUnrelatedResources(3L, EResourceType.ELEMENT));
		
	}
}
