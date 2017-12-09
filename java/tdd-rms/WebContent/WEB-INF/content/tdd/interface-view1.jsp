<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>
		<title>桃多多接口管理</title>
		<%@ include file="/common/meta.jsp"%>
		<%@ include file="/common/nogrid.jsp"%>
		<script type="text/javascript">
		 	var IId = "";
			$(document).ready(function() {
				init();
				
			
				$("#btn_return").click(function() {
					// window.location.href = "${ctx_tdd}/interface.action";
					window.opener.search();
					closeWindow();
				});
		
				$("#btn_json").click(function(){
					$("#btn_submit").click();
				});
				
		        
			});
		
			function init() {
				
				var cateId = '-1';
				var has_param = 'y';
				
				<c:if test="${not empty entity}">
					<c:if test="${not empty entity.cateId}">
						cateId = '${entity.cateId}';
					</c:if>
					
					<c:if test="${not empty entity.IHasParam}">
						has_param = '${entity.IHasParam}';
					</c:if>
					
					<c:if test="${not empty entity.IId}">
						IId = '${entity.IId}';

						$("#fieldset_json").show();
						<c:if test="${not empty entity.IJson}">
							$("#div_json").text('${entity.IJson}');
						</c:if>
					</c:if>
					
				</c:if>
				
			}
			
		</script>
	</head>
	<body>
		<form id="inputForm" method="post">
		
			<input type="hidden" id="IId" name="IId" value="${IId}" />
		    
			<div>
				
				<table class="mytable" style="width: 98%">
					<tr>
						<td colspan="6" class="main_title"><s:if test="IId==null">创建</s:if>
							<s:else>修改</s:else>接口信息</td>
					</tr>
					<tr>
						<td class="table_title"  style="width:15%">接口名称:</td>
						<td class="table_content"  colspan="2" style="width:35%">${entity.IName}</td>
						<td class="table_title" style="width:15%">接口中文名称:</td>
						<td class="table_content" colspan="2" style="width:35%">${entity.INameZh}</td>
					</tr>
					<tr>
						<td class="table_title" >接口所属分类:</td>
						<td class="table_content"  colspan="2"></td>
			
						<td class="table_title" >接口url:</td>
						<td class="table_content" colspan="2">${entity.IUrl}</td>
					</tr>
					<tr>
						<td class="table_title" >接口描述:</td>
						<td class="table_content" colspan="5">${entity.IDesc}
						</td>
					</tr>
					<tr>
						<td class="table_title" >是否有参数:</td>
						<td class="table_content" colspan="5"></td>
					</tr>
				</table>
			</div>
			<fieldset id="fieldset_param" ><legend>接口参数</legend>
				<div>
					<input class="button" type="button" id="btn_pram_create" value="新增参数" />
					<table id="tab_param" style="width: 100%" class="mytable">
						<tr id="tab_param_title">
							<td class="table_title" style="text-align: center;">参数名称</td>
							<td class="table_title" style="text-align: center;">参数值</td>
							<td class="table_title" style="text-align: center;">参数类型</td>
							<td class="table_title" style="text-align: center;">是否必输</td>
							<td class="table_title" style="text-align: center;">参数描述</td>
							<td class="table_title" style="text-align: center;">操作</td>
						</tr>
						<c:if test="${not empty lstParams}">
							<c:forEach items="${lstParams}" var="iparam" varStatus="status">
								<tr id="param_id_${status.count}">
									<td class='table_content'>${iparam.ipName}</td>
									<td class='table_content'>${iparam.ipValue}</td>
									<td class='table_content'>${iparam.selectIpType}</td>
									<td class='table_content'>${iparam.selectIpRequired}</td>
									<td class='table_content'>${iparam.ipDesc}</td>
								</tr>
							</c:forEach>
						</c:if>
					</table>
				</div>
			</fieldset>
			<fieldset id="fieldset_json" style="display: none"><legend>json相关</legend>
				<div>
					<div>
						json串：<div id="div_json"></div>
					</div>
					<div>
					json说明：${entity.IJsonDesc}
					</div>
				</div>
			</fieldset>
			<div>
				<table id="tab_button" style="width: 100%" cellspacing="0" class="mytable">
					<tr>
						<td align="center">
							<input class="button" type="button" id="btn_return" value="返回" />
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>