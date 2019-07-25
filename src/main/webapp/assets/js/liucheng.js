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
			var temp = getItem(xz);
			tuxing+=temp;
			if(xz.child){	
				tuxing += '<img src="images/liucheng/next1.png" alt="">';
				var lengthint = xz.child.length*75;
				tuxing += "<span style='height: "+lengthint+"px;width:70px;display: inline-block;'>";
				for(var j=0;j<xz.child.length;j++){
					var ch = xz.child[j]		
					var chtemp = getItem(ch);
				    tuxing+=chtemp;
				}
				tuxing += "</span>";
			}	
			if(i != talbeobj.data.length-1){
				tuxing += '<img src="images/liucheng/next1.png" alt="">';
			}
		}
		tuxing = '<div class="process_four"><div class="apply_content fl"><div class="process">'+tuxing+'</div></div></div>';	
		$('#'+talbeobj.divid).append(tuxing);
	};
	
	var getItem = function(item){
		var cname = talbeobj.status[item.id];
		if(!cname){
			cname = "pad16"
		}else if("show" == cname){
			cname = "pad16 "+cname;
		}
		var temp = '<span class="circle '+cname+'" data-num="'+item.id+'">'+item.name+'</span>';
		return temp;
	};
	
	return talbeobj;
};

