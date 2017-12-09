package com.rms.commons.filter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.NullRememberMeServices;
import org.springframework.security.web.authentication.RememberMeServices;

public class RequestHeaderProcessingFilter extends AbstractAuthenticationProcessingFilter {
	private String usernameHeader = "j_username";

	private String passwordHeader = "j_password";

	private RememberMeServices rememberMeServices = null;

	private AuthenticationSuccessHandler successHandler = null;

	private AuthenticationFailureHandler failureHandler = null;

	private String defaultTargetUrl = "/";

	private String defaultFailureUrl = "/login.jsp";

	private boolean allowSessionCreation = true;

	// j_spring_security_filter
	protected RequestHeaderProcessingFilter() {
		super("/j_spring_security_filter");
		this.rememberMeServices = (super.getRememberMeServices() == null) ? new NullRememberMeServices() : super.getRememberMeServices();
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
		String username = request.getParameter(usernameHeader);
		String password = request.getParameter(passwordHeader);
		SignedUsernamePasswordAuthenticationToken authRequest = new SignedUsernamePasswordAuthenticationToken(username, password);
		return this.getAuthenticationManager().authenticate(authRequest);
	}

	public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authResult) throws IOException, ServletException {
		SecurityContextHolder.getContext().setAuthentication(authResult);
		rememberMeServices.loginSuccess(request, response, authResult);
		// Fire event
		if (this.eventPublisher != null) {
			eventPublisher.publishEvent(new InteractiveAuthenticationSuccessEvent(authResult, this.getClass()));
		}
		if (successHandler == null) {
			successHandler = new PmsAuthenticationSuccessHandler(getDefaultTargetUrl());
		}
		successHandler.onAuthenticationSuccess(request, response, authResult);
	}

	public void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
		SecurityContextHolder.clearContext();
		if (failureHandler == null) {
			failureHandler = new PmsAuthenticationFailureHandler(getDefaultFailureUrl());
		}
		HttpSession session = request.getSession(false);
		if (session != null || allowSessionCreation) {
			request.getSession().setAttribute(SPRING_SECURITY_LAST_EXCEPTION_KEY, failed);
		}
		rememberMeServices.loginFail(request, response);
		failureHandler.onAuthenticationFailure(request, response, failed);
	}

	public String getDefaultTargetUrl() {
		return defaultTargetUrl;
	}

	public void setDefaultTargetUrl(String defaultTargetUrl) {
		this.defaultTargetUrl = defaultTargetUrl;
	}

	public String getPasswordHeader() {
		return passwordHeader;
	}

	public void setPasswordHeader(String passwordHeader) {
		this.passwordHeader = passwordHeader;
	}

	public String getUsernameHeader() {
		return usernameHeader;
	}

	public void setUsernameHeader(String usernameHeader) {
		this.usernameHeader = usernameHeader;
	}

	public String getDefaultFailureUrl() {
		return defaultFailureUrl;
	}

	public void setDefaultFailureUrl(String defaultFailureUrl) {
		this.defaultFailureUrl = defaultFailureUrl;
	}

	public boolean isAllowSessionCreation() {
		return allowSessionCreation;
	}

	public void setAllowSessionCreation(boolean allowSessionCreation) {
		this.allowSessionCreation = allowSessionCreation;
	}
}
