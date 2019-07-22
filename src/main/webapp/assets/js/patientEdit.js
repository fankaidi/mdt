var id = null;

$(function(){

    $("tr[id^='outpatient']").each(function () {
        $(this).show();
    });
    $("tr[id^='hospital']").each(function () {
        $(this).hide();
    });

    id = getQueryVariable("id");

    if(id != undefined && id != null){
        // 初始化数据
        initData(id);
    }

	//条件查询
	$('#btnSearch').bind('click',function(){
		var formdata=getFormData('searchForm');
		$('#grid').datagrid('load',formdata);
	});

});

//保存
function save() {
    //判断：编辑表单的所有控件是否都通过验证
    var isValidate= $('#editForm').form('validate');
    if(isValidate==false){
        return ;
    }

    var formdata=getFormData('editForm');

    $.ajax({
        url: baseUrl + '/patient/save',
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

/**
 * 编辑
 */
function initData(id){
    $.ajax({
        url: baseUrl + '/patient/get?id='+id,
        dataType:'json',
        type:'post',
        success:function(value){

            if(value.type == 'success'){
                $('#editForm').form('load', value.resultData.row);

                changePatientType(value.resultData.row.patientType);
            }
        }
    });
}

function changePatientType(val) {

    var patientType;
    if (val) {
        patientType = val;
	} else {
        patientType = $("input[name='patientType']:checked").val();
	}

    if (patientType == '1') {

        $("tr[id^='outpatient']").each(function () {
            $(this).show();
        });

        $("tr[id^='hospital']").each(function () {
            $(this).hide();
        });

	} else {

        $("tr[id^='outpatient']").each(function () {
            $(this).hide();
        });

        $("tr[id^='hospital']").each(function () {
            $(this).show();
        });
	}
}