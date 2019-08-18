
$(function(){

    var columns=[[
        {field:'applyPerson',title:'申请人',width:100},
        {field:'name',title:'MDT名称',width:200},
        {field:'date',title:'申请日期',width:100},
        {field:'twoYearStatus',title:'评估状态',width:100,formatter:function(value,row,index) {
            if (row.twoYearStatus == '0') {
                return "未发起";
            } else if (row.twoYearStatus == '1') {
                return "已发起";
            } else if (row.twoYearStatus == '2') {
                return "已填写待审核";
            } else if (row.twoYearStatus == '3') {
                return "已审核";
            } else if (row.twoYearStatus == '4') {
                return "审核不通过";
            }
            return '';
        }},
        {field:'-',title:'操作',width:500,formatter:function(value,row,index) {
            
	       	 var launchBtn = "<input type='button' onclick='launch("+row.id+")' class='self-btn' value='发起满2年度评估'/>";
	         var editBtn = "<input type='button' onclick='edit("+row.id+")' class='self-btn' value='MDT团队首席专家填写'/>";
	         var auditBtn = "<input type='button' onclick='auditFun("+row.id+")' class='self-btn' value='审核'/>";
            
            var btn = '';

            // 医务部主任
            if (row.twoYearStatus == '0') {
                btn += launchBtn;
            }

          /*  // 专家
            if (roleIds.indexOf('6') != -1 && row.twoYearStatus != '0') {
                btn += editBtn;
            }

            // 医务部主任
            if (roleIds.indexOf('3') != -1 && row.twoYearStatus == '2') {
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
        onDblClickRow:function(rowIndex,rowData){
			view(rowData.id);
		},
		columns:columns,
		singleSelect:true,
		pagination:true
	});
	
	//条件查询
	$('#btnSearch').bind('click',function(){
        doSearch();
	});

});


function launch(teamId) {
    layer.open({
        type: 2,
        title: 'MDT团队',
        maxmin: true,
        shadeClose: true, //点击遮罩关闭层
        area : ['80%' , '80%'],
        content: 'mdtTeamEdit.html?type=launch2&id=' + teamId
    });
}


/**
 * 编辑
 */
function view(id){
    layer.open({
        type: 2,
        title: 'MDT团队建设期满2年评估',
        maxmin: true,
        shadeClose: true, //点击遮罩关闭层
        area : ['80%' , '80%'],
        content: 'mdtTeamTwoYearAssessEdit.html?type=view&teamId=' + id
    });
}

/**
 * 编辑
 */
function edit(id){
    layer.open({
        type: 2,
        title: 'MDT团队建设期满2年评估',
        maxmin: true,
        shadeClose: true, //点击遮罩关闭层
        area : ['80%' , '80%'],
        content: 'mdtTeamTwoYearAssessEdit.html?type=edit&teamId=' + id
    });
}

/**
 * 审核
 */
function auditFun(id){
    layer.open({
        type: 2,
        title: 'MDT团队建设期满2年评估',
        maxmin: true,
        shadeClose: true, //点击遮罩关闭层
        area : ['80%' , '80%'],
        content: 'mdtTeamTwoYearAssessEdit.html?type=audit&teamId=' + id
    });
}

function doSearch() {
    var formdata=getFormData('searchForm');
    $('#grid').datagrid('load',formdata);
}