package com.sixdee.wfm.user.exception;

import org.springframework.security.core.AuthenticationException;

public class BlockedUserException extends AuthenticationException {
	public BlockedUserException(String s) {
		super(s);
	}
}