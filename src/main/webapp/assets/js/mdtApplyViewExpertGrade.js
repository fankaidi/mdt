var applyId;

$(function(){

    applyId = getQueryVariable("id");

    if(applyId != undefined && applyId != null){
        // initData(id);

        getGrade();

        getAllApplyOpinion();

        getApply();
    }

});

function getGrade() {
    // selectSysGrade
    $.ajax({
        url: baseUrl + '/set/selectSysGrade?type=1',
        data:{},
        dataType:'json',
        type:'post',
        success:function(value){

            if(value.type == 'success'){
                initGrid1(value.resultData.rows);
            }
        }
    });
}

function getAllApplyOpinion() {
    $.ajax({
        url: baseUrl + '/mdtApply/getAllApplyOpinion?applyId=' + applyId,
        data:{},
        dataType:'json',
        type:'post',
        success:function(value){

            if(value.type == 'success'){

                var data = value.resultData.rows;

                var html = '';
                for (var i=0; i<data.length; i++) {

                    html += data[i].department + "  " + data[i].username + ":  " + data[i].content + " \n";

                }
                html += "\n";

                // $("#tr1").html(html)
                $("#summary").html(html)
            }
        }
    });
}

function getApply() {
    $.ajax({
        url: baseUrl + '/mdtApply/get?id=' + applyId,
        data:{},
        dataType:'json',
        type:'post',
        success:function(value){

            if(value.type == 'success'){

                var data = value.resultData.row;
                // $("#tr2").html(data.summary)
                $("#content").html(data.summary)
            }
        }
    });
}

/**
 * 生成 MDT拟请专家列表
 * @param applyId
 */
function initGrid1(data) {

    var columns=[];
    var cols = [
        /*{field:'id',title:'编号',width:100},*/
        {field:'name',title:'专家名称',width:100},
        /*{field:'department',title:'科室',width:200},
         {field:'title',title:'职称',width:200},
         {field:'phone',title:'联系方式',width:200},
         {field:'phoneCornet',title:'手机短号',width:200}*/
        {field:'reply',title:'回复',width:100},
        {field:'replyTime',title:'回复时间',width:200},
        {field:'g',title:'评分',width:800,formatter:function(value,row,index) {

            if (row.list.length > 0) {

                var val = '';
                for (var i=0; i<data.length; i++) {
                    val += row.list[i].description + ": " + row.list[i].grade + "  <br> ";
                }
                return val;
            }

            return "";
        }}
    ];

    for (var i=0; i<data.length; i++) {

        var col = {field:'name1',title:'',width:150,formatter:function(value,row,index) {
            if (row.list.length > 0) {

                console.log(row.list[i])
                console.log(i)
                console.log(value)
                console.log(row)
                console.log(index)
                // return row.list[i].grade;
                return "";
            }
            return "";
        }};

        col.title = data[i].description;

        // cols.push(col);
    }


    columns.push(cols);

    //表格数据初始化
    $('#grid1').datagrid({
        title:'拟请MDT参加专家',
        url:baseUrl + '/mdtApply/listExpertGradeByApplyId?applyId=' + applyId,
        loadFilter: function(data){
            return data.resultData;
        },
        columns:columns,
        singleSelect:true,
        rownumbers:true

    });
}
