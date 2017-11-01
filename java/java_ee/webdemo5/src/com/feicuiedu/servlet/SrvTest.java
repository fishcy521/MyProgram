package com.feicuiedu.servlet;

import com.feicuiedu.model.User;
import com.feicuiedu.utils.DBUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/24.
 */
public class SrvTest extends javax.servlet.http.HttpServlet {
	protected void doPost(javax.servlet.http.HttpServletRequest request,
						  javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
		
	}
	
	protected void doGet(javax.servlet.http.HttpServletRequest request,
						 javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
		System.out.println("srvTest");
		
		String userName =  request.getParameter("userName");
		String passwd = request.getParameter("passwd");
		
		List<User> lstUser = new ArrayList<>();
		
		if ("admin".equals(userName)&&"pwd".equals(passwd)) {
			List<Map<String,Object>> list = DBUtil.query("select * from user_", null);
			
			for (Map<String,Object> map:list) {
				User user = new User();
				user.setName((String) map.get("name"));
				user.setSex((String) map.get("sex"));
				lstUser.add(user);
				
			}
		}
		
		request.setAttribute("lstUser",lstUser);
		request.getRequestDispatcher("end.jsp").forward(request,response);
	}
}
