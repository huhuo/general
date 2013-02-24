<%@page import="com.eray.systemmanage.constant.Constant"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set value="${pageContext.request.contextPath }" var="path" scope="application"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<!-- 加载Extjs 的css和js -->
<link rel="stylesheet" type="text/css" href="${path}/res/ext-integration/js/ext/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="${path}/res/system-manage/css/icon.css" />
<link rel="stylesheet" type="text/css" href="${path}/res/system-manage/css/main.css" />
<script type="text/javascript" src="${path}/res/ext-integration/js/ext/ext-all.js"></script>
<script type="text/javascript" src="${path}/res/ext-integration/js/ext/locale/ext-lang-zh_CN.js"></script>
<script type="text/javascript" src="${path}/res/ext-integration/js/extend/eray-request.js"></script>
<script type="text/javascript" src="${path}/res/ext-integration/js/extend/eray-rowediting.js"></script>
<script type="text/javascript" src="${path}/res/ext-integration/js/extend/eray-combobox.js"></script>
<script type="text/javascript" src="${path}/res/ext-integration/js/extend/eray-store.js"></script>
<script type="text/javascript" src="${path}/res/system-manage/js/model.js"></script>

<script type="text/javascript">

	appContextPath = '${path}';	// used for context path in js file 
	
	Ext.onReady(function(){	
		// 主框架
		Ext.create('Ext.container.Viewport', {
			layout: 'border',
			items: [{
				id: 'home_title_panel',
				region: 'north',
				height: 88,
				frame: true,
				border: false,
				margins: '0 0 0 0',
				//baseCls:'bg_panel',
				items:[{
			//		id:'home_title_panel',
					title: '欢迎！${sessionScope.seUser.username}',
					width:160,
					height:25,
					margin:'41 18 0 0',
					baseCls: 'welcome_panel'
				}, {
					width:80,
					height:25,
					margin:'35 60 0 0',
					layout: 'fit',
					baseCls: 'exit_panel',
					items: {
						xtype: 'button',
						iconCls: 'icon-user',
						text: '退出',
						listeners: {
							click: function(btn, e, eOpts) {
								document.location.href = '${path}/sm/enter/logout.do';
							}
						}
					}
				}]
			 }, {
				 xtype: 'tabpanel',
				 margins: '0 0 0 0',
				id: 'home_main_panel',
				region: 'center',
				layout: 'fit',
				items: [
						<c:forEach items="${sessionScope.seModule}" var="moduleFirEntry" varStatus="statusFir">
							{
								title: '${moduleFirEntry.key.name}',   //first level module's name
								iconCls: '${moduleFirEntry.key.icon}', //'icon-user', //'${moduleFirEntry.key.icon}'
								layout: 'border',
								items:[{	// to crate menu for each platform
									region: 'west',
									collapsible: true,
									title: '导航栏',
									width: 145,
									layout: 'accordion',
									split: true,
									items: [
										<c:forEach items="${moduleFirEntry.value}" var="moduleSecEntry" varStatus="statusSec">
											{
												xtype: 'panel',
												title: '${moduleSecEntry.key.name}',	//second level module's name
												border: false,
												autoScroll: true,
												items: new Ext.view.View({
													//baseCls: "modules", /////////////
													store: Ext.create('Ext.data.ArrayStore', {
														storeId: 'moduleStore${moduleSecEntry.key.id}', 
														autoDestroy: true,
														idIndex: 0,
													    fields: ['id', 'name', 'icon', 'url', 'parentId'],
													    data: [								// third level modules
															<c:forEach items="${moduleSecEntry.value}" var="moduleThi" varStatus="statusThi">
															['${moduleThi.id}', '${moduleThi.name}', '${moduleThi.icon}', '${moduleThi.url}', '${moduleThi.parentId}']<c:if test="${!statusThi.last}">,</c:if>
															</c:forEach>
													    ]
													}),				// to create store for each menu
													tpl: new Ext.XTemplate(
															'<tpl for=".">',
											                   '<div id="{id}" class="module">',
											                   /* (!Ext.isIE?'<img width="64" height="64" src="{icon}"/>': */
											                	   '<div><img width="64" height="64" src="${path}/{icon}"/></div> '/*) */,
											                       '<div>{name}</div>',
											                   '</div>',
											               '</tpl>'
										 			),				// to create XTemplate for each menu
													autoHeight: true,
													multiSelect: false,
													itemSelector: 'div.module',
													overItemCls: 'module-hover',
													multiSelect: false,
													emptyText: '无菜单',
													listeners: {
														itemclick: function(view, record, item, index, e, eOpts) {
															var panelId = 'home_main_panel'+'${moduleFirEntry.key.id}';
															var mainPanel = Ext.getCmp(panelId);
															mainPanel.removeAll(true);
															Ext.getCmp(panelId).getLoader().load({
																url: '${path}/'+record.data.url
															});
														}
													}
												})
											}<c:if test="${!statusSec.last}">,</c:if>
										</c:forEach>
									]
								},{	// to create main panel for each platform
									id: 'home_main_panel'+'${moduleFirEntry.key.id}',
									region: 'center',
									layout: 'fit',
									html: true,
									autoScroll: true,
									loader: {
										url: '',
										scripts: true
									}
								}]
								
							} <c:if test="${!statusFir.last}">,</c:if>
						</c:forEach>
					
				]
			}]
		});
	
		<c:if test="${not empty seModuleIdxPanel}">
			var idxPanel = Ext.getCmp('home_main_panel${seModuleIdxPanel.id}');
			idxPanel.removeAll(true);
			idxPanel.getLoader().load({url: '${path}/${seModuleIdxPage.url}'});
		</c:if>
		//Ext.getCmp('home_main_panel').getLoader().load();
		
	});
	
</script>

<title>Insert title here</title>
</head>
<body>


</body>
</html>