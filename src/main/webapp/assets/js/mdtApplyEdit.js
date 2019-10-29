var id;
var type;  // 类型 区分 新增、编辑、审核等

$(function(){
    id = getQueryVariable("id");
    type = getQueryVariable("type");

    if(id != undefined && id != null){
        initData(id);
        initGrid1(id);
        initGrid2(id);
    } else {
        getMdtApplyKey();
        getMdtPurpose();
        //初始化值
        createVal();
    }

    if(type != undefined && type != null){
        if (type == 'add') {
            $("#btn1").show();
            $("#btn2").show();
        }
        if (type == 'edit') {
            $("#btn1").show();
        }
     }
});

// 选择患者
function choosePatient() {
	var formdata=getFormData('editForm');
    layer.open({
        type: 2,
        title: '患者选择',
        maxmin: true,
        shadeClose: true, //点击遮罩关闭层
        area : ['95%' , '95%'],
        content: 'sysPatientSearch.html?patientType='+formdata.patientType
    });
}

//医生选择
function chooseUser() {
	var tjuserid =  $("#tjuserid").val();
	if(!tjuserid){
		tjuserid = "";
	}
    layer.open({
        type: 2,
        title: '医生选择',
        maxmin: true,
        shadeClose: true, //点击遮罩关闭层
        area : ['95%' , '95%'],
        content: 'sysUserSearch.html?id='+tjuserid
    });
}

function getPatient(id) {
    $.ajax({
        url: baseUrl + '/patient/get?id='+id,
        dataType:'json',
        type:'post',
        success:function(value){
            if(value.type == 'success'){
                setTeamInfoFrom(value.resultData.row);
            } else {
                $.messager.alert('提示',value.message);
            }
        }
    });
}

//设置患者基本属性
function setTeamInfoFrom(user) {
    var myObject = {};
    myObject.userId = user.id;
    myObject.name = user.name;
    myObject.gender = user.gender;
    myObject.birthday = user.birthday;
    myObject.age = user.age;
    myObject.phone = user.phone;
    myObject.patientId = user.id; 
    myObject.idcard = user.idcard; 
    
    if(user.patientType == '2'){
    	 myObject.number = user.medicalNo; 
    	 myObject.diagnoseDate = user.medicalDate;
    }else{
    	 myObject.number = user.inHospitalNo; 
    	 myObject.diagnoseDate = user.inHospitalDate; 
    }
    myObject.overview = "病史："+(user.medicalHistory?user.medicalHistory:"") +"\n体检："+(user.medicalExam?user.medicalExam:"")+"\n处理："+(user.dispose?user.dispose:"")+"\n初步诊断："+(user.primaryDiagnosis?user.primaryDiagnosis:""); 

    $('#editForm').form('load', myObject);
}


function getSearchUser(userm) {	
	var idarr = [];
	var namearr = [];
	for(var key in userm){
		var dobj = userm[key];
		idarr.push(dobj.id);
		namearr.push(dobj.name)
	}
	var myObject = {};
	myObject.tjuserid = idarr.join(",");
	myObject.tjusername = namearr.join(",");
	$('#editForm').form('load', myObject);
}

//设置当前用户的值
function createVal() {
    $.ajax({
        url: baseUrl + '/auth/getUser',
        dataType:'json',
        type:'post',
        success:function(value){
            if(value.type == 'success'){
                var user = value.resultData.row;
                var myObject = {};
                myObject.applyPerson = user.name;
                myObject.applyPersonId = user.id;
                myObject.applyPhone = user.phone;
                myObject.gender = user.gender;
                myObject.patientType = '2';
                myObject.isCharge = '1';
                myObject.applyCreatetime = (new Date()).Format("yyyy-MM-dd hh:mm:ss");
                $('#editForm').form('load', myObject);               
            } else {
                $.messager.alert('提示',value.message);
            }
        }
    });
}

