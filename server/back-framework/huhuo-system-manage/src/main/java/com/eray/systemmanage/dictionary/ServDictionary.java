package com.eray.systemmanage.dictionary;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.systemmanage.constant.Dictionary.GroupName;
import com.eray.systemmanage.constant.EResourceType;
import com.eray.systemmanage.constant.MemcachedRegion;
import com.eray.systemmanage.security.user.ModelUser;
import com.huhuo.integration.db.mysql.Condition;
import com.huhuo.integration.db.mysql.Page;
import com.huhuo.integration.service.MemcachedServ;




@Service("smServDictionary")
public class ServDictionary implements IServDictionary {
	@Resource(name="smMapperDictionary")
	private MapperDictionary iDictionaryMapper;
	
	@Resource(name = "memcachedService")
	private MemcachedServ servMemcached;
	protected String region = MemcachedRegion.DictionaryRegion.region();
	protected String separator = "_";
	
	
	
	@Override
	public Integer add(ModelDictionary t) {
		int result = 0;
		if(validObj(t) && validStr(t.getDictKey())){
			result = iDictionaryMapper.add(t);
			String groupName = t.getGroupName();
			if(validStr(groupName))
				servMemcached.delete(region, groupName.toUpperCase());
		}
		return result;
	}

	/*public ModelSysDictionary find(ModelSysDictionary id) {
		if(id!=null){
			return iSysDictionaryMapper.find(id);
		}
		return null;
	}*/

	@Override
	public Integer delete(ModelDictionary t) {
		Integer r = 0;
		if(validObj(t) && validObj(t.getId())){
			r = iDictionaryMapper.delete(t);
			if(r>0){
				String groupName = t.getGroupName();
				if(validStr(groupName)){
					servMemcached.delete(region,groupName.toUpperCase());
				}else{
					servMemcached.flushAll();
				}
			}
		}

		return r;
	}
	
	@Override
	public Integer update(ModelDictionary t) {
		Integer r = 0;
		if(validObj(t) && validObj(t.getId())){
			r =  iDictionaryMapper.update(t);
			if(r>0)
				servMemcached.flushAll();
		}
		 return r;
	}	
	
	@Override
	public List<ModelDictionary> findByGroupNameOrdByDictKeyAsc(GroupName groupName) {
		if(!validStr(groupName.toString())){
			return new ArrayList<ModelDictionary>(0);
		}
		List<ModelDictionary> dictList = null;
		
		dictList = servMemcached.get(region, groupName.toString());
		
		if(dictList==null){
			dictList = iDictionaryMapper.findByGroupNameOrdByDictKeyAsc(groupName);
			servMemcached.set(region, groupName.toString(), dictList);
		}
		
		return dictList;
	}

	public String findValByGroupNameKey(GroupName groupName, String dictKey) {
		List<ModelDictionary> dictList = findByGroupNameOrdByDictKeyAsc(groupName);
		for(ModelDictionary dict: dictList){
			if(dict.getDictKey().equals(dictKey)){
				return dict.getDictValue();
			}
		}
		return null;
	}

	public String findDisplayValByGroupNameKey(GroupName groupName, String dictKey) {
		List<ModelDictionary> dictList = findByGroupNameOrdByDictKeyAsc(groupName);
		for(ModelDictionary dict: dictList){
			if(dict.getDictKey().equals(dictKey)){
				return dict.getDictDisplayName();
			}
		}
		return null;
	}

	public ModelDictionary findByGroupNameKey(GroupName groupName, String dictKey) {
		List<ModelDictionary> dictList = findByGroupNameOrdByDictKeyAsc(groupName);
		for(ModelDictionary dict: dictList){
			if(dict.getDictKey().equals(dictKey)){
				return dict;
			}
		}
		return new ModelDictionary();
	}

	public ModelDictionary findByGroupNameKey(GroupName groupName, Integer dictKey) {
		if(dictKey != null) {
			return findByGroupNameKey(groupName, dictKey.toString());
		} else {
			return null;
		}
	}

	public List<ModelDictionary> findByIds(List<Integer> idList) {
		return iDictionaryMapper.findByIds(idList);
	}

	@Override
	public Integer addBatch(List<ModelDictionary> dictList) {
		Integer r = iDictionaryMapper.addBatch(dictList);
		if(r>0)
			servMemcached.flushAll();
		return r;
	}

	public int deleteBachByIds(List<Integer> idList) {
		int r = iDictionaryMapper.deleteBatchByIds(idList);
		if(r>0)
			servMemcached.flushAll();
		return r;
	}
	
	public List<ModelDictionary> selectBy(ModelDictionary dictionary) {
		return iDictionaryMapper.selectBy(dictionary);
	}

	public List<ModelDictionary> selectByAsPage(ModelDictionary dictionary, int offset,
			int limit) {
		return iDictionaryMapper.selectByAsPage(dictionary, offset, limit);
	}
	
	/**************************************
	 * unoverride function
	 **************************************/
	public boolean validStr(String obj){
		if(obj!=null && obj.length()>0){
			return true;
		}
		return false;
	}

	
	protected boolean validObj(Object obj){
		return obj!=null?true:false;
	}

	public List<ModelDictionary> findByGroupNames(List<String> groupNameList) {
		return iDictionaryMapper.findByGroupNames(groupNameList);
	}

	public Long countByGroupNames(List<String> groupNameList) {
		return iDictionaryMapper.countByGroupNames(groupNameList);
	}

	public Long findMaxKey(String groupName) {
		return iDictionaryMapper.findMaxKey(groupName);
	}

	@Override
	public List<ModelDictionary> getResource(ModelUser user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EResourceType getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ModelDictionary> findByCondition(
			Condition<ModelDictionary> condition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ModelDictionary> findByCondition(
			Condition<ModelDictionary> condition, boolean injected) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long countByCondition(Condition<ModelDictionary> condition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <V> ModelDictionary find(V id) {
		if(id!=null){
			return iDictionaryMapper.find(id);
		}
		return null;
	}

	@Override
	public Long count() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ModelDictionary> findModels(Page page) {
		// TODO Auto-generated method stub
		return null;
	}

	
	

}
