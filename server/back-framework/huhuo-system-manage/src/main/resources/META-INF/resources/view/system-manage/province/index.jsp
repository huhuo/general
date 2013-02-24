<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
	Ext.onReady(function(){
		// 数据源
		var sysProvinceStore = Ext.create('Ext.data.Store', {
	        autoLoad: true,
	        autoSync: false,
	        model: 'ModelProvince',
	        pageSize: 25,
	        proxy: {
	        	pageParam: 'pageNo',
	            startParam: 'page.start',
	            limitParam: 'page.limit',
	            type: 'rest',
	            url: '${path}/sm/province/get.do',
	            reader: {
	                type: 'json', root: 'records', totalProperty: 'total'
	            },
	            writer: {
	                type: 'json'
	            },
	            actionMethods: {read: 'post' }
	        }
	    });
		
		 // 行编辑插件
	    var sysProvinceRowEditing = Ext.create('Ext.grid.plugin.ERowEditing', {
	    	clicksToEdit: 2,
	    	listeners: {
	    		beforeedit: function(editor,e,eopts){
	    			var province = editor.record.data;
	    			if(province.isNew){
	    				sysProvinceGrid.down('#idEditor').field.setDisabled(false);
	    			} else{
	    				sysProvinceGrid.down('#idEditor').field.setDisabled(true);
	    			}
	    			
	    		},
	    		edit: function(editor, e, eOpts) {
	    			// 发送请求，操作数据库
	    			var province = editor.record.data;
	    			if(province.isNew){
						ajaxRequest('${path}/sm/province/add.do', province, sysProvinceStore);
	    			} else{
	    				ajaxRequest('${path}/sm/province/update.do', province, sysProvinceStore);
	    			}
	    		},
	    		canceledit: function(grid, eOpts) {
	    			if(!grid.record.data.id) {	// 如果grid.record.data.id==null
	    				sysProvinceStore.remove(grid.record);
	    			}
	    		}
	    	}
		});
		
	 // 表格
	    var sysProvinceGrid = Ext.create('Ext.grid.Panel', {
	        title:'省份管理',
	    	plugins: [sysProvinceRowEditing],
	        layout: 'fit',
	        frame: false,
	        store: sysProvinceStore,
	        iconCls: 'icon-user',
	        columns: [{
	            text: 'ID',
	            width: 120,
	            sortable: true,
	            dataIndex: 'id',
	            itemId:'idEditor',
	            editor:{
	            	xtype: 'numberfield',
	            	allowBlank: false,
	            	blankText: 'id不能为空',
	            	minValue: '1'
	            }
	        }, {
	            text: '省份名称',
	            width: 120,
	            sortable: true,
	            dataIndex: 'name',
	            editor: {
					xtype: 'textfield',
					allowBlank: false,
					blankText: '省份名称不能为空',
	            	minLength: 1,
	            	minLengthText: '省份名称最少1个字符',
	            	maxLength: 50,
	            	maxLengthText:'省份名最多50个字符'
	            }
	        }, {
	            header: '拼写',
	            width: 120,
	            sortable: true,
	            dataIndex: 'spelling',
	            editor: {
	                xtype: 'textfield',
	                allowBlank: false,
	                blank:'拼写不能为空',
	            	minLength: 1,
	            	minLengthText: '拼写最少1个字符',
	            	maxLength: 50,
	            	maxLengthText:'拼写最多50个字符'
	            }
	        }, {
	            text: '排序',
	            width: 80,
	            sortable: true,
	            dataIndex: 'orderNo',
	            editor:{
	            	xtype:'numberfield',
	            	allowBlank:false,
	            	blank:'排序不能为空',
	            	minValue:0
	            }
	        }, {
	            text: '地图区域ID',
	            width: 80,
	            sortable: true,
	            dataIndex: 'mapAreaId',
		        editor: {
		            xtype: 'textfield',
	            	maxLength: 20,
	            	maxLengthText: '地图区域Id最多20个字符'
		        }
	        }, {
	            text: '元数据',
	            width: 80,
	            sortable: true,
	            xtype: 'booleancolumn',
	            trueText: '有',
	            falseText: '无',
	            dataIndex: 'hasMetaData',
	            editor: {
	                xtype: 'combo',
	                allowBlank: false,
	                store: Ext.create('Ext.data.Store', {
	                	fields: ['display', 'value'],
	                	data: [
							{"display":"有", "value":true},
							{"display":"无", "value":false}
	                	]
	                }),
	                displayField: 'display',
	                valueField: 'value',
	                value: '{hasMetaData}'
	            }
	        }],
	        dockedItems: [{
	            xtype: 'toolbar',
	            dock: 'top',
	            items: [{
	                text: '添加',
	                iconCls: 'icon-add',
	                handler: function(){
	                    var province = new ModelProvince();
	                    province.data.orderNo = 0;
	                    province.data.isNew = true;
	                    sysProvinceStore.insert(0, province);
						sysProvinceRowEditing.startEdit(0, 0);
	                }
	            }, '-', {
	                itemId: 'delete',
	                text: '删除',
	                iconCls: 'icon-delete',
	                disabled: true,
	                handler: function(){
	                    var selection = sysProvinceGrid.getView().getSelectionModel().getSelection()[0];
	                    Ext.MessageBox.confirm('确认', '确定要删除该省份吗？', function(response) {
	                        if (response=='yes' && selection) {
	                        	// 删除页面上的模型对象
	                        	if(!selection.data.id) {	// selection.data.id==null（不存在）
	                        		sysProvinceStore.remove(selection);
	                        	} else {	// 将记录从服务器中删除，并重载页面
	                        		ajaxRequest('${path}/sm/province/delete.do', selection.data, sysProvinceStore);
	                        	}
	                        }
	                    });
	                }
	            }, {
	            	itemId: 'update',
	            	text: '修改',
	                iconCls: 'icon-update',
	                disabled: true,
	                handler: function(){
	                    var selection = sysProvinceGrid.getView().getSelectionModel().getSelection()[0];
	                    sysProvinceRowEditing.startEdit(selection, 1);
	                }
	            },{ xtype: 'tbspacer' 
	            },'->','ID：',{
	            	xtype    : 'textfield',
	            	itemId     : 'idFind',
	            	width : 80
	                //emptyText: 'enter search term'
	            },'省份名称：',{
	            	xtype    : 'textfield',
	            	itemId   : 'pNameFind',
	            	width    : 80
	            },'拼写：',{
	            	xtype    : 'textfield',
	            	itemId   : 'spellingFind',
	            	width    : 80
	            },'排序：',{
	            	xtype    : 'textfield',
	            	itemId   : 'orderNoFind',
	                width    : 50
	            },'地图区域ID:',{
	            	xtype    : 'textfield',
	            	itemId   : 'mapAreaIdFind',
	            	width    : 50
	            },'元数据:',{
	            	xtype    : 'combo',
	            	queryMode: 'local',
	            	itemId   : 'hasMetaDataFind',
	                store: Ext.create('Ext.data.Store', {
	                	fields: ['display', 'value'],
	                	data: [
	                	    {"display":"全部", "value":-1},
							{"display":"有", "value":true},
							{"display":"无", "value":false}
	                	]
	                }),
	                displayField: 'display',
	                valueField: 'value',
	                width    : 50
	            },{
	                text: '查询',
	                iconCls: 'icon-search',
	                handler: function(){
	                    var province = new ModelProvince();
	                    province.id =  Ext.String.trim(sysProvinceGrid.down('#idFind').getValue());
	                    province.name =  Ext.String.trim(sysProvinceGrid.down('#pNameFind').getValue());
	                    province.spelling =  Ext.String.trim(sysProvinceGrid.down('#spellingFind').getValue());
	                    province.orderNo =  Ext.String.trim(sysProvinceGrid.down('#orderNoFind').getValue());
	                    province.mapAreaId =  Ext.String.trim(sysProvinceGrid.down('#mapAreaIdFind').getValue());
	                    province.hasMetaData = sysProvinceGrid.down('#hasMetaDataFind').getValue();
	                    ajaxSearch(province, sysProvinceStore);
	                }
	            }]
	        }, {
	            xtype: 'pagingtoolbar',
	            dock: 'bottom',
	            store: sysProvinceStore,
	            displayInfo: true,
	            displayMsg: '显示记录 {0} - {1}，共 {2}条',
	            emptyMsg: "无数据"
	        }]
	    });
	 
	    
	    // 自定义方法（发送请求）
	    var ajaxRequest = function(url, province, store) {
	    	Ext.eray.request({
				url: url,
				params: {
					'id': province.id,
					'name': province.name,
					'spelling': province.spelling,
					'orderNo': province.orderNo,
					'mapAreaId': province.mapAreaId,
					'hasMetaData': province.hasMetaData
				},
				successCallback: function(responseText, scope) {
					// 更新数据源
					store.removeAll();
					store.load();
				}
			});
		};
		
		sysProvinceGrid.down('#hasMetaDataFind').setValue(-1);
		
		var ajaxSearch = function(province, store){
			if(province.hasMetaData==-1){
				province.hasMetaData=null;
			}
			store.getProxy().extraParams= {
				'id': province.id,
				'name': province.name,
				'spelling': province.spelling,
				'orderNo': province.orderNo,
				'mapAreaId': province.mapAreaId,
				'hasMetaData': province.hasMetaData
	 		};
			
			// 更新数据源
			store.load();
		};
		
	    // 设置选择变化事件
	    sysProvinceGrid.getSelectionModel().on('selectionchange', function(selModel, selections){
	    	sysProvinceGrid.down('#delete').setDisabled(selections.length === 0);
	    });
	    sysProvinceGrid.getSelectionModel().on('selectionchange', function(selModel, selections){
	    	sysProvinceGrid.down('#update').setDisabled(selections.length === 0);
	    });
	    var moduleMange = Ext.getCmp('home_main_panel1000000');
	    moduleMange.removeAll();
	    moduleMange.add(sysProvinceGrid); 
		 
	});
</script>