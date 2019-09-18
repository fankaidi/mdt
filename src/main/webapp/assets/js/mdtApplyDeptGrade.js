var id;
var type;
$(function(){

    id = getQueryVariable("id");
    type = getQueryVariable("type");
    if("view" == type){
    	$("#btn1").hide();
    	$("#btn2").hide();
    }
    if(id != undefined && id != null){
        initData(id);
        
    }
});

function save() {
	var formdata=getFormData('editForm');
	formdata.id =id;
	 var trs = $("#grid1").find('tr');	 
	 var doctors = [];	 
	 trs.each(function(x, tr) {
		 if(x == 0){
			 return;
		 }
		 //对医生的评分和意见
		 var doctor = {}
		 doctor.userId = $(tr).attr("id"); 
		 doctor.zjYiJian = {}; 
		 var tds = $(tr).children();
		 doctor.zjYiJian.content = $(tds[1]).find("input").val();
		 doctors.push(doctor);	
	 })	
	 formdata.doctors = JSON.stringify(doctors);
	 $.ajax({
        url: baseUrl + '/mdtApply/saveDeptGrade',
        data:formdata,
        dataType:'json',
        type:'post',
        success:function(value){
            if(value.type == 'success'){
            	 alert('保存成功');
            	 var mylay = parent.layer.getFrameIndex(window.name);
                 parent.layer.close(mylay);
                 window.parent.doSearch();
            } else {
                $.messager.alert('提示',value.message);
            }
        }
	 });
}

/**
 * 生成 MDT拟请专家列表
 * @param applyId
 */
var headgrade = null;
function initGrid1(apply) {
	if(apply.doctors[0].ksPinFenList && apply.doctors[0].ksPinFenList.length > 0){
		headgrade = [];
		var pinfens = apply.doctors[0].ksPinFenList;
		for(var i=0;i<pinfens.length;i++){
			var pinfen = pinfens[i];
			var bz = {id:pinfen.sysGradeId,type:pinfen.type,description:pinfen.description,minValue:pinfen.minValue,maxValue:pinfen.maxValue};
			headgrade.push(bz);
		}
		genItem(headgrade,apply);            
	}else{
		$.ajax({
	        url: baseUrl + '/mdtApply/getGradeItem?type=' + 2,
	        dataType:'json',
	        type:'post',
	        success:function(result){
	            if(result.type == 'success'){
	            	headgrade = result.resultData.rows;
	                genItem(headgrade,apply);            
	            }
	        }
	    });
	}
	 
}

// 组装专家内容
function genItem(data,apply) {
	var tr = '<tr><td class="labelcss" style="width: 10%">专家姓名</td><td>专家治疗方案</td></tr>';
    $("#grid1").append(tr)   
    var rows = apply.doctors;
  	for(var i=0;i<rows.length;i++){
  		var row = rows[i];
  		var content = '';	
  		if(row.zjYiJian){
  			content = row.zjYiJian.content;
  		}
  		var html = "<tr id='"+row.userId+"'><td class='labelcss'>"+row.name+"</td>"
  		+"<td><input type='text' class='easyui-textbox' data-options='multiline:true' value='"+content+"' style='width:90%;height:200px'></td>"
  		+"</tr>";
  		$('#grid1').append(html);
  	} 
}

//会诊意见书打印
function informFun() {
    layer.open({
        type: 2,
        title: '会诊意见书打印',
        maxmin: true,
        shadeClose: true, //点击遮罩关闭层
        area : ['100%' , '100%'],
        content: 'mdtApplyYijian.html?id=' + id
    });
}

/**
 * 编辑
 */
function initData(id){
	var loginuser = getUser();	
    $.ajax({
        url: baseUrl + '/mdtApply/detail?id='+id,
        dataType:'json',
        type:'post',
        success:function(value){
            if(value.type == 'success'){
            	var row = value.resultData.row;
            	if(row.applyPersonId != loginuser.id && (loginuser.teamIds && loginuser.teamIds.indexOf(row.teamId) == -1)){
            		$('#dg').hide();
            	}
                $('#editForm').form('load', value.resultData.row);
                initGrid1(value.resultData.row);
            }
        }
    });
}
