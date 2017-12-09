<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<html>
<head>
	<title>桃多多接口管理</title>
<%@ include file="/common/meta.jsp" %>
<%@ include file="/common/nogrid.jsp" %>
<script type="text/javascript" >

$(document).ready(function(){
	$("#uploadbutton").click(function () {
		/**
	    var filename = $("#uploadFile").val();
	 
	    $.ajax({
	      type: "POST",
	      url: "http://42.62.114.249:8080/uploads/jscripts/uploadImageFile.do",
	      enctype: 'multipart/form-data',
	      data: {
	        type: "8"
	      },
	      success: function (data) {
	    	console.log("data="+data);
	      }
	    });
	    */
	    
	    //$('#uploadForm').ajaxSubmit();
	  });
	
	$('#uploadForm').ajaxForm({  
        dataType: 'json',  
        success: function (data) {
        	var rtnType = data.rtnType;
			var rtnMsg = data.rtnMsg;
			alert("rtnType="+rtnType+"||rtnMsg="+rtnMsg);
	    },
		error:function (data) {
			console.log("data="+data);
		}
	
    });  
});


</script>

</head>
<body >
<%@ include file="/common/header.jsp" %>
	<form id="uploadForm" action="${ctx_tdd}/upload!uploadImage.action" method="post" 
	enctype ="multipart/form-data">
		<table class="mytable" width="98%">
				<tr><td colspan="2" class="left_title">
					<img src="${ctx}/images/dottt.png" border="0"/>
				</td></tr>
			      <tr>
			        <td nowrap="nowrap">请选择上传文件：</td>
			        <td>
			        	<input type="file" id="uploadFile" style="width:320px" name="uploadFile"/>
			        </td>
			      </tr>
			      <tr>
			        <td colspan="2" align="center">
			        	<input class="pmsBtnClass" type="submit" value="上传导入" id="btnUpload" />
			        </td>
			      </tr>
			   </table>
	</form>
</body>
</html>