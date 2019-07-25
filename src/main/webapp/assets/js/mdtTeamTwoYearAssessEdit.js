var teamId;
var type;  // 类型 区分 新增、编辑、审核等

$(function(){

    teamId = getQueryVariable("teamId");
    type = getQueryVariable("type");

    $("#teamId").val(teamId);

    if(teamId != undefined && teamId != null){
        initData(teamId);
        initTeam(teamId);
        initGrid1(teamId);
        initGrid2(teamId);
        initGrid20(teamId);
        
        var user = getUser();
        var myObject = {};
        myObject.auditName = user.name;
        myObject.createdTime = (new Date()).Format("yyyy-MM-dd hh:mm:ss");;
        $('#editForm2').form('load', myObject); 
    }
});


/**
 * 编辑
 */
function initTeam(id){
    $.ajax({
        url: baseUrl + '/mdtTeam/twoYearInfo.do?id='+id,
        dataType:'json',
        type:'post',
        success:function(value){
            if(value.type == 'success'){
            	var row = value.resultData.row
            	if(type != "view"){
            		  if (row.twoYearStatus == '1') {
                          $("#btn1").show();
                       }
                       if (row.twoYearStatus == '2') { 
                          $("#audit1").show();
                      	  $("#audit2").show();
                          $("#btn2").show();
                       }
            	}
                showLiuCheng(parseInt(row.twoYearStatus));        
                initTwoYear(row);
            }
        }
    });
}
/**
 * 初始化两年数据
 */
function initTwoYear(data){
	var yuedulist = data.yueDuPinGuList;
	if (parseInt(data.twoYearStatus) <= 1) {
		 var myObject = {};
		 var objlist = data.objList;
		 for(var i=0;i<objlist.length;i++){
			var oyear = objlist[i];
		    myObject["year"+(i+1)] = oyear.year; 		    
		    var casenumtemp = 0;
		    for(var j=0;j<12;j++){
		    	casenumtemp = casenumtemp+yuedulist[i*12+j].num;
		    }
	        myObject["caseNum"+(i+1)] = casenumtemp;
		 }
        $('#editForm').form('load', myObject); 
    }
	var datayuedu = [];
	for(var i=0;i<yuedulist.length;i++){
		var yuedu = yuedulist[i];
		var arr = [];
		arr.push(yuedu.year+"年"+yuedu.month+"月");
		arr.push(yuedu.num);
		datayuedu.push(arr);
	}
	
	Highcharts.chart('zhuxing', {
	    chart: {
	        type: 'column'
	    },
	    title: {
	        text: '月度完成情况'
	    },
	    subtitle: {
	        text: '月度完成情况'
	    },
	    xAxis: {
	        type: 'category',
	        labels: {
	            rotation: -45,
	            style: {
	                fontSize: '13px',
	                fontFamily: 'Verdana, sans-serif'
	            }
	        }
	    },
	    yAxis: {
	        min: 0,
	        title: {
	            text: '完成数'
	        }
	    },
	    legend: {
	        enabled: false
	    },
	    tooltip: {
	        pointFormat: '完成数'
	    },
	    series: [{
	        name: 'Population',
	        data: datayuedu,
	        dataLabels: {
	            enabled: true,
	            rotation: -90,
	            color: '#FFFFFF',
	            align: 'right',
	            format: '{point.y:.1f}', // one decimal
	            y: 10, // 10 pixels down from the top
	            style: {
	                fontSize: '13px',
	                fontFamily: 'Verdana, sans-serif'
	            }
	        }
	    }]
	});
}




function initGrid1(teamId) {

    var columns=[[
        /*{field:'id',title:'编号',width:100},*/
        {field:'title',title:'论文题目',width:100},
        {field:'serialNumber',title:'期刊号',width:200},
        {field:'postedDate',title:'发表时间',width:200},
        {field:'-',title:'操作',width:200,formatter:function(value,row,index) {
            return "<a href='#' onclick='delTeamPaper("+row.id+")'>删除</a>";
        }}
    ]];

    //表格数据初始化
    $('#grid1').datagrid({
        title:'建期两年MDT病种研究方向发表的论文',
        url:baseUrl + '/mdtTeam/selectTeamPaper?teamId=' + teamId,
        loadFilter: function(data){
            return data.resultData;
        },
        columns:columns,
        singleSelect:true,
        rownumbers:true,
        toolbar: [{
            iconCls: 'icon-add',
            text:'添加',
            handler: function(){

                layer.open({
                    type: 2,
                    title: '添加',
                    maxmin: true,
                    shadeClose: true, //点击遮罩关闭层
                    area : ['80%' , '80%'],
                    content: 'mdtTeamPaperEdit.html?teamId=' + teamId
                });
            }
        }]

    });
}


