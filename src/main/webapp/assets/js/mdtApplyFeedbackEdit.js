var id;
var applyId;

$(function(){

    id = getQueryVariable("id");
    applyId = getQueryVariable("applyId");

    $("#applyId").val(applyId);
    getApplyData(applyId);

    if(id != undefined && id != null){
        initData(id);
    }
    // else {
    //     $('#visitName').textbox('setValue', 'visitName');
    //     $('#visitName').textbox('setText', 'visitName');
    // }
});

function getApplyData(applyId) {

    $.ajax({
        url: baseUrl + '/mdtApply/get?id='+applyId,
        dataType:'json',
        type:'post',
        success:function(value){
            if(value.type == 'success'){
                var data = value.resultData.row;
                $("#name").html(data.name);
                if (data.gender == '1') {
                    $("#gender").html("男");
                } else {
                    $("#gender").html("女");
                }
                $("#birthday").html(data.birthday);
                $("#number").html(data.number);
                $("#mdtDate").html(data.mdtDate);
            }
        }
    });
}

//保存
function save(){

    //判断：编辑表单的所有控件是否都通过验证
    var isValidate= $('#editForm').form('validate');
    if(isValidate==false){
        return ;
    }

    var formdata=getFormData('editForm');

    $.ajax({
        url: baseUrl + '/mdtApply/saveFeedback',
        data:formdata,
        dataType:'json',
        type:'post',
        success:function(value){
            if(value.type == 'success'){
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
 * 编辑
 */
function initData(id){
    $.ajax({
        url: baseUrl + '/mdtApply/getFeedback?id='+id,
        dataType:'json',
        type:'post',
        success:function(value){
            if(value.type == 'success'){
                $('#editForm').form('load', value.resultData.row);
            }
        }
    });
}
