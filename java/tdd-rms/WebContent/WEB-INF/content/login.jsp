<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>桃多多接口管理</title>
<%@ include file="/common/meta.jsp" %>
<link href="${ctx}/css/style.css" type="text/css" rel="stylesheet"/>
<script src="${ctx}/js/jquery.js" type="text/javascript"></script>
<script src="${ctx}/js/jquery.form.js" type="text/javascript"></script>
<script src="${ctx}/js/jquery.metadata.js" type="text/javascript"></script>
<script src="${ctx}/js/validate/jquery.validate.js" type="text/javascript"></script>
<script src="${ctx}/js/validate/messages_cn.js" type="text/javascript"></script>
<script src="${ctx}/js/myFunc.js" type="text/javascript"></script>
</head>
<body>
<b>Welcom to 桃多多</b>
<br/>
<a href="${ctx}/tdd/category.action">接口分类查询</a>
<a href="${ctx}/tdd/interface.action">接口信息查询</a>
<a href="${ctx}/tdd/function.action">app功能查询</a>
<a href="${ctx}/tdd/dict.action">系统字典查询</a>
<a href="${ctx}/tdd/message.action">系统提示信息查询</a>
<a href="${ctx}/tdd/file.action">文件上传</a>
<a href="${ctx}/tdd/video.action">视频上传</a>
</body>
</html>
