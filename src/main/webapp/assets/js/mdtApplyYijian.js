function setheight(){
	var ht = "800px";
	var ua = navigator.userAgent;
	if(/msie/i.test(ua)){
		ht = "1200px";
	}else if("ActiveXObject" in window){
		ht = "1200px";
	}
	$("#overtd").height(ht);

}

var id = null;
$(function(){
	id = getQueryVariable("id");
    if(id != undefined && id != null){
    	getMdtPurpose();
        initData(id);
    }
    setheight();
});


//获取MDT目的的多选框值
var box = {};
function getMdtPurpose() {
 $.ajax({
     url: baseUrl + '/set/getCodeByType?type=1',
     data:{},
     async: false,
     dataType:'json',
     type:'post',
     success:function(value){
         if(value.type == 'success'){
             var data = value.resultData.rows;
             for (var i=0; i<data.length; i++) {
            	 box[data[i].code] = data[i].value;
             }
         }
     }
 });
}


function getOrgById(id) {
	var data = null
	 $.ajax({
	     url: baseUrl + '/org/get',
	     data:{id:id},
	     async: false,
	     dataType:'json',
	     type:'post',
	     success:function(value){
	         if(value.type == 'success'){
	             data = value.resultData.row;
	         }
	     }
	 });
	 return data;
}


/**
 * 初始化数据2
 */
function initData(id){	
    $.ajax({
        url: baseUrl + '/mdtApply/detail?id='+id,
        dataType:'json',
        type:'post',
        success:function(value){
            if(value.type == 'success'){
                var data = value.resultData.row;
                $('#name').html(data.name);
                $('#number').html(data.number);
                $('#gender').html(data.gender);
                $('#age').html(data.patientUser.age);
                $('#jzks').html("未知");
                $('#jzys').html("未知");
                $('#cbzd').html(data.patientUser.primaryDiagnosis); 
                $('#mdtDate').html(data.mdtDate);
                $('#mdtLocation').html(data.mdtLocation);
                var names = [];
                data.doctors.forEach(function(item){
          		  names.push(item.name);
          		});
                $('#menbers').html(names.join(','));
                
                var mdtPurpose = data.mdtPurpose;
                var mdtPurposeNames = [];
                var arrmdt = mdtPurpose.split(',');
                arrmdt.forEach(function doraa(item){
                	if(item != '5'){
                		mdtPurposeNames.push(box[item]);
                	}else{
                		mdtPurposeNames.push(data.otherMdtPurpose);
                	}
                });
                $('#mdtPurpose').html(mdtPurposeNames.join(','));
                $('#overview').html(data.overview);
                var hzyj = "会诊意见："+data.zjyj;
                hzyj += "\n\n\n下一步诊疗方案："+data.houxu;
                $('#hzyj').html(hzyj);
                
                
                var qianming = "";
                $(data.doctors).each(function(x, doc) {
                	var org = getOrgById(doc.department);
    				qianming += "<div><div style='float:left;width:200px;'>&nbsp;&nbsp;&nbsp;&nbsp;科室："+org.name+"</div>" 
    					+"<div style='float:left;width:200px;'>&nbsp;&nbsp;&nbsp;&nbsp;会诊专家签名："+doc.name+"</div> "
    					+"<div style='float:left;width:200px;'>&nbsp;&nbsp;&nbsp;&nbsp;职称："+doc.title+"</div></div>";
        		})
        		qianming += "<div>&nbsp;&nbsp;&nbsp;&nbsp;申请者："+data.applyPerson+"</div>" 
				+"<div >&nbsp;&nbsp;&nbsp;&nbsp;记录者："+data.jlrname+"</div> ";
                var now = new Date();
                var riqi = now.Format("yyyy年MM月dd日");
                qianming += "<br><div><div style='float:right;'>"+riqi+"&nbsp;&nbsp;&nbsp;&nbsp;</div></div>";
                qianming += "<div style='clear:both;'><div style='float:right;'>(单位盖章有效)&nbsp;&nbsp;&nbsp;&nbsp;</div></div>";
                qianming += "<div style='clear:both;text-align:center'>(备注：本报告一式两份，一份交患者，一份由门诊疑难病联合会诊中心留档)</div>";
               
                $('#qianming').html(qianming);
                
            }
        }
    });
}

function printW() {
	$("#printW").hide();
    window.print();
    $("#printW").show(); 
}

