var id;
var type;
$(function(){

    id = getQueryVariable("id");
    type = getQueryVariable("type");
    if("view" == type){
    	$("#btn1").hide();
    }
    if(id != undefined && id != null){
        initData(id);
    }
});

function save() {
	 var formdata = {id:id};
	 var trs = $("#grid1").find('tr');
	 
	 var doctors = [];
	 
	 trs.each(function(x, tr) {
		 if(x == 0){
			 return;
		 }
		 //对医生的评分和意见
		 var doctor = {}
		 doctor.userId = $(tr).attr("id"); 
		 doctor.zjPinFenList = [];
		 var tds = $(tr).children();
		 tds.each(function(y, tdd) {
			 var td = $(tdd);
			 if(y == 0){
				 return;
			 }
			 var hd = headgrade[y-1];		
			 var divs = $(td.children()[0]);
			 var maxvalue = divs.attr("dataid");
			 var pinfenitem = {sysGradeId:hd.id,grade:maxvalue};
			 doctor.zjPinFenList.push(pinfenitem);
		 });
		 doctors.push(doctor);	
	 })	
	  formdata.doctors = JSON.stringify(doctors);

	 
	 $.ajax({
	        url: baseUrl + '/mdtApply/saveZJPinFen',
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
	if(apply.doctors[0].zjPinFenList && apply.doctors[0].zjPinFenList.length > 0){
		headgrade = [];
		var pinfens = apply.doctors[0].zjPinFenList;
		for(var i=0;i<pinfens.length;i++){
			var pinfen = pinfens[i];
			var bz = {id:pinfen.sysGradeId,type:pinfen.type,description:pinfen.description,minValue:pinfen.minValue,maxValue:pinfen.maxValue};
			headgrade.push(bz);
		}
		genItem(headgrade,apply);            
	}else{
		$.ajax({
	        url: baseUrl + '/mdtApply/getGradeItem?type=' + 1,
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
	var tr = '<tr><td class="labelcss">专家姓名</td> ';
	var tds = '';
    for (var i=0; i< data.length; i++) {
    	var da = data[i];
    	var xing = doxinghtml(da.maxValue,0);
    	tds += ' <td>'+xing+'</td>';
    	tr += ' <td>'+da.description+'</td>';
        tr += '</td>';
    }
    tr += '</tr>';
    $("#grid1").append(tr)
    
    var rows = apply.doctors;
  	for(var i=0;i<rows.length;i++){
  		var row = rows[i];
  		var zjPinFenList = row.zjPinFenList;
  		if(zjPinFenList && zjPinFenList.length>0){
  			tds = '';
  			for (var jj=0; jj< zjPinFenList.length; jj++) {
  		    	var da = zjPinFenList[jj];
  		    	var xing = doxinghtml(da.maxValue,da.grade);
  		    	tds += ' <td>'+xing+'</td>';
  		    }
  		}	
  		var html = "<tr id='"+row.userId+"'><td class='labelcss'>"+row.name+"</td>"+tds+"</tr>";
  		$('#grid1').append(html);
  	} 
}




function doxinghtml(size,index){
	var ddhtml = "<div dataid='"+index+"'>";
	for(var i=0;i<size;i++){
		var d = '<div dataid="'+(i+1)+'" class="myfloatleft" onclick="choosexin(this)"><h1>☆</h1></div>';
		if(i < index){
			d = '<div dataid="'+(i+1)+'" class="myfloatleft" style="color:orange;" onclick="choosexin(this)"><h1>★</h1></div>';
		}
		ddhtml += d;
	}
	ddhtml += "</div>";
	return ddhtml;
}

function choosexin(obj){
	var myvalue = $(obj).attr("dataid");
	$(obj).parent().attr("dataid",myvalue);
	var childs = $(obj).parent().children();
	for(var i=0;i<childs.length;i++){//将所有都变白星星
		childs[i].innerHTML="<h1>☆</h1>";
		childs[i].setAttribute("style","color:black");
	}
	for(var i=0;i<myvalue;i++){//通过传入的id（也是一个数字）确定id以下的div都是橙色星星。
		childs[i].innerHTML="<h1>★</h1>";
		childs[i].setAttribute("style","color:orange");
	} 
}




/**
 * 编辑
 */
function initData(id){
    $.ajax({
        url: baseUrl + '/mdtApply/detail?id='+id,
        dataType:'json',
        type:'post',
        success:function(value){
            if(value.type == 'success'){
                $('#editForm').form('load', value.resultData.row);
                initGrid1(value.resultData.row);
            }
        }
    });
}