// 获取MDT目的的多选框值
function getMdtPurpose() {
    $.ajax({
        url: baseUrl + '/set/getCodeByType?type=1',
        data:{},
        dataType:'json',
        type:'post',
        success:function(value){
            if(value.type == 'success'){
                var data = value.resultData.rows;
                var box = '';
                for (var i=0; i<data.length; i++) {
                    if ($("#mdtPurpose").val().indexOf(data[i].code) != -1) {
                        box += '<br><input type="checkbox" name="mdtPurposeBox" onclick="changePurposeBox()" checked value="'+ data[i].code +'">' + data[i].value;
                    } else {
                        box += '<br><input type="checkbox" name="mdtPurposeBox" onclick="changePurposeBox()" value="'+ data[i].code +'">' + data[i].value;
                    }
                    if((data.length-1) != i){
                    	box +="<br>";
                    }
                }
                $("#mdtPurposeSpan").html(box);
            }
        }
    });
}

function changePurposeBox() {
    var val = '';
    $('input[name="mdtPurposeBox"]:checked').each(function(){
        val += $(this).val() + ',';
    });
    $("#mdtPurpose").val(val);
}

/**
 * 生成 MDT拟请专家列表
 * @param applyId
 */
function initGrid1(applyId) {
    var columns=[[
        {field:'name',title:'专家名称',width:100},
        {field:'teamName',title:'MDT团队',width:200},
        {field:'department',title:'科室',width:200},
        {field:'title',title:'职称',width:200},
        {field:'phone',title:'联系方式',width:200},
        {field:'phoneCornet',title:'手机短号',width:200},
        {field:'-',title:'操作',width:200,hidden: (type != 'add' && type != 'edit'),formatter:function(value,row,index) {
            return "<a href='#' onclick='delApplyDoctor("+row.id+")'>删除</a>";
        }}
    ]];

    var toolbar;
    if (type == 'edit' || type == 'add') {
        toolbar = [
            {
	            iconCls: 'icon-add',
	            text:'添加MDT团队专家',
	            handler: function(){
	            	changeDiseaseName();
	            }
            },
        	{
	            iconCls: 'icon-add',
	            text:'添加非团队专家',
	            handler: function(){
	                addTeamInfo();
	            }
            }]
    } else {
        toolbar = [];
    }

    //表格数据初始化
    $('#grid1').datagrid({
        title:'拟请MDT参加专家',
        url:baseUrl + '/mdtApply/listApplyDoctorByApplyId?applyId=' + applyId,
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
function initGrid2(applyId) {
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
        url:baseUrl + '/lc/list.do?busitype=mdt_apply&bisiid=' + applyId,
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
function getMdtApplyKey() {
    $.ajax({
        url: baseUrl + '/mdtApply/getMdtApplyKey',
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

function addTeamInfo() {
    if (!$("#id").val()) {
        $.messager.alert('提示', '请先保存MDT申请');
        return;
    }
    layer.open({
        type: 2,
        title: '添加专家',
        maxmin: true,
        shadeClose: true, //点击遮罩关闭层
        area : ['95%' , '95%'],
        content: 'mdtApplyDoctorEdit.html?applyId=' + id
    });
}

//保存
function save(){
    var formdata=getFormData('editForm');
    if(!formdata.name){
    	 $.messager.alert('提示','患者姓名必须填写');
    	 return;
    }
    if(!formdata.dezl){
   	 $.messager.alert('提示','第二诊疗必须选择');
   	 return;
    }
    if(!formdata.mdtDate){
   	 $.messager.alert('提示','MDT时间必须填写');
   	 return;
    }
    if(!formdata.mdtLocation){
      	 $.messager.alert('提示','MDT地点必须填写');
      	 return;
      }
    if(!formdata.mdtPurpose){
     	 $.messager.alert('提示','MDT目的必须填写');
     	 return;
     }
    
    
    $.ajax({
        url: baseUrl + '/mdtApply/save',
        data:formdata,
        dataType:'json',
        type:'post',
        success:function(value){
            if(value.type == 'success'){
            	 $.messager.alert('成功','保存成功');
                var mylay = parent.layer.getFrameIndex(window.name);
                parent.layer.close(mylay);

                window.parent.doSearch();
            } else {

                $.messager.alert('提示',value.message);
            }
        }
    });
}


/*
* 设置流程状态
*/
function showLiuCheng(apply){
	var auditStatus = apply.applyStatus;
	
    var data = [{id:"0000",name:"开始"},{id:"0",name:"申请人申请"},{id:"1",name:"科主任审核"},{id:"2",name:"医务部主任审核"}
    ,{id:"11",name:"申请人流转",child:[{id:"isJiaofei1",name:"缴费单"},{id:"isZhiqing1",name:"知情同意"},{id:"isDuanxin1",name:"发送短信"}]}
    ,{id:"13",name:"MDT会诊",child:[{id:"isKsdafen1",name:"会诊意见书"},{id:"isXiaojie1",name:"科室小结"},{id:"isZjdafen1",name:"专家打分"}]}
    ,{id:"15",name:"随访"},{id:"19",name:"结束"}];
  
    if(apply.patientType == '2'){
    	 data.splice(2, 2);
    }
    
    if(auditStatus>=1 && auditStatus<=3){
  	  var ad = data[(auditStatus+1)+""];
  	  $.ajax({
            url: baseUrl + '/daiban/search.do?busitype=mdt_apply&busiid='+id,
  			dataType:'json',
  			async: false,
  			success:function(value){
                if(value.type == 'success'){
              	  var row = value.resultData.row
              	  if(row != null){
              		  ad.name += "-"+row.user.name;
              	  }
              	
                }
  			}
  		});	  
    }
   
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
    status["isJiaofei"+apply.isJiaofei] = "show";
    status["isZhiqing"+apply.isZhiqing] = "show";
    status["isDuanxin"+apply.isDuanxin] = "show";
    status["isKsdafen"+apply.isKsdafen] = "show";
    status["isZjdafen"+apply.isZjdafen] = "show";
    status["isXiaojie"+apply.isXiaojie] = "show";   
    
    var obj = new createLiucheng("liucheng",status);
    obj.data = data;
    obj.init();
}

function commintAudit() {
    $("#applyStatus").val(1);

    save();
}

/**
 * 删除MDT拟请专家
 */
function delApplyDoctor(id){
	
	$.messager.confirm('提示','确定要删除此记录吗？',function(r){
		if(r)
		{
			$.ajax({
                url: baseUrl + '/mdtApply/delApplyDoctorById?id='+id,
				dataType:'json',
				success:function(value){
                    if(value.type == 'success'){
                        doSearch();
					} else {
                        $.messager.alert('提示',value.message);
                    }
				}
			});		
		}	
	});	
}

/**
 * 编辑
 */
function initData(id){
    $.ajax({
        url: baseUrl + '/mdtApply/get?id='+id,
        dataType:'json',
        type:'post',
        success:function(value){
            if(value.type == 'success'){
                var data = value.resultData.row;
                $('#editForm').form('load', data);

                if (type == 'edit' || type == 'audit') {
                	  if (data.applyStatus == 0 || data.applyStatus == 9 ) {
                          $("#btn2").show();
                      }else if (data.applyStatus == 1 || data.applyStatus == 2) {
                		  $("#audit1").show();
                          $("#audit2").show();
                          $("#btn4").show();
                          var user = getUser();
                          var myObject = {};
                          myObject.auditName = user.name;
                          myObject.auditResult = 1;
                          myObject.createdTime = (new Date()).Format("yyyy-MM-dd hh:mm:ss");;
                          $('#editForm2').form('load', myObject);   
                	  }
                   
                }
                $("#id").val(data.id);
                getMdtPurpose();
                showLiuCheng(data);
            }
        }
    });
}


function changeDiseaseName() {
    if (!$("#id").val()) {
        $.messager.alert('提示', '请先保存MDT申请');
        return;
    }

    layer.open({
        type: 2,
        title: 'MDT团队库',
        maxmin: true,
        shadeClose: true, //点击遮罩关闭层
        area : ['95%' , '95%'],
        content: 'mdtApplyTeam.html?applyId=' + id
    });
}

function doSearch() {
    $('#grid1').datagrid('reload');
}

function setCeatedDeptId(createdDeptid) {
	var myObject = {createdDeptid:createdDeptid};
	$('#editForm').form('load', myObject);
	doSearch();
}

function auditSave() {

    //判断：编辑表单的所有控件是否都通过验证
    var isValidate= $('#editForm2').form('validate');
    if(isValidate==false){
        return ;
    }
    var formdata = getFormData('editForm2');
    formdata.id = id;
    formdata.yijian = JSON.stringify(formdata);

    $.ajax({
        url: baseUrl + '/mdtApply/audit',
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