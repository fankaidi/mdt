
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

    var formdata=getFormData('editForm');

    $.ajax({
        url: baseUrl + '/user/save',
        data:formdata,
        dataType:'json',
        type:'post',
        success:function(value){
        	debugger
            if(value.type == 'success'){
            	$.messager.alert('提示',value.message);
                var mylay = parent.layer.getFrameIndex(window.name);
                parent.layer.close(mylay);
                window.parent.doSearch();
            }  
        }

    });
}

function intDepartment() {
	$('#title').combobox({
        url: baseUrl + "/set/getCodeByType?type=2",
        loadFilter: function(data){
            return data.resultData.rows;
        },
        valueField:'value',
        textField:'value'
    });
}