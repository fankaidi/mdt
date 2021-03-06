$(function(){
	
	$('#grid').datagrid({
		url:'storedetail_listByPage.action',
		columns:[[
		  		    {field:'uuid',title:'编号',width:100},
		  		    {field:'storeuuid',title:'仓库',width:100,formatter:function(value,row,index){
		  		    	return ajax('store_get.action?id=',value,'storeuuid_'+index,'t.name');		  		    	
		  		    }},
		  		    {field:'goodsuuid',title:'商品',width:100,formatter:function(value,row,index){
		  		    	return ajax('goods_get.action?id=',value,'goodsuuid_'+index,'t.name');		  		    	
		  		    }},
		  		    {field:'num',title:'数量',width:100}	    
		  ]],
		pagination:true,
		singleSelect:true,
		onDblClickRow:function(rowIndex,rowData){
			$('#storeoperWindow').window('open');
			
			//显示仓库名称
			ajax('store_get.action?id=',rowData.storeuuid,'store','t.name');
			//显示商品名称
			ajax('goods_get.action?id=',rowData.goodsuuid,'goods','t.name');
			
			//筛选仓库操作记录
			$('#storeoperGrid').datagrid('load',{'t1.storeuuid':rowData.storeuuid,'t1.goodsuuid':rowData.goodsuuid});
			
			
		}
	});
	
	$('#storeoperGrid').datagrid({
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
		  		    	return ajax('store_get.action?id=',value, 'store_'+index,'t.name');
		  		    }},
		  		    {field:'goodsuuid',title:'商品',width:100,formatter:function(value,row,index){
		  		    	return ajax('goods_get.action?id=',value, 'goods_'+index,'t.name');
		  		    }},
		  		    {field:'num',title:'数量',width:100},
		  		    {field:'type',title:'类型',width:100,formatter:function(value){
		  		    	return storetype[value];
		  		    }}		    
			  ]],
		  pagination:true,
		  singleSelect:true
		
	});
	
	//查询
	$('#btnSearch').bind('click',function(){
		var formdata=getFormData('searchForm');
		$('#grid').datagrid('load',formdata);
	});
	
});