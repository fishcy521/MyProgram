package com.feicuiedu.user.servlet;

import com.feicuiedu.utils.Config;
import com.feicuiedu.utils.DBUtil;

import javax.servlet.http.HttpServlet;
import java.io.IOException;

/**
 * Created by Administrator on 2017/4/27.
 */
public class SrvMaintain extends HttpServlet {
	
	protected void doGet(javax.servlet.http.HttpServletRequest request,
						 javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
		
	}
	
	protected void doPost(javax.servlet.http.HttpServletRequest request,
						  javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
		System.out.println(">>>>>>>>>>>>>>>>>");
		
		
		String loginName = request.getParameter("login_name");
		String userName = request.getParameter("use_name");
		String password = request.getParameter("password");
		String sex = request.getParameter("sex");
		String birthday = request.getParameter("birthday");
		String remark = request.getParameter("remark");
		

		
		String message = null;
		String flag = null;
		try {
			DBUtil.updateMethod(
					"insert into user_(login_name,user_name,passwd,sex,birthday,remark) values(?,?,?,?,?,?)",
					new String[]{loginName, userName, password, sex, birthday, remark});
			
			message = Config.getValue("M001");
			flag = "1";
		}
		catch (Exception e) {
			e.printStackTrace();
			message = Config.getValue("M002") + ":" + e.getMessage();
			flag = "2";
		}
		
		request.setAttribute("message", message);
		request.setAttribute("flag", flag);
		request.getRequestDispatcher("maintain.jsp").forward(request, response);
		
		
	}
}
