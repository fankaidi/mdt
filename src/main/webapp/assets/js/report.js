$(function(){
	
	$('#grid').datagrid({
		url:url,
		columns:columns		
	});
	
	//查询
	$('#btnSearch').bind('click',function(){
		var formdata= getFormData("searchForm"); 
		$('#grid').datagrid('load',formdata);
		
		//刷新统计图
		$('#chart').attr('src','report_orderChart.action?date1='+ formdata['date1']  +'&date2='+ formdata['date2']);
		
	});
	
	//导出EXCEL
	$('#btnExport').bind('click',function(){
		var formdata= getFormData("searchForm"); 
		window.open('report_orderExcel.action?date1='+ formdata['date1']  +'&date2='+ formdata['date2']);
	});
	
})