
var method="";//保存提交的方法名称 
$(function(){

    var columns=[[
        /*{field:'id',title:'编号',width:100},*/
        {field:'number',title:'工号',width:100},
        {field:'name',title:'姓名',width:100},
        {field:'birthday',title:'出生日期',width:100},
        {field:'department',title:'所在科室',width:200},
        {field:'title',title:'职称',width:100},
        {field:'education',title:'学历',width:100},
        {field:'phone',title:'手机号',width:100},
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
		url:baseUrl + '/user/list'+listParam,
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
				// $('#editWindow').window('open');
				// $('#editForm').form('clear');

                layer.open({
                    type: 2,
                    title: '用户管理',
                    maxmin: true,
                    shadeClose: true, //点击遮罩关闭层
                    area : ['80%' , '80%'],
                    content: 'userEdit.html'
                });
			}
		}]

	});
	
	//条件查询
	$('#btnSearch').bind('click',function(){
		var formdata=getFormData('searchForm');
		var dept = formdata.department;
		if(dept instanceof Array){
			formdata.departments = dept.join(",");
		}else{
			formdata.departments = dept;
		}	
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
			url: baseUrl + '/user/save',
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
				url:baseUrl+'/user/delete.do?id='+id,
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

    layer.open({
        type: 2,
        title: '用户管理',
        maxmin: true,
        shadeClose: true, //点击遮罩关闭层
        area : ['80%' , '80%'],
        content: 'userEdit.html?id=' + id
    });
}