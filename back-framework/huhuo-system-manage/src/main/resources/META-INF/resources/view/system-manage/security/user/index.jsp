<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
Ext.QuickTips.init();
 
Ext.apply(Ext.form.VTypes, {
    checkpwd : function(val, field) {
        if (field.initialPassField) {
            var pwd = Ext.getCmp(field.initialPassField);
            return (val == pwd.getValue());
        }
        return true;
    },
    checkpwdText : '两次输入的密码不相符'
});

Ext.onReady(function(){
    var userStore = Ext.create('Ext.data.Store', {
        autoLoad: true,
        autoSync: false,
        model: 'ModelUser',
        pageSize: 23,
        proxy: {
            type: 'rest',
            url: '${path}/sm/security/user/get.do',
            pageParam: 'pageNo',
            startParam: 'page.start',
            limitParam: 'page.limit',
            actionMethods: 'POST',
            reader: {
                type: 'json',
                root: 'records'
            }
        }
    });
   /*  var userProvinceCombo =Ext.create('Ext.form.field.EProvinceComboBox', {
		includeAll : true,
		selectMode : true
	});
    userProvinceCombo.load(); */
    var userProvinceStore = Ext.create('Ext.data.EProvinceStore');
    userProvinceStore.load();
    
    var departmentStore = Ext.create('Ext.data.JsonStore',{
    	storeId: 'departmentId',
    	autoLoad: false,
    	proxy:{
    		type:'ajax',
    		url:'${path}/sm/department/get.do', //todopage
    		extraParams: {
    			level: 2	// 获取第三级的所有部门
    		},
    		reader:{
    			type: 'json',
    			root: 'records',
    			idProperty: 'id'
    		}
    	},
		fields: [{
			name: 'value',
			mapping: 'id'
		}, {
			name: 'display',
			mapping: 'name'
		}, 'desc', 'parentId', 'level', 'leaf', 'createTime']
	});
  	
    var addupdateform = new Ext.form.Panel({
    	frame: true,
    	bodyStyle: 'padding:5px;',
    	defaultType: 'textfield',
    	fieldDefaults:{
    		msgTarget: 'side',
    		labelWidth: 75 
    	},
    	defaults:{
    		anchor:'100%'
    	},
    	items:[{fieldLabel:'用户名', name:'username', allowBlank: false},
    	       {fieldLabel:'用户昵称', name:'displayName'},
    	       {xtype:'radiogroup',itemId: 'activeStatusRadiogroup', fieldLabel:'状态', column:2, items:[{boxLabel:'可用', name:'activeStatus', inputValue: true, checked: true},
    	                                                              {boxLabel:'禁用', name:'activeStatus', inputValue: false}]},
    	       {fieldLabel:'联系电话', name:'contactPhone'},
    	       {fieldLabel:'手机', name:'cellphone'},
    	       {fieldLabel:'邮箱', name:'email', vtype:'email'},
    	       {xtype:'combobox', fieldLabel:'部门', name:'departmentId', editable: false, displayField:'display', valueField:'value'},//todopage
    	       {xtype:'eprovincecombo', name:'provinceId', editable: false, loadStore: true, labelWidth: 75},
    	       {fieldLabel:'密码', name:'password',inputType:'password', allowBlank: false, id:'sm_security_user_pwd'},
    	       {fieldLabel:'确认密码', name:'confirmPassword', inputType:'password', allowBlank: false, initialPassField:'sm_security_user_pwd',vtype:'checkpwd'},
    	       {xtype:'hidden', name:'id'}
    	],
    	buttonAlign:'center',
    	buttons:[{
	    		text:'提交', formBind: true, 
	    		handler: function(){
	    			var form=this.up('form').getForm();
	    			if(form.isValid()){
	    				form.submit({
	    					url:'${path}/sm/security/user/save.do',
	    					method:'POST',
	    					success:function(form, action){
	    						form.reset();
	    						addupdatewin.hide();
	    						userStore.load();
	    						Ext.eray.parseMessage(true, action.result);
	    					},
	    					failure:function(form, action){
	    						form.reset();
	    						addupdatewin.hide();
	    						userStore.load();
	    						Ext.eray.parseMessage(true, action.result);
	    					}
	    				});
	    			}
	    		}
    		},
    		{
    			text:'取消',
    			handler: function(){
    				this.up('form').getForm().reset();
    				addupdatewin.hide();
    			}	
    		}
    	]
    });
    var addupdatewin = new Ext.Window({
    	title: '用户管理',
    	width: 500,
    	layout: 'fit',
    	closeAction: 'hide',
    	//bodyStyle:'padding:5px;',
    	items: addupdateform
    });
    
    
    /**
    * delete user event
    */
    var deleteev = function(){
        var selection = this.up('gridpanel').getView().getSelectionModel().getSelection()[0];
        if (selection) {
     		Ext.MessageBox.confirm('确认', '确定要删除该用户吗？', function(response){
     			if(response=='yes'){
     				var rmid = selection.data.id;
     				if(!rmid){ 				// remove from page
     					userStore.remove(selection);
     				}else{					// remove from service
     					Ext.eray.request({
     			    		url: '${path}/sm/security/user/delete.do',
     			    		params:{
     			    			'id': rmid
     			    		},
     			    		successCallback: function(responseText, scope){
     			    			userStore.removeAll();
     			    			userStore.load();
     			    		}
     			    	});
     				}
     			}
     		});
        }
    };
    
    var addev = function(){
    	addupdatewin.show();
    };
    
    var updateev = function(id){
    	//handle id todopage
    	addupdatewin.show();
    	addupdateform.getForm().load({
    		url:'${path}/sm/security/user/get/'+id+'.do',
    		success:function(form,action){
    			addupdateform.down('#activeStatusRadiogroup').setValue({'activeStatus': action.result.data.activeStatus});
    		}
    	});
    };
    
    var grid = Ext.create('Ext.grid.Panel', {
        layout: 'fit',
        frame: true,
        store: userStore,
        iconCls: 'icon-user',
        columns: [
        {
            text: 'ID',
            width:50,
            sortable: true,
            dataIndex: 'id'
        }, {
            text: '用户名',
            flex:1,
            sortable: true,
            dataIndex: 'username'
        }, {
            header: '用户昵称',
            flex:1,
            sortable: true,
            dataIndex: 'displayName'
        }, {
            xtype:'booleancolumn',
            text: '是否可用',
            flex:1,
            sortable: true,
            hideable: true,
            trueText:'可用',
            falseText:'停用',
            dataIndex: 'activeStatus'
        }, {
            header: '联系电话',
            flex:1,
            sortable: true,
            dataIndex: 'contactPhone'
        }, {
            header: '手机',
            flex:1,
            sortable: true,
            dataIndex: 'cellphone'
        },{
            header: '邮箱',
            flex:1,
            sortable: true,
            dataIndex: 'email'
        },{
        	header: '部门',
        	flex:1,
        	sortable: true,
        	dataIndex: 'departmentId'
        },{
        	header: '省份',
        	flex:1,
        	sortable: true,
        	dataIndex: 'provinceId',
        	renderer: function(value){
        		var m = userProvinceStore.findRecord('value', value);
        		if(m){
        			return m.data.display;
        		}else{
        			return value;
        		}
        	}
        },{
        	xtype:'datecolumn',
            header: '创建时间',
            flex:1,
            format:'Y-m-d H:i:s',
            sortable: true,
            hideable: true,
            dataIndex: 'createTime'
        }],
        listeners:{
        	itemdblclick: function(grid,model, e){
        		updateev(model.data.id);
            }
        	
        },
        dockedItems: [{
            xtype: 'toolbar',
            items: [{
                text: '添加',
                iconCls: 'icon-add',
                handler: addev
            }, '-', {
                itemId: 'delete',
                text: '删除',
                iconCls: 'icon-delete',
                disabled: true,
                handler: deleteev
            },{
            	itemId: 'update',
            	text: '修改',
                iconCls: 'icon-update',
                disabled: true,
                handler: function(){
                	var selection = this.up('gridpanel').getView().getSelectionModel().getSelection()[0];
                	if(selection){
	                	updateev(selection.data.id);
                	}
                }
            }, '-', {
            	itemId: 'relate',
                text: '关联角色',
                iconCls: 'icon-user',
                disabled: true,
                handler: function(btn, event){
                	// 选择项
                    var selection = this.up('gridpanel').getView().getSelectionModel().getSelection()[0];
                	// 未关联的角色
					var unrelatedStore = Ext.create('Ext.data.Store', {
				        model: 'ModelRole',
				        pageSize: 25,	// 加载所有记录
				        proxy: {
				            type: 'rest',
				            url: '${path}/sm/security/userrole/unrelated-roles.do',
				            extraParams: {
				            	userId: selection.get('id')
				            },
				            reader: {
				                type: 'json',
				                root: 'records'
				            }
				        }
				    });
					unrelatedStore.load();
					var relatedStore = Ext.create('Ext.data.Store', {
				        model: 'ModelRole',
				        pageSize: null,		// 加载所有记录
				        proxy: {
				            type: 'rest',
				            url: '${path}/sm/security/userrole/related-roles.do',
				            extraParams: {
				            	userId: selection.get('id')
				            },
				            reader: {
				                type: 'json',
				                root: 'records'
				            }
				        }
				    });
					relatedStore.load();
					var columns = [
			       		{text: "ID", width: 30, sortable: true, dataIndex: 'id'},
			       		{text: "角色名称", width: 100, sortable: true, dataIndex: 'name'},
			       		{text: "角色描述", flex: 1, sortable: true, dataIndex: 'comment'}
			       	];
					// 未关联角色面板
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
				        title            : '未关联角色',
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
					// 已关联角色面板
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
				        title            : '已关联角色',
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
				                	var roleIds = new Array();
				                	for(var i=0; i<relatedStore.count(); i++) {
				                		roleIds[i] = relatedStore.getAt(i).get('id');
				                	}
				                	// 发送请求，同步关联关系
				                	Ext.eray.request({
				            			url: '${path}/sm/security/userrole/relate-roles.do',
				            			params: {
				            				'userId': selection.get('id'),
				            				'roleIds': roleIds
				            			}
				            		});
				                	relateRoleWin.hide(btn);
				                }
				            }]
				        }
				    });
					// 添加窗口
                	var relateRoleWin = Ext.getCmp('sm_security_user_relaterole_win');
	            	if(!relateRoleWin) {
	            		relateRoleWin = Ext.create('widget.window', {
		            		id: 'sm_security_user_relaterole_win',
		            		title:'关联角色',
		            	    autoScroll: false,
		            	    frame: false,
		            	    layout: 'fit',
		            	    width: 800,
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
	            	relateRoleWin.removeAll();
	            	relateRoleWin.add(displayPanel);
	            	relateRoleWin.show(btn);
                }
            }, '->', {
            	xtype: 'textfield',
	        	itemId: 'userNameInput',
	        	fieldLabel: '用户名',
	        	labelAlign: 'right',
	        	labelWidth: 45,
	        	width: 200
            }, {
            	xtype: 'textfield',
            	itemId: 'displayNameInput',
	        	fieldLabel: '显示名称',
	        	labelAlign: 'right',
	        	labelWidth: 60,
	        	width: 200
            }, //todopage 部门下拉列表
            {
	            text: '查询',
	            iconCls: 'icon-search',
	            disabled: false,
	            handler: function(){
	            	var grid = this.up('grid');
	            	var userName = grid.down('#userNameInput').getValue();
	            	var displayName = grid.down('#displayNameInput').getValue();
	            	userStore.getProxy().extraParams = {
	            		'username': userName,
	            		'displayName': displayName
	            	};
	            	userStore.load();
	            }
	        }]
        }],
        bbar: new Ext.PagingToolbar({
            store: userStore,
            displayInfo: true,
            displayMsg: '当前 {0} - {1} 共有 {2}页',
            emptyMsg: "当前无用户记录"
        })
    });
    // 设置事件
    grid.on('selectionchange', function(selModel, selections) {
    	grid.down('#delete').setDisabled(selections.length === 0);
    	grid.down('#update').setDisabled(selections.length === 0);
    	grid.down('#relate').setDisabled(selections.length === 0);
    });

    userStore.on('beforeload', function(store, operation, eOpts) {
    	store.removeAll();
    });

    
    var userTab = Ext.getCmp('sm_security_user_index_tab'); 
    userTab.removeAll(true);
    userTab.add(grid);
    
    
});

</script>