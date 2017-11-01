<%@ page import="java.util.List" %>
<%@ page import="com.feicuiedu.model.User" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/4/24
  Time: 16:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title></title>
  </head>
  <body>

    <table>

      <tr>
        <td>序号</td>
        <td>姓名</td>
        <td>性别</td>
      </tr>

      <%
        List<User> lstUser = (List<User>) request.getAttribute("lstUser");

        for (int i = 0; i < lstUser.size(); i++) {
          User tmpUser = lstUser.get(i);

          %>
            <tr>
              <td><%=i+1%></td>
              <td><%=tmpUser.getName()%></td>
              <td><%=tmpUser.getSex()%></td>
            </tr>
          <%
        }
      %>
    </table>
  </body>
</html>
