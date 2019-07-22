
window.onload = function(){
	$('#loading-mask').fadeOut();
}
var onlyOpenTitle="欢迎使用";//不允许关闭的标签的标题

var _menus;



	
$(function(){
	
	$.ajax({
		url: baseUrl + '/user/indexMenus',
		dataType:'json',
		success:function(value){
			_menus=value.resultData.row;
			InitLeftMenu();//初始化菜单
		}
		
	});
	getUser();
	tabClose();
	tabCloseEven();
	showName();
	
})

/**
 * 显示用户真实姓名
 */
function showName(){
	$.ajax({
		url:baseUrl + '/auth/getUser',
		dataType:'json',
		success:function(data){
			if(data.type == 'success'){
				$('#name').html(data.resultData.row.name);
			}else {
				location.href='login.html';
			}			
		}		
	});	
}

/**
 * 退出登陆
 */
function loginOut(){
	
	$.ajax({
		url:baseUrl + '/auth/logout',
		success:function(value){
			location.href="login.html";
		}		
	});
	
}


//初始化左侧
function InitLeftMenu() {
	$("#nav").accordion({animate:false,fit:true,border:false});
	var selectedPanelname = '';
	
	    $.each(_menus.menus, function(i, n) {
			var menulist ='';
			menulist +='<ul class="navlist">';
	        $.each(n.menus, function(j, o) {
				menulist += '<li><div ><a ref="'+o.id+'" href="#" rel="' + o.url + '" ><span class="icon '+o.icon+'" >&nbsp;</span><span class="nav">' + o.name + '</span></a></div> ';
				/*
				if(o.child && o.child.length>0)
				{
					//li.find('div').addClass('icon-arrow');
	
					menulist += '<ul class="third_ul">';
					$.each(o.child,function(k,p){
						menulist += '<li><div><a ref="'+p.menuid+'" href="#" rel="' + p.url + '" ><span class="icon '+p.icon+'" >&nbsp;</span><span class="nav">' + p.menuname + '</span></a></div> </li>'
					});
					menulist += '</ul>';
				}
				*/
				menulist+='</li>';
	        })
			menulist += '</ul>';
	
			$('#nav').accordion('add', {
	            title: n.name,
	            content: menulist,
					border:false,
	            iconCls: 'icon ' + n.icon
	        });
	
			if(i==0)
				selectedPanelname =n.name;
	
	    });
	
	$('#nav').accordion('select',selectedPanelname);



	$('.navlist li a').click(function(){
		var tabTitle = $(this).children('.nav').text();

		var url = $(this).attr("rel");
		var menuid = $(this).attr("ref");
		var icon = $(this).find('.icon').attr('class');

		var third = find(menuid);
		if(third && third.child && third.child.length>0)
		{
			$('.third_ul').slideUp();

			var ul =$(this).parent().next();
			if(ul.is(":hidden"))
				ul.slideDown();
			else
				ul.slideUp();



		}
		else{
			addTab(tabTitle,url,icon);
			$('.navlist li div').removeClass("selected");
			$(this).parent().addClass("selected");
		}
	}).hover(function(){
		$(this).parent().addClass("hover");
	},function(){
		$(this).parent().removeClass("hover");
	});





	//选中第一个
	//var panels = $('#nav').accordion('panels');
	//var t = panels[0].panel('options').title;
    //$('#nav').accordion('select', t);
}
//获取左侧导航的图标
function getIcon(menuid){
	var icon = 'icon ';
	$.each(_menus.menus, function(i, n) {
		 $.each(n.menus, function(j, o) {
		 	if(o.menuid==menuid){
				icon += o.icon;
			}
		 })
	})

	return icon;
}

function find(menuid){
	var obj=null;
	$.each(_menus.menus, function(i, n) {
		 $.each(n.menus, function(j, o) {
		 	if(o.menuid==menuid){
				obj = o;
			}
		 });
	});

	return obj;
}

function addTab(subtitle,url,icon){
	if(!$('#tabs').tabs('exists',subtitle)){
		$('#tabs').tabs('add',{
			title:subtitle,
			content:createFrame(url),
			closable:true,
			icon:icon
		});
	}else{
		$('#tabs').tabs('select',subtitle);
		$('#mm-tabupdate').click();
	}
	tabClose();
}

function createFrame(url)
{
	var s = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
	return s;
}

function tabClose()
{
	/*双击关闭TAB选项卡*/
	$(".tabs-inner").dblclick(function(){
		var subtitle = $(this).children(".tabs-closable").text();
		$('#tabs').tabs('close',subtitle);
	})
	/*为选项卡绑定右键*/
	$(".tabs-inner").bind('contextmenu',function(e){
		$('#mm').menu('show', {
			left: e.pageX,
			top: e.pageY
		});

		var subtitle =$(this).children(".tabs-closable").text();

		$('#mm').data("currtab",subtitle);
		$('#tabs').tabs('select',subtitle);
		return false;
	});
}


