package com.sixdee.wfm.user.exception;

import org.springframework.security.core.AuthenticationException;

public class AttemptLimitExceededException extends AuthenticationException {
	public AttemptLimitExceededException(String s) {
		super(s);
	}
}