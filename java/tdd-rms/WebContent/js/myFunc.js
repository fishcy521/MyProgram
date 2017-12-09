String.prototype.trim=function(){
    return this.replace(/(^\s*)|(\s*$)/g,"");
}
String.prototype.myLength = function() {
    ///<summary>获得字符串实际长度，中文2，英文1</summary>
    ///<param name="str">要获得长度的字符串</param>
    var realLength = 0, len = this.length, charCode = -1;
    for (var i = 0;i < len; i++ ) {
        charCode = this.charCodeAt(i);
        if (charCode >= 0 && charCode <= 128) realLength += 1;
        else realLength += 2;
    }
    return realLength;
};
//判断日期是否合法 
function IsDate(dateValue) { 
	var regex = new RegExp("^(?:(?:([0-9]{4}(-|\/)(?:(?:0?[1,3-9]|1[0-2])(-|\/)(?:29|30)|((?:0?[13578]|1[02])(-|\/)31)))|([0-9]{4}(-|\/)(?:0?[1-9]|1[0-2])(-|\/)(?:0?[1-9]|1\\d|2[0-8]))|(((?:(\\d\\d(?:0[48]|[2468][048]|[13579][26]))|(?:0[48]00|[2468][048]00|[13579][26]00))(-|\/)0?2(-|\/)29))))$");
	if (!regex.test(dateValue))
		return false;
	else
		return true;
}
//校验是否是数字类型
function isNumber(num) {
 	  var re=new RegExp("^-?[\\d]*\\.?[\\d]*$");
 	  if(re.test(num))
      	 return(!isNaN(parseFloat(num)));
     else
      	 return(false);
}
//比较精确的浮点数相加
function floatAdd(arg1,arg2){
	var r1,r2,m;
	try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
	try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
	m=Math.pow(10,Math.max(r1,r2))
	return (arg1*m+arg2*m)/m
}
/** 获得所选 复选框的数据 获得主键值
* @return {}
*/
function getSelectRowIds(){
	var ids=new Array();
	//myGrid.id 主键
	var chks=document.getElementsByName("gt_"+mygrid.id+"_chk_chk");
	for(var i = 1 ; i < chks.length ; i++){
		if (chks[i].checked) { //被选中状态
		   ids.push(chks[i].value); 
		} 
	}
	return ids;
}

/** 获得所选GRID 对话框的数据 
* @return {}
*/
function getMyDialogData(){
	var itemName=$("#my_itemName").val();
	$("#my_value_input").val("");
	$.ajax({
        url: "items!query.action?itemName="+itemName,
        type: "POST",
        data: {cmd: "jsonList",itemName:itemName},
        success: function(data) {
         	var json = eval(data); 
            $("#my_show_content").empty(); //清除dd的信息
            $("#my_show_content").append("<table id=my_show_table style=\"border:0px solid #cccccc;background-color:#f3f3f3;padding:5px;width:100%;\"><tr><td>选择</td><td>类型ID</td><td>级别</td><td>名称</td></tr></table>");
            $.each(json, function(i,e) {
               $("#my_show_table").append("<tr><td><input type='checkbox' id='my_data_rd' onclick=\"document.all.my_value_input.value='"+e.pmsItemId+"'\" /></td><td>"+e.pmsItemId+"</td><td>"+e.itemLevel+"</td><td>"+e.itemName+"</td></tr>");
            });		            
        }
    });
}
/** 删除选中记录，cellsnum为对象ID列号，url是删除URL地址
* @return {}
*/
function deleteSelectInfo(cellnum,url,btnObj){
	var selcetVals="";
	var rows=mygrid.selectedRows;
	for(var i=0;i<rows.length;i++){
		if(selcetVals=="")
			selcetVals=mygrid.selectedRows[i].cells[cellnum].innerText;
		else
			selcetVals+=","+mygrid.selectedRows[i].cells[cellnum].innerText;
	}
	if(selcetVals==""){
		alert("抱歉，请先选择需要删除的行。");return false;
	}
	if(confirm("确定要删除选中的内容吗？")){
		if(btnObj!=null){btnObj.disabled=true;}
		$.ajax({
	        url: url,
	        type: "POST",
	        data: {cmd:"",selcetVals:selcetVals},
	        success: function(data) {
	        	$("#message").empty(); 
	            $("#message").append("<div class='msgsuccess'>"+data.exception+"</div>"); 
	            mygrid.reload();
	            if(btnObj!=null){btnObj.disabled=false;}
	        },
	        error:function(data) {
	            $("#message").empty();
	            $("#message").append("<div class='msgsuccess'>"+data.exception+"</div>"); 
	            //mygrid.refresh();          
	        }
	    });
    }
}
function openSubWindow(url,width,height,isScroll){
	var winstyle = "status:no;resizable:yes;dialogHeight:350px;dialogWidth:450px;help:no;";
	if(width!=null&&width>0){
		winstyle = "status:no;resizable:yes;dialogHeight:"+height+"px;dialogWidth:"+width+"px;help:no;";
	}
	if(isScroll!=null&&!isScroll){
		winstyle+="scroll:no;";
	}else{
		winstyle+="scroll:yes;";
	}
	//var url="items!select.action";
	return window.showModalDialog(url,window,winstyle);
}
function openWindow(url,width,height){
	var winstyle = "height=300, width=700, top=200, left=200, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no";
	if(width!=null&&width>0){
		winstyle = "height="+height+",width="+width+",top=200, left=200,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no";
	}
	window.open(url,'',winstyle);
}


