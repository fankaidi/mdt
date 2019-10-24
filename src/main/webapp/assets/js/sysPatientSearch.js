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
	           {field:'name',title:'姓名',width:80}         
	       ];
	//住院病人
	if(patientType == 1){
		col.push({field:'inHospitalNo',title:'住院号(住院)',width:100});
		col.push({field:'birthday',title:'出生日期',width:100});
		col.push({field:'gender',title:'性别',width:40});
		col.push({field:'idcard',title:'身份证号',width:150});
		col.push({field:'inHospitalDate',title:'住院日期',width:100});
		col.push({field:'outHospitalDate',title:'出院日期',width:100});	
		col.push({field:'xzz',title:'地址',width:100});	
		col.push({field:'-',title:'操作',width:100,formatter:function(value,row,index) {
	        return "<a href='#' onclick='choose("+row.id+")'>选取</a>";
	    }});
	}else{
		col.push({field:'medicalNo',title:'病历号(门诊)',width:100});
		col.push({field:'birthday',title:'出生日期',width:100});
		col.push({field:'gender',title:'性别',width:40});
		col.push({field:'idcard',title:'身份证号',width:150});
		col.push({field:'yyDate',title:'预约日期',width:100});
		col.push({field:'yyks',title:'预约科室',width:150});
		col.push({field:'xzz',title:'地址',width:100});	
		col.push({field:'-',title:'操作',width:100,formatter:function(value,row,index) {
	        return "<a href='#' onclick='choose("+row.id+")'>选取</a>  &nbsp;&nbsp;&nbsp;&nbsp;<a href='#' onclick='hulue("+row.id+")'>忽略</a>";
	    }});
	}
	
	
	
    var columns=[];
	columns.push(col)
    var formdata=getFormData('searchForm');
    formdata.patientType = patientType;
    formdata.syncData = 1;
    if(formdata.patientType == 2){
        setCreatedDate(formdata);
    }
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

function setCreatedDate(formdata){
	formdata.startCreatedTime = add7Date();
}

function add7Date() {
    var date = new Date();
    date.setDate(date.getDate() - 8);
    return date.Format("yyyy-MM-dd hh:mm:ss");
}


function doSearch() {
    var formdata=getFormData('searchForm');
    formdata.patientType = patientType;
    //门诊
    if(formdata.patientType == 2){
    	formdata.medicalNo = formdata.number;
        setCreatedDate(formdata);
    }else{
    	formdata.syncData = 1;
    	formdata.inHospitalNo = formdata.number;
    }
    if(formdata.number){
    	formdata.syncData = 1;
    }
    

    $('#grid1').datagrid('load',formdata);
}


/**
 * 忽略
 */
function hulue(id){
	$.ajax({
        url: baseUrl + '/patient/hulue.do?id='+id,
        dataType:'json',
        type:'post',
        success:function(value){
            if(value.type == 'success'){
            	doSearch();   
            }
        }
    });
}


/**
 * 选择
 */
function choose(id){
	$.ajax({
        url: baseUrl + '/patient/savebl.do?id='+id,
        dataType:'json',
        type:'post',
        success:function(value){
            if(value.type == 'success'){
            	parent.getPatient(id);
            	var mylay = parent.layer.getFrameIndex(window.name);
                parent.layer.close(mylay);
            }
        }
    });
	
}