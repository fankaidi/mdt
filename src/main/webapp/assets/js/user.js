
var method="";//保存提交的方法名称 
$(function(){

    intDepartment();

    var columns=[[
        /*{field:'id',title:'编号',width:100},*/
        {field:'number',title:'工号',width:100},
        {field:'name',title:'姓名',width:100},
        {field:'birthday',title:'出生日期',width:100},
        {field:'department',title:'所在科室',width:200},
        {field:'title',title:'职称',width:100},
        {field:'education',title:'学历',width:100},

        {field:'-',title:'操作',width:200,formatter:function(value,row,index) {
            return "<a href='#' onclick='edit("+row.id+")'>修改</a> <a href='#' onclick='dele("+row.id+")'>删除</a>";
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

    layer.open({
        type: 2,
        title: '用户管理',
        maxmin: true,
        shadeClose: true, //点击遮罩关闭层
        area : ['80%' , '80%'],
        content: 'userEdit.html?id=' + id
    });

    // method="update";
    // $('#editWindow').window('open');
    //
    // $.ajax({
    //     url: baseUrl + '/user/get?id='+id,
    //     dataType:'json',
    //     type:'post',
    //     success:function(value){
    //
    //         if(value.type = 'success'){
    //             $('#editForm').form('load', value.resultData.row);
    //         }
    //     }
    // });

	// $('#editForm').form('load', '/user/get?id='+id);
}

function intDepartment() {

    // $('#departmentSelect').combobox({
    //     url: baseUrl + '/org/listAll',
    //     // loadFilter: function(data){
    //     // 	console.log(data)
    //     //     return data.resultData.rows;
    //     // },
    //     valueField:'id',
    //     textField:'name'
    // });

    // $.ajax({
    //     url: baseUrl + '/org/listAll',
    //     dataType:'json',
    //     type:'post',
    //     success:function(value){
    //
    //         if(value.type = 'success'){
    //             var datas = value.resultData.rows;
    //
		// 		for(var i=0; i<datas.length; i++) {
    //                 var option = "";
    //                 if (datas[i].id.length == 1) {
    //                 	option = $("<option value='"+ datas[i].id +"'>"+ datas[i].name +"    </option>");
		// 			} else if (datas[i].id.length == 2) {
    //                     option = $("<option value='"+ datas[i].id +"'>----"+ datas[i].name +"    </option>");
		// 			} else if (datas[i].id.length == 4) {
    //                     option = $("<option value='"+ datas[i].id +"'>--------"+ datas[i].name +"    </option>");
		// 			}
    //                 $("#departmentSelect").append(option);
		// 		}
    //         }
    //     }
    // });


    $('#departmentSelect').combobox({
        url: baseUrl + "/org/listAll",
        loadFilter: function(data){
            return data.resultData.rows;
        },
        formatter: function(row){
            var opts = $(this).combobox('options');
            var value = row[opts.valueField]
            var text = row[opts.textField]

            var option = "";
            if (value.length == 1) {

            } else if (value.length == 2) {
                text = "----" + text;
            } else if (value.length == 4) {
                text = "--------" + text;
            }
            return text;
        },
        valueField:'id',
        textField:'name'
    });
}

function doSearch() {
    $('#grid').datagrid('reload');
}