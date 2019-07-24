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
                return "病人缴费";
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
            var viewBtn = "<a href='#' onclick='view("+row.id+")'>查看</a> ";
            var feeBtn = "<a href='#' onclick='feeFun("+row.id+")'>打印缴纳单</a> ";
            var informBtn = "<a href='#' onclick='informFun("+row.id+")'>知情同意书</a> ";
            var expertGradeBtn1 = "<a href='#' onclick='departmentGradeFun1("+row.id+")'>专家打分录入</a> ";
            var viewExpertGradeBtn = "<a href='#' onclick='viewExpertGradeFun("+row.id+")'>专家意见汇总</a> ";
            var summaryBtn = "<a href='#' onclick='summaryFun("+row.id+")'>申请小结</a> ";
            var departmentGradeBtn = "<a href='#' onclick='departmentGradeFun("+row.id+")'>组织科室打分</a> ";
            var feedbackBtn = "<a href='#' onclick='feedbackFun("+row.id+")'>反馈</a> ";

            var btn =  viewBtn + feeBtn +informBtn+departmentGradeBtn+expertGradeBtn1+viewExpertGradeBtn+summaryBtn+feedbackBtn;
            return btn;
        }}
    ]];

    var toolbar = []
 

	//表格数据初始化
	$('#grid').datagrid({
		url:baseUrl + '/mdtApply/findByPage',
		queryParams: {
        	applyStatus: 19,
        	share:'1',
        	orgLevel:1
        },
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
        content: 'mdtApplySummary.html?type=view&id=' + id
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
        content: 'mdtApplyDeptGrade.html?type=view&id=' + id
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
        content: 'mdtApplyDeptLuru.html?type=view&id=' + id
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
        content: 'mdtApplyFeedback.html?type=view&id=' + id
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
    formdata.applyStatus=19;
    formdata.share='1';
    formdata.orgLevel=1;
    $('#grid').datagrid('load',formdata);
}