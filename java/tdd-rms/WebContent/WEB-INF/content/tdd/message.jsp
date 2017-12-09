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
		
		//window.location.href="${ctx_tdd}/interface!input.action";
		openWindow('${ctx_tdd}/interface!input.action',1920,1024);
	});
	
	$("#btn_query").click(function(){
		
		search();
	});
});

var dsOption= {
	fields :[
		{name:'msgId'},
		{name:'msgKey'},
		{name:'msgValue'},
		{name:'msgDesc'}
	],
	recordType:'object'
};

var colsOption = [
{id:'msgId',header:'msgId',  hidden:true},
{id:'msgKey',header:'消息代码',headAlign:'center',align:'left',width:150,editable:true,editor:{type:'text'}},
{id:'msgValue',header:'消息内容',headAlign:'center',align:'left',width:150,editable:true,editor:{type:'text'}},
{id:'msgDesc',header:'消息描述',headAlign:'center',align:'left',width:100,editable:true,editor:{type:'textarea'}}];
	var gridOption = {
		id : "myGrid1",
		loadURL : 'message!gridData.action',
		saveURL : 'message!save.action',
		language : "cn",
		width : "100%",
		height : "700",
		container : 'grid_box',
		remotePaging : true,
		replaceContainer : true,
		dataset : dsOption,
		columns : colsOption,
		onCellClick:function(value,record,cell,row,colNO,rowNO,columnObj,grid){
			getProjectProfessionInfo(value,record,cell,row,colNO,rowNO,columnObj,grid);
		},
		toolbarContent :'nav goto| pagesize | reload | add del save | state',
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
			paramKey : '${param.param_msgKey}',
			paramValue : '${param.param_msgValue}'
		}
		
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
			openWindow('interface!input.action?IId='+id,1920,1024);
		}
		else if (operate_type=='delete'){
			window.location.href='interface!delete.action?IId='+id;
		}
		else if (operate_type=='json'){
			openWindow('interface!viewJson.action?IId='+id,1920,1024);
		}
		else if (operate_type == 'json_encrypt') {
			openWindow('interface!viewEncrypt.action?IId='+id,1920,1024);
		}
		else {
			openWindow('interface!view.action?IId='+id,1920,1024);
		}
	}
</script>

</head>
<body >
<%@ include file="/common/header.jsp" %>
<div id="message"><s:actionmessage theme="custom" cssClass="msgsuccess"/></div>
<form id="mainForm" action="${ctx_tdd}/interface.action" method="post">

<table class="mytable" width="98%">
	<tr><td class="left_title">
		<img src="${ctx}/images/dottt.png" border="0"/>查询条件
	</td></tr>
</table>
<table class="mytable" width="98%" id="capital_condiction" style="display:">
	<tr>
		<td class="table_title" width="60px">消息代码:</td>
		<td class="table_content" width="125px">
			<input type="text" id="param_msgKey" name="param_msgKey" value="${param.param_msgKey}" style="width: 80%"/>
		</td>
		<td class="table_title" width="60px">消息代码&描述:</td>
		<td class="table_content" width="125px">
			<input type="text" id="param_msgValue" name="param_msgValue" value="${param.param_msgValue}" style="width: 80%"/>
		</td>
	</tr>
</table>
<div style="text-align: center">
	<input class="rmsBtnClass" type="button" id="btn_query" value="查询"/>&nbsp;
	<input class="rmsBtnClass" type="button" id="btn_add" value="新增" />
</div>
</form>
<div align="center"><font size="5" style="font-weight: bolder">桃多多接口信息</font></div>
<div id="grid_box" style="border:0px solid #cccccc;background-color:#f3f3f3;padding:5px;width:100%;height:100%"></div>
</body>
</html>