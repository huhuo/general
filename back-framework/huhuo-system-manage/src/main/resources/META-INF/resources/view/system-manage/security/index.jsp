<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
Ext.onReady( function(){
	var authTabs = Ext.create('Ext.tab.Panel',{ 
		title:'权限管理',		
		layout: 'anchor',
		activeTab:0,
		defaults :{
            bodyPadding: 0
        },
        items: [{
        	id: 'sm_security_user_index_tab',
        	title: '人员管理',
            layout: 'fit',
            closable: false,
            autoScroll: true,
            loader: {
                url: '${path}/sm/security/user/index.do',
                contentType: 'html',
                loadMask: true,
                scripts: true
            },
            listeners: {
                activate: function(tab) {
                    tab.loader.load();
                }
            }
        },{
        	id:'sm_security_role_index_tab',
        	title: '角色管理',
        	layout: 'fit',
            closable: false,
            autoScroll: true,
            loader: {
                url: '${path}/sm/security/role/index.do',
                contentType: 'html',
                loadMask: true,
                scripts: true
            },
            listeners: {
                activate: function(tab) {
                    tab.loader.load();
                }
            }
        },{
        	id:'sm_security_authority_index_tab',
        	title: '权限管理',
        	layout: 'fit',
            closable: false,
            autoScroll: true,
            loader: {
                url: '${path}/sm/security/authority/index.do',
                contentType: 'html',
                loadMask: true,
                scripts: true,
                params: {
                	aa: 312
                }
            },
            listeners: {
                activate: function(tab) {
                    tab.loader.load();
                }
            }
        },{
        	id:'sm_security_module_index_tab',
        	title: '模块管理',
        	layout: 'fit',
            closable: false,
            autoScroll: true,
            loader: {
                url: '${path}/sm/security/module/index.do',
                contentType: 'html',
                loadMask: true,
                scripts: true
            },
            listeners: {
                activate: function(tab) {
                    tab.loader.load();
                }
            }
        },{
        	id:'sm_security_element_index_tab',
        	title: '页面元素管理',
        	layout: 'fit',
            closable: false,
            autoScroll: true,
            loader: {
                url: '${path}/sm/security/element/index.do',
                contentType: 'html',
                loadMask: true,
                scripts: true
            },
            listeners: {
                activate: function(tab) {
                    tab.loader.load();
                }
            }
        }]
	});
	
	Ext.getCmp('home_main_panel1000000').add(authTabs);
});
</script>