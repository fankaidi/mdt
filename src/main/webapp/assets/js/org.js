
var method="";//保存提交的方法名称
$(function(){

    var columns=[[
        /*{field:'id',title:'编号',width:100},*/
        {field:'id',title:'编号',width:100},
        {field:'name',title:'名称',width:200},
        {field:'pid',title:'父级编号',width:100},
        {field:'area',title:'片区',width:100},

        {field:'-',title:'操作',width:200,formatter:function(value,row,index) {
        	var editBtn = "<input type='button' onclick='edit("+row.id+")' class='self-btn' value='修改'/>";
        	var deleBtn = "<input type='button' onclick='dele("+row.id+")' class='self-btn' value='删除'/>";
            return editBtn+deleBtn;
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
		url:baseUrl + '/org/findByPage'+listParam,
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
                $('#idInput').textbox('textbox').attr('readonly',false);

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
			url: baseUrl + '/org/' + method,
			data:formdata,
			dataType:'json',
			type:'post',
			success:function(value){

				if(value.type == 'success'){
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
                url: baseUrl + '/org/delete?id='+id,
				dataType:'json',
				success:function(value){
                    if(value.type == 'success'){
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

    $('#idInput').textbox('textbox').attr('readonly',true);

	method="update";
	$('#editWindow').window('open');

    $.ajax({
        url: baseUrl + '/org/get?id='+id,
        dataType:'json',
        type:'post',
        success:function(value){
            if(value.type == 'success'){
                $('#editForm').form('load', value.resultData.row);
            }
        }
    });
}