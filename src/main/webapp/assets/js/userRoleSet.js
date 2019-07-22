var id;//选中的ID

$(function(){

    //表格数据初始化
    $('#grid').datagrid({
        url:baseUrl + '/user/list',
        loadFilter: function(data){
            return data.resultData;
        },
        columns:[[
            {field:'number',title:'工号',width:100},
            {field:'name',title:'姓名',width:100}
        ]],
        singleSelect:true,
        pagination:true,
        onClickRow:clickRow
    });

});

var clickRow=function(rowIndex,rowData){

	$('#tree').tree({
		url:baseUrl + '/role/readEmpRoles?userId='+rowData.id,
        loadFilter: function(data){
            return data.resultData.row;
        },
		animate:true,
		checkbox:true
	});
	id=rowData.id;
}

//保存
function save(){

	var nodes= $('#tree').tree("getChecked");//得到选中的节点集合

	var checkedStr="";
	for(var i=0;i<nodes.length;i++){
		if(i>0){
			checkedStr+=','
		}
		checkedStr+= nodes[i].id ;
	}

	$.ajax({
        url:baseUrl + '/user/saveRole',
		dataType:'json',
		type:'post',
		data:{userId:id,checkedStr:checkedStr},
		success:function(value){
			$.messager.alert('提示',value.message);
		}

	});

}