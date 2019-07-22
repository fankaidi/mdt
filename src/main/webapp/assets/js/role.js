
var method="";//保存提交的方法名称 
$(function(){

    var columns=[[
        {field:'id',title:'编号',width:100},
        {field:'name',title:'名称',width:100},

        {field:'-',title:'操作',width:200,formatter:function(value,row,index) {
            return "<a href='#' onclick='edit("+row.uuid+")'>修改</a> <a href='#' onclick='dele("+row.uuid+")'>删除</a>";
        }}
    ]];
	
	if(typeof(listParam)=='undefined'){
		listParam='';		
	}
	if(typeof(saveParam)=='undefined'){
		saveParam='';		
	}
	
	//表格数据初始化
	$('#grid').datagrid({
		url:'/role/findAll'+listParam,
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
				method="add";
				$('#editWindow').window('open');
				$('#editForm').form('clear');
			}
		}]

	});
	
	//条件查询
	$('#btnSearch').bind('click',function(){
		var formdata=getFormData('searchForm');
		$('#grid').datagrid('load',formdata);
	});

	//保存
	$('#btnSave').bind('click',function(){

		//判断：编辑表单的所有控件是否都通过验证
		var isValidate= $('#editForm').form('validate');
		if(isValidate==false){
			return ;
		}

		var formdata=getFormData('editForm');

		$.ajax({
			url:name+'_'+method+'.action'+saveParam,
			data:formdata,
			dataType:'json',
			type:'post',
			success:function(value){

				if(value.success){
					$('#editWindow').window('close');
					$('#grid').datagrid('reload');
				}
				$.messager.alert('提示',value.message);
			}

		});


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
				url:name+'_delete.action?id='+id,
				dataType:'json',
				success:function(value){
					if(value.success){
						$('#grid').datagrid('reload');
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
	method="update";
	$('#editWindow').window('open');
	$('#editForm').form('load',name+'_get.action?id='+id);	
}