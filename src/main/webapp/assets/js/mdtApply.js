$(function(){

    var columns=[[
        {field:'patientType',title:'患者类型',width:70,formatter:function(value,row,index) {
            if (row.patientType == '1') {
                return "住院";
            } else if (row.patientType == '2') {
                return "门诊";
            }
            return '';
        }},
        {field:'name',title:'患者姓名',width:120},
        {field:'gender',title:'性别',width:50},
        {field:'diagnoseDate',title:'入院/首诊时间',width:120},
        {field:'mdtDate',title:'MDT时间',width:120},
        {field:'mdtLocation',title:'MDT地点',width:150},
        {field:'applyPerson',title:'申请人',width:120},
        {field:'applyStatus',title:'申请状态',width:150,formatter:function(value,row,index) {
            if (row.applyStatus == 0) {
                return "未提交";
            } else if (row.applyStatus == 1) {
                return "已提交未审核";
            }  else if (row.applyStatus == 2) {
                return "科主任已审核";
            } else if (row.applyStatus == 11) {
                return "申请人流转";
            } else if (row.applyStatus == 9) {
                return "审核不通过";
            } else if (row.applyStatus == 13) {
                return "MDT会诊";
            }else if (row.applyStatus == 15) {
                return "等待反馈";
            }else if (row.applyStatus == 19) {
                return "完成";
            }else if (row.applyStatus == -1) {
                return "已作废";
            }
            return '';
        }},

        {field:'-',title:'操作',width:700,formatter:function(value,row,index) {
        	var editBtn = "<input type='button' onclick='edit("+row.id+")' class='self-btn' value='修改'/>";
        	var auditBtn = "<input type='button' onclick='auditFun("+row.id+")' class='self-btn' value='审核'/>";
            var feeBtn = "<input type='button' onclick='feeFun("+row.id+")' class='self-btn' value='打印缴纳单'/>";
            var msgBtn = "<input type='button' onclick='msgFun("+row.id+")' class='self-btn' value='短信通知'/>";
            var informBtn = "<input type='button' onclick='informFun("+row.id+")' class='self-btn' value='知情同意书'/>";
            var expertGradeBtn = "<input type='button' onclick='expertGradeFun("+row.id+")' class='self-btn' value='专家打分'/>";
            var expertGradeBtn1 = "<input type='button' onclick='departmentGradeFun1("+row.id+")' class='self-btn' value='专家打分录入'/>";
            var viewExpertGradeBtn = "<input type='button' onclick='viewExpertGradeFun("+row.id+")' class='self-btn' value='专家意见汇总'/>";
            var summaryBtn = "<input type='button' onclick='summaryFun("+row.id+")' class='self-btn' value='申请小结'/>";
            var departmentGradeBtn = "<input type='button' onclick='departmentGradeFun("+row.id+")' class='self-btn' value='组织科室打分'/>";
            var feedbackBtn = "<input type='button' onclick='feedbackFun("+row.id+")' class='self-btn' value='反馈'/>";
            var deleBtn = "<input type='button' onclick='dele("+row.id+")' class='self-btn' value='删除'/>";
            var zuofeiBtn = "<input type='button' onclick='zuofei("+row.id+")' class='self-btn' value='作废'/>";

            var btn = "";

            var user = getUser();

            // 申请人本人才有这个权限
            if (user.id == row.applyPersonId && (row.applyStatus == 0 || row.applyStatus == 9 )) {
                btn = btn + editBtn;
                btn = btn + deleBtn;
            }

            // 是否需要审批按钮
            if (row.isSp == 1) {
            	btn = btn + auditBtn;
            }

            //缴费
            if(row.applyStatus >= 11){
            	btn = btn+feeBtn +informBtn+msgBtn ;
            } 
            //打分小结
            if(row.applyStatus >= 13 ){
            	btn = btn + departmentGradeBtn+summaryBtn+expertGradeBtn1;
            }
            //作废
            if(row.applyStatus >= 11 && row.applyStatus <= 13){
            	btn = btn +zuofeiBtn ;
            } 
            //反馈
            if(row.applyStatus >= 15){
            	btn = btn +feedbackBtn+viewExpertGradeBtn;
            }
            return btn;
        }}
    ]];

    var toolbar = [{
            iconCls: 'icon-add',
            text:'MDT申请',
            handler: function(){
                layer.open({
                    type: 2,
                    title: 'MDT申请',
                    maxmin: true,
                    shadeClose: true, //点击遮罩关闭层
                    area : ['80%' , '80%'],
                    content: 'mdtApplyEdit.html?type=add'
                });
            }
        }]
 

	//表格数据初始化
	$('#grid').datagrid({
		url:baseUrl + '/mdtApply/findByPage',
        loadFilter: function(data){
            return data.resultData;
        },
        onDblClickRow:function(rowIndex,rowData){
			view(rowData.id);
		},
		columns:columns,
		singleSelect:true,
		pagination:true,
		toolbar: toolbar

	});
	
	//条件查询
	$('#btnSearch').bind('click',function(){
        doSearch();
	});

});

