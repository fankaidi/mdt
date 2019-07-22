
var method="";//保存提交的方法名称

function initGrid1() {

    var columns=[[
		/*{field:'id',title:'编号',width:100},*/
        {field:'a1',title:'姓名',width:100},
        {field:'a2',title:'回复时间',width:170},
        {field:'a3',title:'回复',width:100},
        {field:'-',title:'回复查看',width:100,formatter:function(value,row,index) {
            return "<a href='#' onclick='dele("+row.id+")'>查看</a>";
        }},
        {field:'a4',title:'申请会诊必要性',width:150, formatter:function(value,row,index) {
                return '<span class="icon"></span><span class="icon"></span><span class="icon"></span><span class="icon"></span><span class="icon"></span>';
        }},

        {field:'a4',title:'会诊申请书目的明确',width:150},
        {field:'a4',title:'病情现况资料详尽',width:150},
        {field:'a4',title:'会诊书写规范、详细',width:150},
        {field:'a4',title:'会诊意见解决问题',width:150}
    ]];

    //表格数据初始化
    $('#grid1').datagrid({
        title:'专家评分',
        url:baseUrl + '/mdtApply/list5',
        loadFilter: function(data){
            return data.resultData;
        },
        columns:columns,
        singleSelect:true,
        fitColumns: false

    });
}

$(function(){
    initGrid1();

    var columns=[[
        /*{field:'id',title:'编号',width:100},*/
        {field:'number',title:'患者类型',width:100},
        {field:'name',title:'患者姓名',width:100},
        {field:'name',title:'入院/首诊时间',width:100},
        {field:'name',title:'MDT时间',width:100},
        {field:'name',title:'MDT地点',width:100},
        {field:'name',title:'审核状态',width:100},

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
		url:baseUrl + '/mdt/apply'+listParam,
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
                // var $win;
                // $win = $('#editWindow').window({
                //     title: '添加课程设置信息',
                //     width: ($(window).width() - 100),
                //     height: ($(window).height() - 200),
                //     // top: ($(window).height() - 820) * 0.5,
                //     // left: ($(window).width() - 450) * 0.5,
                //     shadow: true,
                //     modal: true,
                //     iconCls: 'icon-add',
                //     closed: true,
                //     minimizable: false,
                //     maximizable: false,
                //     collapsible: false
                // });
                //
                // $win.window('open');
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
			url: '/user/save',
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
	method="update";
	$('#editWindow').window('open');

    $.ajax({
        url: '/user/get?id='+id,
        dataType:'json',
        type:'post',
        success:function(value){

            if(value.type == 'success'){
                $('#editForm').form('load', value.resultData.row);
            }
        }
    });

	// $('#editForm').form('load', '/user/get?id='+id);
}