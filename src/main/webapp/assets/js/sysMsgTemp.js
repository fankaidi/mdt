
$(function(){

    var columns=[[
        /*{field:'id',title:'编号',width:100},*/
        {field:'type',title:'类型',width:150,formatter:function(value,row,index) {
            if (row.type == '1') {
                return "MDT短信";
            }
            return "";
        }},
        {field:'content',title:'内容',width:800},

        {field:'-',title:'操作',width:200,formatter:function(value,row,index) {
            return " <a href='#' onclick='edit("+row.id+")'>编辑</a> ";
        }}
    ]];
	
	//表格数据初始化
	$('#grid').datagrid({
		url:baseUrl + '/set/selectSysMsgTemp',
        loadFilter: function(data){
            return data.resultData;
        },
		columns:columns,
		singleSelect:true,
		pagination:true
	});

});

function edit(id) {

    layer.open({
        type: 2,
        title: '短信模板设置',
        maxmin: true,
        shadeClose: true, //点击遮罩关闭层
        area : ['50%' , '80%'],
        content: 'sysMsgTempEdit.html?id=' + id
    });
}

function doSearch() {

    var formdata=getFormData('searchForm');
    $('#grid').datagrid('load',formdata);
}