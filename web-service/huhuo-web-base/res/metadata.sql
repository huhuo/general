/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.0.22-community-nt : Database - cw-metadata
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`cw-metadata` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `cw-metadata`;

/*Table structure for table `meta_activity` */

DROP TABLE IF EXISTS `meta_activity`;

CREATE TABLE `meta_activity` (
  `id` bigint(20) NOT NULL auto_increment COMMENT '主键',
  `businessid` bigint(20) default NULL COMMENT '保存具体业务ID，如metadata的id',
  `processid` int(20) default NULL,
  `nodeId` int(20) default NULL,
  `operator` varchar(50) default NULL,
  `opinion` varchar(1000) default NULL,
  `dealstatus` int(11) default NULL COMMENT ' 0 退回，1通过',
  `createtime` datetime default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='流程执行表';

/*Data for the table `meta_activity` */

insert  into `meta_activity`(`id`,`businessid`,`processid`,`nodeId`,`operator`,`opinion`,`dealstatus`,`createtime`) values (1,4,NULL,NULL,'超级管理员','同意',NULL,'2012-06-21 13:47:41'),(2,4,NULL,NULL,'超级管理员','同意',NULL,'2012-06-21 14:06:42'),(3,4,NULL,NULL,'超级管理员','同意',NULL,'2012-06-21 14:10:05'),(4,4,NULL,NULL,'超级管理员','同意',NULL,'2012-06-21 14:14:48'),(5,4,NULL,NULL,'超级管理员','同意',NULL,'2012-06-21 14:27:31'),(6,4,NULL,NULL,'超级管理员','同意',NULL,'2012-06-21 14:29:01'),(7,4,NULL,NULL,'超级管理员','同意',NULL,'2012-06-21 14:32:56'),(8,5,NULL,NULL,'超级管理员','同意',NULL,'2012-06-21 14:39:29'),(9,7,NULL,NULL,'超级管理员','同意',NULL,'2012-06-21 15:11:32'),(10,8,NULL,NULL,'超级管理员','同意',NULL,'2012-06-21 15:21:13');

/*Table structure for table `meta_cityhotlink` */

DROP TABLE IF EXISTS `meta_cityhotlink`;

CREATE TABLE `meta_cityhotlink` (
  `id` bigint(11) NOT NULL auto_increment COMMENT 'ID',
  `cityHotLinkName` varchar(50) default NULL COMMENT '城市页面热点链接的名字',
  `cityHotLinkUrl` varchar(1000) default NULL COMMENT '城市页面热点链接的地址',
  `cityTabVisualEffect` bigint(11) default NULL COMMENT '城市页面标签页的显示效果',
  `cityTabVisualEffectText` varchar(100) default NULL COMMENT '城市页面标签页的显示效果文字',
  `cityHotLinkIcon` varchar(100) default NULL COMMENT '城市页面热点链接的图标',
  `createTime` datetime default NULL COMMENT '创建时间',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `meta_cityhotlink` */

insert  into `meta_cityhotlink`(`id`,`cityHotLinkName`,`cityHotLinkUrl`,`cityTabVisualEffect`,`cityTabVisualEffectText`,`cityHotLinkIcon`,`createTime`) values (2,'gdfs','greter',NULL,'wrwe','','2012-06-21 14:35:52'),(3,'rwe','rwer',113,'rwer','','2012-06-21 14:35:56'),(4,'yrt','yrty',113,'6456456','','2012-06-21 14:38:23'),(5,'yrty','rty',NULL,'yrty','','2012-06-21 14:38:28'),(6,'热点链接name','热点链接url',113,'热点链接','','2012-06-21 15:54:50'),(7,'热点name','热点url',113,'热点','','2012-06-21 20:15:53'),(8,'热点2','热2 url',115,'','','2012-06-21 20:16:07'),(9,'预警信息','wisp://pAlarmList.wi#curCity',NULL,'','','2012-06-25 15:24:59'),(10,'周边天气','wsip://pMapServices.wi#peripheral',NULL,'','','2012-06-25 15:25:09'),(11,'天气雷达','wsip://pMapServices.wi#radar',NULL,'','','2012-06-25 15:25:21'),(15,'预警信息','wisp://pAlarmList.wi#curCity',NULL,'','','2012-06-26 10:42:02'),(16,'周边天气','wisp://pMapServices.wi?detail=peripheral',NULL,'','','2012-08-28 09:56:15'),(18,'预警信息','wisp://pAlarmList.wi#curCity',NULL,'','','2012-06-26 11:06:43'),(19,'周边天气','wisp://pMapServices.wi?detail=peripheral',NULL,'','','2012-08-28 09:48:30');

/*Table structure for table `meta_data` */

DROP TABLE IF EXISTS `meta_data`;

CREATE TABLE `meta_data` (
  `id` bigint(11) NOT NULL auto_increment COMMENT 'ID',
  `provinceId` bigint(11) default NULL COMMENT '所属省份ID（国家级定义，省级隐藏）',
  `allClientVersion` tinyint(4) default NULL COMMENT '是否为所有客户端（国家级和省级都有，省级覆盖国家级？）',
  `availableClientVersion` varchar(200) default NULL COMMENT '适用客户端版本，字典表组名clientVersion（与allClientVersion相斥，国家级和省级都有，省级覆盖国家级？）',
  `allCellphoneSystem` tinyint(4) default NULL COMMENT '是否为所有手机平台（不知道是否还有用？--不需要）',
  `availableCellphoneSystem` varchar(50) default NULL COMMENT '适用手机平台，字典表组名client_platform（国家级和省级都有，省级覆盖国家级？）',
  `profession` tinyint(4) default NULL COMMENT '是否为专业版（国家级和省级都有，省级覆盖国家级？）',
  `tabChoose` varchar(200) default NULL COMMENT '标签页的选择（国家级）',
  `metaDataTitle` varchar(100) default NULL COMMENT '版本信息（如，上海版.普适 for general）（省级）',
  `titleName` varchar(100) default NULL COMMENT '标题（省级）',
  `logoUrl` varchar(100) default NULL COMMENT 'Logo 地址（国家级）',
  `splashScreenUrl` varchar(1000) default NULL COMMENT '开机闪屏页面地址（省级）',
  `splashScreenText` varchar(100) default NULL COMMENT '开机闪屏页面文字（省级）',
  `aboutPageUrl` varchar(1000) default NULL COMMENT '关于页面图片的地址（国家级）',
  `aboutPageText` varchar(100) default NULL COMMENT '关于页面的文字（国家级）',
  `homePageUrl` varchar(1000) default NULL COMMENT '首页的地址（国家级）',
  `infoPageUrl` varchar(1000) default NULL COMMENT '资讯类页面的地址（找不到--不需要）',
  `warningPageTelNum` varchar(50) default NULL COMMENT '预警页电话号码（省级）',
  `warningPageBlogUrl` varchar(1000) default NULL COMMENT '预警页提示的微博地址（国家级）',
  `cityHotLinkChoose` varchar(200) default NULL COMMENT '城市页面热点链接的选择（省级）',
  `forecastSpan` varchar(10) default NULL COMMENT '显示预报天数（国家级）',
  `liveSpan` varchar(10) default NULL COMMENT '显示实况小时数（国家级）',
  `defaultLivingIndex` varchar(100) default NULL COMMENT '默认生活指数选择，字典表组名defaultLivingIndex（国家级）',
  `provinceSerFuncChoose` varchar(200) default NULL COMMENT '省级服务功能框的选择（省级，与表meta_province_ser_func关联）',
  `provinceTipsPic` varchar(100) default NULL COMMENT '省级提示图片（省级）',
  `provinceTipsText` varchar(100) default NULL COMMENT '省级提示文本（省级）',
  `provinceBackgroundPicUrl` varchar(1000) default NULL COMMENT '省份背景图片url（省级）',
  `provinceBackgroundPicPositon` varchar(100) default NULL COMMENT '省级背景图片位置（省级）',
  `valueAddedSers` varchar(50) default NULL COMMENT '增值服务设置（不知道是否仍在使用）',
  `advertisePosChoose` varchar(20) default NULL COMMENT '广告位选择（省级，取字典表组名为ADVPOSITION的id）',
  `advertisePosStatus` tinyint(4) default NULL COMMENT '广告位开闭状态（不知道是否还有用？）',
  `sinaTwitterUrl` varchar(1000) default NULL COMMENT '新浪微博URL（省级）',
  `neteasyTwitterUrl` varchar(1000) default NULL COMMENT '网易微博地址（省级）',
  `qqTwitterUrl` varchar(1000) default NULL COMMENT 'QQ微博地址（省级）',
  `updateToProfession` tinyint(4) default NULL COMMENT '是否可以升级为专业版（省级）',
  `proTitle` varchar(50) default NULL COMMENT '专业版标题（省级）',
  `proPicUrl` varchar(1000) default NULL COMMENT '专业版图片地址（省级）',
  `updateUrl` varchar(1000) default NULL COMMENT '升级地址（省级）',
  `proText` varchar(1000) default NULL COMMENT '文本编辑（省级）',
  `surveyName` varchar(50) default NULL COMMENT '调查名称（省级）',
  `surveyIconUrl` varchar(1000) default NULL COMMENT '调查图标路径（省级）',
  `surveyUrl` varchar(1000) default NULL COMMENT '调查页面地址（省级）',
  `newsUrl` varchar(1000) default NULL COMMENT '咨询页面地址（省级）',
  `recommandPageChoose` varchar(200) default NULL COMMENT '推荐页面选择（国家级，与meta_recommandpage表关联）',
  `linkPageChoose` varchar(200) default NULL COMMENT '链接页面选择（省级，与meta_linkpage表关联）',
  `processId` int(11) default NULL COMMENT '流程ID（审批，与流程审批meta_activity表关联）',
  `createTime` datetime default NULL COMMENT '创建时间',
  `approvalStatus` int(11) default NULL COMMENT '审批状态（审批，与processId配合，用于流程审批）',
  `drafter` int(11) default NULL COMMENT '创建人',
  `remark` varchar(200) default NULL COMMENT '备注',
  `releaseTime` datetime default NULL COMMENT '发布时间',
  `copyright` varchar(200) default NULL COMMENT '版权所有（省级）',
  `lawinfo` varchar(200) default NULL COMMENT '法律信息（省级）',
  `address` varchar(200) default NULL COMMENT '联系地址（省级）',
  `hotline` varchar(50) default NULL COMMENT '400服务热线（省级）',
  `email` varchar(50) default NULL COMMENT '邮件地址（省级）',
  `website` varchar(100) default NULL COMMENT '网站（省级）',
  `voicephone` varchar(50) default NULL COMMENT '声讯电话（省级）',
  `mobilenum` varchar(20) default NULL COMMENT '移动特服号（省级）',
  `mobilecode` varchar(20) default NULL COMMENT '移动特服订制指令（省级）',
  `unicomnum` varchar(20) default NULL COMMENT '联通特服号（省级）',
  `unicomcode` varchar(20) default NULL COMMENT '联通特服订制指令（省级）',
  `telecomnum` varchar(20) default NULL COMMENT '电信特服号（省级）',
  `telecomcode` varchar(20) default NULL COMMENT '电信特服订制指令（省级）',
  `telephone` varchar(20) default NULL COMMENT '联系电话（省级）',
  `nationwide` tinyint(4) default NULL COMMENT '国家范围（用于区分记录属于省级或者国家级）',
  `weibo` varchar(100) default NULL COMMENT '微博（国家级，基本信息）',
  `frontpage` varchar(200) default NULL COMMENT '首页（？？国家级，homePageUrl？在用？）',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `meta_data` */

insert  into `meta_data`(`id`,`provinceId`,`allClientVersion`,`availableClientVersion`,`allCellphoneSystem`,`availableCellphoneSystem`,`profession`,`tabChoose`,`metaDataTitle`,`titleName`,`logoUrl`,`splashScreenUrl`,`splashScreenText`,`aboutPageUrl`,`aboutPageText`,`homePageUrl`,`infoPageUrl`,`warningPageTelNum`,`warningPageBlogUrl`,`cityHotLinkChoose`,`forecastSpan`,`liveSpan`,`defaultLivingIndex`,`provinceSerFuncChoose`,`provinceTipsPic`,`provinceTipsText`,`provinceBackgroundPicUrl`,`provinceBackgroundPicPositon`,`valueAddedSers`,`advertisePosChoose`,`advertisePosStatus`,`sinaTwitterUrl`,`neteasyTwitterUrl`,`qqTwitterUrl`,`updateToProfession`,`proTitle`,`proPicUrl`,`updateUrl`,`proText`,`surveyName`,`surveyIconUrl`,`surveyUrl`,`newsUrl`,`recommandPageChoose`,`linkPageChoose`,`processId`,`createTime`,`approvalStatus`,`drafter`,`remark`,`releaseTime`,`copyright`,`lawinfo`,`address`,`hotline`,`email`,`website`,`voicephone`,`mobilenum`,`mobilecode`,`unicomnum`,`unicomcode`,`telecomnum`,`telecomcode`,`telephone`,`nationwide`,`weibo`,`frontpage`) values (1,10133,0,'135',0,'138',1,NULL,'澳门版.专业 for general','f',NULL,'erewr','erwe',NULL,NULL,NULL,NULL,'rwer',NULL,'35,36,61,63',NULL,NULL,NULL,'69,70,105','23','xvzxv','fasdf','as',NULL,NULL,0,'dfsa','sadfsaf','fsdf',1,'fasd','','','','d','dffsdf','as','sdfsdf',NULL,'9,10',NULL,'2012-08-10 18:18:27',3,NULL,NULL,'2012-12-17 11:29:01','wer','wer','wer','we','wer','werwer','werwer','eqw','qwe','e','qwe','dfsdf','sdasd','rwe',0,NULL,NULL),(3,10101,1,'',0,'138',0,'5,6','北京市大众版 for general',NULL,'yrt',NULL,NULL,'yrt','rty','yrty',NULL,NULL,'rt',NULL,'5','5','99,93,103,89',NULL,NULL,NULL,NULL,NULL,NULL,'118',0,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'5,6',NULL,NULL,'2012-06-21 13:45:50',4,NULL,NULL,'2012-06-21 15:45:56',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'rty',NULL),(7,10101,0,'135',0,'138',0,NULL,'北京市大众版 for general','标题',NULL,'开机闪屏','闪屏文字',NULL,NULL,NULL,NULL,'预警资讯',NULL,'1',NULL,NULL,NULL,'1','eqw','asd','asdasd','asd',NULL,NULL,0,'das','qwe','qwe',1,'e','das','dasd','qwedas','eqwe','qwe','eqwe','dasdasd',NULL,'1',NULL,'2012-06-21 15:11:05',4,NULL,NULL,'2012-06-21 15:44:20','版权声明','法律','联系地址','400','官方email','官方网站','声讯','qwe','dasd','e','qwe','qwe','qwe','联系电弧',0,NULL,NULL),(8,10101,0,'135,136',0,'138',0,NULL,'北京市大众版 for general','标题',NULL,'开机闪屏','闪屏文字',NULL,NULL,NULL,NULL,'预警资讯',NULL,'1',NULL,NULL,NULL,'1','eqw','asd','asdasd','asd',NULL,NULL,0,'das','qwe','qwe',1,'e','das','dasd','qwedas','eqwe','qwe','eqwe','dasdasd',NULL,'1',NULL,'2012-06-21 15:14:38',4,NULL,NULL,'2012-09-25 17:40:12','版权声明','法律','联系地址','400','官方email','官方网站','声讯','qwe','dasd','e','qwe','qwe','qwe','联系电弧',0,NULL,NULL),(12,10109,0,'135,136',0,'140',0,'','河北省大众版 for windowsphone',NULL,'',NULL,NULL,'','','',NULL,NULL,'',NULL,'','','89,91,89,91',NULL,NULL,NULL,NULL,NULL,NULL,'',0,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'',NULL,NULL,'2012-06-21 17:20:49',4,NULL,NULL,'2012-06-21 19:02:45',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'',NULL),(19,10109,0,'135,136',0,'140',0,'','河北省大众版 for windowsphone',NULL,'',NULL,NULL,'','','',NULL,NULL,'',NULL,'','','89,91,89,91',NULL,NULL,NULL,NULL,NULL,NULL,'',0,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'',NULL,NULL,'2012-06-21 17:20:04',4,NULL,NULL,'2012-06-21 17:20:49',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'',NULL);

/*Table structure for table `meta_linkpage` */

DROP TABLE IF EXISTS `meta_linkpage`;

CREATE TABLE `meta_linkpage` (
  `id` bigint(11) NOT NULL auto_increment COMMENT 'ID',
  `linkpageName` varchar(50) default NULL COMMENT '链接页面名字',
  `linkpageurl` varchar(1000) default NULL COMMENT '链接页面链接',
  `linkpagetip` bigint(11) default NULL COMMENT '链接页面显示效果',
  `linkpagetiptext` varchar(100) default NULL COMMENT '链接页面显示效果文字',
  `createtime` datetime default NULL COMMENT '创建时间',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `meta_linkpage` */

insert  into `meta_linkpage`(`id`,`linkpageName`,`linkpageurl`,`linkpagetip`,`linkpagetiptext`,`createtime`) values (2,'rwer','wer',113,'rwerwer','2012-06-21 14:36:58'),(3,'rwer','we',NULL,'fsdfsdf','2012-06-21 14:37:02'),(4,'rwe','rwerw',112,'','2012-06-21 14:37:07'),(5,'6456','456456',NULL,'6456456','2012-06-21 14:39:02'),(6,'链接name','链接url',NULL,'也免除','2012-06-21 15:57:44'),(7,'链接name','链接url',113,'链接','2012-06-21 20:19:05');

/*Table structure for table `meta_province_ser_func` */

DROP TABLE IF EXISTS `meta_province_ser_func`;

CREATE TABLE `meta_province_ser_func` (
  `id` bigint(11) NOT NULL auto_increment COMMENT 'ID',
  `provinceSerFuncName` varchar(50) default NULL COMMENT '省级服务功能框的名字',
  `provinceSerFuncUrl` varchar(1000) default NULL COMMENT '省级服务功能框的地址',
  `provinceSerFuncVisualEffect` bigint(11) default NULL COMMENT '省级服务功能框的显示效果',
  `provinceSerFuncVisualEffectText` varchar(1000) default NULL COMMENT '省级服务功能框的显示效果文字',
  `provinceSerFuncIcon` varchar(1000) default NULL COMMENT '省级服务功能框的图标',
  `createtime` datetime default NULL COMMENT '创建时间',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `meta_province_ser_func` */

insert  into `meta_province_ser_func`(`id`,`provinceSerFuncName`,`provinceSerFuncUrl`,`provinceSerFuncVisualEffect`,`provinceSerFuncVisualEffectText`,`provinceSerFuncIcon`,`createtime`) values (3,'rwe','rwer',NULL,'r','werwer','2012-06-21 14:36:20'),(4,'rwe','rwer',115,'','rwer','2012-06-21 14:36:26'),(5,'yrt','yrty',NULL,'y','rtyrty','2012-06-21 14:38:32'),(6,'yrt','hfgh',115,'','fghfgh','2012-06-21 14:38:37'),(7,'hfg','hrty334',112,'','rtyrty','2012-07-12 13:53:17'),(8,'省级服务name','省级服务url',115,'','省级服务icon','2012-06-21 15:55:17'),(9,'省级服务name','省级服务url',113,'省级服务','省级服务icon','2012-06-21 20:16:46'),(10,'省2','省2 url',112,'','省2 icon','2012-06-21 20:17:10'),(11,'气象预警','http://wap.gd.weather.com.cn/alarm/',NULL,'','http://data.weather.com.cn/images/test/yjxx.png','2012-06-25 15:26:52'),(12,'台风路径','wsip://pMapServices.wi#typhoon',NULL,'','http://data.weather.com.cn/images/test/tflj.png','2012-06-25 15:27:11'),(13,'天气雷达','wsip://pMapServices.wi#radar',NULL,'','http://data.weather.com.cn/images/test/ldt.png','2012-06-25 15:27:29'),(14,'周边天气','wsip://pMapServices.wi#peripheral?areaid=101280101',NULL,'','http://data.weather.com.cn/images/test/yt.png','2012-06-25 15:27:49'),(15,'南海海洋','http://wap.gd.weather.com.cn/sea/sea_nh_client.shtml',NULL,'','http://data.weather.com.cn/images/test/hytq.png','2012-06-25 15:28:18'),(16,'沿海海洋','http://wap.gd.weather.com.cn/sea/sea_yh_client.shtml',NULL,'','http://data.weather.com.cn/images/test/hytq.png','2012-06-25 15:28:59'),(17,'新浪微博','http://weibo.com/910620121',NULL,'','http://data.weather.com.cn/images/test/tqwb.png','2012-06-25 15:29:18'),(18,'服务推荐','http://gd.weather.com.cn/cnweather/fwtj/index.shtml',NULL,'','http://data.weather.com.cn/images/test/WAP.png','2012-06-25 15:29:34'),(20,'气象科普','http://gd.weather.com.cn/cnweather/qxkp/index.shtml',NULL,'','http://data.weather.com.cn/images/test/WAP.png','2012-06-25 15:30:20');

/*Table structure for table `meta_recommandpage` */

DROP TABLE IF EXISTS `meta_recommandpage`;

CREATE TABLE `meta_recommandpage` (
  `id` bigint(11) NOT NULL auto_increment COMMENT 'ID',
  `recommandPageName` varchar(50) default NULL COMMENT '推荐页面名字',
  `recommandPageUrl` varchar(1000) default NULL COMMENT '推荐页面链接',
  `recommandPageTipText` varchar(100) default NULL COMMENT '推荐页面显示效果文字',
  `recommandPageTip` bigint(11) default NULL COMMENT '推荐页面显示效果',
  `createtime` datetime default NULL COMMENT '创建时间',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `meta_recommandpage` */

insert  into `meta_recommandpage`(`id`,`recommandPageName`,`recommandPageUrl`,`recommandPageTipText`,`recommandPageTip`,`createtime`) values (7,'rty','rty','rty',NULL,'2012-06-21 15:45:39'),(8,'ert','dfgre','erte',NULL,'2012-06-21 15:45:43'),(9,'推荐页面name','推荐页面url','',NULL,'2012-06-21 16:00:28'),(10,'推荐页面name','推荐页面url','推荐页面',113,'2012-06-21 20:13:35');

/*Table structure for table `meta_tabpage` */

DROP TABLE IF EXISTS `meta_tabpage`;

CREATE TABLE `meta_tabpage` (
  `id` bigint(11) NOT NULL auto_increment COMMENT 'ID',
  `tabName` varchar(50) default NULL COMMENT '标签页的名字',
  `tabVisualEffect` bigint(11) default NULL COMMENT '标签页的显示效果',
  `tabVisualEffectText` varchar(50) default NULL COMMENT '标签页的显示效果文字',
  `tabUrl` varchar(1000) default NULL COMMENT '标签页的地址',
  `hotLinkIcon` varchar(1000) default NULL COMMENT '标签页的图标（地址）',
  `createtime` datetime default NULL COMMENT '创建时间',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `meta_tabpage` */

insert  into `meta_tabpage`(`id`,`tabName`,`tabVisualEffect`,`tabVisualEffectText`,`tabUrl`,`hotLinkIcon`,`createtime`) values (7,'7567',NULL,'7567567','567','','2012-06-21 15:45:31'),(8,'yrty',NULL,'rty','rty','','2012-06-21 15:45:35'),(9,'标签页name',113,'标签页','标签页url','','2012-06-21 20:12:54'),(10,'标2',115,'','标2url','','2012-06-21 20:13:16');

/*Table structure for table `meta_version` */

DROP TABLE IF EXISTS `meta_version`;

CREATE TABLE `meta_version` (
  `id` bigint(20) NOT NULL auto_increment COMMENT 'ID',
  `provinceId` bigint(20) default NULL COMMENT '所属省份',
  `clientSystemId` int(20) default NULL COMMENT '客户端系统编号',
  `clientVersionId` int(20) default NULL COMMENT '客户端版本编号',
  `nationwide` tinyint(4) default NULL COMMENT '国家范围',
  `versionNo` int(11) default NULL COMMENT '版本编号',
  `metaDataId` bigint(20) default NULL COMMENT '元数据编号',
  `profession` tinyint(4) default NULL COMMENT '是否专业版',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `meta_version` */

insert  into `meta_version`(`id`,`provinceId`,`clientSystemId`,`clientVersionId`,`nationwide`,`versionNo`,`metaDataId`,`profession`) values (1,10101,140,135,1,50,153,0),(5,10101,138,135,1,51,939,0),(6,10101,138,136,1,51,939,0),(8,10101,140,135,0,50,245,0),(9,10101,140,136,0,50,5,0),(10,10101,138,135,0,57,1225,0),(11,10101,138,136,0,57,1225,0),(12,10109,140,135,1,50,120,0),(13,10109,140,135,1,50,37,1),(14,10109,140,136,1,50,37,1),(15,10109,140,136,1,50,40,0),(16,10109,140,135,0,50,136,0),(17,10109,140,136,0,50,43,0),(18,10109,138,135,1,51,959,0),(19,10109,138,136,1,51,959,0),(20,10109,138,136,0,52,1027,0);

/*Table structure for table `sys_dictionary` */

DROP TABLE IF EXISTS `sys_dictionary`;

CREATE TABLE `sys_dictionary` (
  `id` bigint(20) NOT NULL,
  `groupName` varchar(100) default NULL COMMENT '字典组名称',
  `groupDisplayName` varchar(100) default NULL COMMENT '组别显示名称',
  `dictKey` varchar(100) default NULL COMMENT '典字key',
  `dictValue` varchar(100) default NULL COMMENT '典字value',
  `dictDisplayName` varchar(100) default NULL COMMENT '字典值显示名称',
  `orderNo` int(11) default NULL COMMENT '存储用于排序的数字，值越大越往后排',
  `comment` varbinary(500) default NULL COMMENT '备注',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典表';

/*Data for the table `sys_dictionary` */

insert  into `sys_dictionary`(`id`,`groupName`,`groupDisplayName`,`dictKey`,`dictValue`,`dictDisplayName`,`orderNo`,`comment`) values (1,'gender','性别','1','','男',0,NULL),(2,'gender','性别','0','','女',0,NULL),(4,'audit_status','审核状态','1','','待审核',0,NULL),(5,'audit_status','审核状态','2','','审核通过',0,NULL),(6,'audit_status','审核状态','3','','审核不通过',0,NULL),(7,'visible_status','显示状态','1','','显示',0,NULL),(8,'visible_status','显示状态','0','','不显示',0,NULL),(10,'city_level','城市级别','1','','一级城市',0,NULL),(11,'city_level','城市级别','2','','二级城市',0,NULL),(12,'city_level','城市级别','3','','三级城市',0,NULL),(15,'client_platform','客户端平台','1','ios','iOS',0,''),(16,'client_platform','客户端平台','2','Android','Android',0,NULL),(17,'client_platform','客户端平台','3','','S40 webapp',0,''),(18,'client_platform','客户端平台','4','','Symbian S60 V5',0,NULL),(19,'client_platform','客户端平台','5','','Symbian Belle',0,NULL),(20,'client_platform','客户端平台','6','','Meego',0,NULL),(24,'appreciation_type','增值业务类型','1','','声讯服务',0,NULL),(25,'appreciation_type','增值业务类型','2','','嵌入HTML5块服务',0,NULL),(26,'appreciation_type','增值业务类型','3','','短彩信点播服务',0,NULL),(27,'appreciation_type','增值业务类型','4','','短彩信包月定制服务',0,NULL),(28,'ugc_type','UGC类型','1','','通信员资讯',0,NULL),(29,'ugc_type','UGC类型','2','','实景图片',0,NULL),(30,'ugc_type','UGC类型','3','','评论',0,NULL),(31,'ugc_type','UGC类型','4','','签到',0,NULL),(32,'ugc_type','UGC类型','5','','视频',0,NULL),(40,'move_type','移动类型','1','','日际跨省移动',0,NULL),(41,'move_type','移动类型','2','','日际跨地区移动',0,NULL),(42,'move_type','移动类型','3','','异地查询',0,NULL),(46,'account_type','账号类型','1','','微薄账号',0,''),(47,'account_type','账号类型','2','','qq账号',0,NULL),(48,'account_type','账号类型','3','','网站账号',0,NULL),(49,'account_type','账号类型','4','','其他',0,NULL),(50,'client_channel','客户端渠道','1','ChinaWeather','中国天气网',0,NULL),(51,'client_channel','客户端渠道','2','iTunes','iTunes',0,NULL),(52,'client_channel','客户端渠道','3','AndroidMarket','Android Market',0,NULL),(53,'online_time_type','在线时长类型','1','','0-1分钟',0,NULL),(54,'online_time_type','在线时长类型','2','','1-5分钟',0,NULL),(55,'online_time_type','在线时长类型','3','','5分钟以上',0,NULL),(57,'login_count_type','登录次数类型','1','','单次登录用户数',0,NULL),(58,'login_count_type','登录次数类型','2','','二次登录用户数',0,NULL),(59,'login_count_type','登录次数类型','3','','多次登录用户数',0,NULL),(61,'page_view_type','页面浏览类型','1','','浏览1页',0,NULL),(62,'page_view_type','页面浏览类型','2','','浏览2页\n\n',0,NULL),(63,'page_view_type','页面浏览类型','3','','浏览3页',0,NULL),(64,'page_view_type','页面浏览类型','4','','浏览4页',0,NULL),(65,'page_view_type','页面浏览类型','5','','浏览5页及\n以\n上',0,NULL),(66,'client_platform','客户端平台','7','','机顶盒',0,NULL),(67,'memcached_search_type','memcached查询类型','forecast','','天气预报+天气实况',0,NULL),(68,'memcached_search_type','memcached查询类型','alarm','','预警信息',0,NULL),(69,'memcached_search_type','memcached查询类型','weatherIndex','','天气指数',0,NULL),(70,'memcached_search_type','memcached查询类型','forecast_new','','天气预报+天气实况（新接口）',0,NULL),(71,'client_platform','客户端平台','8','wp','Windows Phone7',0,NULL),(72,'client_platform','客户端平台','0','','全部',0,NULL),(73,'move_type','移动类型','0','','全部',0,NULL),(75,'ugc_type','UGC类型','0','','全部',0,NULL),(76,'appreciation_type','增值业务类型','0','','法师短裤费德里克',0,''),(77,'client_platform','客户端平台','9','','PC桌面插件',0,NULL),(78,'notify_send_type','通知发送类型','1','1','用户',0,'表示直接与用户id联系'),(79,'notify_send_type','通知发送类型','2','2','用户组',0,'表示通过用户组id与用户表lianxi'),(80,'notify_send_type','通知发送类型','3','3','角色',0,'表示通过角色id与用户表联系'),(81,'notify_type','通知类型','1','1','系统通知',0,NULL),(82,'notify_type','通知类型','2','2','短消息',0,NULL),(83,'notify_type','通知类型','3','3','提醒',0,NULL),(84,'notify_send_status','通知发送状态','1','1','未发送',0,'通知的发送状态'),(85,'notify_send_status','通知发送状态','2','1','已发送',0,'通知的发送状态'),(87,'defaultLivingIndex','默认指数','ac','ac','空调开启指数',0,''),(88,'defaultLivingIndex','默认指数','ag','ag','息斯敏过敏指数',0,NULL),(89,'defaultLivingIndex','默认指数','bp','bp','高血压气象指数',0,NULL),(90,'defaultLivingIndex','默认指数','cl','cl','晨练指数',0,NULL),(91,'defaultLivingIndex','默认指数','co','co','舒适度指数',0,NULL),(92,'defaultLivingIndex','默认指数','ct','ct','穿衣指数',0,NULL),(93,'defaultLivingIndex','默认指数','dy','dy','钓鱼指数',0,NULL),(94,'defaultLivingIndex','默认指数','fs','fs','防晒指数',0,NULL),(95,'defaultLivingIndex','默认指数','gj','gj','逛街指数',0,NULL),(96,'defaultLivingIndex','默认指数','gm','gm','感冒指数',0,NULL),(97,'defaultLivingIndex','默认指数','gz','gz','干燥指数',0,NULL),(98,'defaultLivingIndex','默认指数','hc','hc','划船指数',0,NULL),(99,'defaultLivingIndex','默认指数','jt','jt','交通指数',0,NULL),(100,'defaultLivingIndex','默认指数','xq','xq','心情指数',0,NULL),(101,'defaultLivingIndex','默认指数','yd','yd','运动指数',0,NULL),(102,'defaultLivingIndex','默认指数','yh','yh','约会指数',0,NULL),(103,'defaultLivingIndex','默认指数','lk','lk','路况指数',0,NULL),(104,'defaultLivingIndex','默认指数','ls','ls','晾晒指数',0,NULL),(105,'defaultLivingIndex','默认指数','mf','mf','美发指数',0,NULL),(106,'defaultLivingIndex','默认指数','nl','nl','夜生活指数',0,NULL),(107,'defaultLivingIndex','默认指数','pj','pj','啤酒指数',0,NULL),(108,'defaultLivingIndex','默认指数','pk','pk','放风筝指数',0,NULL),(109,'defaultLivingIndex','默认指数','pl','pl','空气污染扩散条件指数',0,NULL),(110,'defaultLivingIndex','默认指数','pp','pp','化妆指数',0,NULL),(111,'defaultLivingIndex','默认指数','st','st','体感温度',0,NULL),(112,'visualeffect','显示效果','new','new','提示new',0,NULL),(113,'visualeffect','显示效果','bubble','bubble','气泡',0,NULL),(115,'visualeffect','显示效果','Highlight','Highlight','加亮',0,NULL),(116,'advposition','广告位','1','1','广告位一',0,NULL),(117,'advposition','广告位','2','2','广告位二',0,NULL),(118,'advposition','广告位','3','3','广告位三',0,NULL),(119,'advposition','广告位','4','4','广告位四',0,NULL),(120,'valueadded','增值服务','MOBILE_SMS','MOBILE_SMS','中国移动短信定制服务',0,NULL),(121,'valueadded','增值服务','MOBILE_MMS','MOBILE_MMS','中国移动彩信定制服务',0,NULL),(122,'valueadded','增值服务','UNICOM_SMS','UNICOM_SMS','中国联通短信定制服务',0,NULL),(123,'valueadded','增值服务','UNICOM_MMS','UNICOM_MMS','中国联通彩信定制服务',0,NULL),(124,'valueadded','增值服务','TELECOM_SMS','TELECOM_SMS','中国电信短信定制服务',0,NULL),(125,'valueadded','增值服务','TELECOM_MMS','TELECOM_MMS','中国电信彩信定制服务',0,NULL),(126,'valueadded','增值服务','CONSULT_TELEPHONE','CONSULT_TELEPHONE','人工咨询服务',0,NULL),(127,'valueadded','增值服务','IVR_TELEPHONE','IVR_TELEPHONE','声讯服务',0,NULL),(128,'processstatus','审批状态','1','1','审核中',0,NULL),(129,'processstatus','审批状态','2','2','已发布',0,NULL),(130,'defaultLivingIndex','默认指数','uv','uv','紫外线强度指数',0,NULL),(131,'defaultLivingIndex','默认指数','wc','wc','风寒指数',0,NULL),(132,'defaultLivingIndex','默认指数','xc','xc','洗车指数',0,NULL),(133,'defaultLivingIndex','默认指数','ys','ys','雨伞指数',0,NULL),(134,'defaultLivingIndex','默认指数','zs','zs','中暑指数',0,NULL),(135,'clientVersion','客户端版本','2.0.0','2.0.0','2.0.0',0,NULL),(136,'clientVersion','客户端版本','2.0.1','2.0.1','2.0.1',0,NULL),(138,'clientSystem','客户端系统','general','general','其他',0,''),(140,'clientSystem','客户端系统','windowsphone','windowsphone','windowsphone',0,NULL),(141,'metaDataSystemIp','metaData同步服务器IP','61.4.184.64','iehg@114!*$>^$;','61.4.184.64',0,NULL),(142,'metaDataSystemIp','metaData同步服务器IP','61.4.184.65','iehg@114!*$>^%;','61.4.184.65',0,NULL),(143,'metaDataSystemIp','metaData同步服务器IP','61.4.184.36','RmQlTuggHHA2','61.4.184.36',0,NULL),(144,'client_platform','客户端平台','10',NULL,'WIN 8系列',0,NULL),(149,'report_progress_stat','进度(报告)','1',NULL,'需求',0,NULL),(151,'report_progress_stat','进度(报告)','2',NULL,'设计',0,NULL),(153,'report_progress_stat','进度(报告)','3',NULL,'开发',0,NULL),(154,'ifPublish','是否上市','0',NULL,'否',0,NULL),(155,'ifPublish','是否上市','1',NULL,'是',0,NULL),(156,'biz_report_taskSort','任务类别','1','','设计',0,''),(157,'biz_report_taskSort','任务类别','2',NULL,'开发',0,NULL),(158,'biz_report_taskSort','任务类别','3',NULL,'测试',0,NULL),(159,'biz_report_taskSort','任务类别','4',NULL,'研究',0,NULL),(160,'biz_report_taskSort','任务类别','5',NULL,'讨论',0,NULL),(161,'biz_report_taskSort','任务类别','6',NULL,'界面',0,NULL),(162,'biz_report_taskSort','任务类别','7',NULL,'其他',0,NULL),(164,'report_progress_stat','进度(报告)','4',NULL,'测试',0,NULL),(165,'report_progress_stat','进度(报告)','5',NULL,'完成',0,NULL),(167,'biz_report_moduleBelonged','所属模块','1',NULL,'GIS',0,NULL),(168,'biz_report_moduleBelonged','所属模块','2',NULL,'ADI',0,NULL),(169,'biz_report_moduleBelonged','所属模块','3',NULL,'客户端',0,NULL),(170,'biz_report_bugType','bug类型','1',NULL,'UI',0,NULL),(171,'biz_report_bugType','bug类型','2',NULL,'性能',0,NULL),(172,'biz_report_bugType','bug类型','3',NULL,'稳定性',0,NULL),(173,'biz_report_bugType','bug类型','4',NULL,'基本功能未实现',0,NULL),(174,'biz_report_bugType','bug类型','5',NULL,'数据',0,NULL),(175,'biz_report_bugType','bug类型','6',NULL,'新需求',0,NULL),(176,'biz_report_priority','优先级','1',NULL,'高',0,NULL),(177,'biz_report_priority','优先级','2',NULL,'中',0,NULL),(178,'biz_report_priority','优先级','3','','低',0,''),(179,'report_test_stat','bug状态','1',NULL,'新建',0,NULL),(180,'report_test_stat','bug状态','2',NULL,'开始分析',0,NULL),(181,'report_test_stat','bug状态','3',NULL,'已解决',0,NULL),(182,'report_test_stat','bug状态','4',NULL,'验证',0,NULL),(183,'report_test_stat','bug状态','5',NULL,'完成',0,NULL),(184,'report_test_stat','bug状态','6',NULL,'未通过',0,NULL),(185,'report_test_stat','bug状态','7',NULL,'延后',0,NULL),(187,'WARNING_STATUS','预警信息推送状态','0','','未推送',0,''),(189,'WARNING_STATUS','预警信息推送状态','1','','正在推送',0,''),(191,'WARNING_STATUS','预警信息推送状态','2','','已经推送',0,''),(192,'client_channel','客户端渠道','4','91Shouji','91助手',0,NULL),(193,'client_channel','客户端渠道','5','OviStore','ovi store',0,NULL),(194,'client_channel','客户端渠道','6','MicrosoftMarket','微软应用商店',0,NULL),(195,'client_channel','客户端渠道','7','JifengApp','机锋应用',0,NULL),(196,'client_channel','客户端渠道','8','HuaweiCloud','华为智慧云平台',0,NULL),(197,'client_channel','客户端渠道','9','EsurfingMarket','电信天翼空间',0,NULL),(198,'client_channel','客户端渠道','10','WoMarket','联通沃商店',0,NULL),(199,'client_channel','客户端渠道','11','MicrosoftMarketWin8','微软Windows 8商店',0,NULL),(201,'clientVersion','客户端版本','2.0.2','2.0.2','2.0.2',0,''),(202,'clientVersion','客户端版本','2.0.3','2.0.3','2.0.3',0,''),(203,'clientVersion','客户端版本','2.0.4','2.0.4','2.0.4',0,''),(205,'clientVersion','客户端版本','2.1.0','2.1.0','2.1.0',0,''),(207,'client_channel','客户端渠道','12','Googleplay','谷歌商店',0,''),(209,'client_channel','客户端渠道','12','Googleplay','谷歌商店',0,''),(211,'client_channel','客户端渠道','13','BaiduAppMarket','百度移动应用',0,''),(213,'client_channel','客户端渠道','14','Renren','人人',0,''),(215,'client_channel','客户多渠道','15','TencentWeibo','腾讯',0,''),(217,'clientVersion','客户端版本','2.0.5','2.0.5','2.0.5',0,''),(219,'clientVersion','客户端版本','2.1.1','2.1.1','2.1.1',0,''),(221,'clientVersion','客户端版本','2.1.2','2.1.2','2.1.2',0,''),(223,'clientSystem','客户端系统','ios','ios','ios',0,''),(225,'clientVersion','客户端版本','2.1.3','2.1.3','2.1.3',0,''),(227,'clientVersion','客户端版本','2.1.4','2.1.4','2.1.4',0,''),(229,'clientVersion','客户端版本','2.1.5','2.1.5','2.1.5',0,'');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
