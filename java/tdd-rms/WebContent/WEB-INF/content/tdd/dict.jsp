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
		{name:'dictId'},
		{name:'dictName'},
		{name:'dictDesc'},
		{name:'dictType'}
	],
	recordType:'object'
};

var colsOption = [
{id:'dictId',header:'dictId',  hidden:true},
{id:'iname',header:'字典名称',headAlign:'center',align:'left',width:150},
{id:'inameZh',header:'字典描述',headAlign:'center',align:'left',width:150},
{id:'cateName',header:'接口分类',headAlign:'center',align:'left',width:100},
{id:'status',header:'开发状态',headAlign:'center',align:'left',width:80},
{id:'urlStr',header:'接口地址',headAlign:'center',align:'left',width:200},
{id:'params',header:'参数',headAlign:'center',align:'left',width:150},
{id:'idesc',header:'接口描述',headAlign:'center',align:'left',width:350},
{id:'operation',header:'操作',headAlign:'center',align:'center',width:400,
	renderer:function(value,record,columnObj,grid,colNo,rowNo){
		var iid=record["iid"];
		var params=record["params"];
		var hasParam=record["ihasParam"];
		var str_btn_json = "";
		if (hasParam == 'y') {
			if (params != '') {
				str_btn_json = "<input type='button'value='json' class='rmsBtnClass' onclick=\"viewInfo('"+iid+ "','json')\" />";
			}
		}
		else {
			str_btn_json = "<input type='button'value='json' class='rmsBtnClass' onclick=\"viewInfo('"+iid+ "','json')\" />"; 
		}
		return "<div style='vertical-align:middle;'>"
		+"<input type='button'value='修改' class='rmsBtnClass' onclick=\"viewInfo('"+iid+ "','update')\" />"
		+"<input type='button'value='删除' class='rmsBtnClass' onclick=\"viewInfo('"+iid+ "','delete')\" />"
		+"<input type='button'value='查看' class='rmsBtnClass' onclick=\"viewInfo('"+iid+ "','view')\" />"
		+str_btn_json
		+"</div>";
			}
		} ];
	var gridOption = {
		id : "myGrid1",
		loadURL : 'interface!gridData.action',
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
			paramIName : '${param.iName}',
			paramIUrl : '${param.iUrl}',
			paramFuncId : '${param.funcId}',
			paramICateId : '${param.iCateId}',
			paramStatus : '${param.status}',
			paramHasParam : '${param.hasParam}'
		},
		exportURL : 'interface!export.action',
		exportFileName : 'rms_interface_list.xlsx'
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
		<td class="table_title" width="60px">接口名称(描述):</td>
		<td class="table_content" width="125px">
			<input type="text" id="iName" name="iName" value="${param.iName}" style="width: 80%"/>
		</td>
		<td class="table_title" width="60px">接口地址:</td>
		<td class="table_content" width="125px">
			<input type="text" id="iUrl" name="iUrl" value="${param.iUrl}" style="width: 80%"/>
		</td>
		<td class="table_title" width="60px">功能:</td>
		<td class="table_content" width="125px">
			<select id="funcId" name="funcId" style="width: 85%">
				<option value="-1">--请选择--</option>
				<c:forEach items="${listFunc}" var="func">
					<option value="${func.funcId }" 
						<c:if test="${not empty param.funcId && func.funcId == param.funcId}">selected</c:if>>
						${func.funcName}(${func.funcNameZh})
					</option>
				</c:forEach>
			</select>
		</td>
	</tr>
	
	<tr>
		<td class="table_title" width="60px">分类:</td>
		<td class="table_content" width="125px">
			<select id="iCateId" name="iCateId" style="width: 85%">
				<option value="-1">--请选择--</option>
				<c:forEach items="${listCategory}" var="category">
					<option value="${category.ICateId }" 
						<c:if test="${not empty param.iCateId && category.ICateId == param.iCateId}">selected</c:if>>
						${category.ICateName}
					</option>
				</c:forEach>
			</select>
			<!-- 
				<input type="text" id="iCateId" name="iCateId" value="${param.iCateId}"/>
			 -->
		</td>
		<td class="table_title" width="60px">参数:</td>
		<td class="table_content" width="125px">
			<select id="hasParam" name="hasParam">
				<option value="-1">--请选择--</option>
					<option value="y" 
						<c:if test="${not empty param.hasParam && 'y' == param.hasParam}">selected</c:if>>
						有参数
					</option>
					<option value="n" 
						<c:if test="${not empty param.hasParam && 'n' == param.hasParam}">selected</c:if>>
						无参数
					</option>
			</select>
			<!-- 
				<input type="radio" name="rad_has_param" value="y">有参数&nbsp;
				<input type="radio" name="rad_has_param" value="n">无参数
			 -->
		</td>
		<td class="table_title" width="60px">状态:</td>
		<td class="table_content" width="125px">
			<select id="status" name="status">
				<option value="-1">--请选择--</option>
					<option value="n" 
						<c:if test="${not empty param.status && 'n' == param.status}">selected</c:if>>
						未开发
					</option>
					<option value="y" 
						<c:if test="${not empty param.status && 'y' == param.status}">selected</c:if>>
						已开发
					</option>
			</select>
			<!-- 
				<input type="text" id="iCateId" name="iCateId" value="${param.iCateId}"/>
			 -->
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