
$(function(){

    var url = window.location.href;
    id = url.split("id=")[1];
    if(id != undefined && id != null){
        initData(id);

        $("#applyId").val(id)
        $("#userId").val(getUser().id)
        $("#userName").val(getUser().name)
    }

    getExpertGradeItem();
});

/**
 * 编辑
 */
function initData(id) {
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

/**
 * 获取专家打分项目
 */
function getExpertGradeItem(){
    $.ajax({
        url: baseUrl + '/mdtApply/getGradeItem?type=' + 1,
        dataType:'json',
        type:'post',
        success:function(value){
            if(value.type == 'success'){
                genItem(value.resultData.rows)
            }
        }
    });
}

function genItem(data) {

    for (var i=0; i< data.length; i++) {

        var tr = '<tr id="tr'+ (i+1) +'"> '
            + '     <td>'+ data[i].description +'（'+ data[i].minValue +'-'+ data[i].maxValue +'分）:</td> '
            + ' <td> '
            + ' <input style="display: none" name="list['+ i +'].gradeId" value="'+ data[i].id +'"> '
            + ' <input style="display: none" name="list['+ i +'].description" value="'+ data[i].description +'"> '
            + ' <input style="display: none" name="list['+ i +'].minValue" value="'+ data[i].minValue +'"> '
            + ' <input style="display: none" name="list['+ i +'].maxValue" value="'+ data[i].maxValue +'"> '
            + ' <input name="list['+ i +'].grade" class="easyui-numberspinner" data-options="required: true" style="width:80px;"> '
            + '     </td> '
            + '    </tr>';

        $("#table").append(tr)
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
        url: baseUrl + '/mdtApply/saveExpertGrade',
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