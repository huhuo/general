<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript">

Ext.onReady(function(){
	var elementStore = Ext.create('Ext.data.Store',{
		model: 'ModelElement',   
		autoSync: false,
		autoLoad: true,
		pageSize:23,
	    proxy: {
	            type: 'rest',
	            url: '${path}/sm/security/element/get.do',
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
    var elementRowEditing = Ext.create('Ext.grid.plugin.ERowEditing', {
    	clicksToEdit: 2,
    	listeners: {
    		edit: function(editor, e, eOpts) {
    			// 发送请求，操作数据库
    			var sysElement = editor.record.data;
				ajaxRequest('${path}/sm/security/element/save.do', sysElement, elementStore);
    		},
    		canceledit: function(grid, eOpts) {
    			if(!grid.record.data.id) {	// 如果grid.record.data.id==null
    				elementStore.remove(grid.record);
    			}
    		}
    	} 
    });
	 
    var elementGrid = Ext.create('Ext.grid.Panel', {
        plugins: [elementRowEditing],
        layout: 'fit',
        frame: true,
        store: elementStore,
        iconCls: 'icon-user',
       // columnLines: true,
        columns: [{
            text: 'ID',
            width: 100,
            sortable: true,
            dataIndex: 'id'
        }, {
            text: '页面元素名称',
            width: 250,
            sortable: true,
            dataIndex: 'name',
            editor: {
				xtype: 'textfield',
				allowBlank: false,
				blankText: '页面元素名称不能为空',
            	minLength: 1,
            	minLengthText: '页面元素名称最少1个字符',
            	maxLength: 255,
            	maxLengthText:'页面元素名称最多255个字符'
            }
        },{
            text: '地址',
            width: 300,
            sortable: true,
            dataIndex: 'location',
            editor: {
				xtype: 'textfield',
				allowBlank: false,
				blankText: '地址不能为空',
            	minLength: 1,
            	minLengthText: '地址最少1个字符',
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
                	var sysElement = new ModelElement();
                	var startIndex = 0;
                	if(selection) {
                		startIndex = selection.index + 1;
                    }  
                	elementStore.insert(startIndex, sysElement);
                    elementRowEditing.startEdit(startIndex, 0);
                }
            }, {
            	itemId: 'update',
            	text: '修改',
                iconCls: 'icon-update',
                disabled: true,
                handler: function(){
                    var selection = this.up('grid').getView().getSelectionModel().getSelection()[0];
                    elementRowEditing.startEdit(selection, 1);
                }
            }, {
                itemId: 'delete',
                text: '删除',
                iconCls: 'icon-delete',
                disabled: true,
                handler: function(){
                    var selection = this.up('grid').getView().getSelectionModel().getSelection()[0];
                    Ext.MessageBox.confirm('确认', '确定要删除该页面元素吗？', function(response) {
                        if (response=='yes' && selection) {
                        	// 删除页面上的模型对象
                        	if(!selection.data.id) {	// selection.data.id==null（不存在）
                        		elementStore.remove(selection);
                        	} else {	// 将记录从服务器中删除，并重载页面
                        		ajaxRequest('${path}/sm/security/element/delete.do', selection.data, elementStore);
                        	}
                        }
                    });
                }
            }, '->', {
            	xtype: 'textfield',
	        	itemId: 'elemIdInput',
	        	fieldLabel: '页面元素ID',
	        	labelAlign: 'right',
	        	labelWidth: 80,
	        	width: 200
            }, {
            	xtype: 'textfield',
            	itemId: 'elemNameInput',
	        	fieldLabel: '页面元素名称',
	        	labelAlign: 'right',
	        	labelWidth: 80,
	        	width: 200
            }, {
            	xtype: 'textfield',
            	itemId: 'locationInput',
	        	fieldLabel: '地址',
	        	labelAlign: 'right',
	        	labelWidth: 40,
	        	width: 200
            },{
            	xtype: 'textfield',
            	itemId: 'commentInput',
	        	fieldLabel: '备注',
	        	labelAlign: 'right',
	        	labelWidth: 40,
	        	width: 200
            }, {
	            text: '查询',
	            iconCls: 'icon-search',
	            disabled: false,
	            handler: function(){
	            	var grid = this.up('grid');
	            	var elemId = grid.down('#elemIdInput').getValue();
	            	var elemName = grid.down('#elemNameInput').getValue();
	            	var location = grid.down('#locationInput').getValue();
	            	var comment = grid.down('#commentInput').getValue();
	            	elementStore.getProxy().extraParams = {
	            		'id': elemId,
	            		'name': elemName,
	            		'location': location,
	            		'comment': comment
	            	};
	            	elementStore.load();
	            }
	        }]
        }, {
            xtype: 'pagingtoolbar',
            dock: 'bottom',
            store: elementStore,
            displayInfo: true,
            displayMsg: '显示记录 {0} - {1}，共 {2}条',
            emptyMsg: "无数据"
        }]
    });
	
    elementGrid.getSelectionModel().on('selectionchange', function(selModel, selections){
    	elementGrid.down('#delete').setDisabled(selections.length === 0);
    	elementGrid.down('#update').setDisabled(selections.length === 0);
    });
    
    var mainPanel = Ext.getCmp('sm_security_element_index_tab');
    mainPanel.removeAll();
    mainPanel.add(elementGrid);
    
    var ajaxRequest = function(url, sysElement, store){
    	Ext.eray.request({
			url: url,
			params: {
				'id': sysElement.id,
        		'name': sysElement.name,
        		'location': sysElement.location,
        		'comment': sysElement.comment
			},
			successCallback: function(responseText, scope) {
				// 更新数据源
				store.removeAll();
				store.load();
			}
		});
    	
    };
    
});
</script>