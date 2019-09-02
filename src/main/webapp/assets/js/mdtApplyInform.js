var id = null;
var type = null;
$(function(){
	id = getQueryVariable("id");
	type = getQueryVariable("type");
    if(id != undefined && id != null){
        initData(id);
    }
    if(type == 'sm'){
    	$("#btnSave1").hide();
    	$("#btnSave2").hide();
    	$("#btnSave4").hide();
	    $("#btnSave3").show();
	    $(document.body).css('font-size','25px');
	    $('input').css('font-size','25px');
    }
   
});

/**
 * 初始化数据
 */
function initData(id){
	//获取知情同意书信息
	 $.ajax({
	        url: baseUrl + '/applyknow/get.do?applyId='+id,
	        dataType:'json',
	        type:'post',
	        success:function(value){
	            if(value.type == 'success'){
	                var data = value.resultData.row;
	                //有的话直接渲染
	                if(data){
	                	if(data.qmdate1 != null){
	                		initDate(new Date(data.qmdate1),1,data);
	                	}
	                	if(data.qmdate2 != null){
	                		initDate(new Date(data.qmdate2),2,data);
	                	}             	
	                	initDate(new Date(data.qmdate3),3,data);
	                	if(data.nameqmurl){
	                		var nameqmurl = '<img src="'+data.nameqmurl+'" height="50" />';
	                		$('#nameqmurl').html(nameqmurl);
	                	}
	                	$('#editForm').form('load', data);
	                }else{
	                //没有，就要进行组装
	                	initData2(id);
	                }
	            }
	        }
	    });
}

/**
 * 初始化数据2
 */
function initData2(id){	
    $.ajax({
        url: baseUrl + '/mdtApply/get?id='+id,
        dataType:'json',
        type:'post',
        success:function(value){
            if(value.type == 'success'){
                var data = value.resultData.row;
                var myObject = {};
                myObject.name = data.name;
                myObject.gender = data.gender;
                myObject.phone = data.phone;
                myObject.idcard = data.idcard;
                if(data.patientType == 1){
                	myObject.medicalNo = data.number;
                }else{
                	myObject.inHospitalNo = data.number;
                }
            	myObject.nameqm = data.name;	
            	var myDate = new Date();
            	initDate(myDate,1,myObject);
            	initDate(myDate,2,myObject);
            	initDate(myDate,3,myObject);
            	 $('#editForm').form('load', myObject);
            }
        }
    });
    //计算费用
    $.ajax({
        url: baseUrl + '/mdtApply/calculateFee?applyId='+id,
        dataType:'json',
        type:'post',
        success:function(value){
            if(value.type == 'success'){
                var data = value.resultData.row;
                var myObject = {feiyong:data};
            	 $('#editForm').form('load', myObject);
            }
        }
    });
    
    
}





/**
 * 显示二维码
 */
function qr(){
	var formdata = {codeUrl:'http://61.153.205.134:9080/'+baseUrl+'/assets/mdtApplyInform.html?type=sm&id='+id};
    $.ajax({
        url: baseUrl + '/file/qr.do',
        data:formdata,
        dataType:'json',
        type:'post',
        success:function(value){
            if(value.type == 'success'){
                var data = value.resultData.row;
                layer.open({
                    type: 2,
                    title: '知情同意书二维码',
                    maxmin: true,
                    shadeClose: true, //点击遮罩关闭层
                    area : ['80%' , '80%'],
                    content:data
                });
            }
        }
    });
}


/**
 * 签名页面
 */
function qm(){
	location.href= baseUrl+"/assets/mdtApplyInformQm.html?id="+id 
}



function printW() {
	$("#printW").hide();
    window.print();
    $("#printW").show();
}

function initDate(myDate,index,myObject){
	var str = myDate.Format("yyyy-MM-dd");
	var arr = str.split('-');
	myObject["year"+index] = arr[0];
	myObject["month"+index] = arr[1];
	myObject["day"+index] = arr[2];
	
}


//保存
function save(){
  //判断：编辑表单的所有控件是否都通过验证
  var isValidate= $('#editForm').form('validate');
  if(isValidate==false){
      return ;
  }
  var formdata=getFormData('editForm');
  formdata.id = id;
  formdata.applyId = id;
  if(formdata.year1){
	  formdata.qmdate1 = formdata.year1+'-'+formdata.month1+'-'+formdata.day1;
  }else{
	  formdata.qmdate1 = "";
  }
  if(formdata.year2){
	  formdata.qmdate2 = formdata.year2+'-'+formdata.month2+'-'+formdata.day2;
  }else{
	  formdata.qmdate2 = "";
  }
  formdata.qmdate3 = formdata.year3+'-'+formdata.month3+'-'+formdata.day3;
  
  $.ajax({
      url: baseUrl + '/applyknow/save.do',
      data:formdata,
      async: false,
      dataType:'json',
      type:'post',
      success:function(value){
          if(value.type == 'success'){
        	  alert("保存成功");
                window.parent.doSearch();
          } else {
              $.messager.alert('提示',value.message);
          }
      }
  });
}
