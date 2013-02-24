package com.eray.systemmanage.security.relation;

import java.util.List;

import com.eray.systemmanage.security.authority.ModelAuthority;

public interface IServRoleAuthority {
	Integer addCollection(List<Long> roleIds, List<Long> authorityIds);
	Integer addCollection(Long roleId, List<Long> authorityIds);
	Integer addCollection(List<Long> roleIds, Long authorityId);
	Integer deleteByRoleId(Long roleId);
	Integer deleteByAuthorityId(Long authorityId);
	
	List<ModelAuthority> relatedAuthorities(Long roleId, Long start, Long limit);
	Long countRelatedAuthorities(Long roleId);
	
	List<ModelAuthority> unrelatedAuthorities(Long roleId, Long start, Long limit);
	Long countUnrelatedAuthorities(Long roleId);
}
