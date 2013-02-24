<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript">
Ext.require([
             'Ext.data.*',
             'Ext.grid.*',
             'Ext.tree.*'
         ]);
Ext.QuickTips.init();

Ext.override(Ext.data.TreeStore, {
    load : function(options) {
        options = options || {};
        options.params = options.params || {};

        var me = this, node = options.node || me.tree.getRootNode(), root;

        // If there is not a node it means the user hasnt defined a rootnode
        // yet. In this case lets just
        // create one for them.
        if (!node) {
            node = me.setRootNode( {
                expanded : true
            });
        }

        if (me.clearOnLoad) {
            node.removeAll(false);
        }

        Ext.applyIf(options, {
            node : node
        });
        options.params[me.nodeParam] = node ? node.getId() : 'root';

        if (node) {
            node.set('loading', true);
        }
        return me.callParent( [ options ]);
    }
}); 

Ext.onReady(function(){
	 var moduleStore = Ext.create('Ext.data.TreeStore', {
	        autoLoad: false,
	        autoSync: false,
	        model: 'ModelModule',
	        proxy: {
	            type: 'rest',
	            url: '${path}/sm/security/module/get-children.do',
				actionMethods: 'post',
	            reader: {
	                type: 'json',
	                root: 'records'
	            },
	            writer: {
	                type: 'json'
	            }
	        },
	        listeners:{
	        	beforeappend: function(thisNode, node){
	        		node.data.icon = null; //避免icon使用数据库中记录的值
	        	}
	        }
	});
	var expandPath='';
	var selPath = '';
	var add = false;
	/***********************
	编辑module的信息，可更新
	************************/
	var moduleEditForm = Ext.create('Ext.form.Panel', {
		frame: true,
		//labelWidth: 55,
	    width: 500,
	    fieldDefaults:{
    		msgTarget: 'side',
    		labelWidth: 75 
    	},
	    defaultType: 'textfield',
	    defaults:{
    		anchor:'100%'
    	},
	    items:[{
            xtype: 'numberfield',
	    	fieldLabel: 'ID',
            name: 'id',
            allowBlank: false,
            minValue: 1
	    },{
	    	fieldLabel: '模块名',
	    	name: 'name',
	    	allowBlank: false
	    },{
	    	fieldLabel: '地址',
	    	name: 'url'
	    },{
	    	fieldLabel: '图标',
	    	name: 'icon'
	    },{
	    	xtype: 'numberfield',
	    	fieldLabel: '显示级',
	    	name: 'orderNo',
	    	allowBlank: false,
	    	minValue: 1
        	//step: 1
	    },{ 
	    	xtype: 'radiogroup',
	    	itemId: 'visibleRadiogroup',
	    	fieldLabel:'可见', 
	    	column:2, 
	    	items:[{boxLabel:'是', name:'visible', inputValue: true, checked: true},
	    	       {boxLabel:'否', name:'visible', inputValue: false}
	    	]
	    },{
        	xtype:'hidden',
        	fieldLabel: 'parentId',
        	name:'parentId'
        },{
        	xtype:'hidden',
        	fieldLabel: 'level',
        	name:'level'
        },{
        	xtype:'hidden',
        	fieldLabel: 'leaf',
        	name:'leaf'
        }],
        buttonAlign:'center',
        buttons: [{
       	 text: '提交',
            handler:function(){
            	var form=this.up('form').getForm();
    			if(form.isValid()){
    				if(add){
    					urlStr = '${path}/sm/security/module/add.do';
    					var selection = treePanel.getSelectionModel().getSelection()[0];
    					if(selection){
    						form.findField("parentId").setValue(selection.get("id"));
    					}else{
    						form.findField("parentId").setValue(0);
    					}
    				}else{
    					urlStr = '${path}/sm/security/module/update.do';
    				}
    				
    				form.submit({
    					url: urlStr,
    					method:'POST',
    					success:function(form, action){
    						form.reset();
    						moduleEditWin.hide();
    						loadTreeStore();
    						Ext.eray.parseMessage(true, action.result);
    					},
    					failure:function(form, action){
    						form.reset();
    						moduleEditWin.hide();
    						loadTreeStore();
    						Ext.eray.parseMessage(true, action.result);
    					}
    				});
    			}
            }
       },{
       	text: '取消',
        handler:function(){
        	this.up('form').getForm().reset();
        	moduleEditWin.hide();
        }
       }]
	});
	var moduleEditWin = Ext.create('Ext.Window', {
		title: '模块管理',
		autoScroll: true,
	    layout: 'fit',
	    plain: true,
        autoDestroy: false,
        closeAction: 'hide',
        bodyStyle: 'padding:5px;',
        buttonAlign: 'center',
        items: moduleEditForm
	});	
	
	/**********************
	delete event
	***********************/
	var deleteev = function(){
        var selection = this.up('treepanel').getView().getSelectionModel().getSelection()[0];
        if (selection) {
     		Ext.MessageBox.confirm('确认', '确定要删除该模块吗？', function(response){
     			if(response=='yes'){
     				var rmid = selection.data.id;
     				if(!rmid){ 				// remove from page
     					moduleStore.remove(selection);
     					loadTreeStore();
     				}else{				// remove from service
     					var parentId = selection.data.parentId;
     					if(parentId=='root') parentId = 0;
     					Ext.eray.request({
     			    		url: '${path}/sm/security/module/delete.do',
     			    		params:{
     			    			'id': rmid,
     			    			'parentId': parentId
     			    		},
     			    		successCallback: function(responseText, scope){
     			    			selPath = '';
     			    			loadTreeStore();
     			    		}
     			    	});
     				}
     			}
     		});
        }
    };
	
	/**********************
	add event
	***********************/
	var addev = function(){
		add = true;
		moduleEditWin.show();
    };
	
	/**********************
	update event
	***********************/
	var updateev = function(){
		add = false;
		var selection = this.up('treepanel').getView().getSelectionModel().getSelection()[0];
    	if(selection){
    		var id = selection.data.id;
    		if(id=='root') id= 0;
    		moduleEditWin.show();
    		moduleEditForm.getForm().load({
        		url:'${path}/sm/security/module/get/'+id+'.do',
        		success:function(form,action){
        			moduleEditForm.down('#visibleRadiogroup').setValue({'visible':action.result.data.visible});
        		}
        	});
    	}
    };
	
	/**********************
	grid tree
	***********************/
	var treePanel = Ext.create('Ext.tree.Panel', {
		layout: 'fit',
		frame: true,
        useArrows: true,
        rootVisible: false,
        store: moduleStore,
        multiSelect: true,
        singleExpand: true,
        columns:[{
        	xtype: 'treecolumn',
        	text: 'ID',
        	flex: 1,
        	sortable: true,
            dataIndex: 'id'
        },{
        	text: '模块名',
        	flex: 1,
        	sortable: true,
            dataIndex: 'name'
        },{
        	text: '地址',
        	flex: 1,
            dataIndex: 'url'
        } ,{
        	text: '图标',
        	flex: 2,
            dataIndex: 'nickIcon'
        } ,{
        	text: '显示级',
        	flex: 1,
            dataIndex: 'orderNo'
        }, {
        	text: '可见',
        	xtype: 'booleancolumn',
        	falseText: '否',
        	trueText: '是',
        	flex: 1,
        	dataIndex: 'visible'
        }],
        listeners: {
        	selectionchange: function(model, records) {
                if (records.length>0) {
                    selPath = moduleStore.getNodeById(records[0].data.id).getPath();
                }
                this.down('#update').setDisabled(records.length === 0);
                this.down('#delete').setDisabled(records.length === 0);
            } ,
            itemexpand: function(node) {  
                expandPath = node.getPath(); 
            },
            itemcollapse: function(node){
            	expandPath = node.getPath();
            	expandPath = expandPath.substring(0, expandPath.lastIndexOf('/'));
            }
        },
        dockedItems: [{
            xtype: 'toolbar',
            items: [{
                text: '添加',
                iconCls: 'icon-add',
                handler: addev
            },{
                itemId: 'delete',
                text: '删除',
                disabled: true,
                iconCls: 'icon-delete',
                handler: deleteev
            },{
            	itemId: 'update',
            	text: '修改',
            	disabled: true,
                iconCls: 'icon-update',
                handler: updateev
            }]
        }]
	});

	var loadTreeStore = function(){
		moduleStore.load({callback:function(records, operation, success){
			if(expandPath!='')
				treePanel.expandPath(expandPath, 'id', '/', function(){
					if(selPath!='')
					 	treePanel.selectPath(selPath);
				});
		    
		}});
	};
	
	var moduleTab = Ext.getCmp('sm_security_module_index_tab');
	moduleTab.removeAll();
	moduleTab.add(treePanel);
});
</script>