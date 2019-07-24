var applyId;

$(function(){

    applyId = getQueryVariable("id");

    if(applyId != undefined && applyId != null){
        // initData(id);
        getAllApplyOpinion();

        getApply();
    }

});

function getAllApplyOpinion() {
    $.ajax({
        url: baseUrl + '/mdtApply/getAllApplyOpinion?applyId=' + applyId,
        data:{},
        dataType:'json',
        type:'post',
        success:function(value){

            if(value.type == 'success'){

                var data = value.resultData.rows;

                var html = '';
                for (var i=0; i<data.length; i++) {

                    html += data[i].department + "  " + data[i].username + ":  " + data[i].content + " \n";

                }
                html += "\n";

                // $("#tr1").html(html)
                $("#summary").html(html)
            }
        }
    });
}

function getApply() {
    $.ajax({
        url: baseUrl + '/mdtApply/detail?id='+applyId,
        dataType:'json',
        type:'post',
        success:function(value){
            if(value.type == 'success'){
            	  var data = value.resultData.row;
                  $("#content").html(data.summary)
                initGrid1(value.resultData.row);
            }
        }
    });
}

/**
 * 生成 MDT拟请专家列表
 * @param applyId
 */
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
	}
}



//组装专家内容
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
	
}
