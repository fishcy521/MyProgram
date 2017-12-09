<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<html>
<head>
	<title>桃多多接口管理</title>
<%@ include file="/common/meta.jsp" %>
<%@ include file="/common/grid3.jsp" %>
<script type="text/javascript" >

$(document).ready(function(){
	
	$("#btn_add").click(function(){
		
		//window.location.href="${ctx_tdd}/category!input.action";
		openWindow('${ctx_tdd}/category!input.action',1920,1024);
	});
	
	$("#btn_query").click(function(){
		
		search();
	});
});

var dsOption= {
	fields :[
		{name:'icateId'},
		{name:'icateName'}
	],
	recordType:'object'
};

var colsOption = [
//{id:'id',header:'id',  hidden:true},
//{id:'departmentid',header:'departmentid',hidden:true},
//{id:'managerid',header:'managerid',hidden:true},
{id:'icateId',header:'icateId',  hidden:true},
//{id:'IName',header:'接口名称',headAlign:'center',align:'center',width:150},
{id:'icateName',header:'接口分类名称',headAlign:'center',align:'left',width:250},
{id:'operation',header:'操作',headAlign:'center',align:'center',width:160,
	renderer:function(value,record,columnObj,grid,colNo,rowNo){
		var icateId=record["icateId"];
		return "<div style='vertical-align:middle;'>"
		       +"<input type='button'value='修改' class='rmsBtnClass' onclick=\"viewInfo('"+icateId+ "','update')\" />"
		       +"<input type='button'value='删除' class='rmsBtnClass' onclick=\"viewInfo('"+icateId+ "','delete')\" />"
		       +"</div>";
				}
			} ];
	var gridOption = {
		id : "myGrid1",
		loadURL : '${ctx_tdd}/category!gridData.action',
		language : "cn",
		width : "100%",
		height : "700",
		container : 'grid_box',
		remotePaging : true,
		replaceContainer : true,
		dataset : dsOption,
		columns : colsOption,
		toolbarContent : 'nav goto | pagesize | reload | state| xls',
		skin : "default",
		pageSize : default_page_size,
		pageSizeList : default_page_size_list,
		allowCustomSkin : true,
		allowFreeze : true,
		allowGroup : true,
		showIndexColumn : true,
		resizable : true,
		stripeRows : true,
		showGridMenu : true,
		allowHide : true,
		//根据checkbox多选
		// multiSelect:true,
		// selectRowByCheck:true,
		parameters : {
			paramICateName : '${param.iCateName}'
		},
		exportURL : 'capital-exp!export.action',
		exportFileName : 'alm_project_capital_exp.xls'
	};
	var mygrid = new Sigma.Grid(gridOption);
	Sigma.Util.onLoad(Sigma.Grid.render(mygrid));

	function viewInfo(id,operate_type) {
		
		if(operate_type=='update') {
			// window.location.href='category!input.action?ICateId='+id;
			openWindow('category!input.action?ICateId='+id,1920,1024);
		}
		else if (operate_type=='delete'){
			window.location.href='category!delete.action?ICateId='+id;
		}
		/**
		var url="cupaa!sync.action?userGuidView="+userGuidView+"&parentGuidView="+parentGuidView+"&viewGuidView="+viewGuidView;
		
		$.ajax({
		    url: url,
		    type: "POST",
		    data: {cmd:""},
		    success: function(data) {
		    	$("#message").empty(); 
		        $("#message").append("<div class='msgsuccess'>"+data.success+"</div>"); 
		        //mygrid.reload();
		        if(btnObj!=null){btnObj.disabled=false;}
		    },
		    error:function(data) {
		        $("#message").empty();
		        $("#message").append("<div class='msgsuccess'>"+data.exception+"</div>"); 
		        //mygrid.refresh();          
		    }
		});
		 */
		//alert("url="+url);
		//openSubWindow(url,800,600);
		//window.location.reload();
	}
	// 查询按钮事件
	function btn_search_onclick() {
		//$("#erpType").val("");
		//$("#projectId").val("");
		////$("#orgId").val("");
		search();
		// window.location.href="capital-exp.action?erpType=&orgId=&projectId=&projectType=";
	}
</script>

</head>
<body >
	<%@ include file="/common/header.jsp"%>
	<div id="message"><s:actionmessage theme="custom" cssClass="msgsuccess"/></div>
<form id="mainForm" action="${ctx_tdd}/category.action" method="post">

<table class="mytable" width="98%">
	<tr><td class="left_title">
		<img src="${ctx}/images/dottt.png" border="0"/>查询条件
	</td></tr>
</table>
<table class="mytable" width="98%" id="capital_condiction" style="display:">
	<tr>
		<td class="table_title" width="120px">接口分类名称:</td>
		<td class="table_content" width="125px">
			<input type="text" id="iCateName" name="iCateName" value="${param.iCateName}"/>
		</td>
		<td class="table_content">
			<input class="rmsBtnClass" type="button" id="btn_query" value="查询" />
			<input class="rmsBtnClass" type="button" id="btn_add" value="新增" />
		</td>
	</tr>
</table>
</form>
<div align="center"><font size="5" style="font-weight: bolder">桃多多接口分类信息</font></div>
<div id="grid_box" style="border:0px solid #cccccc;background-color:#f3f3f3;padding:5px;width:100%;height: 100%"></div>
</body>
</html>