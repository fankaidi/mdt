
$(function(){

    var columns=[[
        /*{field:'id',title:'编号',width:100},*/
        {field:'type',title:'类型',width:150,formatter:function(value,row,index) {
            if (row.type == '1') {
                return "专家评分";
            } else if (row.type == '2') {
                return "组织科室评分";
            }
            return "";
        }},
        {field:'description',title:'评分项说明',width:200},
        {field:'minValue',title:'最小分数',width:100},
        {field:'maxValue',title:'最大分数',width:100},

        {field:'-',title:'操作',width:200,formatter:function(value,row,index) {
        	var editBtn = "<input type='button' onclick='edit("+row.id+")' class='self-btn' value='修改'/>";
        	var deleBtn = "<input type='button' onclick='dele("+row.id+")' class='self-btn' value='删除'/>";
            return editBtn+deleBtn;
        }}
    ]];
	
	//表格数据初始化
	$('#grid').datagrid({
		url:baseUrl + '/set/selectSysGrade',
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
                    title: '评分项设置',
                    maxmin: true,
                    shadeClose: true, //点击遮罩关闭层
                    area : ['80%' , '80%'],
                    content: 'sysGradeEdit.html'
                });
			}
		}]

	});
	
	//条件查询
	$('#btnSearch').bind('click',function(){
        doSearch();
	});

});

function edit(id) {

    layer.open({
        type: 2,
        title: '评分项设置',
        maxmin: true,
        shadeClose: true, //点击遮罩关闭层
        area : ['80%' , '80%'],
        content: 'sysGradeEdit.html?id=' + id
    });
}

/**
 * 删除 
 */
function dele(id){
	
	$.messager.confirm('提示','确定要删除此记录吗？',function(r){
		if(r)
		{
			$.ajax({
				url: baseUrl + '/set/delSysGrade?id='+id,
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