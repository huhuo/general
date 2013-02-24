package com.eray.systemmanage.dictionary;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eray.systemmanage.common.IServBaseResource;
import com.eray.systemmanage.constant.Dictionary.GroupName;


public interface IServDictionary extends IServBaseResource<ModelDictionary> {
	/**
	 * 根据groupname得到一组字典，按dictKey升序排列
	 * 
	 * @param groupName
	 *            字典组名
	 * @return 如果没有查到结果返回长度为0的list
	 */
	public List<ModelDictionary> findByGroupNameOrdByDictKeyAsc(GroupName groupName);

	/**
	 * 根据groupName、dictKey得到字典值(dictValue)
	 * 
	 * @param groupName
	 *            字典组名
	 * @param dictKey
	 *            字典key
	 * @return 如果没有查到结果，返回null
	 */
	public String findValByGroupNameKey(GroupName groupName, String dictKey);
	
	/**
	 * 根据groupName、dictKey得到字典显示值(dictDisplayName)
	 * 
	 * @param groupName
	 *            字典组名
	 * @param dictKey
	 *            字典key
	 * @return 如果没有查到结果，返回null
	 */
	public String findDisplayValByGroupNameKey(GroupName groupName, String dictKey);

	/**
	 * 根据groupName、dictKey得到字典
	 * 
	 * @param groupName
	 *            字典组名
	 * @param dictKey
	 *            字典key
	 * @return 如果没有查到结果，返回null
	 */
	public ModelDictionary findByGroupNameKey(GroupName groupName, String dictKey);
	
	/**
	 * 如果dictKey==null，则返回null
	 * @see #findByGroupNameKey(GroupName, String)
	 */
	public ModelDictionary findByGroupNameKey(GroupName groupName, Integer dictKey);
	
	/**
	 * 根据id列表查询字典表
	 * 
	 * @param idList
	 *            id列表
	 * @return 返回字典列表，没有返回长度为0的list
	 */
	public List<ModelDictionary> findByIds(List<Integer> idList);
	
	/**
	 * 根据id列表，批量删除
	 * 
	 * @param idList
	 *            id列表
	 * @return
	 */
	public int deleteBachByIds( List<Integer> idList);

	/**
	 * 模糊查询
	 * 
	 * @param dictionary
	 *            dictionary成员是记录的相关字段的一部分，都将加入至返回列表，如果dictionary或所有成员都为null，那么将返回所有记录
	 * @return 查询列表
	 */
	List<ModelDictionary> selectBy(ModelDictionary dictionary);
	
	/**
	 * 模糊分页查询
	 * 
	 * @param dictionary
	 *            dictionary成员是记录的相关字段的一部分，都将加入至返回列表，如果dictionary或所有成员都为null，那么将返回所有记录
	 * @param offset
	 *            偏移量
	 * @param limit
	 *            查询长度
	 * @return 查询列表
	 */
	List<ModelDictionary> selectByAsPage(ModelDictionary dictionary, int offset, int limit);
	/**
	 * 根据id列表查询字典表
	 * 
	 * @param groupNameList
	 *            组名列表
	 * @return 返回字典列表，没有返回长度为0的list
	 */
	public List<ModelDictionary> findByGroupNames(@Param("groupNameList") List<String> groupNameList);
	/**
	 * 根据组名列表查询字典表数据条数
	 * 
	 * @param groupNameList
	 *            组名列表
	 * @return 返回字典列表，没有返回长度为0的list
	 */
	public Long countByGroupNames(@Param("groupNameList") List<String> groupNameList);
	public Long findMaxKey(String groupName);
	
}
