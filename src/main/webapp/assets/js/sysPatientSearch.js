var patientType;
$(function() {
	patientType = getQueryVariable("patientType");
	initGrid1();
});


function initGrid1() {
	var col = [
	           {field:'patientType',title:'患者类型',width:80,formatter:function(value,row,index) {
	           	if (row.patientType == '1') {
	           		return "住院";
	   			} else if (row.patientType == '2') {
	   				return "门诊";
	   			}
	               return "";
	           }},
	           {field:'name',title:'姓名',width:80},
	           {field:'medicalNo',title:'病历号(门诊)',width:100},
	           {field:'inHospitalNo',title:'住院号(住院)',width:100},   
	           {field:'birthday',title:'出生日期',width:100},
	           {field:'gender',title:'性别',width:40},
	           {field:'idcard',title:'身份证号',width:150}
	       ];
	//住院病人
	if(patientType == 1){
		col.push({field:'inHospitalDate',title:'住院日期',width:100});
		col.push({field:'outHospitalDate',title:'出院日期',width:100});	
	}else{
		col.push({field:'medicalDate',title:'创建时间',width:100});
	}
	
	col.push({field:'-',title:'操作',width:100,formatter:function(value,row,index) {
        return "<a href='#' onclick='choose("+row.id+")'>选取</a>";
    }});
	
    var columns=[];
	columns.push(col)
    var formdata=getFormData('searchForm');
    formdata.patientType = patientType;
    //表格数据初始化
    $('#grid1').datagrid({
        title:'患者选择',
        url:baseUrl + '/patient/list',
        queryParams: formdata,
        loadFilter: function(data){
            return data.resultData;
        },
        columns:columns,
        singleSelect:true,
        rownumbers:true
    });
}

function doSearch() {
    var formdata=getFormData('searchForm');
    formdata.patientType = patientType;
    //门诊
    if(formdata.patientType == 2){
    	formdata.medicalNo = formdata.number
    }else{
    	formdata.inHospitalNo = formdata.number
    }
    
    $('#grid1').datagrid('load',formdata);
}

/**
 * 选择
 */
function choose(teamInfoId){
	parent.getPatient(teamInfoId);
	var mylay = parent.layer.getFrameIndex(window.name);
    parent.layer.close(mylay);
}