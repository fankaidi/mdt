var baseUrl = "/mdt";

$(function(){

    // getUser();
});

function getQueryVariable(variable) {
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i=0;i<vars.length;i++) {
        var pair = vars[i].split("=");
        if(pair[0] == variable){return pair[1];}
    }
    return null;
}

function getUser() {

    var user = '';

    $.ajax({
        url: baseUrl + '/auth/getUser',
        data:{},
        async: false,
        dataType:'json',
        type:'post',
        success: function (value) {
            if (value.type == 'success') {

                user = value.resultData.row;

                if (value.resultData.row == null) {

                    alert("登录超时，请重新登录");
                    location.href = "login.html";
                }
            } else {

                alert(value.message);
            }
        }
    });

    return user;
}

function layerOpen(title, content) {
    layer.open({
        type: 2,
        title: title,
        maxmin: true,
        shadeClose: true, //点击遮罩关闭层
        area : ['80%' , '80%'],
        content: content
    });
}