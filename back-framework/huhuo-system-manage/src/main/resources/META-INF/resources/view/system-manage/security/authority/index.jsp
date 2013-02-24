<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript">

Ext.onReady(function(){
	var authStore = Ext.create('Ext.data.Store',{
		model: 'ModelAuthority',   
		autoSync: false,
		autoLoad: true,
		pageSize:23,
	    proxy: {
	            type: 'rest',
	            url: '${path}/sm/security/authority/get.do',
	            pageParam: 'pageNo',
	            startParam: 'page.start',
	            limitParam: 'page.limit',
	            actionMethods: 'post',
	            reader: {
	                type: 'json',
	                root: 'records'
	            }
	        }
	});
	
	 // 行编辑插件
    var authRowEditing = Ext.create('Ext.grid.plugin.ERowEditing', {
    	clicksToEdit: 2,
    	listeners: {
    		edit: function(editor, e, eOpts) {
    			// 发送请求，操作数据库
    			var sysAuthority = editor.record.data;
				ajaxRequest('${path}/sm/security/authority/save.do', sysAuthority, authStore);
    		},
    		canceledit: function(grid, eOpts) {
    			if(!grid.record.data.id) {	// 如果grid.record.data.id==null
    				authStore.remove(grid.record);
    			}
    		}
    	} 
    });
	
    var gridAuthCtrl = Ext.create('Ext.grid.Panel', {
        plugins: [authRowEditing],
        layout: 'fit',
        frame: true,
        store: authStore,
        iconCls: 'icon-user',
       // columnLines: true,
        columns: [{
            text: 'ID',
            width: 100,
            sortable: true,
            dataIndex: 'id'
        }, {
            text: '权限名称',
            width: 250,
            sortable: true,
            dataIndex: 'name',
            editor: {
				xtype: 'textfield',
				allowBlank: false,
				blankText: '权限名称不能为空',
            	minLength: 1,
            	minLengthText: '权限名称最少1个字符',
            	maxLength: 255,
            	maxLengthText:'权限名称最多255个字符'
            }
        }, {
            text: '备注',
            width: 400,
            sortable: true,
            dataIndex: 'comment',
            editor: {
				xtype: 'textfield',
				allowBlank: true
            }
        }], dockedItems: [{
            xtype: 'toolbar',
            dock: 'top',
            items: [{
                text: '添加',
                iconCls: 'icon-add',
                handler: function(){
                	var selection = this.up('grid').getView().getSelectionModel().getSelection()[0];
                    var addIdx = 0;
                	if(selection) {
                		addIdx = selection.index + 1;
                    }
                	
                   	var authority = new ModelAuthority();
                   	authStore.insert(addIdx, authority);
					authRowEditing.startEdit(addIdx, 0);
                }
            }, {
            	itemId: 'update',
            	text: '修改',
                iconCls: 'icon-update',
                disabled: true,
                handler: function(){
                    var selection = this.up('grid').getView().getSelectionModel().getSelection()[0];
                    authRowEditing.startEdit(selection, 1);
                }
            }, {
                itemId: 'delete',
                text: '删除',
                iconCls: 'icon-delete',
                disabled: true,
                handler: function(){
                    var selection = this.up('grid').getView().getSelectionModel().getSelection()[0];
                    Ext.MessageBox.confirm('确认', '确定要删除该权限吗？', function(response) {
                        if (response=='yes' && selection) {
                        	// 删除页面上的模型对象
                        	if(!selection.data.id) {	// selection.data.id==null（不存在）
                        		authStore.remove(selection);
                        	} else {	// 将记录从服务器中删除，并重载页面
                        		ajaxRequest('${path}/sm/security/authority/delete.do', selection.data, authStore);
                        	}
                        }
                    });
                }
            }, '-',{
            	itemId: 'relateModule',
            	text: '关联模块',
                iconCls: 'icon-user',
                disabled: true,
                handler: function(btn, event){
                	var selection = this.up('grid').getView().getSelectionModel().getSelection()[0];
                	var authId = selection.get('id');
                	hndRelateModule(btn, authId);
                }
            }, {
            	itemId: 'relateElement',
            	text: '关联页面元素',
                iconCls: 'icon-user',
                disabled: true,
                handler: function(btn, event){
                	var selection = this.up('grid').getView().getSelectionModel().getSelection()[0];
                	var authId = selection.get('id');
                	hndRelateElement(btn, authId);
                }
            }, {
            	itemId: 'relateProvince',
            	text: '关联省份',
                iconCls: 'icon-user',
                disabled: true,
                handler: function(btn, event){
                	var selection = this.up('grid').getView().getSelectionModel().getSelection()[0];
                	var authId = selection.get('id');
                	hndRelateProvince(btn, authId);
                }
            },'->', {
            	xtype: 'textfield',
	        	itemId: 'authIdInput',
	        	fieldLabel: '权限ID',
	        	labelAlign: 'right',
	        	labelWidth: 60,
	        	width: 200
            }, {
            	xtype: 'textfield',
            	itemId: 'authNameInput',
	        	fieldLabel: '权限名称',
	        	labelAlign: 'right',
	        	labelWidth: 80,
	        	width: 200
            }, {
            	xtype: 'textfield',
            	itemId: 'commentInput',
	        	fieldLabel: '备注',
	        	labelAlign: 'right',
	        	labelWidth: 80,
	        	width: 200
            }, {
	            text: '查询',
	            iconCls: 'icon-search',
	            disabled: false,
	            handler: function(){
	            	var grid = this.up('grid');
	            	var authId = grid.down('#authIdInput').getValue();
	            	var authName = grid.down('#authNameInput').getValue();
	            	var comment = grid.down('#commentInput').getValue();
	            	authStore.getProxy().extraParams = {
	            		'id': authId,
	            		'name': authName,
	            		'comment': comment
	            	};
	            	authStore.load();
	            }
	        }]
        }, {
            xtype: 'pagingtoolbar',
            dock: 'bottom',
            store: authStore,
            displayInfo: true,
            displayMsg: '显示记录 {0} - {1}，共 {2}条',
            emptyMsg: "无数据"
        }]
    });
	
    /* ********************************************
    	菜单按钮随 grid 记录选择变化
       ******************************************** */
    gridAuthCtrl.getSelectionModel().on('selectionchange', function(selModel, selections){
    	gridAuthCtrl.down('#delete').setDisabled(selections.length === 0);
    	gridAuthCtrl.down('#update').setDisabled(selections.length === 0);
    	gridAuthCtrl.down('#relateModule').setDisabled(selections.length === 0);
    	gridAuthCtrl.down('#relateElement').setDisabled(selections.length === 0);
    	gridAuthCtrl.down('#relateProvince').setDisabled(selections.length === 0);
    });
    
    /* ********************************************
		权限添加与更新的请求处理
   	   ******************************************** */
    var ajaxRequest = function(url, sysAuthority, store){
    	Ext.eray.request({
			url: url,
			params: {
				'id': sysAuthority.id,
        		'name': sysAuthority.name,
        		'comment': sysAuthority.comment
			},
			successCallback: function(responseText, scope) {
				// 更新数据源
				store.removeAll();
				store.load();
			}
		});
    	
    };
	
    /* ********************************************
    	创建数据源
    	******************************************* */
    var createStore = function(modelO, pageSizeO, groupFieldO, urlO, extraParamsO){
    	var store = Ext.create('Ext.data.Store', {
	        model: modelO,
	        autoLoad: false,
	        pageSize: pageSizeO,	// 加载所有记录
	        groupField: groupFieldO,
	        proxy: {
	            type: 'rest',
	            url: urlO,
	            extraParams: extraParamsO,
	            reader: {
	                type: 'json',
	                root: 'records'
	            }
	        },
    		getGroupString: function(instance){
    			var value = instance.data.parentId;
    			var m = secModuleStore.findRecord('id', value);
       			if(m){
        			return m.data.name;
        		}else{
        			return value;
        		}
    		}
	    });
    	
    	return store;
    };
    /* ********************************************
    	创建弹出窗口
    	******************************************* */
    var createWindow = function(w, h, titleO, idO){
    	relateModuleWin = Ext.create('widget.window', {
    		id: idO,
    		title:titleO,
    	    autoScroll: false,
    	    frame: false,
    	    layout: 'fit',
    	    width: w,
    	    height: h,
    	    constrain: true,
			items: {
				border: false
			},
    	    collapsible: true,
    	    modal: true,
    	    closeAction: 'hide'
    	});
    	
    	return relateModuleWin;
    };
    
   	var secModuleStore = Ext.create('Ext.data.Store', {
		model: 'ModelModule',
		autoSync: false,
		//autoLoad: true,
		pageSize: null,
		proxy:{
			type: 'rest',
			url: '${path}/sm/security/module/get-module.do',
			pageParam: 'pageNo',
			startParam: 'page.start',
			limitParam: 'page.limit',
			actionMethods: 'post',
			reader:{
				type: 'json',
				root: 'records'
			}
		}
	});
   	secModuleStore.load();
    
    /* ********************************************
    	处理关联模块的函数
    	******************************************* */
	var hndRelateModule = function(btn, authId){
		var moduleO = 'ModelModule';
		var groupNameO = 'parentId';
		var extraParamsO = { type: 1, authorityId: authId };
		 var unrelatedStore = createStore(moduleO, 100, groupNameO, 
				'${path}/sm/security/authorityresource/unrelated-res.do', extraParamsO);
		
		var relatedStore = createStore(moduleO, null, groupNameO, 
				'${path}/sm/security/authorityresource/related-res.do', extraParamsO);
	
		unrelatedStore.load();
		relatedStore.load(); 
		
		var columns = [
			       		{text: "ID", width: 80, sortable: true, dataIndex: 'id'},
			       		{text: "模块名称", width: 100, sortable: true, dataIndex: 'name'},
			       		{text: "访问地址", flex: 1, sortable: true, dataIndex: 'url'},
			       		{text: "所属模块", width: 100, sortable: true, dataIndex: 'parentId', renderer: function(value){
			       			var m = secModuleStore.findRecord('id', value);
			       			if(m){
			        			return m.data.name;
			        		}else{
			        			return value;
			        		}
			       		}},
			       		{text: "状态", xtype:'booleancolumn', width: 80, trueText:'可用', falseText:'停用', sortable: true, dataIndex: 'visible'},
			       		{text: "创建时间", xtype: 'datecolumn', format: 'Y-m-d H:i:s', width: 150, sortable: true, dataIndex: 'createTime'}
			       	];
					// 未关联菜单面板
		var unrelatedGrid = Ext.create('Ext.grid.Panel', {
	    // multiSelect: true,
		   features: Ext.create('Ext.grid.feature.Grouping',{
				groupByText: '{name}',
				hideGroupedHeader: false,
				groupHeaderTpl: '{name} ({rows.length} 个菜单)'
			}),
			selModel: Ext.create('Ext.selection.CheckboxModel'),
	        viewConfig: {
	            plugins: {
	                ptype: 'gridviewdragdrop',
	                dragGroup: 'firstGridDDGroup',
	                dropGroup: 'secondGridDDGroup'
	            }
	        },
	        store            : unrelatedStore,
	        columns          : columns,
	        stripeRows       : true,
	        title            : '未关联模块',
	        margins          : '0 2 0 0',
	        dockedItems: [{
	        	xtype: 'pagingtoolbar',
	            dock: 'bottom',
	            store: unrelatedStore,
	            displayInfo: true,
	            displayMsg: '显示记录 {0} - {1}，共 {2}条',
	            emptyMsg: "无数据"
	        }]
	    });	
		
			// 已关联菜单面板
		var relatedGrid = Ext.create('Ext.grid.Panel', {
			features: Ext.create('Ext.grid.feature.Grouping',{
				groupByText: '{name}',
				hideGroupedHeader: false,
				groupHeaderTpl: '{name} ({rows.length} 个菜单)'
			}),
	        selModel: Ext.create('Ext.selection.CheckboxModel'),
	        viewConfig: {
	            plugins: {
	                ptype: 'gridviewdragdrop',
	                dragGroup: 'secondGridDDGroup',
	                dropGroup: 'firstGridDDGroup'
	            }
	        },
	        store            : relatedStore,
	        columns          : columns,
	        stripeRows       : true,
	        title            : '已关联模块',
	        margins          : '0 0 0 3'
	    });
		
			// 主面板
		var displayPanel = Ext.create('Ext.Panel', {
	        width        : 800,
	        height       : 400,
	        layout       : {
	            type: 'hbox',
	            align: 'stretch',
	            padding: 5
	        },
	        defaults     : { flex : 1 }, //auto stretch
	        items        : [
	           unrelatedGrid,
	           relatedGrid
	        ],
	        dockedItems: {
	            xtype: 'toolbar',
	            dock: 'bottom',
	            items: ['->', // Fill
	            {
	                text: '同步关联数据至服务器',
	                iconCls: 'icon-user',
	                handler: function(){
	                	var moduleIds = new Array();
	                	for(var i=0; i<relatedStore.count(); i++) {
	                		moduleIds[i] = relatedStore.getAt(i).get('id');
	                	}
	                	// 发送请求，同步关联关系
	                	Ext.eray.request({
	            			url: '${path}/sm/security/authorityresource/relate-res.do',
	            			params: {
	            				'type': 1,
	            				'authorityId': authId,
	            				'resourceIds': moduleIds
	            			}
	            		});
	                	relateModuleWin.hide(btn);
	                }
	            }]
	        }
	    });
	    var relateModuleWinId = 'sm_security_auth_index_relatemodule_win';
		var relateModuleWin = Ext.getCmp(relateModuleWinId);
    	if(!relateModuleWin) {
    		relateModuleWin = createWindow(1000, 500, '关联模块', relateModuleWinId);
    	};
    	relateModuleWin.removeAll();
	    relateModuleWin.add(displayPanel);
    	relateModuleWin.show(btn);
	};
	
	/* ********************************************
		处理关联页面元素的函数
		******************************************* */
	var hndRelateElement = function(btn, authId){
		var moduleO = 'ModelElement';
		var extraParamsO = { type: 2,authorityId: authId };
		 var unrelatedStore = createStore(moduleO, 100, null, 
				'${path}/sm/security/authorityresource/unrelated-res.do', extraParamsO);
		
		var relatedStore = createStore(moduleO, null, null, 
				'${path}/sm/security/authorityresource/related-res.do', extraParamsO);
	
		unrelatedStore.load();
		relatedStore.load(); 
		
		var columns = [
			       		{text: "ID", width: 50, sortable: true, dataIndex: 'id'},
			       		{text: "页面元素名称", width: 80, sortable: true, dataIndex: 'name'},
			       		{text: "地址", flex: 1, sortable: true, dataIndex: 'location'},
			       		{text: "备注", width: 150, sortable: true, dataIndex: 'comment'}
			       	];
					// 未关联菜单面板
		var unrelatedGrid = Ext.create('Ext.grid.Panel', {
	    // multiSelect: true,
			selModel: Ext.create('Ext.selection.CheckboxModel'),
	        viewConfig: {
	            plugins: {
	                ptype: 'gridviewdragdrop',
	                dragGroup: 'firstGridDDGroup',
	                dropGroup: 'secondGridDDGroup'
	            }
	        },
	        store            : unrelatedStore,
	        columns          : columns,
	        stripeRows       : true,
	        title            : '未关联页面元素',
	        margins          : '0 2 0 0',
	        dockedItems: [{
	        	xtype: 'pagingtoolbar',
	            dock: 'bottom',
	            store: unrelatedStore,
	            displayInfo: true,
	            displayMsg: '显示记录 {0} - {1}，共 {2}条',
	            emptyMsg: "无数据"
	        }]
	    });	
		
			// 已关联菜单面板
		var relatedGrid = Ext.create('Ext.grid.Panel', {
	        selModel: Ext.create('Ext.selection.CheckboxModel'),
	        viewConfig: {
	            plugins: {
	                ptype: 'gridviewdragdrop',
	                dragGroup: 'secondGridDDGroup',
	                dropGroup: 'firstGridDDGroup'
	            }
	        },
	        store            : relatedStore,
	        columns          : columns,
	        stripeRows       : true,
	        title            : '已关联页面元素',
	        margins          : '0 0 0 3'
	    });
		
			// 主面板
		var displayPanel = Ext.create('Ext.Panel', {
	        width        : 800,
	        height       : 400,
	        layout       : {
	            type: 'hbox',
	            align: 'stretch',
	            padding: 5
	        },
	        defaults     : { flex : 1 }, //auto stretch
	        items        : [
	           unrelatedGrid,
	           relatedGrid
	        ],
	        dockedItems: {
	            xtype: 'toolbar',
	            dock: 'bottom',
	            items: ['->', // Fill
	            {
	                text: '同步关联数据至服务器',
	                iconCls: 'icon-user',
	                handler: function(){
	                	var elemIds = new Array();
	                	for(var i=0; i<relatedStore.count(); i++) {
	                		elemIds[i] = relatedStore.getAt(i).get('id');
	                	}
	                	// 发送请求，同步关联关系
	                	Ext.eray.request({
	            			url: '${path}/sm/security/authorityresource/relate-res.do',
	            			params: {
	            				'type': 2,
	            				'authorityId': authId,
	            				'resourceIds': elemIds
	            			}
	            		});
	                	relateElemWin.hide(btn);
	                }
	            }]
	        }
	    });
	    var relateElemWinId = 'sm_security_authority_index_relateelem_win';
		var relateElemWin = Ext.getCmp(relateElemWinId);
    	if(!relateElemWin) {
    		relateElemWin = createWindow(1000, 500, '关联页面元素', relateElemWinId);
    	};
    	relateElemWin.removeAll();
    	relateElemWin.add(displayPanel);
    	relateElemWin.show(btn);
	};
	
	/* ********************************************
	处理关联省份的函数
	******************************************* */
	var hndRelateProvince = function(btn, authId){
		var moduleO = 'ModelProvince';
		var extraParamsO = { type: 3,authorityId: authId };
		 var unrelatedStore = createStore(moduleO, 100, null, 
				'${path}/sm/security/authorityresource/unrelated-res.do', extraParamsO);
		
		var relatedStore = createStore(moduleO, null, null, 
				'${path}/sm/security/authorityresource/related-res.do', extraParamsO);
	
		unrelatedStore.load();
		relatedStore.load(); 
		
		var columns = [
			       		{text: "ID", width: 50, sortable: true, dataIndex: 'id'},
			       		{text: "省份", width: 80, sortable: true, dataIndex: 'name'},
			       		{text: "拼写", flex: 1, sortable: true, dataIndex: 'spelling'}
			       	];
					// 未关联菜单面板
		var unrelatedGrid = Ext.create('Ext.grid.Panel', {
	    // multiSelect: true,
			selModel: Ext.create('Ext.selection.CheckboxModel'),
	        viewConfig: {
	            plugins: {
	                ptype: 'gridviewdragdrop',
	                dragGroup: 'firstGridDDGroup',
	                dropGroup: 'secondGridDDGroup'
	            }
	        },
	        store            : unrelatedStore,
	        columns          : columns,
	        stripeRows       : true,
	        title            : '未关联省份',
	        margins          : '0 2 0 0',
	        dockedItems: [{
	        	xtype: 'pagingtoolbar',
	            dock: 'bottom',
	            store: unrelatedStore,
	            displayInfo: true,
	            displayMsg: '显示记录 {0} - {1}，共 {2}条',
	            emptyMsg: "无数据"
	        }]
	    });	
		
			// 已关联菜单面板
		var relatedGrid = Ext.create('Ext.grid.Panel', {
	        selModel: Ext.create('Ext.selection.CheckboxModel'),
	        viewConfig: {
	            plugins: {
	                ptype: 'gridviewdragdrop',
	                dragGroup: 'secondGridDDGroup',
	                dropGroup: 'firstGridDDGroup'
	            }
	        },
	        store            : relatedStore,
	        columns          : columns,
	        stripeRows       : true,
	        title            : '已关联省份',
	        margins          : '0 0 0 3'
	    });
		
			// 主面板
		var displayPanel = Ext.create('Ext.Panel', {
	        width        : 800,
	        height       : 400,
	        layout       : {
	            type: 'hbox',
	            align: 'stretch',
	            padding: 5
	        },
	        defaults     : { flex : 1 }, //auto stretch
	        items        : [
	           unrelatedGrid,
	           relatedGrid
	        ],
	        dockedItems: {
	            xtype: 'toolbar',
	            dock: 'bottom',
	            items: ['->', // Fill
	            {
	                text: '同步关联数据至服务器',
	                iconCls: 'icon-user',
	                handler: function(){
	                	var provinceIds = new Array();
	                	for(var i=0; i<relatedStore.count(); i++) {
	                		provinceIds[i] = relatedStore.getAt(i).get('id');
	                	}
	                	// 发送请求，同步关联关系
	                	Ext.eray.request({
	            			url: '${path}/sm/security/authorityresource/relate-res.do',
	            			params: {
	            				'type': 3,
	            				'authorityId': authId,
	            				'resourceIds': provinceIds
	            			}
	            		});
	                	relateProvinceWin.hide(btn);
	                }
	            }]
	        }
	    });
	    var relateProvinceWinId = 'sm_security_authority_index_relatepid_win';
		var relateProvinceWin = Ext.getCmp(relateProvinceWinId);
    	if(!relateProvinceWin) {
    		relateProvinceWin = createWindow(1000, 500, '关联省份', relateProvinceWinId);
    	};
    	relateProvinceWin.removeAll();
    	relateProvinceWin.add(displayPanel);
    	relateProvinceWin.show(btn);
	};
    
    var authTab = Ext.getCmp('sm_security_authority_index_tab');
    authTab.removeAll();
    authTab.add(gridAuthCtrl); 
    
});
</script>