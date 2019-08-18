
$(function() {
	initGrid1();
});

function initGrid1() {
	 var columns=[[
	               {field:'busitypeStr',title:'类型',width:"50%"},
	               {field:'createdTimeStr',title:'申请时间',width:"30%"},
	               {field:'applyPersonId',title:'申请人',width:"20%",formatter:function(value,row,index) {
	                   return row.user.name;
	               }}
	           ]];


   //表格数据初始化
   $('#grid1').datagrid({
       url:baseUrl + '/daiban/list.do',
       loadFilter: function(data){
	       if(data.resultData.rows == null){
	       		data.resultData.rows = [];
	       }
           return data.resultData;
       },
       onDblClickRow:function(rowIndex,rowData){
       	edit(rowData.busitype,rowData.bisiid);
		},
       columns:columns,
       singleSelect:true,
       pagination:true

   });
}

function doSearch() {
    var formdata=getFormData('searchForm');
    $('#grid1').datagrid('load',formdata);
}


function edit(type, id) {
    if (type == 'mdt_team') {
        layerOpen('MDT团队审核','mdtTeamEdit.html?type=audit&id=' + id)
    }
    if (type == 'mdt_apply') {
    	layerOpen('MDT申请审核', 'mdtApplyEdit.html?type=audit&id=' + id);
    }
    if (type == 'mdt_team_objective') {
        layerOpen('MDT团队年度评估', 'mdtTeamAnnualAssessEdit.html?type=audit&teamId=' + id);
    }
    if (type == 'mdt_team_assess') {
        layerOpen('MDT团队满两年评估', 'mdtTeamTwoYearAssessEdit.html?type=audit&teamId=' + id);
    }
}