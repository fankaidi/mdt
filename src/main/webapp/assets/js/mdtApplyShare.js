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
        	var feeBtn = "<input type='button' onclick='feeFun("+row.id+")' class='self-btn' value='缴费单'/>";     
            var informBtn = "<input type='button' onclick='informFun("+row.id+")' class='self-btn' value='知情同意'/>";
            var departmentGradeBtn = "<input type='button' onclick='departmentGradeFun("+row.id+")' class='self-btn' value='会诊意见书'/>";
            var summaryBtn = "<input type='button' onclick='summaryFun("+row.id+")' class='self-btn' value='科室小结'/>";
           // var expertGradeBtn = "<input type='button' onclick='expertGradeFun("+row.id+")' class='self-btn' value='专家打分'/>";
            //专家打分录入
            var expertGradeBtn1 = "<input type='button' onclick='departmentGradeFun1("+row.id+")' class='self-btn' value='专家打分'/>";
            var viewExpertGradeBtn = "<input type='button' onclick='viewExpertGradeFun("+row.id+")' class='self-btn' value='专家意见汇总'/>";      
            var feedbackBtn = "<input type='button' onclick='feedbackFun("+row.id+")' class='self-btn' value='随访'/>";

            var btn =  feeBtn +informBtn+departmentGradeBtn+expertGradeBtn1+viewExpertGradeBtn+summaryBtn+feedbackBtn;
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
        onDblClickRow:function(rowIndex,rowData){
			view(rowData.id);
		},
        loadFilter: function(data){
        	if(!data.resultData.rows){
        		data.resultData.rows = [];
        	}
        	
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