function initGrid2(teamId) {

    var columns=[[
        /*{field:'id',title:'编号',width:100},*/
        {field:'name',title:'项目名称',width:100},
        {field:'researchDate',title:'项目研究时间',width:200},
        {field:'projectFund',title:'项目经费',width:200},
        {field:'-',title:'操作',width:200,formatter:function(value,row,index) {
            return "<a href='#' onclick='delTeamIssue("+row.id+")'>删除</a>";
        }}
    ]];

    //表格数据初始化
    $('#grid2').datagrid({
        title:'建期两年MDT病种研究方向开展的课题探究',
        url:baseUrl + '/mdtTeam/selectTeamIssue?teamId=' + teamId,
        loadFilter: function(data){
            return data.resultData;
        },
        columns:columns,
        singleSelect:true,
        rownumbers:true,
        toolbar: [{
            iconCls: 'icon-add',
            text:'添加',
            handler: function(){

                layer.open({
                    type: 2,
                    title: '添加',
                    maxmin: true,
                    shadeClose: true, //点击遮罩关闭层
                    area : ['80%' , '80%'],
                    content: 'mdtTeamIssueEdit.html?teamId=' + teamId
                });
            }
        }]

    });
}


/**
 * 生成 意见列表
 * @param applyId
 */
function initGrid20(id) {

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
    $('#grid20').datagrid({
        title:'审批信息',
        url:baseUrl + '/lc/list.do?busitype=mdt_team_assess&bisiid=' + id,
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
        url: baseUrl + '/mdtTeam/saveTeamAssess',
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

/**
 * 编辑
 */
function initData(teamId){
    $.ajax({
        url: baseUrl + '/mdtTeam/getTeamAssess?teamId='+teamId,
        dataType:'json',
        type:'post',
        success:function(value){
            if(value.type == 'success'){
                if (value.resultData.row != null) {
                    $('#editForm').form('load', value.resultData.row);
                }
            }
        }
    });
}

function delTeamPaper(id) {
    $.messager.confirm('提示','确定要删除此记录吗？',function(r){
        if(r)
        {
            $.ajax({
                url: baseUrl + '/mdtTeam/delTeamPaper?id='+id,
                dataType:'json',
                success:function(value){
                    if(value.type == 'success'){
                        doSearch();
                    }
                }
            });
        }
    });
}

function delTeamIssue(id) {
    $.messager.confirm('提示','确定要删除此记录吗？',function(r){
        if(r)
        {
            $.ajax({
                url: baseUrl + '/mdtTeam/delTeamIssue?id='+id,
                dataType:'json',
                success:function(value){
                    if(value.type == 'success'){
                        doSearch();
                    }
                }
            });
        }
    });
}

function doSearch() {
    $('#grid1').datagrid('load');
    $('#grid2').datagrid('load');
}

function sauditSave() {
	 var formdata=getFormData('editForm'); 
	 var formdata2 = getFormData('editForm2');
	 formdata.yijian = JSON.stringify(formdata2);
    $.ajax({
        url: baseUrl + '/mdtTeam/auditTwoYearAssess',
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

/*
* 设置流程状态
*/
function showLiuCheng(auditStatus){
  var data = [{id:"0000",name:"开始"},{id:"0",name:"医务部发起"},{id:"1",name:"团队首席专家填写"},{id:"2",name:"医务部主任审核"},{id:"3",name:"结束"}];  
  var status = {"0000":"show"};  
  if(auditStatus == 4){
  	auditStatus = 1;
  }
  for(var i=0;i<=auditStatus;i++){
	    if(i == auditStatus){
	    	status[i+""] = "active";
	    }else{
	    	status[i+""] = "show";
	    }
  }
  var obj = new createLiucheng("liucheng",status);
  obj.data = data;
  obj.init();
}