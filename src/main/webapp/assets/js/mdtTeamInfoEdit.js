var id;  // id
var teamId;  // 团队表主id

$(function() {
    id = getQueryVariable("id");
    teamId = getQueryVariable("teamId");
    if(id != undefined && id != null){
        edit(id);
    }else{
    	teaminfo();
    }
    $("#teamId").val(teamId);
    setUser();
    $('#title').combobox({
        url: baseUrl + "/set/getCodeByType?type=2",
        loadFilter: function(data){
            return data.resultData.rows;
        },
        valueField:'value',
        textField:'value'
    });
});

var change = false;
// 设置用户
function setUser() {
    $('#userSelect').combobox({
        url: baseUrl + "/user/listAll",
        loadFilter: function(data){
            return data.resultData.rows;
        },
        onSelect: function(param){
            getUserInfo(param.id);
            change = true;
        },
        onChange: function (n, o) {
        	if(change){
        		change = false;
        		return;
        	}
            var RangeType = n;//当前选择的[范围类型]         
            $.getJSON(baseUrl + "/user/listAll",
                {numberOrNameLike: RangeType },
                function (json) {
                    $("#userSelect").combobox("loadData",json);
            });
        },
        valueField:'id',
        textField:'remark'
    });
}

function getUserInfo(id) {
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



//保存
function save() {
    //判断：编辑表单的所有控件是否都通过验证
    var isValidate= $('#editForm').form('validate');
    if(isValidate==false){
        return ;
    }
    var formdata=getFormData('editForm');
    $.ajax({
        url: baseUrl + '/mdtTeam/saveTeamInfo',
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
function teaminfo(){
    $.ajax({
        url: baseUrl + '/mdtTeam/findTeamInfoByTeamId?teamId='+teamId,
        dataType:'json',
        type:'post',
        success:function(value){
            if(value.type == 'success'){
            	var rows = value.resultData.rows;
            	var myObject = {};
                myObject.specialistType = 1;         
            	if(rows){
            		for(var i=0;rows.length>i;i++){
            			var row = rows[i];
            			var specialistType = parseInt(row.specialistType);
            			if(specialistType <= myObject.specialistType){
            				myObject.specialistType ++;
            			}
                	}
            		if(myObject.specialistType > 4){
            			myObject.specialistType = 4;
            		}
            	}
                $('#editForm').form('load', myObject);
            } else {
                $.messager.alert('提示',value.message);
            }
        }
    });
}



/**
 * 编辑
 */
function edit(id){
    $.ajax({
        url: baseUrl + '/mdtTeam/getTeamInfo?id='+id,
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