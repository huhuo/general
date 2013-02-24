package com.eray.systemmanage.dictionary;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eray.systemmanage.common.MapperBaseResource;
import com.eray.systemmanage.constant.Dictionary.GroupName;

public interface MapperDictionary extends MapperBaseResource<ModelDictionary> {
	
	/**
	 * 批量添加
	 * 
	 * @param dictList
	 *            字典列表
	 * @return 添加行数
	 */
	public int addBatch(@Param("dictList") List<ModelDictionary> dictList);
	
	/**
	 * 批量删除
	 * 
	 * @param idList
	 *            id列表
	 * @return 删除行数
	 */
	public int deleteBatchByIds(@Param("idList") List<Integer> idList);
	
	/**
	 * 根据组名，查询一组字典信息
	 * 
	 * @param GroupName
	 *            字典组名
	 * @return 字典列表，没有返回长度为0的list
	 */
	public List<ModelDictionary> findByGroupName(GroupName GroupName);
	
	/**
	 * 根据组名，查询一组字典信息按字典值(dictKey)升序排列
	 * 
	 * @param GroupName
	 *            字典组名
	 * @return 字典列表，没有返回长度为0的list
	 */
	public List<ModelDictionary> findByGroupNameOrdByDictKeyAsc(GroupName groupName);
	
	/**
	 * 根据组名、字典key，查询字典
	 * 
	 * @param groupName
	 *            字典组名
	 * @param dictKey
	 *            字典key
	 * @return 字典(Dictionary)
	 */
	public ModelDictionary findByGroupNameKey(@Param("groupName") GroupName groupName, @Param("dictKey") String dictKey);
	
	/**
	 * 根据组名、字典key，查询字典值(dictValue)
	 * 
	 * @param groupName
	 *            字典组名
	 * @param dictKey
	 *            字典key
	 * @return 字典值(dictValue)
	 */
	public String findValByGroupNameKey(@Param("groupName") GroupName groupName, @Param("dictKey") String dictKey);
	
	/**
	 * 根据组名、字典key，查询字典显示值(dictDisplayName)
	 * 
	 * @param groupName
	 *            字典组名
	 * @param dictKey
	 *            字典key
	 * @return 字典显示值(dictDisplayName)
	 */
	public String findDisplayValByGroupNameKey(@Param("groupName") GroupName groupName, @Param("dictKey") String dictKey);

	/**
	 * 根据id列表查询字典表
	 * 
	 * @param idList
	 *            id列表
	 * @return 返回字典列表，没有返回长度为0的list
	 */
	public List<ModelDictionary> findByIds(@Param("idList") List<Integer> idList);
	
	/**
	 * 模糊查询
	 * 
	 * @param dictionary
	 *            dictionary成员是记录的相关字段的一部分，都将加入至返回列表，如果dictionary或所有成员都为null，那么将返回所有记录
	 * @return 查询列表
	 */
	List<ModelDictionary> selectBy(@Param("dictionary") ModelDictionary dictionary);
	
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
	List<ModelDictionary> selectByAsPage(@Param("dictionary") ModelDictionary dictionary, @Param("offset") int offset, @Param("limit") int limit);
	
	/**
	 * 根据组名列表查询字典表
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
