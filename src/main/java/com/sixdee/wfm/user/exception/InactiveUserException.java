package com.sixdee.wfm.user.exception;

import org.springframework.security.core.AuthenticationException;

public class InactiveUserException extends AuthenticationException {
	public InactiveUserException(String s) {
		super(s);
	}
}