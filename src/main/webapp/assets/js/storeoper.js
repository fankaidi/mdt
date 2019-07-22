$(function(){
	
	$('#grid').datagrid({
		url:'storeoper_listByPage.action',
		columns:[[
		  		    {field:'uuid',title:'编号',width:100},
		  		    {field:'empuuid',title:'员工',width:100,formatter:function(value,row,index){
		  		    	return ajax('emp_get.action?id=',value, 'empuuid_'+index,'t.name');
		  		    }},
		  		    {field:'opertime',title:'操作日期',width:100,formatter:function(value){
		  		    	return new Date(value).Format('yyyy-MM-dd');
		  		    }},
		  		    {field:'storeuuid',title:'仓库',width:100,formatter:function(value,row,index){
		  		    	return ajax('store_get.action?id=',value, 'storeuuid_'+index,'t.name');
		  		    }},
		  		    {field:'goodsuuid',title:'商品',width:100,formatter:function(value,row,index){
		  		    	return ajax('goods_get.action?id=',value, 'goodsuuid'+index,'t.name');
		  		    }},
		  		    {field:'num',title:'数量',width:100},
		  		    {field:'type',title:'类型',width:100,formatter:function(value){
		  		    	return storetype[value];
		  		    }}		    
			  ]],
		  pagination:true,
		  singleSelect:true
		
	});
	
	$('#btnSearch').bind('click',function(){
		var formdata=getFormData("searchForm");
		$('#grid').datagrid('load',formdata);
		
	});
	
});