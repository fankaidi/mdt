
$(function(){

    var columns=[[
        /*{field:'id',title:'编号',width:100},*/
        {field:'min',title:'科室数(>=)',width:100},
        {field:'max',title:'科室数(<=)',width:100},
        {field:'price',title:'挂号费用（元）',width:150},

        {field:'-',title:'操作',width:200,formatter:function(value,row,index) {
            return " <a href='#' onclick='dele("+row.id+")'>删除</a>";
        }}
    ]];
	
	//表格数据初始化
	$('#grid').datagrid({
		url:baseUrl + '/set/selectSysFee',
        loadFilter: function(data){
            return data.resultData;
        },
		columns:columns,
		singleSelect:true,
		pagination:true,
		toolbar: [{
			iconCls: 'icon-add',
			text:'增加',
			handler: function(){

                layer.open({
                    type: 2,
                    title: '收费情况设置',
                    maxmin: true,
                    shadeClose: true, //点击遮罩关闭层
                    area : ['80%' , '80%'],
                    content: 'sysFeeEdit.html'
                });
			}
		}]

	});
	
	//条件查询
	$('#btnSearch').bind('click',function(){
        doSearch();
	});

});

/**
 * 删除 
 */
function dele(id){
	
	$.messager.confirm('提示','确定要删除此记录吗？',function(r){
		if(r)
		{
			$.ajax({
				url: baseUrl + '/set/delSysFee?id='+id,
				dataType:'json',
				success:function(value){
                    if(value.type == 'success'){
                        doSearch();
                    }
                    $.messager.alert('提示',value.message);
				}
			});		
		}	
	});	
}

function doSearch() {

    var formdata=getFormData('searchForm');
    $('#grid').datagrid('load',formdata);
}