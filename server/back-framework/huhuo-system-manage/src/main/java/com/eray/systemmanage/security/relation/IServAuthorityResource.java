package com.eray.systemmanage.security.relation;

import java.util.List;

import com.eray.systemmanage.constant.EResourceType;

public interface IServAuthorityResource {
	Integer addCollection(List<Long> authorityIds, List<Long> resourceIds, EResourceType type);
	Integer addCollection(Long authorityId, List<Long> resourceIds, EResourceType type);
	Integer addCollection(List<Long> authorityIds, Long resourceId, EResourceType type);
	
	Integer deleteByAuthorityId(Long authorityId);
	Integer deleteByAuthorityId(Long authorityId, EResourceType type);
	Integer deleteByResourcedId(Long resourceId, EResourceType type);
	
	<T> List<T> relatedResources(Long authorityId, EResourceType type, Long start, Long limit);
	Long countRelatedResources(Long authorityId, EResourceType type);
	<T> List<T> unrelatedResources(Long authorityId, EResourceType type, Long start, Long limit);
	Long countUnrelatedResources(Long authorityId, EResourceType type);
}
