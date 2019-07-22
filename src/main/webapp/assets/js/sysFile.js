
$(function(){

    var columns=[[
        {field:'title',title:'标题',width:400},
        {field:'createdTimeStr',title:'创建时间',width:200},
        {field:'-',title:'操作',width:200,formatter:function(value,row,index) {
            return "<a href='#' onclick='edit("+row.id+")'>修改</a> <a href='#' onclick='dele("+row.id+")'>删除</a>";
        }}
    ]];

	//表格数据初始化
	$('#grid').datagrid({
		url:baseUrl + '/file/list.do',
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
                    title: '资料信息',
                    maxmin: true,
                    shadeClose: true, //点击遮罩关闭层
                    area : ['80%' , '80%'],
                    content: 'sysFileEdit.html'
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
                url: baseUrl + '/file/delete.do?id='+id,
				dataType:'json',
				success:function(value){
                    if(value.type = 'success'){
                        doSearch();
                    }
                    $.messager.alert('提示',value.message);
				}
			});		
		}	
	});	
}

/**
 * 编辑
 */
function edit(id){

    layer.open({
        type: 2,
        title: '资料信息',
        maxmin: true,
        shadeClose: true, //点击遮罩关闭层
        area : ['80%' , '80%'],
        content: 'sysFileEdit.html?id=' + id
    });
}

function doSearch() {
    var formdata=getFormData('searchForm');
    $('#grid').datagrid('load',formdata);
}
