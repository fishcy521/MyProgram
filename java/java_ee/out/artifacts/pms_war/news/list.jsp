<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.feicuiedu.utils.DBUtil" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/4/27
  Time: 7:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%

    String type = request.getParameter("type") == null?"-1":request.getParameter("type");

    List<Map<String,Object>> list = (List<Map<String, Object>>) request.getAttribute("list");
    List<Map<String,Object>> lstTypes = (List<Map<String, Object>>) request.getAttribute("lstTypes");

%>
<!doctype>
<html>
<head>
    <title>新闻系统</title>
    <meta charset="UTF-8">
    <script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.js"></script>
    <script>
        $(document).ready(function () {
            var type = "<%=type%>";
            $("#type").val(type);

            $("#btn_query").click(function () {
                $("#frm_news_query").attr("action","/pms/news/list").attr("method","post").submit();
            });

            $("#btn_create").click(function () {

            });

            $("#btn_delete").click(function(){

            });
        });

    </script>
</head>
<body>
    <div style="text-align: center">新闻列表</div>
    <form id="frm_news_query" name="frm_new_query">
    <table>
        <tr>
            <td>新闻类型:</td>
            <td>
                <select id="type" name="type">
                    <option value="-1">请选择</option>
                    <%
                        for (Map<String,Object> optionMap:lstTypes) {
                            String optionValue = (String)optionMap.get("dict_value");
                            String optionDesc = (String) optionMap.get("dict_desc");
                            %>
                        <option value="<%=optionValue%>"><%=optionDesc%></option>
                    <%
                        }
                    %>
                </select>
            </td>
            <td><input type="button" id="btn_query" name="btn_query" value="查询"></td>
            <td>
                <input type="button" id="btn_create" name="btn_create" value="新增">
                <input type="button" id="btn_delete" name="btn_delete" value="删除">
            </td>
        </tr>
    </table>
    <table>

    </table>
    </form>
</body>
</html>