package com.rms.commons.filter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class PmsAuthenticationFailureHandler implements AuthenticationFailureHandler {
	private String defaultUrl = "/login.action";

	public PmsAuthenticationFailureHandler() {
	}

	public PmsAuthenticationFailureHandler(String url) {
		defaultUrl = url;
	}

	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException auth) throws IOException, ServletException {
		// TODO 自动生成方法存根
		response.sendRedirect(defaultUrl);
	}

}
