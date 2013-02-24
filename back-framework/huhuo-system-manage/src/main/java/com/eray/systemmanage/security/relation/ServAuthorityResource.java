package com.eray.systemmanage.security.relation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.systemmanage.constant.EResourceType;
import com.eray.systemmanage.province.ModelProvince;
import com.eray.systemmanage.security.element.ModelElement;
import com.eray.systemmanage.security.module.ModelModule;
import com.huhuo.integration.util.BeanUtils;

@Service("smServAuthorityResource")
public class ServAuthorityResource implements IServAuthorityResource {

	@Resource(name = "smMapperAuthorityResource")
	private MapperAuthorityResource mapperAuthorityResource;

	@Override
	public Integer addCollection(List<Long> authorityIds,
			List<Long> resourceIds, EResourceType type) {
		if (authorityIds == null || authorityIds.size() == 0
				|| resourceIds == null || resourceIds.size() == 0
				|| type == null) {
			return 0;
		}
		return mapperAuthorityResource.addCollection(authorityIds,
				resourceIds, type.value);
	}

	@Override
	public Integer addCollection(Long authorityId, List<Long> resourceIds, EResourceType type) {
		if (authorityId == null) {
			return 0;
		}
		List<Long> authorityIds = new ArrayList<Long>();
		authorityIds.add(authorityId);

		return this.addCollection(authorityIds, resourceIds, type);
	}

	@Override
	public Integer addCollection(List<Long> authorityIds, Long resourceId, EResourceType type) {
		if (resourceId == null) {
			return 0;
		}
		List<Long> resourceIds = new ArrayList<Long>();
		resourceIds.add(resourceId);

		return this.addCollection(authorityIds, resourceIds, type);
	}

	@Override
	public Integer deleteByAuthorityId(Long authorityId) {
		if(authorityId==null){
			return 0;
		}
		return mapperAuthorityResource.deleteByAuthorityId(authorityId, null);
	}

	@Override
	public Integer deleteByAuthorityId(Long authorityId, EResourceType type) {
		if(authorityId==null){
			return 0;
		}
		return mapperAuthorityResource.deleteByAuthorityId(authorityId, type.value);
	}
	
	@Override
	public Integer deleteByResourcedId(Long resourceId, EResourceType type) {
		if(resourceId==null || type==null){
			return 0;
		}
		return mapperAuthorityResource.deleteByResourcedId(resourceId, type.value);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> relatedResources(Long authorityId, EResourceType type,
			Long start, Long limit) {
		String tbl = getTbl(type);
		Class<T> beanType = getBeanType(type);
		if(authorityId==null || type==null || tbl==null || beanType==null){
			return new ArrayList<T>();
		}
		
		List<Map<String, Object>> list = mapperAuthorityResource.relatedResources(tbl, type.value, authorityId, start, limit);
		return BeanUtils.toBean(beanType, list);
	}

	@Override
	public Long countRelatedResources(Long authorityId, EResourceType type) {
		String tbl = getTbl(type);
		if(authorityId==null || type==null || tbl==null){
			return 0L;
		}
		
		return mapperAuthorityResource.countRelatedResources(tbl, type.value, authorityId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> unrelatedResources(Long authorityId, EResourceType type,
			Long start, Long limit) {
		String tbl = getTbl(type);
		Class<T> beanType = getBeanType(type);
		if(authorityId==null || type==null || tbl==null || beanType==null){
			return new ArrayList<T>();
		}
		
		List<Map<String, Object>> list = mapperAuthorityResource.unrelatedResources(tbl, type.value, authorityId, start, limit);
		return BeanUtils.toBean(beanType, list);
	}

	@Override
	public Long countUnrelatedResources(Long authorityId, EResourceType type) {
		String tbl = getTbl(type);
		if(authorityId==null || type==null || tbl==null){
			return 0L;
		}
		return mapperAuthorityResource.countUnrelatedResources(tbl, type.value, authorityId);
	}
	
	protected String getTbl(EResourceType type) {
		if (type == null)
			return null;
		switch (type) {
		case MODULE:
			return "sys_module";
		case PROVINCE:
			return "sys_province";
		case ELEMENT:
			return "sys_element";
		default:
			return null;
		}
	}
	
	@SuppressWarnings("rawtypes")
	protected static Class getBeanType(EResourceType type){
		if (type == null)
			return null;
		switch (type) {
		case MODULE:
			return ModelModule.class;
		case PROVINCE:
			return ModelProvince.class;
		case ELEMENT:
			return ModelElement.class;
		default:
			return null;
		}
	}


}
