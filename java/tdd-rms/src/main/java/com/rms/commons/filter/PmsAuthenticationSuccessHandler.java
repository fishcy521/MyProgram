package com.rms.commons.filter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.rms.modules.utils.ConvertUtil;

public class PmsAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	private String defaultUrl = "/pms/psm/project.action.action";

	public PmsAuthenticationSuccessHandler() {
	}

	public PmsAuthenticationSuccessHandler(String url) {
		defaultUrl = url;
	}

	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException, ServletException {
		// TODO 自动生成方法存根
		String url = request.getContextPath() + defaultUrl;
		String paramUrl = ConvertUtil.convertNull(request.getParameter("url"));
		if (!"".equals(paramUrl)) {
			url = request.getContextPath() + paramUrl;
		} else {
			url = request.getContextPath() + defaultUrl;
		}
		response.sendRedirect(url);
	}

}
