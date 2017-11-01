package com.feicuiedu.news.servlet;

import com.feicuiedu.utils.DBUtil;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/27.
 */
public class SrvList extends javax.servlet.http.HttpServlet {
	protected void doPost(javax.servlet.http.HttpServletRequest request,
						  javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
		
		String type = request.getParameter("type") == null?"-1":request.getParameter("type");
		StringBuilder sbSQL = new StringBuilder();
		sbSQL.append("select * from news_ where 1=1");
		String[] args = null;
		if (!"-1".equals(type)) {
			args = new String[1];
			sbSQL.append(" and type = ?");
			args[0] = type;
		}
		List<Map<String,Object>> list = DBUtil.queryMethod(sbSQL.toString(), args);
		
		List<Map<String,Object>> lstTypes = DBUtil.queryMethod("select * from dict_detail_ where dict_id = (select id from dict_ where dict_name='news_type')", null);
		
		request.setAttribute("list",list);
		request.setAttribute("lstTypes",lstTypes);
		
		
		request.getRequestDispatcher("../news/list.jsp").forward(request,response);
	}
	
	protected void doGet(javax.servlet.http.HttpServletRequest request,
						 javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
		doPost(request,response);
	}
}
