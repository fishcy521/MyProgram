<%@ page import="com.feicuiedu.utils.DBUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/4/27
  Time: 7:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<Map<String,Object>> list =
            DBUtil.queryMethod("select * from dict_detail_ a where a.dict_id = (select id from dict_ b where b.dict_name=?) order by dict_order",new String[]{"user_sex"});

    String loginName = request.getParameter("login_name")==null?"":request.getParameter("login_name");

%>
<!doctype>
<html>
<head>
    <title>用户管理</title>
    <meta charset="UTF-8">
    <link type="text/css" rel="stylesheet" href="../css/my.css">
    <script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.js"></script>
    <script src="../js/jquery_timer.js"></script>
    <script>
        $(document).ready(function () {

            var message = '<%=request.getAttribute("message")==null ? "":(String)request.getAttribute("message")%>'
            var flag = '<%=request.getAttribute("flag")==null ? "":(String)request.getAttribute("flag")%>'

            if (flag != '') {
                $("#message").html(message).show();
                if (flag == '1') {
                    $('body').oneTime('3s',function () {
                        // window.location='../login.jsp'
                    });

                }

            }

            $("#btn_save").click(function () {


                $("#frm_maintain")
                    .attr("action","/pms/user/maintain")
                    .attr("method","post").submit();
            });
        });

    </script>
</head>

<body>
    <p id="message" class="p_message"></p>
    <form id="frm_maintain" name="frm_maintain">
        <table>
            <tr>
                <td class="title">登陆账号:</td>
                <td><input type="text" id="login_name" name="login_name" value="<%=loginName%>"></td>
            </tr>
            <tr>
                <td class="title">用户姓名:</td>
                <td><input type="text" id="use_name" name="use_name"></td>
            </tr>
            <tr>
                <td class="title">用户密码:</td>
                <td><input type="password" id="password" name="password"></td>
            </tr>
            <tr>
                <td class="title">确认密码:</td>
                <td><input type="password" id="confirm_password" name="confirm_password"></td>
            </tr>
            <tr>
                <td class="title">性别:</td>
                <td>
                    <%
                        for (int i = 0; i < list.size(); i++) {
                            Map<String,Object> map = list.get(i);
                            String dict_value = (String) map.get("dict_value");
                            String dict_desc = (String) map.get("dict_desc");
                            %>
                        <input type="radio"  name="sex" value="<%=dict_value%>"><%=dict_desc%>
                    <%
                        }
                    %>

            </tr>
            <tr>
                <td class="title">生日:</td>
                <td><input type="date" id="birthday" name="birthday"></td>
            </tr>
            <tr>
                <td class="title">备注:</td>
                <td><textarea cols="30" rows="5" name="remark"></textarea></td>
            </tr>
            <tr>
                <td class="title"><input type="button" id="btn_save" name="btn_save" value="保存"></td>
                <td><input type="button" id="btn_return" name="btn_return" value="返回"></td>
            </tr>
        </table>

    </form>
</body>
</html>