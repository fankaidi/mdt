
//通用上传文件方法2019
function uploadFileKS(callback,url) {
    var file;
    var form = $('<form action="'+url+'" method="post"></form>');
    file = $('<input type="file" name="file" style="display:none"/>');
    $('body').append(form);
    $(form).append(file);
    file.bind('change', function () {form.submit();});
    file.attr("multiple", "multiple");
    form.ajaxForm({
        beforeSubmit: function () {
            var check = true;
            return check;
        },
        success: function (data) {
            $("#loading").fadeOut();
            if (!data.resultData) {
            	alert(data.message);
            } else if (callback != null) {  
            	callback(data); 
            }
            form.remove();
        },
        error: function () {    	
            $("#loading").fadeOut();
            alert("上传失败");
            form.remove();
        }
    });
    file.click();
}


