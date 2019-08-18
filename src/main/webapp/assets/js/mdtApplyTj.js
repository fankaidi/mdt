$(function(){

});

function doSearch() {
    var formdata=getFormData('searchForm');
    if(formdata.startApplyCreatetime){
    	 formdata.startApplyCreatetime += ' 00:00:00';
    }
    if(formdata.endApplyCreatetime){
   	 	formdata.endApplyCreatetime += ' 23:59:59';
    }
    var columns=[[
                  {field:'applyCreatetime',title:'申请时间',width:140},
                  {field:'gender',title:'MDT专家名单',width:300,formatter:function(value,row,index) {
                	  var names = [];
                	  row.doctors.forEach(function(item){
                		  names.push(item.name);
                		  });
                	  return names.join(',');
                  }},
                  {field:'applyPerson',title:'MDT申请人',width:120},
                  {field:'tjusername',title:'推荐人',width:200},
                  {field:'mdtLocation',title:'MDT地点',width:150},
                  {field:'feiyong',title:'费用',width:120},
                  {field:'applyStatus',title:'状态',width:150,formatter:function(value,row,index) {
                      if (row.isZjdafen == 0) {
                          return "进行";
                      } else {
                          return "完成";
                      }
                  }}
              ]];
          	
  	//表格数据初始化
  	$('#grid').datagrid({
  		url:baseUrl + '/mdtApply/findByPage',
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


//导出，使用这种方式 可以，使用 ajax请求不可以 导出excel
function exportExcel(){
     var form = $("<form>");
     form.attr('style', 'display:none');
     form.attr('target', '');
     form.attr('method', 'post');
     form.attr('action', baseUrl + '/mdtApply/exportApply.do');

     
     var formdata=getFormData('searchForm');
     if(formdata.startApplyCreatetime){
    	 var input1 = $('<input>');
         input1.attr('type', 'hidden');
         input1.attr('name', 'startApplyCreatetime');
         input1.attr('value', formdata.startApplyCreatetime += ' 00:00:00'); 
         form.append(input1);
     }
     if(formdata.endApplyCreatetime){
    	 var input1 = $('<input>');
         input1.attr('type', 'hidden');
         input1.attr('name', 'endApplyCreatetime');
         input1.attr('value', formdata.endApplyCreatetime += ' 23:59:59'); 
         form.append(input1);
     }
     $('body').append(form);
     form.submit();
     form.remove();    
}