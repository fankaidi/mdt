var id = null;
$(function(){
	id = getQueryVariable("id");
    if(id != undefined && id != null){
        initData(id);
        getSysMsgTemp(1);
        getSysMsgTemp(2);
    }
    initGrid1(id);
});

/**
 * 生成 MDT拟请专家列表
 * @param applyId
 */
function initGrid1(applyId) {
    var columns=[[
		/*{field:'id',title:'编号',width:100},*/
        {field:'name',title:'专家名称',width:100},
        {field:'department',title:'科室',width:200},
        {field:'title',title:'职称',width:100},
        {field:'phone',title:'手机长号',width:150},
        {field:'phoneCornet',title:'手机短号',width:100}
    ]];

    //表格数据初始化
    $('#grid1').datagrid({
        title:'拟请MDT参加专家',
        url:baseUrl + '/mdtApply/listApplyDoctorByApplyId?applyId=' + applyId,
        loadFilter: function(data){
            return data.resultData;
        },
        columns:columns,
        singleSelect:true,
        rownumbers:true
    });
}

/**
 * 编辑
 */
function initData(id){
    $.ajax({
        url: baseUrl + '/mdtApply/get?id='+id,
        dataType:'json',
        type:'post',
        success:function(value){
            if(value.type == 'success'){
                $('#editForm').form('load', value.resultData.row);
            }
        }
    });
}

function getSysMsgTemp(type){
    $.ajax({
        url: baseUrl + '/set/getSysMsgTempByType?type='+type,
        dataType:'json',
        type:'post',
        success:function(value){
            if(value.type == 'success'){
                $("#msgTd"+type).html(value.resultData.row.content)
            }
        }
    });
}

function sendMsg(type) {
    //判断：编辑表单的所有控件是否都通过验证
    var isValidate= $('#editForm').form('validate');
    if(isValidate==false){
        return ;
    }
    var formdata=getFormData('editForm');
    formdata.type = type;
    $.ajax({
        url: baseUrl + '/mdtApply/sendMsg',
        data:formdata,
        dataType:'json',
        type:'post',
        success:function(value){
            if(value.type == 'success') {
                alert("发送成功");
                var mylay = parent.layer.getFrameIndex(window.name);
                parent.layer.close(mylay);
                window.parent.doSearch();
            } else {

                $.messager.alert('提示',value.message);
            }
        }
    });
}
