/**********************************************************************
 * ModelProvince
 **********************************************************************/
Ext.define('ModelProvince', {
    extend: 'Ext.data.Model',
    fields: [
             {name: 'id', type: 'int', useNull: true}, 
             {name:'name',type:'string'}, 
             {name:'spelling', type:'string'}, 
    		 {name:'orderNo', type: 'int', useNull:true}, 
    		 {name:'mapAreaId', type:'string'},
    		 {name:'hasMetaData',type:'bool', useNull:true},
    		 {name:'isNew',type:'bool'}
    		]
});

/**********************************************************************
 * ModelUser
 **********************************************************************/
Ext.define('ModelUser', {
	extend: 'Ext.data.Model',
	fields:[
	        {name:'id', type: 'long', useNull: true},
	        {name:'username', type:'string'},
	        {name:'displayName', type:'string'},
	        {name:'activeStatus', type:'boolean'},
	        {name:'contactPhone', type:'string'},
	        {name:'cellphone', type:'string'},
	        {name:'email', type:'string'},
	        {name:'departmentId', type: 'long', useNull: true},
	        {name:'provinceId', type:'long', useNull: true},
	        {name:'remark', type:'string'},
	        {name:'createTime', type:'date', dateFormat:'Y-m-d H:i:s'}	
	]
});

/**********************************************************************
 * ModelRole
 **********************************************************************/
Ext.define('ModelRole', {
    extend: 'Ext.data.Model',
    fields: [
             {name: 'id', type: 'long', useNull: true }, 
             'name', 
             'comment' 
    ]
});

/**********************************************************************
 * ModelAuthority
 **********************************************************************/
Ext.define('ModelAuthority', {
	extend: 'Ext.data.Model',
	fields:[{name:'id', type:'long', useNull: true},
	        'name',
	        'comment'
	]
});

/**********************************************************************
 * ModelElement
 **********************************************************************/
Ext.define('ModelElement', {
	extend: 'Ext.data.Model',
	fields:[{name:'id', type:'long', useNull: true},
	        'name',
	        'location',
	        'comment'
	]
});

/**********************************************************************
 * ModelModule
 **********************************************************************/
Ext.define('ModelModule', {
	extend: 'Ext.data.Model',
	fields:[{name:'id', type:'long', useNull: true},
	        'name',
	        'url',
	        {name:'nickIcon',mapping:'icon'},
	        {name:'visible', type:'bool'},
	        'orderNo',
	        'parentId',
	        'level',
	        {name:'leaf', type:'bool'},
	        {name:'createTime', type:'date', dateFormat:'Y-m-d H:i:s'}
	]
});
/**********************************************************************
 * ModelSysDictionary
 **********************************************************************/
Ext.define('ModelDictionary', {
	extend: 'Ext.data.Model',
	fields:[{name:'id', type:'long', useNull: true},
	        {name:'groupName', type:'String'},
	        {name:'groupDisplayName', type:'String'},
	        {name:'dictKey', type:'String'},
	        {name:'dictValue', type:'String'},
	        {name:'dictDisplayName', type:'String'},
	        {name:'orderNo', type:'Integer'},
	        {name:'comment', type:'String'},
	        ]
});



