package com.sixdee.wfm.user.exception;

import org.springframework.security.core.AuthenticationException;

public class NonExistentUserException extends AuthenticationException {
	public NonExistentUserException(String s) {
		super(s);
	}
}