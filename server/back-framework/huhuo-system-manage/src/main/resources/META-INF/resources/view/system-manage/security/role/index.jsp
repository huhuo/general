<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">

Ext.onReady(function(){
	// 数据源
    var store = Ext.create('Ext.data.Store', {
        autoLoad: true,
        model: 'ModelRole',
        pageSize: 23,
        proxy: {
            type: 'ajax',
            url: '${path}/sm/security/role/get.do',
            pageParam: 'pageNo',
            startParam: 'page.start',
            limitParam: 'page.limit',
            actionMethods: 'POST',
            reader: {
                type: 'json',
                root: 'records',
                totalProperty: 'total'
            },
            writer: {
                type: 'json'
            }
        }
    });
    // 行编辑插件
    var rowEditing = Ext.create('Ext.grid.plugin.ERowEditing', {
    	clicksToEdit: 2,
    	listeners: {
    		edit: function(editor, e, eOpts) {
    			// 发送请求，操作数据库
    			var role = editor.record.data;
				ajaxRequest('${path}/sm/security/role/save.do', role, store);
    		},
    		canceledit: function(grid, eOpts) {
    			if(!grid.record.data.id) {	// 如果grid.record.data.id==null
					store.remove(grid.record);
    			}
    		}
    	}
    });
    var sm = Ext.create('Ext.selection.CheckboxModel');
    // 表格
    var grid = Ext.create('Ext.grid.Panel', {
//		selModel: sm,
        plugins: [rowEditing],
        layout: 'fit',
        frame: true,
        store: store,
        iconCls: 'icon-user',
        columnLines: false,
        columns: [{
            text: 'ID',
            width: 40,
            sortable: true,
            dataIndex: 'id'
        }, {
            text: '角色名',
            width: 150,
            sortable: true,
            dataIndex: 'name',
            editor: {
				xtype: 'textfield',
				allowBlank: false,
				blankText: '角色名不能为空',
            	minLength: 1,
            	minLengthText: '角色名最少1个字符',
            	maxLength: 100,
            	maxLengthText:'角色最多100个字符'
            }
        }, {
        	text: '描述',
        	flex: 1,
            sortable: true,
            hideable: true,
            dataIndex: 'comment',
            editor: {
				xtype: 'textfield',
				allowBlank: true,
            	maxLength: 255,
            	maxLengthText:'描述最多255个字符'
            }
        }],
        dockedItems: [{
            xtype: 'toolbar',
            dock: 'top',
            items: [{
                text: '添加',
                iconCls: 'icon-add',
                handler: function(){
                	var selection = this.up('grid').getView().getSelectionModel().getSelection()[0];
                    var insertIdx = 0;
                	if(selection) {
	                   insertIdx = selection.index + 1;
                    }
                   	var role = new ModelRole();
					store.insert(insertIdx, role);
                    rowEditing.startEdit(insertIdx, 0);
                    
                }
            }, {
            	itemId: 'update',
            	text: '修改',
                iconCls: 'icon-update',
                disabled: true,
                handler: function(){
                    var selection = this.up('grid').getView().getSelectionModel().getSelection()[0];
                    rowEditing.startEdit(selection, 1);
                }
            }, {
                itemId: 'delete',
                text: '删除',
                iconCls: 'icon-delete',
                disabled: true,
                handler: function(){
                    var selection = this.up('grid').getView().getSelectionModel().getSelection()[0];
                    Ext.MessageBox.confirm('确认', '确定要删除该角色吗？', function(response) {
                        if (response=='yes' && selection) {
                        	// 删除页面上的模型对象
                        	if(!selection.data.id) {	// selection.data.id==null（不存在）
                        		store.remove(selection);
                        	} else {	// 将记录从服务器中删除，并重载页面
                        		ajaxRequest('${path}/sm/security/role/delete.do', selection.data, store);
                        	}
                        }
                    });
                }
            }, '-', {
            	itemId: 'relate',
            	text: '关联权限',
                iconCls: 'icon-user',
                disabled: true,
                handler: function(btn, event){
                	// selection
                	var selection = this.up('grid').getView().getSelectionModel().getSelection()[0];
                	// 未关联的模块
					var unrelatedStore = Ext.create('Ext.data.Store', {
				        model: 'ModelAuthority',
				        pageSize: 30,
				        proxy: {
				            type: 'rest',
				            url: '${path}/sm/security/roleauthority/unrelated-authorities.do',
				            extraParams: {
				            	roleId: selection.get('id')
				            },
				            reader: {
				                type: 'json',
				                root: 'records'
				            }
				        }
				    });
					unrelatedStore.load();
					var relatedStore = Ext.create('Ext.data.Store', {
				        model: 'ModelAuthority',
				        pageSize: null,		// 加载所有记录
				       //groupField: 'parentName',
				        proxy: {
				            type: 'rest',
				            url: '${path}/sm/security/roleauthority/related-authorities.do',
				            extraParams: {
				            	roleId: selection.get('id')
				            },
				            reader: {
				                type: 'json',
				                root: 'records'
				            }
				        }
				    });
					relatedStore.load();
					var columns = [
			       		{text: "ID", width: 100, sortable: true, dataIndex: 'id'},
			       		{text: "权限名称", width: 255, sortable: true, dataIndex: 'name'}
			       	];
					// 未关联权限面板
					var unrelatedGrid = Ext.create('Ext.grid.Panel', {
//						multiSelect: true,
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
				        title            : '未关联权限',
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
				        title            : '已关联权限',
				        margins          : '0 0 0 3'
				    });
					// 主面板
					var displayPanel = Ext.create('Ext.Panel', {
				        width        : 750,
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
				                	var authIds = new Array();
				                	for(var i=0; i<relatedStore.count(); i++) {
				                		authIds[i] = relatedStore.getAt(i).get('id');
				                	}
				                	// 发送请求，同步关联关系
				                	Ext.eray.request({
				            			url: '${path}/sm/security/roleauthority/relate-authorities.do',
				            			params: {
				            				'roleId': selection.get('id'),
				            				'authorityIds': authIds
				            			}
				            		});
				                	relateModuleWin.hide(btn);
				                }
				            }]
				        }
				    });
                	// 添加窗口
                	var relateModuleWin = Ext.getCmp('sm_security_role_index_relateauth_win');
	            	if(!relateModuleWin) {
	            		relateModuleWin = Ext.create('widget.window', {
		            		id: 'sm_security_role_index_relateauth_win',
		            		title:'关联模块',
		            	    autoScroll: false,
		            	    frame: false,
		            	    layout: 'fit',
		            	    width: 750,
		            	    height: 400,
		            	    constrain: true,
							items: {
								border: false
							},
		            	    collapsible: true,
		            	    modal: true,
		            	    closeAction: 'hide'
		            	});
	            	}
	            	relateModuleWin.removeAll();
	            	relateModuleWin.add(displayPanel);
	            	relateModuleWin.show(btn);
                }
            }, '->', {
            	xtype: 'textfield',
	        	itemId: 'idInput',
	        	fieldLabel: '角色ID',
	        	labelAlign: 'right',
	        	labelWidth: 45,
	        	width: 200
            },{
            	xtype: 'textfield',
	        	itemId: 'nameInput',
	        	fieldLabel: '角色名',
	        	labelAlign: 'right',
	        	labelWidth: 45,
	        	width: 200
            }, {
            	xtype: 'textfield',
            	itemId: 'commentInput',
	        	fieldLabel: '描述',
	        	labelAlign: 'right',
	        	labelWidth: 45,
	        	width: 200
            }, {
	            text: '查询',
	            iconCls: 'icon-search',
	            disabled: false,
	            handler: function(){
	            	var grid = this.up('grid');
	            	var id = grid.down('#idInput').getValue();
	            	var name = grid.down('#nameInput').getValue();
	            	var comment = grid.down('#commentInput').getValue();
	            	store.getProxy().extraParams = {
	            		'id': id,
	            		'name': name,
	            		'comment': comment
	            	};
	            	store.load();
	            }
	        }]
        }, {
            xtype: 'pagingtoolbar',
            dock: 'bottom',
            store: store,
            displayInfo: true,
            displayMsg: '显示记录 {0} - {1}，共 {2}条',
            emptyMsg: "无数据"
        }]
    });
    // 自定义方法（发送请求）
    var ajaxRequest = function(url, role, store) {
		Ext.eray.request({
			url: url,
			params: {
				'id': role.id,
				'name': role.name,
				'comment': role.comment
			},
			successCallback: function(responseText, scope) {
				// 更新数据源
				store.removeAll();
				store.load();
			}
		});
	};
    // 设置选择变化事件
    grid.getSelectionModel().on('selectionchange', function(selModel, selections){
        grid.down('#delete').setDisabled(selections.length === 0);
        grid.down('#update').setDisabled(selections.length === 0);
        grid.down('#relate').setDisabled(selections.length === 0);
    });
    store.on('beforeload', function() {
    	this.removeAll();
    });
    var roleTab = Ext.getCmp('sm_security_role_index_tab');
    roleTab.removeAll();
    roleTab.add(grid);
});
</script>