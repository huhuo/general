package com.huhuo.integration.db.mysql;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * dictionary manager responsable for storing all 
 * @author root
 */
public class DictMgr {

	private static Map<String, Map<Integer, Dict>> map = new HashMap<String, Map<Integer,Dict>>();
	
	public static List<Dict> add(String group, Dict dict) {
		Map<Integer, Dict> mapOfGroup = map.get(group);
		if(mapOfGroup == null) {
			mapOfGroup = new HashMap<Integer, Dict>();
		}
		mapOfGroup.put(dict.getKey(), dict);
		map.put(group, mapOfGroup);
		return get(group);
	}
	
	public static List<Dict> get(String group) {
		List<Dict> list = new ArrayList<Dict>();
		list.addAll(map.get(group).values());
		return list;
	}
	
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
	
	public static Dict get(String group, Integer key) {
		Dict dict = map.get(group).get(key);
		return dict;
	}
	
}