function jumpPage(pageNo) {
	$("#pageNo").val(pageNo);
	$("#mainForm").submit();
}
function sort(orderBy, defaultOrder) {
	if ($("#orderBy").val() == orderBy) {
		if ($("#order").val() == "") {
			$("#order").val(defaultOrder);
		}
		else if ($("#order").val() == "desc") {
			$("#order").val("asc");
		}
		else if ($("#order").val() == "asc") {
			$("#order").val("desc");
		}
	}
	else {
		$("#orderBy").val(orderBy);
		$("#order").val(defaultOrder);
	}

	$("#mainForm").submit();
}
function search() {
	$("#order").val("");
	$("#orderBy").val("");
	$("#pageNo").val("1");
	$("#mainForm").submit();
}

/** 
 * 用来查看一个对象的属性 
 */  
function debugObjectInfo(obj){  
   traceObject(obj);  
   function traceObject(obj){   
           var str = '';  
           if(obj!=null&&obj.tagName&&obj.name&&obj.id)  
           str="<table border='1' width='100%'><tr><td colspan='2' bgcolor='#ffff99'>traceObject 　　tag: &lt;"+obj.tagName+"&gt;　　 name = \""+obj.name+"\" 　　id = \""+obj.id+"\" </td></tr>";   
           else{  
                   str="<table border='1' width='100%'>";   
           }  
           var key=[];   
           for(var i in obj){   
                   key.push(i);   
           }   
           key.sort();   
           for(var i=0;i<key.length;i++){   
                   var v= new String(obj[key[i]]).replace(/</g,"&lt;").replace(/>/g,"&gt;");   
                   str+="<tr><td valign='top'>"+key[i]+"</td><td>"+v+"</td></tr>";   
           }   
           str=str+"</table>";   
           writeMsg(str);   
   }   
   function trace(v){   
           var str="<table border='1' width='100%'><tr><td bgcolor='#ffff99'>";   
           str+=String(v).replace(/</g,"&lt;").replace(/>/g,"&gt;");   
           str+="</td></tr></table>";   
           writeMsg(str);   
   }   
   function writeMsg(s){   
         traceWin=window.open("","traceWindow","height=600, width=800,scrollbars=yes");   
         traceWin.document.write(s);   
   }   
}
function hideOrShow(objid){
	var obj=document.getElementById(objid)
	if(obj.style.display==""){
		obj.style.display="none";
	}else{
		obj.style.display="";
	}
}
function closeWindow() {
	var browserName = navigator.appName;
   	if (browserName == "Netscape") {
       window.open('', '_self', '');
       window.close();
   	}
    else {
       if (browserName == "Microsoft Internet Explorer") {
           window.open('', '_parent', '');
           window.close();
       }
    }
  }