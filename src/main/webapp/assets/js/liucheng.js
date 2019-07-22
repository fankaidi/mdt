//封装流程类
function createLiucheng(divid,status) {
	var talbeobj ={};
	talbeobj.divid = divid;
	talbeobj.data = [];
	talbeobj.status = status;
	
	talbeobj.init = function(){
		var tuxing = "";
		for(var i=0;i<talbeobj.data.length;i++){
			var xz = talbeobj.data[i];
			var cname = "pad16";
			if(status == i){
				cname = "active"
			}	
			var temp = '<span class="circle '+cname+'" data-num="'+i+'" src="images/liucheng/next1.png">'+xz.name+'</span>';
			if(i != talbeobj.data.length-1){
			 temp += '<img src="images/liucheng/next1.png" alt="">';
			}
			tuxing += temp;
		}
		tuxing = '<div class="process_four"><div class="apply_content fl"><div class="process">'+tuxing+'</div></div></div>';
		
		$('#'+talbeobj.divid).append(tuxing);
	};
	return talbeobj;
};
