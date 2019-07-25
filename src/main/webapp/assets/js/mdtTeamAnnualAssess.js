
$(function(){

    var columns=[[
        /*{field:'id',title:'编号',width:100},*/
        {field:'proposer',title:'申请人',width:100},
        {field:'name',title:'MDT名称',width:200},
        {field:'date',title:'申请日期',width:100},
        {field:'annualStatus',title:'年度评估状态',width:100,formatter:function(value,row,index) {
            if (row.annualStatus == '0') {
                return "未发起";
            } else if (row.annualStatus == '1') {
                return "已发起";
            } else if (row.annualStatus == '2') {
                return "已填写待审核";
            } else if (row.annualStatus == '3') {
                return "已审核";
            }
            return '';
        }},
        {field:'-',title:'操作',width:500,formatter:function(value,row,index) {
            var launchBtn = "<a href='#' onclick='launch("+row.id+")'>发起年度评估</a> ";
            var viewTeamBtn = "<a href='#' onclick='viewTeam("+row.id+")'>查看团队详情</a> ";
            var viewBtn = "<a href='#' onclick='view("+row.id+")'>查看</a> ";
            var editBtn = "<a href='#' onclick='edit("+row.id+")'>MDT团队首席专家填写</a> ";
            var auditBtn = "<a href='#' onclick='auditFun("+row.id+")'>审核</a> ";

            var roleIds = getUser().roleIds;

            var btn = viewBtn;

            // 医务部主任
            if (row.annualStatus == '0') {
                btn += launchBtn;
            }

         /*   // 专家
            if (roleIds.indexOf('6') != -1 && row.annualStatus != '0') {
                btn += editBtn;
            }*/

            // 医务部主任
/*            if (roleIds.indexOf('3') != -1 && row.annualStatus == '2') {
                btn += auditBtn;
            }*/

            return btn;
        }}
    ]];
	
	//表格数据初始化
	$('#grid').datagrid({
		url:baseUrl + '/mdtTeam/selectAnnualTeam',
        loadFilter: function(data){
            return data.resultData;
        },
		columns:columns,
		singleSelect:true,
		pagination:true,
		toolbar: []

	});
	
	//条件查询
	$('#btnSearch').bind('click',function(){
        doSearch();
	});

});

/**
 * 审核
 */
function auditFun(teamId){

    layer.open({
        type: 2,
        title: '审核',
        maxmin: true,
        shadeClose: true, //点击遮罩关闭层
        area : ['80%' , '80%'],
        content: 'mdtTeamAnnualAssessEdit.html?type=audit&teamId=' + teamId
    });
}

/**
 * 发起评估
 * @param teamId
 */
function launch(teamId) {
    layer.open({
        type: 2,
        title: 'MDT团队',
        maxmin: true,
        shadeClose: true, //点击遮罩关闭层
        area : ['80%' , '80%'],
        content: 'mdtTeamEdit.html?type=launch&id=' + teamId
    });
}

/**
 * 查看团队
 */
function viewTeam(teamId){
    layer.open({
        type: 2,
        title: 'MDT团队',
        maxmin: true,
        shadeClose: true, //点击遮罩关闭层
        area : ['80%' , '80%'],
        content: 'mdtTeamEdit.html?type=view&id=' + teamId
    });
}


/**
 * 编辑
 */
function view(id){
    layer.open({
        type: 2,
        title: 'MDT团队',
        maxmin: true,
        shadeClose: true, //点击遮罩关闭层
        area : ['80%' , '80%'],
        content: 'mdtTeamAnnualAssessEdit.html?type=view&teamId=' + id
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
        content: 'mdtTeamAnnualAssessEdit.html?type=edit&teamId=' + id
    });
}




function doSearch() {
    var formdata=getFormData('searchForm');
    $('#grid').datagrid('load',formdata);
}