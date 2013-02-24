package com.eray.systemmanage.constant;

/**
 * Memcached区及区内静态key的管理类
 * 
 * @author shifengxuan
 *
 */
public class MemcachedRegion {

	/**
	 * 省份
	 * @author shifengxuan
	 *
	 */
	public enum ProvinceRegion{
		;
		public static String region(){
			return ProvinceRegion.class.getSimpleName();
		}
	}
	
	/**
	 * 城市
	 * @author shifengxuan
	 *
	 */
	public enum CityRegion{
		;
		public static String region(){
			return CityRegion.class.getSimpleName();
		}
	}
	
	/**
	 * 字典
	 * @author shifengxuan
	 *
	 */
	public enum DictionaryRegion{
		;
		public static String region(){
			return DictionaryRegion.class.getSimpleName();
		}
	}
	
	/**
	 * 菜单
	 * 
	 * @author shifengxuan
	 *
	 */
	public enum ModuleRegion{
		;
		public static String region(){
			return ModuleRegion.class.getSimpleName();
		}
	}
}