//绑定右键菜单事件
function tabCloseEven() {

    $('#mm').menu({
        onClick: function (item) {
            closeTab(item.id);
        }
    });

    return false;
}

function closeTab(action)
{
    var alltabs = $('#tabs').tabs('tabs');
    var currentTab =$('#tabs').tabs('getSelected');
	var allTabtitle = [];
	$.each(alltabs,function(i,n){
		allTabtitle.push($(n).panel('options').title);
	})


    switch (action) {
        case "refresh":
            var iframe = $(currentTab.panel('options').content);
            var src = iframe.attr('src');
            $('#tabs').tabs('update', {
                tab: currentTab,
                options: {
                    content: createFrame(src)
                }
            })
            break;
        case "close":
            var currtab_title = currentTab.panel('options').title;
            $('#tabs').tabs('close', currtab_title);
            break;
        case "closeall":
            $.each(allTabtitle, function (i, n) {
                if (n != onlyOpenTitle){
                    $('#tabs').tabs('close', n);
				}
            });
            break;
        case "closeother":
            var currtab_title = currentTab.panel('options').title;
            $.each(allTabtitle, function (i, n) {
                if (n != currtab_title && n != onlyOpenTitle)
				{
                    $('#tabs').tabs('close', n);
				}
            });
            break;
        case "closeright":
            var tabIndex = $('#tabs').tabs('getTabIndex', currentTab);

            if (tabIndex == alltabs.length - 1){
                alert('亲，后边没有啦 ^@^!!');
                return false;
            }
            $.each(allTabtitle, function (i, n) {
                if (i > tabIndex) {
                    if (n != onlyOpenTitle){
                        $('#tabs').tabs('close', n);
					}
                }
            });

            break;
        case "closeleft":
            var tabIndex = $('#tabs').tabs('getTabIndex', currentTab);
            if (tabIndex == 1) {
                alert('亲，前边那个上头有人，咱惹不起哦。 ^@^!!');
                return false;
            }
            $.each(allTabtitle, function (i, n) {
                if (i < tabIndex) {
                    if (n != onlyOpenTitle){
                        $('#tabs').tabs('close', n);
					}
                }
            });

            break;
        case "exit":
            $('#closeMenu').menu('hide');
            break;
    }
}


//弹出信息窗口 title:标题 msgString:提示信息 msgType:信息类型 [error,info,question,warning]
function msgShow(title, msgString, msgType) {
	$.messager.alert(title, msgString, msgType);
}




//设置登录窗口
function openPwd() {
    $('#w').window({
        title: '修改密码',
        width: 300,
        modal: true,
        shadow: true,
        closed: true,
        height: 160,
        resizable:false
    });
}
//关闭登录窗口
function closePwd() {
    $('#w').window('close');
}



//修改密码
function serverLogin() {
    var $newpass = $('#txtNewPass');
    var $rePass = $('#txtRePass');

    if ($newpass.val() == '') {
        msgShow('系统提示', '请输入密码！', 'warning');
        return false;
    }
    if ($rePass.val() == '') {
        msgShow('系统提示', '请在一次输入密码！', 'warning');
        return false;
    }

    if ($newpass.val() != $rePass.val()) {
        msgShow('系统提示', '两次密码不一至！请重新输入', 'warning');
        return false;
    }

    $.post('/ajax/editpassword.ashx?newpass=' + $newpass.val(), function(msg) {
        msgShow('系统提示', '恭喜，密码修改成功！<br>您的新密码为：' + msg, 'info');
        $newpass.val('');
        $rePass.val('');
        close();
    })
    
}

$(function() {

    openPwd();

    $('#editpass').click(function() {
        $('#w').window('open');
    });

    $('#btnEp').click(function() {
        serverLogin();
    })

	$('#btnCancel').click(function(){closePwd();})

   
});



$(function(){
    initGrid();
    initGrid10();
    initGrid1();
    initGrid2();
    initGrid3();
});


function initGrid10() {
    var columns=[[
        {field:'title',title:'标题',width:400},
        {field:'createdTimeStr',title:'创建时间',width:200},
        {field:'-',title:'操作',width:200,formatter:function(value,row,index) {
            return "<a href='#' onclick='fileView("+row.id+")'>查看</a>";
        }}
    ]];
    //表格数据初始化
    $('#grid10').datagrid({
        title:'资料下载',
        url:baseUrl + '/file/list.do',
        loadFilter: function(data){
            return data.resultData;
        },
        columns:columns,
        singleSelect:true,
        pagination:true

    });
}


/**
 * 查看
 */
function fileView(id){

    layer.open({
        type: 2,
        title: '资料信息',
        maxmin: true,
        shadeClose: true, //点击遮罩关闭层
        area : ['80%' , '80%'],
        content: 'sysFileEdit.html?type=view&id=' + id
    });
}


