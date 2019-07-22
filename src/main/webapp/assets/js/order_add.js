var isEditingRowIndex;//当前编辑行的索引
$(function(){
	
	$('#grid').datagrid({
		columns:[[
		  		    {field:'goodsuuid',title:'商品编号',width:100,editor:{type:'numberbox',options:{
		  		    	disabled:true
		  		    }}},
		  		    {field:'goodsname',title:'商品名称',width:100,editor:{type:'combobox',options:{
		  		    	url:'goods_list.action',
		  		    	valueField:'name',
		  		    	textField:'name',
		  		    	onSelect:function(record){		  		    		
		  		    		//价格编辑框
		  		    		var priceEdt= $('#grid').datagrid('getEditor',{index:isEditingRowIndex,field:'price'});
		  		    		if(Request['type']=='1'){
		  		    			$(priceEdt.target).val(record.inprice);//对价格编辑框赋值
		  		    		}
		  		    		if(Request['type']=='2'){
		  		    			$(priceEdt.target).val(record.outprice);//对价格编辑框赋值
		  		    		}
		  		    	    //得到商品编号编辑框
		  		    		var goodsuuidEdt= $('#grid').datagrid('getEditor',{index:isEditingRowIndex,field:'goodsuuid'});
		  		    		$(goodsuuidEdt.target).val(record.uuid);//对商品编号编辑框赋值
		  		    		
		  		    		cal();//重新计算
		  		    		sum();//合计
		  		    	}
		  		    }}},
		  		    {field:'price',title:'价格',width:100,editor:{type:'numberbox',options:{
		  		    	precision:2		  		    	
		  		    }}},
		  		    {field:'num',title:'数量',width:100,editor:'numberbox'},
		  		    {field:'money',title:'金额',width:100,editor:{type:'numberbox',options:{
		  		    	precision:2	,
		  		    	disabled:true
		  		    }}},
		  		    {field:'-',title:'操作',width:100,formatter:function(value,row,index){
		  		    	return "<a href='#' onclick='deleteRow("+index+")'>删除</a>";
		  		    }}
			 ]],
	     singleSelect:true,
	     toolbar:[
              {
            	  iconCls:'icon-add',
            	  text:'增加',
            	  handler:function(){
            		  //增加一行
            		  $('#grid').datagrid('appendRow',{"num":0,"money":0});
            		  //关闭上一次编辑的行
            		  $('#grid').datagrid('endEdit',isEditingRowIndex);
            		  //得到最后一行的索引
            		  isEditingRowIndex=$('#grid').datagrid('getRows').length-1;		  
            		  //开启编辑行
            		  $('#grid').datagrid('beginEdit',isEditingRowIndex);
            		  
            		  bindGridEvent();//绑定表格事件
            		  
            	  }
              }
	       ],
	       onClickRow:function(rowIndex,rowData){
	    	   //关闭上一次编辑的行
     		  $('#grid').datagrid('endEdit',isEditingRowIndex);
     		  //得到最后一行的索引
     		  isEditingRowIndex=rowIndex;		  
     		  //开启编辑行
     		  $('#grid').datagrid('beginEdit',isEditingRowIndex);
     		  bindGridEvent();//绑定表格事件
	       }
	
	
		
	});
	
	
	//供应商下拉列表
	$('#supplier').combogrid({
		url:'supplier_list.action?t1.type='+Request['type'],
		columns:[[
		  		    {field:'uuid',title:'编号',width:100},
		  		    {field:'name',title:'名称',width:100},
		  		    {field:'address',title:'地址',width:100},
		  		    {field:'contact',title:'联系人',width:100},
		  		    {field:'tele',title:'电话',width:100},
		  		    {field:'email',title:'EMAIL',width:100},
		  		    {field:'type',title:'类型',width:100}		    
		 ]],
		 idField:'uuid',
		 textField:'name',
		 width:400,
		 panelWidth:700,
		 mode:'remote'
	});
	
	//提交订单
	$('#btnSave').bind('click',function(){
	
		//结束编辑
		$('#grid').datagrid('endEdit',isEditingRowIndex);		
		var formdata=getFormData("orderForm");		
		//追加属性JSON
		formdata['json']=JSON.stringify( $('#grid').datagrid('getRows'));		
		
		$.ajax({
			url:'orders_add.action?t.type='+Request['type'],
			dataType:'json',
			type:'post',
			data:formdata,
			success:function(value){
				
				if(value.success){
					$('#sum').html('0.00');//合计数清空
					$('#grid').datagrid('loadData',{total:0,rows:[]});//清空表格
				}
				$.messager.alert('提示',value.message);
				
			}
			
		});
		
		
	});
	
});

/**
 * 计算 
 */
function cal(){
	//价格编辑框
	var priceEdt= $('#grid').datagrid('getEditor',{index:isEditingRowIndex,field:'price'});
	var price=$(priceEdt.target).val();//提取价格编辑框的值	
	//数量编辑框
	var numEdt= $('#grid').datagrid('getEditor',{index:isEditingRowIndex,field:'num'});
	var num=$(numEdt.target).val();//提取数量编辑框的值	
	//得到金额
	var money=(price*num).toFixed(2);	
	//金额编辑框
	var moneyEdt= $('#grid').datagrid('getEditor',{index:isEditingRowIndex,field:'money'});
	$(moneyEdt.target).val(money);//给金额编辑框赋值
	//给金额单元格赋值
	$('#grid').datagrid('getRows')[isEditingRowIndex].money=money;
	
}

/**
 * 绑定表格事件
 */
function bindGridEvent(){
	//绑定数量编辑框
	var numEdt= $('#grid').datagrid('getEditor',{index:isEditingRowIndex,field:'num'});
	$(numEdt.target).bind('keyup',function(){
		cal();//计算金额	
		sum();//合计
	});
	
	//绑定价格编辑框
	var priceEdt= $('#grid').datagrid('getEditor',{index:isEditingRowIndex,field:'price'});
	$(priceEdt.target).bind('keyup',function(){
		cal();//计算金额	
		sum();//合计
	});
	
}

/**
 * 删除行
 * @param index
 */
function deleteRow(index){
	//结束编辑
	$('#grid').datagrid('endEdit',isEditingRowIndex);
	
	//删除行
	$('#grid').datagrid('deleteRow',index);
	
	//获取表格的数据 
	var data= $('#grid').datagrid('getData');
	
	//加载表格的数据 
	$('#grid').datagrid('loadData',data);
		
	sum();//合计
}

/**
 * 求合计数
 */
function sum(){
	
	var money=0;
	var rows=$('#grid').datagrid('getRows');
	for(var i=0;i<rows.length;i++){
		money+=parseFloat( rows[i].money);		
	}
	$('#sum').html(money.toFixed(2));
}
