var id;  // id
var applyId;  // MDT申请主id

$(function() {

	id = getQueryVariable("id");
	applyId = getQueryVariable("applyId");
    if(id){
        edit(id);
    }

    $("#applyId").val(applyId);

    setDepartment();

    setUser();
});


// 设置科室下拉框
function setUser() {

    $('#userSelect').combobox({
        url: baseUrl + "/user/listAll",
        loadFilter: function(data){
            return data.resultData.rows;
        },
        onSelect: function(param){
            getUser(param.id)
        },
        valueField:'id',
        textField:'name'
    });
}

function getUser(id) {
    $.ajax({
        url: baseUrl + '/user/get?id='+id,
        dataType:'json',
        type:'post',
        success:function(value){

            if(value.type == 'success'){
                setTeamInfoFrom(value.resultData.row);
            } else {
                $.messager.alert('提示',value.message);
            }
        }
    });
}

function setTeamInfoFrom(user) {
    var myObject = {};
    myObject.userId = user.id;
    myObject.name = user.name;
    myObject.department = user.department;
    myObject.phone = user.phone;
    myObject.phoneCornet = user.phoneCornet;
    myObject.title = user.title;

    console.log(myObject)

    $('#editForm').form('load', myObject);
}


// 设置科室下拉框
function setDepartment() {

    $('#department').combobox({
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
            }
            return text;
        },
        valueField:'id',
        textField:'name'
    });
}

//保存
function save() {

    //判断：编辑表单的所有控件是否都通过验证
    var isValidate= $('#editForm').form('validate');
    if(isValidate==false){
        return ;
    }

    var formdata=getFormData('editForm');

    $.ajax({
        url: baseUrl + '/mdtApply/addApplyDoctor',
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
function edit(id){
    $.ajax({
        url: baseUrl + '/mdtApply/getApplyDoctor?id='+id,
        dataType:'json',
        type:'post',
        success:function(value){

            if(value.type == 'success'){
                $('#editForm').form('load', value.resultData.row);
            } else {
                $.messager.alert('提示',value.message);
            }
        }
    });
}