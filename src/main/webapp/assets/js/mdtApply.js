$(function(){

    var columns=[[
        /*{field:'id',title:'编号',width:100},*/
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
            if (row.applyStatus == '0') {
                return "未提交";
            } else if (row.applyStatus == '1') {
                return "已提交未审核";
            }  else if (row.applyStatus == '2') {
                return "科主任已审核";
            } else if (row.applyStatus == '11') {
                return "病人缴费";
            } else if (row.applyStatus == '9') {
                return "审核不通过";
            } else if (row.applyStatus == '13') {
                return "MDT会诊";
            }
            return '';
        }},

        {field:'-',title:'操作',width:700,formatter:function(value,row,index) {
            var viewBtn = "<a href='#' onclick='view("+row.id+")'>查看</a> ";
            var editBtn = "<a href='#' onclick='edit("+row.id+")'>修改</a> ";
            var auditBtn = "<a href='#' onclick='auditFun("+row.id+")'>审核</a> ";
            var feeBtn = "<a href='#' onclick='feeFun("+row.id+")'>打印缴纳单</a> ";
            var msgBtn = "<a href='#' onclick='msgFun("+row.id+")'>短信通知</a> ";
            var informBtn = "<a href='#' onclick='informFun("+row.id+")'>知情同意书</a> ";
            var consultBtn = "<a href='#' onclick='consultFun("+row.id+")'>MDT会诊</a> ";
            var expertGradeBtn = "<a href='#' onclick='expertGradeFun("+row.id+")'>专家打分</a> ";
            var expertGradeBtn1 = "<a href='#' onclick='departmentGradeFun1("+row.id+")'>专家打分录入</a> ";
            var viewExpertGradeBtn = "<a href='#' onclick='viewExpertGradeFun("+row.id+")'>概述</a> ";
            var summaryBtn = "<a href='#' onclick='summaryFun("+row.id+")'>申请小结</a> ";
            var departmentGradeBtn = "<a href='#' onclick='departmentGradeFun("+row.id+")'>组织科室打分</a> ";
            var feedbackBtn = "<a href='#' onclick='feedbackFun("+row.id+")'>反馈</a> ";
            var deleBtn = "<a href='#' onclick='dele("+row.id+")'>删除</a> ";

            var btn = "";
            btn = btn + viewBtn;

            var user = getUser();
            
            var roleIds = user.roleIds;

            // 申请人本人才有这个权限
            if (user.id == row.applyPersonId && (row.applyStatus == "0" || row.applyStatus == "9" )) {
                btn = btn + editBtn;
                btn = btn + deleBtn;
            }

            // 是否需要审批按钮
            if (row.isSp == 1) {
            	btn = btn + auditBtn;
            }

            // 住院病人
            if (row.patientType == '1' && parseInt(row.applyStatus) >= 11 ) {
        		//如果收费，就要打印缴费通知单
                if (row.isCharge == '1') {
                    btn = btn + feeBtn;
                }
                btn = btn + informBtn + msgBtn;


                // 本人的一些操作
                btn = btn + departmentGradeBtn + viewExpertGradeBtn + summaryBtn;
                // 专家
                if (roleIds.indexOf('6') != -1) {
                    btn = btn + expertGradeBtn;
                }
                //反馈
                btn += feedbackBtn;
            }
            // 门诊病人
            if (row.patientType == '2' && row.applyStatus == '0' && roleIds.indexOf('7') != -1) {
                btn = btn + informBtn + msgBtn + feedbackBtn;
            }
            //后续操作
            if(parseInt(row.applyStatus) >= 11 ){
            	btn = btn +expertGradeBtn1;
            }
            
            return btn;
        }}
    ]];

    var toolbar = [{
            iconCls: 'icon-add',
            text:'增加',
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
function consultFun() {

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
        title: '专家打分',
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