function initGrid1() {

    var columns=[[
		/*{field:'id',title:'编号',width:100},*/
        {field:'a1',title:'类型',width:100},
        {field:'a2',title:'姓名',width:100},
        {field:'a3',title:'MDT申请表',width:100},
        {field:'a4',title:'性别',width:100},
        {field:'a5',title:'出生日期',width:100},
        {field:'a6',title:'联系电话',width:100},
        {field:'a7',title:'申请科室',width:100},
        {field:'a8',title:'入院/首诊时间',width:100},
        {field:'a9',title:'住院/门诊号',width:100},
        {field:'a10',title:'申请人姓名',width:100}
    ]];

    //表格数据初始化
    $('#grid1').datagrid({
        title:'医务部未审核',
        url:baseUrl + '/mdtApply/list',
        loadFilter: function(data){
            return data.resultData;
        },
        columns:columns,
        singleSelect:true,
        pagination:true

    });
}

function initGrid2() {

    var columns=[[
		/*{field:'id',title:'编号',width:100},*/
        {field:'a1',title:'类型',width:100},
        {field:'a2',title:'姓名',width:100},
        {field:'a3',title:'MDT申请表',width:100},
        {field:'a4',title:'性别',width:100},
        {field:'a5',title:'出生日期',width:100},
        {field:'a6',title:'联系电话',width:100},
        {field:'a7',title:'申请科室',width:100},
        {field:'a8',title:'入院/首诊时间',width:100},
        {field:'a9',title:'住院/门诊号',width:100},
        {field:'a10',title:'申请人姓名',width:100}
    ]];


    //表格数据初始化
    $('#grid2').datagrid({
        title:'医务部审核未通过',
        url:baseUrl + '/mdtApply/list',
        loadFilter: function(data){
            return data.resultData;
        },
        columns:columns,
        singleSelect:true,
        pagination:true

    });
}

function initGrid3() {

    var columns=[[
		/*{field:'id',title:'编号',width:100},*/
        {field:'a1',title:'类型',width:100},
        {field:'a2',title:'申请科室',width:100},
        {field:'a3',title:'姓名',width:100},
        {field:'a4',title:'病历号',width:100},
        {field:'a5',title:'性别',width:100},
        {field:'a6',title:'出生日期',width:100},
        {field:'a7',title:'联系电话',width:100},
        {field:'a8',title:'MDT开始时间',width:100},
        {field:'a9',title:'MDT地点',width:100},
        {field:'a10',title:'MDT申请表',width:100},
        {field:'a11',title:'MDT通知',width:100},
        {field:'a12',title:'MDT知情同意书',width:100},
        {field:'a13',title:'专家建议汇总',width:100}
    ]];


    //表格数据初始化
    $('#grid3').datagrid({
        title:'医务部审核已通过',
        url:baseUrl + '/mdtApply/list3',
        loadFilter: function(data){
            return data.resultData;
        },
        columns:columns,
        singleSelect:true,
        pagination:true

    });
}

function edit(type, id) {
    var roleIds = getUser().roleIds;

    if (type == 'mdt_team') {
        // 普通用户
        if (roleIds.indexOf('7') != -1) {
            layerOpen('MDT团队','mdtTeamEdit.html?type=edit&id=' + id)
        } else {
            layerOpen('MDT团队审核','mdtTeamEdit.html?type=audit&id=' + id)
        }
    }
    if (type == 'mdt_apply') {
        // 普通用户
        if (roleIds.indexOf('7') != -1) {
            layerOpen('MDT申请', 'mdtApplyEdit.html?type=edit&id=' + id);
        } else {
            layerOpen('MDT申请审核', 'mdtApplyEdit.html?type=audit&id=' + id);
        }
    }
    if (type == 'mdt_team_objective') {
        layerOpen('MDT团队年度评估', 'mdtTeamAnnualAssessEdit.html?type=audit&teamId=' + id);
    }
    if (type == 'mdt_team_assess') {
        layerOpen('MDT团队满两年评估', 'mdtTeamTwoYearAssessEdit.html?type=audit&teamId=' + id);
    }
}

function initGrid() {

    var columns=[[
		/*{field:'id',title:'编号',width:100},*/
        {field:'busitypeStr',title:'类型',width:160},
        {field:'createdTimeStr',title:'申请时间',width:150},
        {field:'userid',title:'申请人',width:100,formatter:function(value,row,index) {
            return row.user.name;
        }},
        {field:'title',title:'内容',width:400},
        {field:'-',title:'操作',width:100,formatter:function(value,row,index) {
            var editBtn = "<a href='#' onclick='edit(\""+row.busitype+"\", "+row.bisiid+")'>处理</a> ";
            return editBtn;
        }}
    ]];


    //表格数据初始化
    $('#grid').datagrid({
        title:'代办',
        url:baseUrl + '/daiban/list.do',
        loadFilter: function(data){
            return data.resultData;
        },
        columns:columns,
        singleSelect:true

    });
}

function doSearch() {
    $('#grid').datagrid('load');
}