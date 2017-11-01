<%@ page import="com.feicuiedu.utils.DBUtil" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.LinkedHashMap" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/4/27
  Time: 7:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<Map<String, Object>> lstParentMenu =
            DBUtil.queryMethod("select * from menu_ where parent_id = ? order by order_", new String[]{"-1"});

    LinkedHashMap<Map<String, Object>, List<Map<String, Object>>> result = new LinkedHashMap<>();

    for (Map<String, Object> parentMap : lstParentMenu) {
        int paranet_id = (Integer) parentMap.get("id");
        String paraentName = (String) parentMap.get("name");
        String[] args = new String[]{String.valueOf(paranet_id)};
        List<Map<String, Object>> lstMenu = DBUtil.queryMethod(
                "select * from menu_ where parent_id = ? order by order_", args);
        result.put(parentMap, lstMenu);
    }
%>
<!doctype>
<html>
<head>
    <title>翡翠练习管理平台</title>
    <meta charset="UTF-8">
    <style type="text/css">
        body {
            font-family: "宋体";
            font-size: 12px;
            line-height: 1.5em;
            color: #7FB0C8;
            padding: 0;
            margin: 0;
            background: #336699;
        }

        ul, ol, li, dl, dt, dd {
            margin: 0;
            padding: 0;
            list-style-type: none;
        }

        h1, h2, h3, form, input, iframe, span {
            margin: 0;
            padding: 0;
        }

        a {
            color: #7FB0C8;
        }

        a:link {
            color: #7FB0C8;
            TEXT-DECORATION: none;
        }

        a:visited {
            color: #7FB0C8;
            TEXT-DECORATION: none;
        }

        a:hover {
            color: #fff;
            TEXT-DECORATION: none;
        }

        .white {
            color: #fff;
        }

        .white a:link {
            color: #fff;
            TEXT-DECORATION: none;
        }

        .white a:visited {
            color: #fff;
            TEXT-DECORATION: none;
        }

        .white a:hover {
            color: #73E1F5;
            TEXT-DECORATION: none;
        }

        /* 树形菜单开始 */
        .close {
            float: right;
            clear: right;
            font-size: 12px;
            font-weight: normal;
            cursor: pointer;
            /*padding-right: 10px;*/

        }

        .title {
            font-size: 14px;
            color: #fff;
            margin-bottom: 10px;
            padding-left: 5px;
            width: 100%;
        }

        .menu {
            width: 100%;
            height: auto;
            margin-bottom: 10px;
        }

        .l1 {
            background: #111;
            font-size: 13px;
            /*padding: 5px 0 0 30px;*/
            height: 20px;
            margin-bottom: 5px;
            cursor: pointer;
        }

        .slist {
            margin: 0 0 5px 0;
            display: none;
        }

        .l2 {
            padding: 0 0 0 35px;
            font-size: 13px;
        }

        .l2 a {
            padding: 6px 0 0 5px;
            width: 100%;
            height: 21px;
            display: block;
        }

        .currentl2 a, .l2 a:hover {
            background: #1E5A82;
            color: #fff;
        }

        .sslist {
            background: #156890;
            width: 100%;
            overflow: hidden;
            margin: 0 0 5px 35px;
            display: none;
        }

        .l3 a {
            padding: 6px 0 0 5px;
            width: 100%;
            height: 20px;
            display: block;
        }

        .currentl3 a, .l3 a:hover {
            color: #fff;
            font-weight: bold;
        }
    </style>
    <script type="text/javascript" src="http://keleyi.com/keleyi/pmedia/jquery/jquery-1.4.2.min.js"></script>
    <script type="text/javascript">
        // 树状菜单
        $(document).ready(function () {
            $(".l1").toggle(function () {
                $(".slist").animate({height: 'toggle', opacity: 'hide'}, "slow");
                $(this).next(".slist").animate({height: 'toggle', opacity: 'toggle'}, "slow");
            }, function () {
                $(".slist").animate({height: 'toggle', opacity: 'hide'}, "slow");
                $(this).next(".slist").animate({height: 'toggle', opacity: 'toggle'}, "slow");
            });

            $(".l2").toggle(function () {
                $(this).next(".sslist").animate({height: 'toggle', opacity: 'toggle'}, "slow");
            }, function () {
                $(this).next(".sslist").animate({height: 'toggle', opacity: 'toggle'}, "slow");
            });

            $(".l2").click(function () {
                $(".l3").removeClass("currentl3");
                $(".l2").removeClass("currentl2");
                $(this).addClass("currentl2");
            });

            $(".l3").click(function () {
                $(".l3").removeClass("currentl3");
                $(this).addClass("currentl3");
            });

            $(".close").toggle(function () {
                $(".slist").animate({height: 'toggle', opacity: 'show'}, "fast");
                $(".sslist").animate({height: 'toggle', opacity: 'show'}, "fast");
            }, function () {
                $(".slist").animate({height: 'toggle', opacity: 'hide'}, "fast");
                $(".sslist").animate({height: 'toggle', opacity: 'hide'}, "fast");
            });
        });
    </script>
</head>
<body>
<h1 class="title"><span class="close">全部收起/展开</span></h1>
<div class="menu">
    <h1 class="l1">翡翠JAVAEE练习</h1>
    <div class="slist">

        <%
            for (Map.Entry<Map<String, Object>, List<Map<String, Object>>> entry
                    : result.entrySet()) {
        %>
        <h2 class="l2"><a href="#"><%=(String) (entry.getKey().get("name"))%>
        </a></h2>
        <ul class="sslist">
            <%
                for (Map<String, Object> valueMap : entry.getValue()) {
            %>
            <li class="l3"><a href="<%=(String)(valueMap.get("link"))%>"
                              target="<%=(String)(valueMap.get("target"))%>"><%=(String) (valueMap.get("name"))%>
            </a></li>
            <%
                }
            %>


        </ul>
        <%
            }
        %>
    </div>

</div>
</body>
</html>