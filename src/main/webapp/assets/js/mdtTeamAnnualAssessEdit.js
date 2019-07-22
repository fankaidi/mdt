var teamId;
var type;  // 类型 区分 新增、编辑、审核等

$(function(){

    teamId = getQueryVariable("teamId");
    type = getQueryVariable("type");

    $("#teamId").val(teamId);

    if(teamId != undefined && teamId != null){
        initData(teamId);
        initData2(teamId);
        initGrid2(teamId);
        
        var user = getUser();
        var myObject = {};
        myObject.auditName = user.name;
        myObject.createdTime = (new Date()).Format("yyyy-MM-dd hh:mm:ss");;
        $('#editForm2').form('load', myObject);     
    }
});



/**
 * 生成 意见列表
 * @param applyId
 */
function initGrid2(id) {

    var columns=[[
        {field:'entryName',title:'审批步骤',width:100},
        {field:'userid',title:'审批人',width:200,formatter:function(value,row,index) {
            return row.user.name;
        }},
        {field:'auditResult',title:'审批结果',width:200,formatter:function(value,row,index) {
            return row.auditResult == 1?"通过":"不通过";
        }},
        {field:'auditOpinion',title:'审批意见',width:200},
        {field:'createdTimeStr',title:'审批时间',width:200}
    ]];

    var toolbar = [];

    //表格数据初始化
    $('#grid2').datagrid({
        title:'审批信息',
        url:baseUrl + '/lc/list.do?busitype=mdt_team_objective&bisiid=' + id,
        loadFilter: function(data){
            return data.resultData;
        },
        columns:columns,
        singleSelect:true,
        rownumbers:true,
        toolbar: toolbar

    });
}

//保存
function save() {

    //判断：编辑表单的所有控件是否都通过验证
    var isValidate= $('#editForm').form('validate');
    if(isValidate==false){
        return ;
    }

    var formdata=getFormData('editForm');

    $.ajax({
        url: baseUrl + '/mdtTeam/saveTeamObjective',
        data:formdata,
        dataType:'json',
        type:'post',
        success:function(value){
            if(value.type == 'success'){
                var mylay = parent.layer.getFrameIndex(window.name);
                parent.layer.close(mylay);
                window.parent.doSearch();
            }
            $.messager.alert('提示',value.message);
        }
    });
}


//合计yearSum
function sumyue() {
    //判断：编辑表单的所有控件是否都通过验证
	var yearsum = 0;
	for(var i=1;i<=12;i++){
		var yueval = $('#month'+i).val();
		if(yueval){
			yearsum = yearsum+parseInt(yueval);
		}
	}
	 var myObject = {};
     myObject.yearSum = yearsum;
     $('#editForm').form('load', myObject);         
}
/**
 * 编辑
 */
function initData(teamId){
    $.ajax({
        url: baseUrl + '/mdtTeam/getTeamObjective?teamId='+teamId,
        dataType:'json',
        type:'post',
        success:function(value){
            if(value.type == 'success'){
                if (value.resultData.rows != null) {
                	var rows = value.resultData.rows;
                	if(rows.length > 1){
                		var row = rows[1];
                		$('#editForm').form('load', row);
                	}else{
                		var row = rows[0];
                		var year = parseInt(row.year)+1;
                		var myobj = {year:year};
                		$('#editForm').form('load', myobj);
                	} 
                }
            }
        }
    });
}

/**
 * 编辑2
 */
function initData2(teamId){
    $.ajax({
        url: baseUrl + '/mdtTeam/get?id='+teamId,
        dataType:'json',
        type:'post',
        success:function(value){
            if(value.type == 'success'){
            	var row = value.resultData.row;		
                setDate(new Date(row.date));
            	if (row.annualStatus == '1' || row.annualStatus == '3') {
                    $("#btn1").show();
                }
                if (row.annualStatus == '2') {
                	$("#audit1").show();
                	$("#audit2").show();
                    $("#btn2").show();
                }
            }
        }
    });
}

// 从申请日期的下个月开始，填写12个月的月份
function setDate(date) {
    var month = date.getMonth()+1;
    for (var i = 1; i <= 12 ; i++) {
        if (month == 12) {
            month = 0;
        }
        $('#m' + i).textbox('setText', month + 1 + "月");
        month++;
    }
}

function auditSave() {
	 var formdata=getFormData('editForm');
	 
	 var formdata2 = getFormData('editForm2');
	 formdata.yijian = JSON.stringify(formdata2);
	 
    $.ajax({
        url: baseUrl + '/mdtTeam/auditAnnualAssess' ,
        data:formdata,
        dataType:'json',
        type:'post',
        success:function(value){
        	 if(value.type == 'success'){
                 var mylay = parent.layer.getFrameIndex(window.name);
                 parent.layer.close(mylay);
                 window.parent.doSearch();
             }
             $.messager.alert('提示',value.message);
        }
    });
}