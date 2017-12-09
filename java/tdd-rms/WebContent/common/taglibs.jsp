<%@page import="com.rms.utils.ApplicationProperties"%>
<%@ page import="com.rms.commons.global.RmsGlobal"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<!-- 
	<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
 -->
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ctx_tdd" value="${pageContext.request.contextPath}/tdd"/>
<c:set var="rms_split" value="<%=RmsGlobal.CONTENT_SPLIT %>"/>
<c:set var="default_page_size" value="<%=RmsGlobal.DEFAULT_PAGE_SIZE %>"/>
<c:set var="default_page_size_list" value="<%=RmsGlobal.DEFAULT_PAGE_SIZE_LIST%>"/>

<!doctype html>