
var id="";//保存提交的方法名称
$(function(){

    var url = window.location.href;
    id = url.split("id=")[1];
    if(id != undefined && id != null){
        initData(id);
    }

    intDepartment();

});

/**
 * 编辑
 */
function initData(id){
    $.ajax({
        url: baseUrl + '/user/get?id='+id,
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
        url: baseUrl + '/user/save',
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

function intDepartment() {

    $('#departmentSelect').combobox({
        url: baseUrl + "/org/listAll",
        loadFilter: function(data){
            return data.resultData.rows;
        },
        formatter: function(row){
            var opts = $(this).combobox('options');
            var value = row[opts.valueField]
            var text = row[opts.textField]

            var option = "";
            if (value.length == 1) {

            } else if (value.length == 2) {
                text = "----" + text;
            } else if (value.length == 4) {
                text = "--------" + text;
            } else if (value.length == 6) {
                text = "------------" + text;
            }
            return text;
        },
        valueField:'id',
        textField:'name'
    });
}