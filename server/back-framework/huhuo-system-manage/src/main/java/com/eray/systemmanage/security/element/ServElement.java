package com.eray.systemmanage.security.element;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.systemmanage.constant.EResourceType;
import com.eray.systemmanage.security.relation.IServAuthorityResource;
import com.eray.systemmanage.security.user.ModelUser;
import com.huhuo.integration.db.mysql.Condition;
import com.huhuo.integration.db.mysql.Page;

@Service("smServElement")
public class ServElement implements IServElement{
	@Resource(name="smMapperElement")
	private MapperElement mapperElement;
	@Resource(name="smServAuthorityResource")
	private IServAuthorityResource servAuthorityResource;
	
	@Override
	public Integer addBatch(List<ModelElement> list) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<ModelElement> findByCondition(
			Condition<ModelElement> condition) {
		return this.findByCondition(condition, false);
	}

	@Override
	public List<ModelElement> findByCondition(
			Condition<ModelElement> condition, boolean injected) {
		if(condition==null){
			condition = new Condition<ModelElement>();
		}
		return mapperElement.findByCondition(condition);
	}

	@Override
	public Long countByCondition(Condition<ModelElement> condition) {
		if(condition==null){
			condition = new Condition<ModelElement>();
		}
		return mapperElement.countByCondition(condition);
	}

	@Override
	public Integer add(ModelElement t) {
		return t==null? 0: mapperElement.add(t);
	}

	@Override
	public <V> ModelElement find(V id) {
		if(id==null)
			return null;
		return mapperElement.find(id);
	}

	@Override
	public Integer delete(ModelElement t) {
		if(t == null || t.getId() == null){
			return 0;
		}
		Integer r = mapperElement.delete(t);
		if(r>0){
			servAuthorityResource.deleteByResourcedId(t.getId(), EResourceType.ELEMENT);
		}
		return r;
	}

	@Override
	public Integer update(ModelElement t) {
		return (t == null || t.getId() == null) ? 0 : mapperElement.update(t);
	}

	@Override
	public Long count() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ModelElement> getResource(ModelUser user) {
		if(ModelUser.ADMIN.equals(user.getUsername())){
			return this.findByCondition(null);
		}
		
		if(user.getId()==null){
			return new ArrayList<ModelElement>(0);
		}
		return mapperElement.getResource(user);
	}

	@Override
	public EResourceType getType() {
		return EResourceType.ELEMENT;
	}

	@Override
	public List<ModelElement> findModels(Page page) {
		// TODO Auto-generated method stub
		return null;
	}

}
