var id="";//保存提交的方法名称
var type = null;
$(function(){
	id = getQueryVariable("id");
	type = getQueryVariable("type");
    if(id != undefined && id != null){
        initData(id);
        if('view' == type){
        	 $('#btnSave').hide();
        }     
    }
});


/**
 * 编辑
 */
function initData(id){
    $.ajax({
        url: baseUrl + '/file/get.do?id='+id,
        dataType:'json',
        type:'post',
        success:function(value){
            if(value.type == 'success'){
            	var row = value.resultData.row;
                $('#editForm').form('load', row);
                var f = row.fileList[0];
                $('#filename').attr("href",f.filePath);
                $('#filename').html(f.fileName);   
            }
        }
    });
}

function uploadztpic(){
	uploadFileKS(function(p) {
		var rows = p.resultData.rows;
		var imgs = $("#filename");
		$.each(rows, function(i, row){     
			imgs.attr("href",row.url);   
			imgs.html(row.name);
		});
	}, baseUrl+"/file/addfiles.do");
}

//保存
function save() {
    //判断：编辑表单的所有控件是否都通过验证
    var isValidate= $('#editForm').form('validate');
    if(isValidate==false){
        return ;
    }
    var formdata= getFormData('editForm');
    var fileList = [];
    var filePath = $('#filename').attr("href");
    var fileName = $('#filename').html();
    var adb ={filePath:filePath,fileName:fileName};
    fileList.push(adb);
    
    formdata.fileList = JSON.stringify(fileList);
    
    $.ajax({
        url: baseUrl + '/file/save.do',
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