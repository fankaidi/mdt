var id;
var type;  // 类型 区分 新增、编辑、审核等

$(function(){

    id = getQueryVariable("id");
    type = getQueryVariable("type");

    if(id != undefined && id != null){
        // 初始化数据
        initData(id);
        initGrid2(id);
        // 获取第一个设置的MDT团队目标
        getFirstByTeamId(id);

        // 初始化团队明细列表
        initGrid1(id);
    } else {
        getMdtTeamKey();
        initUser();
    }


    if(type != undefined && type != null){
        if (type == 'add') {
            $("#btn1").show();
            $("#btn2").show();
        }
        if (type == 'edit') {
            $("#btn1").show();
        }
        if (type == 'launch') {
            $("#btn5").show();
        }
        if (type == 'launch2') {
            $("#btn6").show();
        }
    }

    $('#date').datebox({
        onSelect: function(date){
            setDate(date);
        }
    });
});

function initGrid1(teamId) {

    var toolbar ;
    if (type == 'edit' || type == 'add') {
        toolbar = [{
            iconCls: 'icon-add',
            text:'添加专家',
            handler: function(){
                addTeamInfo(id)
            }
        }];
    } else {
        toolbar = [];
    }

    var columns=[[
		/*{field:'id',title:'编号',width:100},*/
        {field:'name',title:'专家姓名',width:100},
        {field:'department',title:'科室',width:200},
        {field:'title',title:'职称',width:200},
        {field:'phone',title:'联系方式',width:200},
        {field:'specialistType',title:'专家类型',width:200,formatter:function(value,row,index) {
            if (row.specialistType == '1') {
                return "首席专家";
            } else if (row.specialistType == '2') {
                return "团队副组长";
            } else if (row.specialistType == '3') {
                return "团队秘书";
            } else if (row.specialistType == '4') {
                return "专家";
            }
            return '';
        }},
        {field:'-',title:'操作',width:200,hidden: (type != 'add' && type != 'edit'),formatter:function(value,row,index) {
            return "<a href='#' onclick='editTeamInfo("+row.id+")'>编辑</a>&nbsp;&nbsp;&nbsp;" +
                    "<a href='#' onclick='delTeamInfo("+row.id+")'>删除</a>";
        }}
    ]];

    //表格数据初始化
    $('#grid1').datagrid({
        title:'MDT团队基本信息（多人明细）',
        url:baseUrl + '/mdtTeam/findTeamInfoByTeamId?teamId=' + teamId,
        loadFilter: function(data){
            return data.resultData;
        },
        columns:columns,
        singleSelect:true,
        rownumbers:true,
        toolbar: toolbar
    });
}

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
        url:baseUrl + '/lc/list.do?busitype=mdt_team&bisiid=' + id,
        loadFilter: function(data){
            return data.resultData;
        },
        columns:columns,
        singleSelect:true,
        rownumbers:true,
        toolbar: toolbar

    });
}



