var applyId;
$(function(){

    var url = window.location.href;
    applyId = url.split("id=")[1];

    $("#applyId").val(applyId);

    initGrid(applyId);
});

function initGrid(id) {

    var columns=[[
        /*{field:'id',title:'编号',width:100},*/
        {field:'visitTime',title:'随访日期',width:200},
        {field:'visitName',title:'随访姓名',width:120},
        {field:'visitPhone',title:'随访人电话',width:120},

        {field:'-',title:'操作',width:500,formatter:function(value,row,index) {
            var editBtn = "<a href='#' onclick='edit("+row.id+")'>修改</a> ";
            var deleBtn = "<a href='#' onclick='dele("+row.id+")'>删除</a> ";

            var btn = editBtn + deleBtn;
            return btn;
        }}
    ]];

    //表格数据初始化
    $('#grid').datagrid({
        url:baseUrl + '/mdtApply/selectFeedbackList?applyId=' + applyId,
        loadFilter: function(data){
            return data.resultData;
        },
        columns:columns,
        singleSelect:true,
        rownumbers:true,
        toolbar: [{
            iconCls: 'icon-add',
            text:'增加',
            handler: function(){

                layer.open({
                    type: 2,
                    title: '反馈',
                    maxmin: true,
                    shadeClose: true, //点击遮罩关闭层
                    area : ['80%' , '80%'],
                    content: 'mdtApplyFeedbackEdit.html?applyId=' + id
                });
            }
        }]

    });
}

/**
 * 删除 
 */
function dele(id){
	
	$.messager.confirm('提示','确定要删除此记录吗？',function(r){
		if(r)
		{
			$.ajax({
                url:baseUrl + '/mdtApply/delFeedback?id=' + id,
				dataType:'json',
				success:function(value){
					if(value.success){
                        doSearch();
					}
					$.messager.alert('提示',value.message);
				}
			});		
		}	
	});	
}

/**
 * 编辑
 */
function edit(id){
    layer.open({
        type: 2,
        title: '反馈',
        maxmin: true,
        shadeClose: true, //点击遮罩关闭层
        area : ['90%' , '90%'],
        content: 'mdtApplyFeedbackEdit.html?id=' + id + "&applyId=" + applyId
    });
}

function doSearch() {
    var formdata=getFormData('searchForm');
    $('#grid').datagrid('load',formdata);
}