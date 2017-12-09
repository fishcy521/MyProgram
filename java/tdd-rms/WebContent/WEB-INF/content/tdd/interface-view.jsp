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
				$("#inputForm").validate({
			
					rules : {
						IName : {
							required : true,
							maxlength : 500
						},
						INameZh : {
							required : true,
							maxlength : 500
						},
						IUrl : {
							required : true,
							maxlength : 500
						},
						cateId : {
							required : true,
							min : 1
						},
						IDesc : {
							required : true,
							maxlength : 3000
						}
						
					},
					messages: {
						cateId: {
							min : "请选择接口所属分类"
							/**,
							maxlength : jQuery.validator.format("1212请输入一个长度最多是 {0} 的字符串")
							*/
						}
					},
					
					submitHandler: function(form) {
						
						form_submit();
					}
				});
			
				$("#btn_return").click(function() {
					// window.location.href = "${ctx_tdd}/interface.action";
					window.opener.search();
					closeWindow();
				});
		
				$("#btn_json").click(function(){
					$("#btn_submit").click();
				});
				
				$("#btn_submit").click(function() {
					$("#inputForm").submit();
					
				});
		        
				$("#btn_pram_create").click(function() {
					var param_index = $("#tab_param tr").length;
					var param_id = "param_id_"+ param_index;
					var param_name = "<input type='text' name='param_name' style='width: 80%'/>";
					var param_value = "<input type='text' name='param_value' style='width: 80%'/>";
					var param_type = "${strParamType}";
					var param_required = "${strParamIsRequired}";
					var param_desc = "<textarea rows='2' cols='50' name='param_desc'  style='width: 80%'></textarea>";
					var param_operate = "<input type='button' onclick='removeTr(\""+ param_id + "\")' value='删除'/>";
					
					var tr_obj = "<tr id='"+param_id+"'>"
							+ "<td class='table_content'>" + param_name + "</td>"
							+ "<td class='table_content'>" + param_value + "</td>"
							+ "<td class='table_content'>" + param_type + "</td>"
							+ "<td class='table_content'>" + param_required + "</td>"
							+ "<td class='table_content'>" + param_desc + "</td>"
							+ "<td class='table_content'>" + param_operate + "</td>" 
							+ "</tr>";
					$("#tab_param").append(tr_obj);
				});
				
				$("input[name='rad_has_param']").click(function(){
					
					if($(this).val() == 'y'){
						$("#fieldset_param").show();
					}
					else {
						$("#fieldset_param").hide();
					}
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
				
				$("#cateId").val(cateId);
				
				if(has_param == 'y'){
					$("input[@type=radio][name='rad_has_param']:nth(0)").attr("checked",true);
					$("#fieldset_param").show();
				}
				else {
					$("input[@type=radio][name='rad_has_param']:nth(1)").attr("checked",true);
					$("#fieldset_param").hide();
				}
				console.log("init");
				
				$("input,textarea").attr("readonly",true);
				
				$("select").attr('disabled', true);
			}
			function removeTr(param_id) {
				$("#" + param_id).remove();
		
			}
			
			function form_submit() {
				
				var param_name_str='';
				var param_value_str = '';
				var param_desc_str = '';
				var param_type_str = '';
				var param_required_str = '';
				
				var each_length = $("input[name='param_name']").length;
				var nameObj = $("input[name='param_name']");
				var valueObj = $("input[name='param_value']");
				var descObj = $("[name='param_desc']");
				var typeObj = $("[name='param_type']");
				var requiredObj = $("[name='param_is_required']");
				
				$("[name='param_desc']").each(function(index,data){
					var tmp_name_val = nameObj.eq(index).val();
					var tmp_value_val = valueObj.eq(index).val();
					var tmp_desc_val = descObj.eq(index).val();
					var tmp_type_val = typeObj.eq(index).val();
					var tmp_required_val = requiredObj.eq(index).val();
					if (tmp_name_val == '') {
						alert("参数名称不能为空");
						$(this).focus();
					}
					if(index == each_length -1){
						
						if (tmp_name_val != '') {
							
							param_name_str += tmp_name_val;
							param_value_str += tmp_value_val;
							param_desc_str += tmp_desc_val;
							param_type_str += tmp_type_val;
							param_required_str += tmp_required_val;
						}
					}	
					else {
						
						if (tmp_name_val !='') {
							
							param_name_str += tmp_name_val +"${rms_split}";
							param_value_str += tmp_value_val +"${rms_split}";
							param_desc_str += tmp_desc_val +"${rms_split}";
							param_type_str += tmp_type_val +"${rms_split}";
							param_required_str += tmp_required_val +"${rms_split}";
						}
					}
				});
				
				$.ajax({
	
					url : '${ctx_tdd}/interface!save.action',
					dataType : "json",
					type : "post",
					//async:false,
					data : {
						"IId" : $("#IId").val(),
						"IName" : $("#IName").val(),
						"INameZh" : $("#INameZh").val(),
						"IUrl" : $("#IUrl").val(),
						"cateId" : $("#cateId").val(),
						"IDesc" : $("[name='IDesc']").val(),
						"IHasParam" : $("input[name='rad_has_param']:checked").val(),
						"paramNames": param_name_str,
						"paramValues": param_value_str,
						"paramDescs": param_desc_str,
						"paramTypes": param_type_str,
						"paramRequireds": param_required_str,
						"IJsonDesc":$("[name='json_desc']").val()
					},
					success : function(data) {
						var rtnType = data.rtnType;
						var rtnMsg = data.rtnMsg;
						var rtnId = data.rtnId;
						// alert("rtnType="+rtnType+"||rtnMsg="+rtnMsg+"rtnId="+rtnId);
						if (rtnMsg != '') {
							alert(rtnMsg);
						}
						if (rtnType == 'Y') {
							//window.location.href = '${ctx_tdd}/interface.action';
							//$("#btn_return").click();
							
							window.location.href='interface!input.action?IId='+rtnId;
							
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
		
			<input type="hidden" id="IId" name="IId" value="${IId}" />
		    
			<div>
				
				<table class="mytable" style="width: 98%">
					<tr>
						<td colspan="6" class="main_title"><s:if test="IId==null">创建</s:if>
							<s:else>修改</s:else>接口信息</td>
					</tr>
					<tr>
						<td class="table_title"  style="width:15%">接口名称:</td>
						<td class="table_content"  colspan="2" style="width:35%">
							<input type="text" id="IName" name="IName" style="width: 80%" value="${entity.IName}" />
							<font color=red>*</font>
						</td>
						<td class="table_title" style="width:15%">接口中文名称:</td>
						<td class="table_content" colspan="2" style="width:35%">
							<input type="text" id="INameZh" name="INameZh" style="width: 80%" value="${entity.INameZh}" />
							<font color=red>*</font>
						</td>
					</tr>
					<tr>
						<td class="table_title" >接口所属分类:</td>
						<td class="table_content"  colspan="2">
							<select id="cateId" name="cateId">
								<option value="-1">--请选择--</option>
								<c:forEach items="${listCategory}" var="category">
									<option value="${category.ICateId }">${category.ICateName}</option>
								</c:forEach>
							</select>
						</td>
			
						<td class="table_title" >接口url:</td>
						<td class="table_content" colspan="2">
							<input type="text" id="IUrl" name="IUrl" style="width: 80%" value="${entity.IUrl}" />
							<font color=red>*</font>
						</td>
					</tr>
					<tr>
						<td class="table_title" >接口描述:</td>
						<td class="table_content" colspan="5">
							<textarea rows="4" cols="150" name="IDesc">${entity.IDesc}</textarea>
						</td>
					</tr>
					<tr>
						<td class="table_title" >是否有参数:</td>
						<td class="table_content" colspan="5">
							<input type="radio" name="rad_has_param" value="y">有参数&nbsp;
							<input type="radio" name="rad_has_param" value="n">无参数
						</td>
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
									<td class='table_content'><input type='text' name='param_name' value="${iparam.ipName}" style='width: 80%'/></td>
									<td class='table_content'><input type='text' name='param_value' value="${iparam.ipValue}" style='width: 80%'/></td>
									<td class='table_content'>${iparam.selectIpType}</td>
									<td class='table_content'>${iparam.selectIpRequired}</td>
									<td class='table_content'><textarea rows='2' cols='50' name='param_desc' style='width: 80%' >${iparam.ipDesc}</textarea></td>
									<td class='table_content'><input type='button' onclick="removeTr('param_id_${status.count}')" value='删除'/></td>
								</tr>
							</c:forEach>
						</c:if>
					</table>
				</div>
			</fieldset>
			<fieldset id="fieldset_json" style="display: none"><legend>json相关</legend>
				<div>
					<input class="button" type="button" id="btn_json" value="生成json" style="display: none"/>
					<div>
						json串：<div id="div_json"></div>
					</div>
					<div>
					json说明：<textarea rows='2' cols='50' name='json_desc' style='width: 80%' >${entity.IJsonDesc}</textarea>
					</div>
				</div>
			</fieldset>
			<div>
				<table id="tab_button" style="width: 100%" cellspacing="0" class="mytable">
					<tr>
						<td align="center">
							<input class="button" type="button" id="btn_submit" value="提交" style="display: none"/>&nbsp; 
							<input class="button" type="button" id="btn_return" value="返回" />
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>