var audit = 0;  // 区分是否MDT团队是否需要审核  audit=1时是不需要审核的
var param = "";

$(function(){
	audit = getQueryVariable("audit");
    if(audit == 1){
        $("#auditStatus").val('4');
        $("#applyPersondiv1").hide();
        $("#applyPersondiv2").hide();
        param = '?auditStatus=4';
    } else {
        audit = 0;
    }
});

$(function(){

	var dobj = [];
	if(audit != 1){
		dobj.push({field:'applyPerson',title:'申请人',width:100});
	}
	dobj.push({field:'name',title:'MDT名称',width:600});
	dobj.push({field:'date',title:'申请日期',width:100});
	dobj.push({field:'auditStatus',title:'审核状态',width:200, hidden: (audit=='1'), formatter:function(value,row,index) {
        // 0:未审核 1:科主任审核 2:医务部主任审核 3:分管院长审核
        if (row.auditStatus == '0') {
            return "未提交";
        } else if (row.auditStatus == '1') {
            return "已提交未审核";
        }  else if (row.auditStatus == '2') {
            return "科主任已审核";
        } else if (row.auditStatus == '3') {
            return "医务部主任已审核";
        } else if (row.auditStatus == '4') {
            return "已审核完成";
        } else if (row.auditStatus == '9') {
            return "审核不通过";
        }
        return '';
    }});
	dobj.push({field:'-',title:'操作',width:200,formatter:function(value,row,index) {
        var editBtn = "<input type='button' onclick='edit("+row.id+")' class='self-btn' value='修改'/>";
        var auditBtn = "<input type='button' onclick='auditFun("+row.id+")' class='self-btn' value='审批'/>";
        var deleBtn = "<input type='button' onclick='dele("+row.id+")' class='self-btn' value='删除'/>";
        var btn = "" ;
        // 普通用户
        if (row.auditStatus == '0' || row.auditStatus == '9' || audit == 1) {
            btn += editBtn + deleBtn;
        }
        return btn;
    }});
    var columns=[dobj];
	
	//表格数据初始化
	$('#grid').datagrid({
		url:baseUrl + '/mdtTeam/findByPage' + param,
        loadFilter: function(data){
            return data.resultData;
        },
		columns:columns,
		onDblClickRow:function(rowIndex,rowData){
			view(rowData.id);
		},
		singleSelect:true,
		pagination:true,
		toolbar: [{
			iconCls: 'icon-add',
			text:'增加',
			handler: function(){
                layer.open({
                    type: 2,
                    title: 'MDT团队',
                    maxmin: true,
                    shadeClose: true, //点击遮罩关闭层
                    area : ['80%' , '80%'],
                    content: 'mdtTeamEdit.html?type=add&audit=' + audit
                });

			}
		}]

	});
	
	//条件查询
	$('#btnSearch').bind('click',function(){
		var formdata=getFormData('searchForm');
		$('#grid').datagrid('load',formdata);
	});

});

/**
 * 审核
 */
function auditFun(id){

    layer.open({
        type: 2,
        title: 'MDT团队审核',
        maxmin: true,
        shadeClose: true, //点击遮罩关闭层
        area : ['80%' , '80%'],
        content: 'mdtTeamEdit.html?type=audit&id=' + id
    });
}

/**
 * 删除
 */
function dele(id){

	$.messager.confirm('提示','确定要删除此记录吗？',function(r){
		if(r)
		{
			$.ajax({
                url: baseUrl + '/mdtTeam/delete?id='+id,
				dataType:'json',
				success:function(value){
                    if(value.type == 'success'){
                        doSearch();
					}
					$.messager.alert('提示',value.message);
				}
			});
		}
	});
}

/**
 * 查看
 */
function view(id){
    layer.open({
        type: 2,
        title: 'MDT团队',
        maxmin: true,
        shadeClose: true, //点击遮罩关闭层
        area : ['80%' , '80%'],
        content: 'mdtTeamEdit.html?type=view&audit='+audit+'&id=' + id
    });
}

/**
 * 编辑
 */
function edit(id){
    layer.open({
        type: 2,
        title: 'MDT团队',
        maxmin: true,
        shadeClose: true, //点击遮罩关闭层
        area : ['80%' , '80%'],
        content: 'mdtTeamEdit.html?type=edit&audit='+audit+'&id=' + id
    });
}

function doSearch() {
    var formdata=getFormData('searchForm');
    $('#grid').datagrid('load',formdata);
}