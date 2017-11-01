<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/4/27
  Time: 7:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype>
<html>
<head>
    <title>翡翠练习管理平台</title>
    <meta charset="UTF-8">
    <link type="text/css" rel="stylesheet" href="css/my.css">
    <script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.js"></script>
    <script>
        $(document).ready(function () {
            $("#div_1").css("height", $(document).height() / 2 + "px");
            //$("#div_1").css("height",$(document).height()+"px");
            //alert($(window).height());

            $("#btn_register").click(function () {
                window.location = "user/maintain.jsp"
            });

            $("#btn_login").click(function () {
                $("#frm_login").attr("action","/pms/system/SrvLogin").attr("method","post").submit();
            });
        });

    </script>
    <style>

        table {
            width: 100%;
        }


    </style>
</head>
<body style="text-align: center">
<div id="div_1" style="background-color: yellow;width: 50%;margin: 0 auto">
    <form id="frm_login" name="frm_login">
        <table>
            <tr>
                <td class="title">用户名:</td>
                <td><input type="text" id="user_name"></td>
            </tr>
            <tr>
                <td class="title">密码:</td>
                <td><input type="password" id="password"></td>
            </tr>
            <tr>
                <td class="title"><input type="button" id="btn_login" name="btn_login" value="登陆"></td>
                <td><input type="button" id="btn_register" name="btn_register" value="注册"></td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>