package com.eray.systemmanage.constant;
/**
 * 规范字典表的字段管理
 * @author wuyuxuan
 */
public class Dictionary {

	public enum GroupName {
		ACCOUNT_TYPE, 					// 账号类型
		APPRECIATION_TYPE,				// 增值业务类型
		AUDIT_STATUS,					// 审核状态
		CITY_LEVEL,						// 城市级别
		CLIENT_CHANNEL,					// 客户端渠道
		CLIENT_PLATFORM,				// 客户端平台（操作系统）
		GENDER,							// 性别
		LOGIN_COUNT_TYPE,				// 登录次数类型
		MEMCACHED_SEARCH_TYPE,			// memcached查询类型
		MOBILE_TYPE,					// 移动类型
		NOTIFY_TYPE,					// 通知类型
		NOTIFY_SEND_TYPE, 				// 通知发送类型
		NOTIFY_SEND_STATUS, 			// 通知发送状态
		ONLINE_TIME_TYPE,				// 在线时长类型
		PAGE_VIEW_TYPE,					// 页面浏览类型
		UGC_TYPE,						// UGC类型(user generate content)
		VISIBLE_STATUS,					// 显示状态
		VISUALEFFECT,					//MetaData显示效果
		DEFAULTLIVINGINDEX,				//默认指数
		CLIENTVERSION,					//客户端版本
		ADVPOSITION,					//广告位
		CLIENTSYSTEM,					//MetaData客户端平台
		METADATASYSTEMIP,				//MetaData同步服务器IP
		REPORT_PROGRESS_STAT,			// 当前进度(1,需求;2,设计;3,开发;4,测试;5,完成)
		IFPUBLISH,						// 是否上市(0,否;1,是)
		BIZ_REPORT_TASKSORT,  			// 任务类别(1,设计;2,开发;3,测试;4,研究;5,讨论;6,界面;7,其他)
		BIZ_REPORT_MODULEBELONGED,		//所属模块（1、GIS；2、ADI；3、客户端）
		BIZ_REPORT_BUGTYPE,				//bug类型（1、UI；2、性能；3、稳定性；4、基本功能未实现；5、数据；6、新需求）
		BIZ_REPORT_PRIORITY,			//优先级（1、高；2、中；3、低）
		REPORT_TEST_STAT,				// 状态（1、新建；2、开始分析；3、已解决；4、验证；5、完成；6、未通过；7、延后）
		WARNING_STATUS					// 推送状态，0：未推送；1：正在推送；2：已经推送
		
	}
	
}
