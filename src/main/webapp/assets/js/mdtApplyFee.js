
$(function(){

    var url = window.location.href;
    id = url.split("id=")[1];
    if(id != undefined && id != null){
        initData(id);

        calculateFee(id);
    }
});

/**
 * 初始化数据
 */
function initData(id){
    $.ajax({
        url: baseUrl + '/mdtApply/get?id='+id,
        dataType:'json',
        type:'post',
        success:function(value){
            if(value.type == 'success'){
                // $('#editForm').form('load', value.resultData.row);

                var data = value.resultData.row;
                $("#name").html("&nbsp;&nbsp;&nbsp;" + data.name + "&nbsp;&nbsp;&nbsp;");
                $("#mdtDate").html(data.mdtDate + "&nbsp;&nbsp;&nbsp;");
                $("#mdtLocation").html(data.mdtLocation + "&nbsp;&nbsp;&nbsp;");
                $("#applyPerson").html(data.applyPerson + "&nbsp;&nbsp;&nbsp;");
                $("#applyCreatetime").html(data.applyCreatetime + "&nbsp;&nbsp;&nbsp;");
                $("#printDate").html(getDate() + "&nbsp;&nbsp;&nbsp;")

            }
        }
    });
}

/**
 * 计算费用
 * @param id
 */
function calculateFee(applyId) {
    $.ajax({
        url: baseUrl + '/mdtApply/calculateFee?applyId='+applyId,
        dataType:'json',
        type:'post',
        success:function(value){
            if(value.type == 'success'){
                $("#fee").html(value.resultData.row);
            }
        }
    });
}

function printW() {
    $("#printW").hide();

    window.print();

    $("#printW").show();
}

function getDate(){

    var myDate = new Date();

    //获取当前年
    var year = myDate.getFullYear();

    //获取当前月
    var month = myDate.getMonth() + 1;

    //获取当前日
    var date = myDate.getDate();
    var h = myDate.getHours(); //获取当前小时数(0-23)
    var m = myDate.getMinutes(); //获取当前分钟数(0-59)
    var s = myDate.getSeconds();

    //获取当前时间
    var now = year + '-' + conver(month) + "-" + conver(date) + " " + conver(h) + ':' + conver(m) + ":" + conver(s);
    return now;
}

//日期时间处理
function conver(s) {
    return s < 10 ? '0' + s : s;
}
