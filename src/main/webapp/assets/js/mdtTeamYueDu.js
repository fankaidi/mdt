$(function(){

});

function doSearch() {
    var formdata=getFormData('searchForm');
    var startYearint = parseInt(formdata.startYear);
    var startMonthint = parseInt(formdata.startMonth);
    
    var endYearint = parseInt(formdata.endYear);
    var endMonthint = parseInt(formdata.endMonth);
    var col = [{field:'name',title:'团队名称',width:200},{field:'sxzj',title:'首席专家',width:80,formatter:function(value,row,index) {
    	return row.sxzj.user.name;
    }},{field:'tdms',title:'团队秘书',width:80,formatter:function(value,row,index) {
    	var temp = "";
    	if(row.tdms){
    		temp = row.tdms.user.name;
    	}
    	return temp;
    }}];
    while((startYearint*12+startMonthint)<=(endYearint*12+endMonthint)){
    	var temp = {field:startYearint+'-'+startMonthint,title:startYearint+'年'+startMonthint+'月',srid:startYearint+'-'+startMonthint,width:100, formatter:function(value,row,index) {
    		var rs = row.yueDuPinGuMap[this.srid];
    		var imgsrc = "lv.png";
    		if(rs.total > 0){
    			if(rs.wanChen <= 0.7){
    				imgsrc = "hong.png";
    			}else if(rs.wanChen <= 0.9){
    				imgsrc = "huang.png";
    			}
    		}
    		var weiwan = "<img style='height:60px;width:50px' src='images/"+imgsrc+"'>";		
            return weiwan;
        }};
    	col.push(temp);
    	if(startMonthint == 12){
    		startMonthint = 1;
    		startYearint++;
    	}else{
    		startMonthint++;
    	} 	
    }
    var columns=[];
    columns.push(col);
          	
  	//表格数据初始化
  	$('#grid').datagrid({
  		url:baseUrl + '/mdtTeam/findYueDu.do',
  		queryParams: formdata,
	      loadFilter: function(data){
	          return data.resultData;
	      },
  		columns:columns,
  		singleSelect:true,
  		pagination:true,
  		toolbar: []

  	});
}