package com.eray.systemmanage.security.module;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.systemmanage.constant.EResourceType;
import com.eray.systemmanage.constant.MemcachedRegion.ModuleRegion;
import com.eray.systemmanage.security.relation.IServAuthorityResource;
import com.eray.systemmanage.security.user.ModelUser;
import com.huhuo.integration.db.mysql.Condition;
import com.huhuo.integration.db.mysql.Page;
import com.huhuo.integration.service.MemcachedServ;
import com.huhuo.integration.util.StringUtils;

@Service("smServModule")
public class ServModule implements IServModule{
	@Resource(name="smMapperModule")
	private MapperModule mapperModule;
	@Resource(name="smServAuthorityResource")
	private IServAuthorityResource servAuthorityResource;
	@Resource(name = "memcachedService")
	private MemcachedServ servMemcached;
	protected static String region = ModuleRegion.region();
	protected static Integer version = 0;
	protected static String separator = "_";
	
	@Override
	public List<ModelModule> findAll() {
		return mapperModule.findAll();
	}

	@Override
	public Map<ModelModule, List<ModelModule>> getModuleGroupedByParentId() {
		// 获取并分组模块数据
		List<ModelModule> moduleList = findAll();
	
		Map<Long, List<ModelModule>> tempMap = new LinkedHashMap<Long, List<ModelModule>>();
		for(ModelModule module : moduleList) {
			Integer level = module.getLevel();
			Long parentId = module.getParentId();
			if(level==1){
				parentId = 0L;
			}
			List<ModelModule> moduleGroup = tempMap.get(parentId);
			if(moduleGroup == null) {
				moduleGroup = new ArrayList<ModelModule>();
				moduleGroup.add(module);
				tempMap.put(parentId, moduleGroup);
			} else {
				moduleGroup.add(module);
			}
		}
		Map<ModelModule, List<ModelModule>> resultMap = new LinkedHashMap<ModelModule, List<ModelModule>>();
		// 按照要求的格式构造分组模块数据，并对母菜单根据orderNo进行排序
		List<ModelModule> parentModuleList = tempMap.get(0L);
		
		for(ModelModule parentModule : parentModuleList) {
			List<ModelModule> value = tempMap.get(parentModule.getId());
			resultMap.put(parentModule, value);
		}
		return resultMap;
	}

	@Override
	public Integer addBatch(List<ModelModule> list) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<ModelModule> findByCondition(
			Condition<ModelModule> condition) {
		
		return findByCondition(condition, false);
	}

	@Override
	public List<ModelModule> findByCondition(
			Condition<ModelModule> condition, boolean injected) {
		if(condition==null){
			condition = new Condition<ModelModule>();
		}
		List<ModelModule> r =  mapperModule.findByCondition(condition);
		if(injected){
			inject(r);
		}
		return r;
	}

	@Override
	public Long countByCondition(Condition<ModelModule> condition) {
		if(condition==null){
			condition = new Condition<ModelModule>();
		}
		return mapperModule.countByCondition(condition);
	}

	@Override
	public Integer add(ModelModule t) {
		if(t == null){
			return 0;
		}
		ModelModule parent = this.find(t.getParentId());
		t.setLeaf(true);
		t.setLevel(0);
		if(parent!=null){
			if(parent.getLeaf()){
				parent.setLeaf(false);
				this.update(parent);
			}
			t.setLevel(parent.getLevel()+1);
		}
		t.setCreateTime(new Date());
		t.setUpdateTime(new Date());
		version ++;
		return mapperModule.add(t);
	}

	@Override
	public <V> ModelModule find(V id) {
		if(id==null){
			return null;
		}
		String key = getKey(id);
		ModelModule r = servMemcached.get(region, key); 
		if(r==null){
			r = mapperModule.find(id);
			if(r!=null){
				servMemcached.set(region, key, r);
			}
		}
		
		return r;
	}

	@Override
	public Integer delete(ModelModule t) {
		if(t==null || t.getId()==null){
			return 0;
		}
		
		int r = mapperModule.delete(t);
		if(r>0){
			version++;
			servAuthorityResource.deleteByResourcedId(t.getId(), EResourceType.MODULE);
			//if parent module hasn't children, update leaf = true
			if(findByParentId(t.getParentId()).size()==0){
				updateLeafById(t.getParentId(), true);
			}
		}
		return r;
	}

	@Override
	public Integer update(ModelModule t) {
		if(t==null || t.getId()==null){
			return 0;
		}
		t.setUpdateTime(new Date());
		int r = mapperModule.update(t);
		version ++;
		return r;
	}

	@Override
	public Long count() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ModelModule> getResource(ModelUser user) {
		List<ModelModule> r = null;
		if(ModelUser.ADMIN.equals(user.getUsername())){
			ModelModule t = new ModelModule();
			t.setVisible(true);
			t.setLevel(2);
			Condition<ModelModule> condition = new Condition<ModelModule>();
			condition.setT(t);
			r = this.findByCondition(condition);
		}else{
			if(user.getId()==null){
				return new ArrayList<ModelModule>(0);
			}
			r = mapperModule.getResource(user);
		}
		inject(r);
		return r;
	}

	@Override
	public EResourceType getType() {
		return EResourceType.MODULE;
	}
	
	public void inject(List<ModelModule> list){
		if(list!=null){
			for(ModelModule module:list){
				inject(module, true);
			}
		}
	}
	
	public void inject(ModelModule module, boolean isRecursive){
		Long parentId = module.getParentId();
		if(parentId!=null && !parentId.equals(0L)){
			module.setParent(find(module.getParentId()));
			if(isRecursive){
				inject(module.getParent(), isRecursive);
			}
		}
	}
	
	public <V> String getKey(V id){
		return StringUtils.join(separator, id, version);
	}

	@Override
	public List<ModelModule> findByParentId(Long parentId) {
		if(parentId==null){
			return new ArrayList<ModelModule>(0);
		}
		return mapperModule.findByParentId(parentId);
	}

	@Override
	public Integer updateLeafById(Long id, boolean leaf) {
		if(id==null) return 0;
		ModelModule t = new ModelModule();
		t.setId(id);
		t.setLeaf(leaf);
		t.setUpdateTime(new Date());
		return mapperModule.updateLeafById(t);
	}

	@Override
	public List<ModelModule> findModels(Page page) {
		// TODO Auto-generated method stub
		return null;
	}
}
