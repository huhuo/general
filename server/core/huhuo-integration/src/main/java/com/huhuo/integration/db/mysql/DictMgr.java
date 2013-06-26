package com.huhuo.integration.db.mysql;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huhuo.integration.exception.UtilException;
/**
 * dictionary manager responsable for storing all 
 * @author root
 */
public class DictMgr {

	private static Map<String, Map<Integer, Dict>> map = new HashMap<String, Map<Integer,Dict>>();
	
	/**
	 * register a new dict to DictMgr with specified group name
	 * @param group group name
	 * @param dict dictionary
	 * @return all dicts registered in DictMgr by specified @param group
	 */
	public static List<Dict> add(String group, Dict dict) {
		Map<Integer, Dict> mapOfGroup = map.get(group);
		if(mapOfGroup == null) {
			mapOfGroup = new HashMap<Integer, Dict>();
		}
		mapOfGroup.put(dict.getKey(), dict);
		map.put(group, mapOfGroup);
		return get(group);
	}
	/**
	 * get all dicts registered in DictMgr by specified @param group
	 * @param group
	 * @return
	 */
	public static List<Dict> get(String group) {
		List<Dict> list = new ArrayList<Dict>();
		list.addAll(getMap(group).values());
		return list;
	}
	/**
	 * a fuzzy query method to search a dict collection by @param disp 
	 * in a collection specified by @param group
	 * @param group a group name with which a dict collection is registered to DictMgr
	 * @param disp key word for fuzzy query
	 * @return
	 */
	public static List<Dict> match(String group, String disp) {
		Collection<Dict> col = get(group);
		List<Dict> ret = new ArrayList<Dict>();
		for(Dict d : col) {
			if(d.getDisp().contains(disp)) {
				ret.add(d);
			}
		}
		return ret;
	}
	/**
	 * get a unique dict with specified group and key
	 * @param group
	 * @param key
	 * @return a single dict registered in DictMgr with @param group and @param key
	 */
	public static Dict get(String group, Integer key) {
		Dict dict = getMap(group).get(key);
		return dict;
	}
	
	public static Map<Integer, Dict> getMap(String group) {
		Map<Integer, Dict> ret = map.get(group);
		if(map.get(group) == null) {
			throw new UtilException(
					String.format("group with name[%s] haven't registered to DictMgr!", group));
		}
		return ret;
	}
	
}
