var code;
var rows;
$(function() {
	code = getQueryVariable("code");
	initGrid1();
});


function initGrid1() {
	var col = [];
	col.push({field:'-',title:'操作',width:60,formatter:function(value,row,index) {
        return "<a href='#' onclick='choose("+index+")' style='background:#1E9FFF;color:#fff;padding:3px;line-height:30px;'>选取</a>";
    }});	
	col.push({field:'dept_CODE',title:'科室',width:100});
	col.push({field:'gqs',title:'过去史',width:180});
	col.push({field:'jzs',title:'家族史',width:180});
	col.push({field:'grs',title:'个人史',width:180});
	col.push({field:'tj',title:'体检',width:180});
	col.push({field:'cl',title:'处理',width:180});	
	col.push({field:'cbzd',title:'初步诊断',width:180});	
	col.push({field:'created_DATE',title:'时间',width:160});		
	
	
	
    var columns=[];
	columns.push(col)
    //表格数据初始化
    $('#grid1').datagrid({
        title:'患者选择',
        url:baseUrl + '/mdtApply/bllist',
        nowrap: false,
        queryParams: {code:code},
        loadFilter: function(data){
        	rows = data.resultData.rows; 
            return data.resultData;
        },
        columns:columns,
        singleSelect:true,
        rownumbers:true
    });
}

/**
 * 选择
 */
function choose(index){
	parent.changebl(rows[index]);
	var mylay = parent.layer.getFrameIndex(window.name);
    parent.layer.close(mylay);	
}