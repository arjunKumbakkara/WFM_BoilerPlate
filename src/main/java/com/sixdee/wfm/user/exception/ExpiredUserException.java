package com.sixdee.wfm.user.exception;

import org.springframework.security.core.AuthenticationException;

public class ExpiredUserException extends AuthenticationException {
	public ExpiredUserException(String s) {
		super(s);
	}
}