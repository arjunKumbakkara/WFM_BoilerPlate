package com.sixdee.wfm.security.auth.ajax;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sixdee.wfm.common.ErrorCode;
import com.sixdee.wfm.common.ErrorDetails;
import com.sixdee.wfm.configuration.ConstantsLoader;
import com.sixdee.wfm.configuration.Globals;
import com.sixdee.wfm.security.exceptions.AuthMethodNotSupportedException;
import com.sixdee.wfm.security.exceptions.JwtExpiredTokenException;
import com.sixdee.wfm.user.exception.AttemptLimitExceededException;
import com.sixdee.wfm.user.exception.BlockedUserException;
import com.sixdee.wfm.user.exception.ExpiredUserException;
import com.sixdee.wfm.user.exception.InactiveUserException;
import com.sixdee.wfm.user.exception.InvalidTokenException;
import com.sixdee.wfm.user.exception.NonExistentUserException;
import com.sixdee.wfm.user.service.UserService;

/**
 * @author Arjun Kumbakkara[Feb15,2019@sixdee|WFM]
 */

@Component
public class AjaxAwareAuthenticationFailureHandler implements AuthenticationFailureHandler {
	private final ObjectMapper mapper;

	@Autowired
	public AjaxAwareAuthenticationFailureHandler(ObjectMapper mapper) {
		this.mapper = mapper;
	}

	@Autowired
	Globals global;

	@Autowired
	UserService userService;

	@Autowired
	ConstantsLoader con;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {

		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);

		int globalConfForMaxAttemptReset = Globals.loginMaxAttempt_Reset;

		if (e instanceof BlockedUserException) {
			mapper.writeValue(response.getWriter(), new ErrorDetails(e.getMessage(), ErrorCode.BLOCKEDUSER, HttpStatus.UNAUTHORIZED));
		}

		if (e instanceof InvalidTokenException) {
			mapper.writeValue(response.getWriter(), new ErrorDetails(e.getMessage(), ErrorCode.INVALID, HttpStatus.UNAUTHORIZED));
		}

		if (global.loginMaxAttempt != 0) {
			if (!(e instanceof BadCredentialsException)) {

			} else if (e instanceof BadCredentialsException) {
				global.loginMaxAttempt--;
				mapper.writeValue(response.getWriter(), new ErrorDetails(e.getMessage(), ErrorCode.AUTHENTICATION, HttpStatus.UNAUTHORIZED));

			}
		} else {
			try {
				userService.blockUserSinceWrongAttemptsExceeded(con.getCurrentUserName());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			global.loginMaxAttempt = globalConfForMaxAttemptReset;
			mapper.writeValue(response.getWriter(), new ErrorDetails("Max Attempts Limit reached.User has been temporarily blocked", ErrorCode.MAX_ATTEMPT, HttpStatus.UNAUTHORIZED));
		}

		if (e instanceof JwtExpiredTokenException) {
			mapper.writeValue(response.getWriter(), new ErrorDetails("Token has expired", ErrorCode.JWT_TOKEN_EXPIRED, HttpStatus.UNAUTHORIZED));
		} else if (e instanceof AuthMethodNotSupportedException) {
			mapper.writeValue(response.getWriter(), new ErrorDetails(e.getMessage(), ErrorCode.AUTHENTICATION, HttpStatus.UNAUTHORIZED));
		} else if (e instanceof BlockedUserException) {
			mapper.writeValue(response.getWriter(), new ErrorDetails(e.getMessage(), ErrorCode.BLOCKEDUSER, HttpStatus.UNAUTHORIZED));
		} else if (e instanceof InactiveUserException) {
			mapper.writeValue(response.getWriter(), new ErrorDetails(e.getMessage(), ErrorCode.INACTIVE_USER, HttpStatus.UNAUTHORIZED));
		} else if (e instanceof ExpiredUserException) {
			mapper.writeValue(response.getWriter(), new ErrorDetails(e.getMessage(), ErrorCode.EXPIRED_USER, HttpStatus.UNAUTHORIZED));
		} else if (e instanceof AttemptLimitExceededException) {
			mapper.writeValue(response.getWriter(), new ErrorDetails(e.getMessage(), ErrorCode.MAX_ATTEMPT, HttpStatus.UNAUTHORIZED));
		} else if (e instanceof NonExistentUserException) {
			mapper.writeValue(response.getWriter(), new ErrorDetails(e.getMessage(), ErrorCode.NON_EXISTENT, HttpStatus.UNAUTHORIZED));
		}

		mapper.writeValue(response.getWriter(), new ErrorDetails("Authentication failed", ErrorCode.AUTHENTICATION, HttpStatus.UNAUTHORIZED));
	}

}
