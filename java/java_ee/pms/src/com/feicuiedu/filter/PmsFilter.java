package com.feicuiedu.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2017/4/28.
 */
public class PmsFilter implements Filter {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("PmsFilter.init");
	}
	
	@Override
	public void doFilter(ServletRequest servletRequest,
						 ServletResponse servletResponse,
						 FilterChain filterChain) throws IOException, ServletException {
		System.out.println("PmsFilter.doFilter");
		servletRequest.setCharacterEncoding("utf-8");
		
		//servletRequest.getRequestDispatcher("/user/maintain.jsp").forward(servletRequest,servletResponse);
		filterChain.doFilter(servletRequest,servletResponse);
	}
	
	@Override
	public void destroy() {
		System.out.println("PmsFilter.destroy");
	}
}
