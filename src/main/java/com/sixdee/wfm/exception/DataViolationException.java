package com.sixdee.wfm.exception;

/**
 * @author Nalini N
 *
 * Date : 02-Apr-2019
 */
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DataViolationException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String resourceName;
	private String fieldName;
	private Object fieldValue;

	public DataViolationException(String operationName) {
		super(String.format("Failed!! Data Violation to delete %s", operationName));
		this.resourceName = operationName;

	}

	public String getResourceName() {
		return resourceName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public Object getFieldValue() {
		return fieldValue;
	}
}
