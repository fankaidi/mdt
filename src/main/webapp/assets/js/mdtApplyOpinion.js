var id;
var userId;

$(function(){

    id = getQueryVariable("id");
    userId = getQueryVariable("userId");
    if(id != undefined && id != null){

        $("#applyId").val(id);
        $("#userId").val(userId);

        initData();
    }

});

function initData() {

    $.ajax({
        url: baseUrl + '/mdtApply/getApplyOpinion?applyId=' + id + '&userId=' + userId,
        data:{},
        dataType:'json',
        type:'post',
        success:function(value){
            if(value.type == 'success') {
                $('#editForm').form('load', value.resultData.row);
            }
        }
    });
}

function save() {

    //判断：编辑表单的所有控件是否都通过验证
    var isValidate= $('#editForm').form('validate');
    if(isValidate==false){
        return ;
    }

    var formdata=getFormData('editForm');

    $.ajax({
        url: baseUrl + '/mdtApply/saveApplyOpinion',
        data:formdata,
        dataType:'json',
        type:'post',
        success:function(value){
            if(value.type == 'success') {

                var mylay = parent.layer.getFrameIndex(window.name);
                parent.layer.close(mylay);
            } else {

                $.messager.alert('提示',value.message);
            }
        }
    });
}