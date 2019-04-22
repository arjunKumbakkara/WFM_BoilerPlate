package com.sixdee.wfm.common;

import java.text.SimpleDateFormat;
/**
* @author Arjun Kumbakkara[2019@sixdee|WFM|v1.0.0] 
*/
import java.util.Date;

import org.springframework.http.HttpStatus;

public class ErrorDetails {
	private String responseTimestamp;
	private String message;
	private int status;
	private String error;
	private String path;

	public ErrorDetails(String message, ErrorCode status, HttpStatus httpStatus) {
		super();
		this.responseTimestamp = new SimpleDateFormat(Constants.RESPONSE_DATE_FORMAT).format(new Date());
		this.message = message;
		this.status = status.getErrorCode();
		this.error = httpStatus.getReasonPhrase();
	}

	public ErrorDetails(String message, HttpStatus status, String path) {
		super();
		this.responseTimestamp = new SimpleDateFormat(Constants.RESPONSE_DATE_FORMAT).format(new Date());
		this.message = message;
		this.status = status.value();
		this.error = status.getReasonPhrase();
		this.path = path;
	}

	public String getResponseTimestamp() {
		return responseTimestamp;
	}

	public String getPath() {
		return path;
	}

	public String getMessage() {
		return message;
	}

	public String getError() {
		return error;
	}

	public int getStatus() {
		return status;
	}
}
