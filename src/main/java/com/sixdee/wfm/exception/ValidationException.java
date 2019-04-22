/**
 * 
	
 */
package com.sixdee.wfm.exception;

/**
 * @author Nalini N
 *
 * Date : 02-Apr-2019
 */
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.sixdee.wfm.configuration.Globals;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ValidationException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fieldName;
	private String errorCode;

	public ValidationException(String fieldName, String errorCodeType, String errorCode) {

		super(String.format(Globals.errorCodeMaster.get(errorCode), fieldName, errorCodeType));
		this.fieldName = fieldName;
		this.errorCode = errorCode;
	}

	public String getFieldName() {
		return fieldName;
	}

	public String getErrorCode() {
		return errorCode;
	}

}
