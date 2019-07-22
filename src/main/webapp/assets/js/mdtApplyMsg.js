
$(function(){

    var url = window.location.href;
    id = url.split("id=")[1];
    if(id != undefined && id != null){
        initData(id);

        getSysMsgTemp();
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

function getSysMsgTemp(){
    $.ajax({
        url: baseUrl + '/set/getSysMsgTempByType?type=1',
        dataType:'json',
        type:'post',
        success:function(value){

            if(value.type == 'success'){
                $("#msgTd").html(value.resultData.row.content)
            }
        }
    });
}

function sendMsg() {

    //判断：编辑表单的所有控件是否都通过验证
    var isValidate= $('#editForm').form('validate');
    if(isValidate==false){
        return ;
    }

    var formdata=getFormData('editForm');

    $.ajax({
        url: baseUrl + '/mdtApply/sendMsg',
        data:formdata,
        dataType:'json',
        type:'post',
        success:function(value){
            if(value.type == 'success') {

                $.messager.alert('提示', "发送成功");
            } else {

                $.messager.alert('提示',value.message);
            }
        }
    });
}
