var id="";//保存提交的方法名称

$(function(){

    var url = window.location.href;
    id = url.split("id=")[1];
    if(id != undefined && id != null){
        initData(id);
    }

});

/**
 * 编辑
 */
function initData(id){
    $.ajax({
        url: baseUrl + '/set/getSysMsgTemp?id='+id,
        dataType:'json',
        type:'post',
        success:function(value){

            if(value.type == 'success'){
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
        url: baseUrl + '/set/saveSysMsgTemp',
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