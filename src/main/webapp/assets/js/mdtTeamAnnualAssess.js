
$(function(){

    var columns=[[
        {field:'applyPerson',title:'申请人',width:100},
        {field:'name',title:'MDT名称',width:600},
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
        	 var launchBtn = "<input type='button' onclick='launch("+row.id+")' class='self-btn' value='发起年度评估'/>";
             var viewTeamBtn = "<input type='button' onclick='viewTeam("+row.id+")' class='self-btn' value='查看团队详情'/>";
             var editBtn = "<input type='button' onclick='edit("+row.id+")' class='self-btn' value='MDT团队首席专家填写'/>";
             var auditBtn = "<input type='button' onclick='auditFun("+row.id+")' class='self-btn' value='审核'/>";
            var btn = '';

            // 医务部主任
            if (row.annualStatus == '0') {
                btn += launchBtn;
            }

            return btn;
        }}
    ]];
	
	//表格数据初始化
	$('#grid').datagrid({
		url:baseUrl + '/mdtTeam/selectAnnualTeam',
        loadFilter: function(data){
            return data.resultData;
        },
        onDblClickRow:function(rowIndex,rowData){
			view(rowData.id);
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