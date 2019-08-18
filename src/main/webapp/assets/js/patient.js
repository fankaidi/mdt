
$(function(){

    var columns=[[
        /*{field:'id',title:'编号',width:100},*/
        {field:'patientType',title:'患者类型',width:100,formatter:function(value,row,index) {
        	if (row.patientType == '2') {
        		return "门诊";
			} else if (row.patientType == '1') {
        		return "住院";
			}
            return "";
        }},
        {field:'name',title:'姓名',width:100},
        {field:'birthday',title:'出生日期',width:100},
        {field:'gender',title:'性别',width:100},
        {field:'idcard',title:'身份证号',width:200},

        {field:'-',title:'操作',width:200,formatter:function(value,row,index) {
        	var editBtn = "<input type='button' onclick='edit("+row.id+")' class='self-btn' value='修改'/>";
        	var deleBtn = "<input type='button' onclick='dele("+row.id+")' class='self-btn' value='删除'/>";
            return editBtn+deleBtn;
        }}
    ]];

	//表格数据初始化
	$('#grid').datagrid({
		url:baseUrl + '/patient/list',
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
                    title: '患者信息',
                    maxmin: true,
                    shadeClose: true, //点击遮罩关闭层
                    area : ['80%' , '80%'],
                    content: 'patientEdit.html'
                });
			}
		}]

	});
	
	//条件查询
	$('#btnSearch').bind('click',function(){
		doSearch();
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
                url: baseUrl + '/patient/delete?id='+id,
				dataType:'json',
				success:function(value){
                    if(value.type = 'success'){
                        doSearch();
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
        title: '患者信息',
        maxmin: true,
        shadeClose: true, //点击遮罩关闭层
        area : ['80%' , '80%'],
        content: 'patientEdit.html?id=' + id
    });
}

function doSearch() {
    var formdata=getFormData('searchForm');
    $('#grid').datagrid('load',formdata);
}

function changePatientType(val) {

    var patientType;
    if (val) {
        patientType = val;
	} else {
        patientType = $("input[name='patientType']:checked").val();
	}


    if (patientType == '2') {

        $("tr[id^='outpatient']").each(function () {
            $(this).show();
        });

        $("tr[id^='hospital']").each(function () {
            $(this).hide();
        });

	} else {

        $("tr[id^='outpatient']").each(function () {
            $(this).hide();
        });

        $("tr[id^='hospital']").each(function () {
            $(this).show();
        });
	}
}