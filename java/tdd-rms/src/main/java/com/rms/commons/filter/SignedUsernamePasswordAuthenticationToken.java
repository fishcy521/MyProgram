package com.rms.commons.filter;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class SignedUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {
	private static final long serialVersionUID = 3145548673810647886L;
	public SignedUsernamePasswordAuthenticationToken(String principal, String credentials) {
		super(principal, credentials);
	}
}
