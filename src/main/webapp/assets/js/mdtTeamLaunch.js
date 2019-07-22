var applyId;  // 团队表主id

$(function() {

    var url = window.location.href;
    applyId = url.split("applyId=")[1];
    if(applyId != undefined && applyId != null){
        $("#applyId").val(applyId)
    }

    setMdtTeam();

    initGrid1('0')
});


function initGrid1(teamId) {

    var columns=[[
        /*{field:'id',title:'编号',width:100},*/
        {field:'name',title:'专家姓名',width:100},
        {field:'department',title:'科室',width:100},
        {field:'title',title:'职称',width:100},
        {field:'phone',title:'联系方式',width:100},
        {field:'specialistType',title:'专家类型',width:100,formatter:function(value,row,index) {
            if (row.specialistType == '1') {
                return "首席专家";
            } else if (row.specialistType == '2') {
                return "团队副组长";
            } else if (row.specialistType == '3') {
                return "团队秘书";
            } else if (row.specialistType == '4') {
                return "专家";
            }
            return '';
        }},
        {field:'-',title:'操作',width:100,formatter:function(value,row,index) {
            return "<a href='#' onclick='choose("+row.id+")'>选取</a>";
        }}
    ]];

    //表格数据初始化
    $('#grid1').datagrid({
        title:'MDT团队基本信息（多人明细）',
        url:baseUrl + '/mdtTeam/findTeamInfoByTeamId?teamId=' + teamId,
        loadFilter: function(data){
            return data.resultData;
        },
        columns:columns,
        singleSelect:true,
        rownumbers:true
    });
}


// 设置科室下拉框
function setMdtTeam() {

    $('#mdtTeam').combobox({
        url: baseUrl + "/mdtTeam/findAllMdtTeam",
        loadFilter: function(data){
            return data.resultData.rows;
        },
        // filter: function(q, row){
        //     var opts = $(this).combobox('options');
        //     return row[opts.textField].indexOf(q) >= 0;//这个方法，即可实现模糊查询
        // },
        onSelect: function(param){
            console.log(param)
            initGrid1(param.id)

            // var url = baseUrl + "/mdtTeam/findAllMdtTeam?id=" + 1;
            // $('#mdtTeam').combobox('reload', url);
        },
        // hasDownArrow:false,
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
function choose(teamInfoId){

    $.ajax({
        url: baseUrl + '/mdtApply/addApplyDoctorFormTeam?teamInfoId='+teamInfoId + "&applyId=" + applyId,
        dataType:'json',
        type:'post',
        success:function(value){

            $.messager.alert('提示',value.message);
            window.parent.doSearch();
        }
    });
}