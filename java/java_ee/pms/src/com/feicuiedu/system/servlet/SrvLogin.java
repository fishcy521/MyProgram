package com.feicuiedu.system.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2017/4/27.
 */
public class SrvLogin extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doPost(req, resp);
		
		String userName = req.getParameter("user_name");
		String password = req.getParameter("password");
		
		
		// 验证用户名是否正确
		if (true) {
			// 正确
			req.getRequestDispatcher("system/main.jsp").forward(req,resp);
			// session 放入 登陆账号
			req.getSession().setAttribute("session_user_name","sky");
			
		}
		else {
			req.setAttribute("error_message","登陆失败");
			// 不正却
			req.getRequestDispatcher("login").forward(req,resp);
		}
		
		
	}
}
