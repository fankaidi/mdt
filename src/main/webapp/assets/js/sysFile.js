var type = null;
$(function(){
	type = getQueryVariable("type");
    var columns=[[
        {field:'title',title:'标题',width:400},
        {field:'createdTimeStr',title:'创建时间',width:200},
        {field:'-',title:'操作',width:200,formatter:function(value,row,index) {
        	if(type == 'view'){
        		return "";
        	}
        	var editBtn = "<input type='button' onclick='edit("+row.id+")' class='self-btn' value='修改'/>";
        	var deleBtn = "<input type='button' onclick='dele("+row.id+")' class='self-btn' value='删除'/>";
            return editBtn+deleBtn;
        }}
    ]];

	//表格数据初始化
	$('#grid').datagrid({
		url:baseUrl + '/file/list.do',
        loadFilter: function(data){
            return data.resultData;
        },
        onDblClickRow:function(rowIndex,rowData){
			view(rowData.id);
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

/**
 * 查看
 */
function view(id){

    layer.open({
        type: 2,
        title: '资料信息',
        maxmin: true,
        shadeClose: true, //点击遮罩关闭层
        area : ['80%' , '80%'],
        content: 'sysFileEdit.html?type=view&id=' + id
    });
}

function doSearch() {
    var formdata=getFormData('searchForm');
    $('#grid').datagrid('load',formdata);
}