// 审核
function auditFun(id){

    layer.open({
        type: 2,
        title: 'MDT申请审核',
        maxmin: true,
        shadeClose: true, //点击遮罩关闭层
        area : ['80%' , '80%'],
        content: 'mdtApplyEdit.html?type=audit&id=' + id
    });
}

// 打印缴纳单
function feeFun(id) {

    layer.open({
        type: 2,
        title: 'MDT缴费通知',
        maxmin: true,
        shadeClose: true, //点击遮罩关闭层
        area : ['80%' , '80%'],
        content: 'mdtApplyFee.html?id=' + id
    });
}

// 短信通知
function msgFun(id) {

    layer.open({
        type: 2,
        title: 'MDT短信通知',
        maxmin: true,
        shadeClose: true, //点击遮罩关闭层
        area : ['80%' , '80%'],
        content: 'mdtApplyMsg.html?id=' + id
    });
}

// 知情同意书
function informFun(id) {

    layer.open({
        type: 2,
        title: 'MDT知情同意书',
        maxmin: true,
        shadeClose: true, //点击遮罩关闭层
        area : ['80%' , '80%'],
        content: 'mdtApplyInform.html?id=' + id
    });
}


// MDT会诊
function expertGradeFun(id) {
    layer.open({
        type: 2,
        title: '专家打分',
        maxmin: true,
        shadeClose: true, //点击遮罩关闭层
        area : ['80%' , '80%'],
        content: 'mdtApplyExpertGrade.html?id=' + id
    });
}

// MDT会诊
function viewExpertGradeFun(id) {
    layer.open({
        type: 2,
        title: '专家意见汇总',
        maxmin: true,
        shadeClose: true, //点击遮罩关闭层
        area : ['80%' , '80%'],
        content: 'mdtApplyViewExpertGrade.html?id=' + id
    });
}

// 申请小结
function summaryFun(id) {

    layer.open({
        type: 2,
        title: '申请小结',
        maxmin: true,
        shadeClose: true, //点击遮罩关闭层
        area : ['80%' , '80%'],
        content: 'mdtApplySummary.html?id=' + id
    });
}

// MDT会诊
function departmentGradeFun(id) {
    layer.open({
        type: 2,
        title: '组织科室打分',
        maxmin: true,
        shadeClose: true, //点击遮罩关闭层
        area : ['80%' , '80%'],
        content: 'mdtApplyDeptGrade.html?id=' + id
    });
}

//MDT会诊
function departmentGradeFun1(id) {
    layer.open({
        type: 2,
        title: '专家打分录入',
        maxmin: true,
        shadeClose: true, //点击遮罩关闭层
        area : ['80%' , '80%'],
        content: 'mdtApplyDeptLuru.html?id=' + id
    });
}


// 反馈
function feedbackFun(id) {
    layer.open({
        type: 2,
        title: '反馈',
        maxmin: true,
        shadeClose: true, //点击遮罩关闭层
        area : ['80%' , '80%'],
        content: 'mdtApplyFeedback.html?id=' + id
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
 * 作废
 */
function zuofei(id){
	$.messager.confirm('提示','确定要作废此记录吗？',function(r){
		if(r){
			$.ajax({
				url:baseUrl + '/mdtApply/zuofei?id='+id,
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
        title: 'MDT申请',
        maxmin: true,
        shadeClose: true, //点击遮罩关闭层
        area : ['80%' , '80%'],
        content: 'mdtApplyEdit.html?type=edit&id=' + id
    });
}

function view(id){
    layer.open({
        type: 2,
        title: 'MDT申请',
        maxmin: true,
        shadeClose: true, //点击遮罩关闭层
        area : ['80%' , '80%'],
        content: 'mdtApplyEdit.html?type=view&id=' + id
    });
}

function doSearch() {
    var formdata=getFormData('searchForm');
    $('#grid').datagrid('load',formdata);
}