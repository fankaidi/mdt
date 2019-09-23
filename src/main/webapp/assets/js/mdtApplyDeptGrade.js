var id;
var type;
$(function(){

    id = getQueryVariable("id");
    type = getQueryVariable("type");
    if("view" == type){
    	$("#btn1").hide();
    	$("#btn2").hide();
    }
    if(id != undefined && id != null){
        initData(id);
        
    }
});

function save() {
	var formdata=getFormData('editForm');
	formdata.id =id;
	 $.ajax({
        url: baseUrl + '/mdtApply/saveDeptGrade',
        data:formdata,
        dataType:'json',
        type:'post',
        success:function(value){
            if(value.type == 'success'){
            	 alert('保存成功');
            } else {
                $.messager.alert('提示',value.message);
            }
        }
	 });
}

//会诊意见书打印
function informFun() {
    layer.open({
        type: 2,
        title: '会诊意见书打印',
        maxmin: true,
        shadeClose: true, //点击遮罩关闭层
        area : ['100%' , '100%'],
        content: 'mdtApplyYijian.html?id=' + id
    });
}

/**
 * 编辑
 */
function initData(id){
	var loginuser = getUser();	
    $.ajax({
        url: baseUrl + '/mdtApply/detail?id='+id,
        dataType:'json',
        type:'post',
        success:function(value){
            if(value.type == 'success'){
            	var row = value.resultData.row;
            	if(row.applyPersonId != loginuser.id && (loginuser.teamIds && loginuser.teamIds.indexOf(row.teamId) == -1)){
            		$('#dg').hide();
            	}
                $('#editForm').form('load', value.resultData.row);
            }
        }
    });
}
