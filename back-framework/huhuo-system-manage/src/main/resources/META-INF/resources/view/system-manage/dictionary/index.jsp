<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">



Ext.onReady(function(){
	// 数据源
    var store = Ext.create('Ext.data.Store', {
        autoLoad: true,
        autoSync: false,
        model: 'ModelDictionary',
        pageSize: 25,
        proxy: {
        	page: 'pageNo',
            start: 'page.start',
            limit: 'page.limit', 
            type: 'rest',
            url: '${path}/sm/dictionary/get.do',
            reader: {
                type: 'json',
                root: 'records',
                totalProperty: 'total'
            },
            writer: {
                type: 'json'
            },
            actionMethods: {read: 'post' }
           // extraParams: {'aaa': 'ddddd'}
        }
    });
	
    // 行编辑插件
    var rowEditing = Ext.create('Ext.grid.plugin.ERowEditing', {
    	clicksToEdit: 2,
    	listeners: {
    		edit: function(editor, e, eOpts) {
    			// 发送请求，操作数据库
    			
    			var dict = editor.record.data;
    			if(dict.isNew){
					ajaxRequest('dictionary/add.do', dict, store);
    			} else{
    				ajaxRequest('dictionary/update.do', dict, store);
    			}
    		},
    		canceledit: function(grid, eOpts) {
    			if(!grid.record.data.id) {	// 如果grid.record.data.id==null
					store.remove(grid.record);
    			}
    		}
    	}
    });
    // 表格
    var grid = Ext.create('Ext.grid.Panel', {
        plugins: [rowEditing],
        layout: 'fit',
        frame: false,
        store: store,
        iconCls: 'icon-user',
        columns: [{
            text: 'ID',
            width: 100,
            sortable: true,
            dataIndex: 'id',
        }, {
            text: '组名',
            width: 150,
            sortable: true,
            dataIndex: 'groupName',
            editor: {
				xtype: 'textfield',
				allowBlank: false,
				blankText: '组名不能为空',
            	minLength: 1,
            	minLengthText: '组名最少1个字符',
            	maxLength: 30,
            	maxLengthText:'组名最多30个字符'
            }
        }, {
            header: '组显示名',
            width: 150,
            sortable: true,
            dataIndex: 'groupDisplayName',
            editor: {
                xtype: 'textfield',
                allowBlank: true,
            	maxLength: 50,
            	maxLengthText:'组显示名最多50个字符'
            }
        }, {
            text: '键',
            width: 120,
            sortable: true,
            dataIndex: 'dictKey',
            editor:{
            	xtype:'textfield',
            	allowBlank:false,
            	minLength: 1,
            	minLengthText: '键最少1个字符',
            	maxLength: 50,
            	maxLengthText:'键最多50个字符'
            }
        }, {
            text: '值',
            width: 120,
            sortable: true,
            dataIndex: 'dictValue',
	        editor: {
	            xtype: 'textfield',
	            allowBlank:true,
            	minLength: 1,
            	minLengthText: '值最少1个字符',
            	maxLength: 100,
            	maxLengthText:'值最多100个字符'
	        }
        }, {
            header: '显示值',
            width: 190,
            sortable: true,
            dataIndex: 'dictDisplayName',
            editor: {
                xtype: 'textfield',
                allowBlank: false,
                minLength: 1,
            	minLengthText: '值最少1个字符',
            	maxLength: 50,
            	maxLengthText:'值显示最多50个字符'
            }
        }, {
            header: '排序',
            width: 160,
            sortable: true,
            dataIndex: 'orderNo',
            renderer: function(value,metaData,record,colIndex,store,view) {
                metaData.tdAttr = 'data-qtip="' + value + '"' ;
          		return value;     
            },
            editor: {
                xtype: 'textfield',
                allowBlank: true,
            	maxLength: 1000,
            	maxLengthText:'备注最多1000个字符'
            }
        }, {
            header: '备注',
            width: 160,
            sortable: true,
            dataIndex: 'comment',
            renderer: function(value,metaData,record,colIndex,store,view) {
                metaData.tdAttr = 'data-qtip="' + value + '"' ;
          		return value;     
            },
            editor: {
                xtype: 'textfield',
                allowBlank: true,
            	maxLength: 1000,
            	maxLengthText:'备注最多1000个字符'
            }
        }],
        dockedItems: [{
            xtype: 'toolbar',
            dock: 'top',
            items: [{
                text: '添加',
                iconCls: 'icon-add',
                handler: function(){
                    var dictionary = new ModelDictionary();
                    dictionary.data.isNew = true;
					store.insert(0, dictionary);
                    rowEditing.startEdit(0, 0);
                }
            }, '-', {
                itemId: 'delete',
                text: '删除',
                iconCls: 'icon-delete',
                disabled: true,
                handler: function(){
                    var selection = grid.getView().getSelectionModel().getSelection()[0];
                    Ext.MessageBox.confirm('确认', '确定要删除该字典吗？', function(response) {
                        if (response=='yes' && selection) {
                        	// 删除页面上的模型对象
                        	if(!selection.data.id) {	// selection.data.id==null（不存在）
                        		store.remove(selection);
                        	} else {	// 将记录从服务器中删除，并重载页面
                        		ajaxRequest('dictionary/delete.do', selection.data, store);
                        	}
                        }
                    });
                }
            }, {
            	itemId: 'test',
            	text: '修改',
                iconCls: 'icon-update',
                disabled: true,
                handler: function(){
                    var selection = grid.getView().getSelectionModel().getSelection()[0];
                    rowEditing.startEdit(selection, 1);
                }
            },{ xtype: 'tbspacer' 
            },'-',
           // 'ID：',{xtype    : 'textfield',itemId     : 'idFind',width : 80},
            '组名：',{
            	xtype    : 'textfield',
            	itemId   : 'groupNameFind',
            	width    : 150
            },'组显示名：',{
            	xtype    : 'textfield',
            	itemId   : 'gDisplayNameFind',
            	width    : 150
            },'键：',{
            	xtype    : 'textfield',
            	itemId   : 'dictKeyFind',
                width    : 120
            },'值',{
            	xtype    : 'textfield',
            	itemId   : 'dictValueFind',
            	width    : 120
            },'显示值',{
            	xtype    : 'textfield',
            	itemId   : 'dictDisplayNameFind',
            	width    : 120
            },'排序',{
            	xtype    : 'textfield',
            	itemId   : 'orderNoFind',
            	width    : 120
            },'备注',{
            	xtype    : 'textfield',
            	itemId   : 'commentFind',
            	width    : 120
            },{
                text: '查询',
                iconCls: 'icon-search',
                handler: function(){
                    var dictionary = new ModelDictionary();
                    dictionary.groupName =  Ext.String.trim(grid.down('#groupNameFind').getValue());
                    dictionary.groupDisplayName =  Ext.String.trim(grid.down('#gDisplayNameFind').getValue());
                    dictionary.dictKey =  Ext.String.trim(grid.down('#dictKeyFind').getValue());
                    dictionary.dictValue =  Ext.String.trim(grid.down('#dictValueFind').getValue());
                    dictionary.dictDisplayName =  Ext.String.trim(grid.down('#dictDisplayNameFind').getValue());
                    dictionary.orderNo =  Ext.Integer.trim(grid.down('#orderNoFind').getValue());
                    dictionary.comment =  Ext.String.trim(grid.down('#comment').getValue());
                    ajaxSearch(dictionary, store);
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
    var ajaxRequest = function(url, dict, store) {
    	Ext.eray.request({
			url: url,
			params: {
				'id':dict.id,
				'groupName': dict.groupName,
				'groupDisplayName': dict.groupDisplayName,
				'dictKey': dict.dictKey,
				'dictValue': dict.dictValue,
				'dictDisplayName': dict.dictDisplayName,
				'orderNo': dict.orderNo,
				'comment': dict.comment
			},
			successCallback: function(responseText, scope) {
				// 更新数据源
				store.removeAll();
				store.load();
			}
		});
	};
	
	var ajaxSearch = function(dict, store){
		store.getProxy().extraParams= {
			'groupName': dict.groupName,
			'groupDisplayName': dict.groupDisplayName,
			'dictKey': dict.dictKey,
			'dictValue': dict.dictValue,
			'dictDisplayName': dict.dictDisplayName,
			'orderNo': dict.orderNo,
			'comment': dict.comment
 		};
		
		// 更新数据源
		store.load();
	};
	
    // 设置选择变化事件
    grid.getSelectionModel().on('selectionchange', function(selModel, selections){
        grid.down('#delete').setDisabled(selections.length === 0);
    });
    grid.getSelectionModel().on('selectionchange', function(selModel, selections){
        grid.down('#test').setDisabled(selections.length === 0);
    });
    var moduleMange = Ext.getCmp('home_main_panel1000000');
    moduleMange.removeAll();
    moduleMange.add(grid);
    
});
</script>