// 获取主键
function getMdtTeamKey() {
    $.ajax({
        url: baseUrl + '/mdtTeam/getMdtTeamKey',
        data:{},
        dataType:'json',
        type:'post',
        success:function(value){
            if(value.type == 'success'){
                id = value.resultData.row;
                $("#id").val(id);
                initGrid1(id);
            }
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



//进入页面初始化数据
function initUser() {
	  var user = getUser();
      var myObject = {};
      myObject.proposer = user.name;
      var nowdata = new Date();
      myObject.date = nowdata.Format("yyyy-MM-dd hh:mm:ss");
      $('#editForm').form('load', myObject);   
      setDate(nowdata);
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
        url: baseUrl + '/mdtTeam/save',
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

function auditSave() {
    //判断：编辑表单的所有控件是否都通过验证
    var isValidate= $('#editForm2').form('validate');
    if(isValidate==false){
        return ;
    }
    var formdata = getFormData('editForm2');
    formdata.yijian = JSON.stringify(formdata);
    
    $.ajax({
        url: baseUrl + '/mdtTeam/audit',
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

function commintAudit() {
    $("#auditStatus").val('1');
    save();
}

/**
 * 编辑
 */
function initData(id){
    $.ajax({
        url: baseUrl + '/mdtTeam/get?id='+id,
        dataType:'json',
        type:'post',
        success:function(value){
            if(value.type == 'success'){
            	var row = value.resultData.row;
                $('#editForm').form('load', row);
                setDate(new Date(row.date));

                $("#id2").val(row.id);
                showLiuCheng(parseInt(row.auditStatus));
                
                if (type == 'audit' || type == 'edit') {
                	if(row.auditStatus == '0' || row.auditStatus == '9'){
                		  $("#btn1").show()
                		  $("#btn2").show();
                	}else{
                		 $("#audit1").show();
	                   	 $("#audit2").show();
	                   	 $("#btn4").show();
	                   	var user = getUser();
	                    var myObject = {};
	                    myObject.auditName = user.name;
	                    myObject.createdTime = (new Date()).Format("yyyy-MM-dd hh:mm:ss");;
	                    $('#editForm2').form('load', myObject);     
                	} 
                }              
            }
        }
    });
}
/*
* 设置流程状态
*/
function showLiuCheng(auditStatus){
  var data = [{id:"0000",name:"开始"},{id:"0",name:"申请人申请"},{id:"1",name:"科主任审核"},{id:"2",name:"医务部主任审核"},{id:"3",name:"分管院长审核"},{id:"4",name:"结束"}];  
  var status = {"0000":"show"};  
  if(auditStatus == 9){
  	auditStatus = 0;
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

/**
 * 获取第一个设置的MDT团队目标
 */
function getFirstByTeamId(teamId){
    $.ajax({
        url: baseUrl + '/mdtTeam/getFirstByTeamId?teamId='+teamId,
        dataType:'json',
        type:'post',
        success:function(value){
            if(value.type == 'success'){
                $('#editForm').form('load', value.resultData.row);
                $("#id").val(id); // 防止id被重置
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


function addTeamInfo(teamId) {
    if (!teamId) {
        $.messager.alert('提示', '请先保存MDT团队申请');
        return;
    }

    layer.open({
        type: 2,
        title: 'MDT团队基本信息（多人明细）',
        maxmin: true,
        shadeClose: true, //点击遮罩关闭层
        area : ['80%' , '80%'],
        content: 'mdtTeamInfoEdit.html?teamId=' + teamId
    });
}

function editTeamInfo(id) {
    layer.open({
        type: 2,
        title: 'MDT团队基本信息（多人明细）',
        maxmin: true,
        shadeClose: true, //点击遮罩关闭层
        area : ['80%' , '80%'],
        content: 'mdtTeamInfoEdit.html?id=' + id
    });
}

// 删除 MDT团队基本信息（多人明细）
function delTeamInfo(id) {

    $.messager.confirm('提示','确定要删除此记录吗？',function(r){
        if(r)
        {
            $.ajax({
                url: baseUrl + '/mdtTeam/delTeamInfo?id='+id,
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
    $('#grid1').datagrid('reload');
}


function launch() {
    $.ajax({
        url: baseUrl + '/mdtTeam/launchAnnualAssess?teamId='+$("#id").val(),
        dataType:'json',
        type:'post',
        success:function(value){
            $.messager.alert('提示',value.message);
            if(value.type == 'success'){
                var mylay = parent.layer.getFrameIndex(window.name);
                parent.layer.close(mylay);

                window.parent.doSearch();
            }
        }
    });
}

function launch2() {
    $.ajax({
        url: baseUrl + '/mdtTeam/launchTwoYearAssess?teamId='+$("#id").val(),
        dataType:'json',
        type:'post',
        success:function(value){
            $.messager.alert('提示',value.message);
            if(value.type == 'success'){
                var mylay = parent.layer.getFrameIndex(window.name);
                parent.layer.close(mylay);

                window.parent.doSearch();
            }
        }
    });
}