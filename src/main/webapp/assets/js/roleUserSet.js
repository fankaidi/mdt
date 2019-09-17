var id;//选中的ID


$(function(){

    //表格数据初始化
    $('#grid').datagrid({
        url:baseUrl + '/role/findAll',
        loadFilter: function(data){
            return data.resultData;
        },
        columns:[[
            {field:'id',title:'角色编号',width:100},
            {field:'name',title:'角色名称',width:100},
            {field:'level',title:'角色数据权限级别',width:100,formatter:function(value,row,index) {
                //
                if (row.level == '1') {
                    return "集团";
                } else if (row.level == '2') {
                    return "院区";
                } else if (row.level == '3') {
                    return "部门";
                } else if (row.level == '4') {
                    return "科室";
                } else if (row.level == '5') {
                    return "个人";
                }
                return '';
            }},
        ]],
        singleSelect:true,
        onClickRow:clickRow
    });

});

	var clickRow=function(rowIndex,rowData){
		//表格数据初始化
	    $('#grid2').datagrid({
	        url:baseUrl + '/user/selectUserByRoleId?id='+rowData.id,
	        loadFilter: function(data){
	        	if(!data.resultData.rows){
	        		data.resultData.rows = [];
	        	}
	            return data.resultData;
	        },
	        columns:[[
	            {field:'name',title:'用户名称',width:200},
	            {field:'number',title:'用户工号',width:200}
	        ]],
	        singleSelect:true,
	        onClickRow:clickRow
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
			url:baseUrl + '/role/save',
			dataType:'json',
			type:'post',
			data:{roleId:id,checkedStr:checkedStr},
			success:function(value){
				$.messager.alert('提示',value.message);
			}

		});

	}