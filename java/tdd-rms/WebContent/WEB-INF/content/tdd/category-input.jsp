<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>桃多多接口管理</title>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/nogrid.jsp"%>
<script type="text/javascript">
	$(document).ready(function(){
		
		$("#inputForm").validate({
			
			rules: {
				ICateName: {
					required : true,
					maxlength : 500
				}
			}
			
			/**
			,messages: {
				ICateName: {
					required : "该字段必须输入123",
					maxlength : jQuery.validator.format("1212请输入一个长度最多是 {0} 的字符串")
				}
			}
			*/
			,submitHandler: function(form) {
				
				form_submit();
			}
		});
		
		
		$("#btn_return").click(function(){
				
			window.opener.search();
			closeWindow();		
		});
		
		$("#btn_left").click(function(){
			
			$("#sel_iid_unselected option:selected").each(function(index,data_option){
				
				data_option = $(this);
				var tmp_data_option = "<option value='"+data_option.val().val()+"'>"+data_option.text()+"</option>";
				$("#sel_iid_unselected option[value='"+data_option.val()+"']").remove();
				
				var can_append = true;
				$("#sel_iid_selected option").each(function(index_selected, data_option_selected){
					data_option_selected = $(this);
					if(data_option_selected.val() == data_option.val()){
						can_append = false;
					}	
				});
				
				if (can_append) {
					
					$("#sel_iid_selected").append(tmp_data_option);
				}
			});
		});
		
		$("#btn_right").click(function(){
			
			$("#sel_iid_selected option:selected").each(function(index,data_option){
				
				data_option = $(this);
				var tmp_data_option = "<option value='"+data_option.val()+"'>"+data_option.text()+"</option>";
				$("#sel_iid_selected option[value='"+data_option.val()+"']").remove();
				
				var can_append = true;
				$("#sel_iid_selected option").each(function(index_unselected, data_option_unselected){
					data_option_unselected = $(this);
					if(data_option_unselected.val() == data_option.val()){
						can_append = false;
					}	
				});
				
				if (can_append) {
					
					$("#sel_iid_unselected").append(tmp_data_option);
				}
			});
			
		});
		
		$("#btn_submit").click(function(){
			$("#inputForm").submit();
		});
	});	
	
	function form_submit() {
		$("#sel_iid_selected").find("option").each(function(index,data_option){
			
			if(index == $("#sel_iid_selected").find("option").length -1) {
				
				$("#interfaceIds").val($("#interfaceIds").val()+$(this).val());
			}
			else {
				
				$("#interfaceIds").val($("#interfaceIds").val()+$(this).val()+",");
			}
		});

		$.ajax({
			
			url : '${ctx_tdd}/category!save.action',
			dataType : "json",
			type : "post",
			//async:false,
			data : {
				"ICateName" : $("#ICateName").val(),
				"ICateId" : $("#ICateId").val(),
				"interfaceIds" : $("#interfaceIds").val()
			},
			success : function(data) {
				var rtnType = data.rtnType;
				var rntMsg = data.rtnMsg;
				
				if (rntMsg != '') {
					alert(rntMsg);
				}
				if(rtnType == 'Y') {
					$("#btn_return").click();
				}
			},
			error : function() {
			}
		});
	}
</script>
</head>
<body>
	<form id="inputForm" method="post">
	
		<input type="hidden" id="ICateId" name="ICateId" value="${ICateId}" />
		<input type="hidden" id="interfaceIds" name="interfaceIds" value="" />
		<table class="mytable" style="width: 100%">
			<tr>
				<td colspan="5" class="main_title"><s:if test="ICateId==null">创建</s:if>
					<s:else>修改</s:else>接口分类信息</td>
			</tr>
			<tr>
				<td class="table_title" width="90px">接口分类名称:</td>
				<td class="table_content" width="270px">
					<input type="text" id="ICateName" name="ICateName" style="width: 150px" value="${entity.ICateName}"/><font color=red>*</font>
				</td>
				<td class="table_title" width="90px" >&nbsp;</td>
				<td class="table_content" colspan="2">&nbsp;</td>
			</tr>
			<tr style="height: 400px">
				<td class="table_title">已关联接口</td>
				<td class="table_content" style="height: 100%">
					<select id="sel_iid_selected" name="sel_iid_selected" multiple="multiple" style="width: 100%;height: 100%">
						<c:if test="${not empty lstInterfaceSel}">
							<c:forEach items="${lstInterfaceSel}" var="interface">
								<option value="${interface.IId}">${interface.IName}(${interface.INameZh})</option>
							</c:forEach>
						</c:if>
					</select>
				</td>
				<td class="table_title">
					<input type="button" class="button" id="btn_left" name="btn_left" value="&lt;&lt;"><br>
					<input type="button" class="button" id="btn_right" name="btn_right" value="&gt;&gt;">
				</td>
				<td class="table_title">可关联接口</td>
				<td class="table_content">
					<select id="sel_iid_unselected" name="sel_iid_unselected" multiple="multiple" style="width: 100%;height: 100%">
						<c:if test="${not empty lstInterfaceUnSel}">
							<c:forEach items="${lstInterfaceUnSel}" var="interface">
								<option value="${interface.IId}">${interface.IName}(${interface.INameZh})</option>
							</c:forEach>
						</c:if>
					</select>
				</td>
			</tr>
			<tr>
				<td colspan="5" align="center">
					<input class="button" type="button" id="btn_submit" value="提交" />&nbsp; 
					<input class="button" type="button" id="btn_return" value="返回" /></td>
			</tr>
		</table>
	</form>
</body>
</html>