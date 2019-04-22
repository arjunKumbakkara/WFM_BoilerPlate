package com.sixdee.wfm.common;

/**
* @author Arjun Kumbakkara[2019@sixdee|WFM|v1.0.0] 
*/
import com.fasterxml.jackson.annotation.JsonValue;

public enum ErrorCode {
	GLOBAL(2),

	AUTHENTICATION(10), JWT_TOKEN_EXPIRED(11), BLOCKEDUSER(12), INACTIVE_USER(13), EXPIRED_USER(14), MAX_ATTEMPT(16), INVALID(17), NON_EXISTENT(18), BAD_REQUEST(400);

	private int errorCode;

	private ErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	@JsonValue
	public int getErrorCode() {
		return errorCode;
	}
}
