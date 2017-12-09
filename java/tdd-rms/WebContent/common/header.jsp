<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<div id="hd">
	<div id="title">
		<h1>桃多多——管理平台(rms V1.0)</h1>
		
		<!-- 
			当前用户：当前组织：
			<a href="${ctx}/login.action">切换角色</a>
			<a href="${ctx}/account/user.action?curFuncNo=user">用户管理</a>
			<a href="${ctx}/account/sys-template.action?curFuncNo=sysTemplate">系统模板管理</a>
			<a href="${ctx}/j_spring_security_logout">注销</a>
			<a href="${ctx}/j_spring_security_logout">退出登录</a>
		 -->
	</div>
	<div id="menu">
		|<a href="${ctx}/tdd/category.action">接口分类信息查询</a>
		|<a href="${ctx}/tdd/interface.action">接口信息查询</a>
		|<a href="${ctx}/tdd/function.action">app功能查询</a>
		|<a href="${ctx}/tdd/dict.action">系统字典查询</a>
		|<a href="${ctx}/tdd/file.action">文件上传</a>
		|<a href="${ctx}/tdd/video.action">视频上传</a>
	</div>
</div>