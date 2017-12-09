<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<html>
<head>
	<title>桃多多接口管理</title>
<%@ include file="/common/meta.jsp" %>
<%@ include file="/common/grid1.jsp" %>
<script type="text/javascript" >

$(document).ready(function(){
	
	$("#btn_add").click(function(){
		
		openWindow('${ctx_tdd}/function!input.action',1920,1024);
	});
	
	$("#btn_query").click(function(){
		
		search();
	});
});

var dsOption= {
	fields :[
		{name:'funcId'},
		{name:'funcName'},
		{name:'funcNameZh'},
		{name:'funcDesc'}
	],
	recordType:'object'
};

var colsOption = [
{id:'funcId',header:'funcId',  hidden:true},
{id:'funcName',header:'功能英文名称',headAlign:'center',align:'left',width:150},
{id:'funcNameZh',header:'功能中文名称',headAlign:'center',align:'left',width:150},
{id:'funcDesc',header:'功能描述',headAlign:'center',align:'left',width:300},
{id:'operation',header:'操作',headAlign:'center',align:'center',width:240,
	renderer:function(value,record,columnObj,grid,colNo,rowNo){
		var funcId=record["funcId"];

		var str_btn_json = "";
		
		return "<div style='vertical-align:middle;'>"
		+"<input type='button'value='修改' class='rmsBtnClass' onclick=\"viewInfo('"+funcId+ "','update')\" />"
		+"<input type='button'value='删除' class='rmsBtnClass' onclick=\"viewInfo('"+funcId+ "','delete')\" />"
		+str_btn_json
		+"</div>";
			}
		} ];
	var gridOption = {
		id : "myGrid1",
		loadURL : 'function!gridData.action',
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
			paramFuncName : '${param.funcName}',
			paramFuncNameZh : '${param.funcNameZh}',
			paramFuncDesc : '${param.funcDesc}'
		},
		exportURL : 'function!export.action',
		exportFileName : 'rms_function_list.xlsx'
	};
	var mygrid = new Sigma.Grid(gridOption);
	Sigma.Util.onLoad(Sigma.Grid.render(mygrid));

	function viewInfo(id, operate_type) {
		
		/**
		var str_param = "&iName=${param.iName}&iUrl=${parma.iUrl}&iDesc=${param.iDesc}"+
		"&iCateId=${param.iCateId}&hasParam=${param.hasParam}&status=${param.status}";
		*/
		
		if(operate_type=='update') {
			//window.location.href='interface!input.action?IId='+id;
			openWindow('function!input.action?funcId='+id,1920,1024);
		}
		else if (operate_type=='delete'){
			window.location.href='function!delete.action?funcId='+id;
		}
	}
</script>

</head>
<body >
<%@ include file="/common/header.jsp" %>
<div id="message"><s:actionmessage theme="custom" cssClass="msgsuccess"/></div>
<form id="mainForm" action="${ctx_tdd}/function.action" method="post">

<table class="mytable" width="98%">
	<tr><td class="left_title">
		<img src="${ctx}/images/dottt.png" border="0"/>查询条件
	</td></tr>
</table>
<table class="mytable" width="98%" id="capital_condiction" style="display:">
	<tr>
		<td class="table_title" width="60px">功能名称:</td>
		<td class="table_content" width="125px">
			<input type="text" id="funcName" name="funcName" value="${param.funcName}"/>
		</td>
		<td class="table_title" width="60px">功能中文名称:</td>
		<td class="table_content" width="125px">
			<input type="text" id="funcNameZh" name="funcNameZh" value="${param.funcNameZh}"/>
		</td>
		<td class="table_title" width="60px">功能描述:</td>
		<td class="table_content" width="125px">
			<input type="text" id="funcDesc" name="funcDesc" value="${param.funcDesc}"/>
		</td>
	</tr>
	
</table>
<div style="text-align: center">
	<input class="rmsBtnClass" type="button" id="btn_query" value="查询"/>&nbsp;
	<input class="rmsBtnClass" type="button" id="btn_add" value="新增" />
</div>
</form>
<div align="center"><font size="5" style="font-weight: bolder">app功能清单</font></div>
<div id="grid_box" style="border:0px solid #cccccc;background-color:#f3f3f3;padding:5px;width:100%;height:100%"></div>
</body>
</html>