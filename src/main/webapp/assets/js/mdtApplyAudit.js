var id;

$(function(){

    var url = window.location.href;
    id = url.split("id=")[1];
    if(id != undefined && id != null){
        // 初始化数据
        initData(id);
    }
});

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
                // $('#editForm').form('load', value.resultData.row);
                init(value.resultData.row);
            }
        }
    });
}

function init(data) {

    $("#id").val(data.id);

    // 住院病人，需要审核
    if (data.patientType == '1') {

        if (data.applyStatus == '0') {
            $("#audit1").show();
            $("#audit2").show();
        } else if (data.applyStatus == '1') {
            $("#audit3").show();
            $("#audit4").show();
        }
    }
}

function save() {

    //判断：编辑表单的所有控件是否都通过验证
    var isValidate= $('#editForm').form('validate');
    if(isValidate==false){
        return ;
    }

    var formdata=getFormData('editForm');

    $.ajax({
        url: baseUrl + '/mdtApply/audit',
        data:formdata,
        dataType:'json',
        type:'post',
        success:function(value){

            if(value.type == 'success'){
                var mylay = parent.layer.getFrameIndex(window.name);
                parent.layer.close(mylay);

                window.parent.doSearch();
            }
            $.messager.alert('提示',value.message);
        }
    });
}