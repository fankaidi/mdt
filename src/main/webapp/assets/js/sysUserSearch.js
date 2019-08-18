
var userm = {};
$(function() {
	initGrid1();
});


function initGrid1() {
	var columns=[[
	              {field:'number',title:'工号',width:100},
	              {field:'name',title:'姓名',width:100},
	              {field:'department',title:'所在科室',width:200},
	              {field:'title',title:'职称',width:100},
	              {field:'-',title:'操作',width:100,formatter:function(value,row,index) {
	                  return "<a href='#' onclick='choose("+row.id+")'>选取</a>";
	              }}
	          ]];
    //表格数据初始化
    $('#grid1').datagrid({
        title:'用户选择',
        url:baseUrl + '/user/list',
        loadFilter: function(data){
            return data.resultData;
        },
        columns:columns,
        singleSelect:true,
        rownumbers:true
    });
}

function doSearch() {
    var formdata=getFormData('searchForm');
    $('#grid1').datagrid('load',formdata);
}

/**
 * 选择
 */
function choose(id){
	if(userm[id]){
		alert('该用户已选择');
		return;
	}
	$.ajax({
        url: baseUrl + '/user/get?id='+id,
        dataType:'json',
        type:'post',
        success:function(value){
            if(value.type == 'success'){
            	var row = value.resultData.row;
            	userm[id] = row;
            	var html = '<div class="userinfo" dataid="'+row.id+'">'+row.name+'<button type="button" onclick="del(this)">删除</button></div>';
            	$('#selectUser').append(html);
            } else {
                $.messager.alert('提示',value.message);
            }
        }
    });
}

/**
 * 确认选择
 */
function queren(){
	parent.getSearchUser(userm);
	var mylay = parent.layer.getFrameIndex(window.name);
    parent.layer.close(mylay);

}


/**
 * 删除
 */
function del(obj){
	$(obj).parent().remove();
}