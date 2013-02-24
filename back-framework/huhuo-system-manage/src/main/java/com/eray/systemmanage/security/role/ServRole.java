package com.eray.systemmanage.security.role;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.systemmanage.security.relation.IServRoleAuthority;
import com.eray.systemmanage.security.relation.IServUserRole;
import com.huhuo.integration.db.mysql.Condition;
import com.huhuo.integration.db.mysql.Page;

@Service("smServRole")
public class ServRole implements IServRole {
	@Resource(name="smMapperRole")
	private MapperRole mapperRole;
	@Resource(name="smServRoleAuthority")
	private IServRoleAuthority servRoleAuthority;
	@Resource(name="smServUserRole")
	private IServUserRole servUserRole;
	
	@Override
	public Integer addBatch(List<ModelRole> list) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<ModelRole> findByCondition(Condition<ModelRole> condition) {
		return findByCondition(condition, false);
	}

	@Override
	public List<ModelRole> findByCondition(
			Condition<ModelRole> condition, boolean injected) {
		if(condition==null){
			condition = new Condition<ModelRole>();
		}
		return mapperRole.findByCondition(condition);
	}

	@Override
	public Long countByCondition(Condition<ModelRole> condition) {
		if(condition==null){
			condition = new Condition<ModelRole>();
		}
		return mapperRole.countByCondition(condition);
	}

	@Override
	public Integer add(ModelRole t) {
		return t==null? 0: mapperRole.add(t);
	}

	@Override
	public <V> ModelRole find(V id) {
		return id == null ? null : mapperRole.find(id);
	}

	@Override
	public Integer delete(ModelRole t) {
		if (t == null || t.getId() == null){ 
			return 0;
		} 
		Integer r = mapperRole.delete(t);
		if(r>0){
			servRoleAuthority.deleteByRoleId(t.getId());
			servUserRole.deleteByRoleId(t.getId());
		}
		return r;
	}

	@Override
	public Integer update(ModelRole t) {
		return (t == null || t.getId() == null) ? 0 : mapperRole.update(t);
	}

	@Override
	public Long count() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ModelRole> findModels(Page page) {
		// TODO Auto-generated method stub
		return null;
	}

}
