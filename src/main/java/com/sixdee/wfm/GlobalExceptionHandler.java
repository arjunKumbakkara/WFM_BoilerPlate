package com.sixdee.wfm;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.sixdee.wfm.common.ErrorDetails;
import com.sixdee.wfm.exception.DataViolationException;
import com.sixdee.wfm.exception.ResourceNotFoundException;
import com.sixdee.wfm.exception.ValidationException;

@ControllerAdvice
public class GlobalExceptionHandler {
	public static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	public ResponseEntity<?> globleExcpetionHandler(Exception ex, HttpServletRequest request) {
		logger.error("Trace of the encountered ERROR :");
		ex.printStackTrace();
		ErrorDetails errorDetails = new ErrorDetails(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, request.getRequestURI());
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {
		logger.error("||**TRACE**||  of the encountered ERROR :");
		ErrorDetails errorDetails = new ErrorDetails(ex.getMessage(), HttpStatus.NOT_FOUND, request.getRequestURI());
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}
	/*
	 * @ExceptionHandler(Exception.class) public ResponseEntity<?> globleExcpetionHandler(Exception ex, WebRequest request) { logger.error("||**TRACE**||  of the encountered ERROR :");
	 * ex.printStackTrace(); ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false)); return new ResponseEntity<>(errorDetails,
	 * HttpStatus.INTERNAL_SERVER_ERROR); }
	 */

	// The below configured Mapper does NOT capture the exception thrown in the Filter.Which we have handled explicitly.
	@ExceptionHandler(WebApplicationException.class)
	public ResponseEntity<?> webApplicationException(Exception ex, HttpServletRequest request) {
		logger.error("||**TRACE**|| of the encountered ERROR :ResponseEntityExceptionHandler");
		ex.printStackTrace();
		ErrorDetails errorDetails = new ErrorDetails(ex.getMessage(), HttpStatus.BAD_REQUEST, request.getRequestURI());
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<?> validationException(ValidationException ex, HttpServletRequest request) {
		logger.error("||**TRACE**||  of the encountered ERROR :");
		ErrorDetails errorDetails = new ErrorDetails(ex.getMessage(), HttpStatus.BAD_REQUEST, request.getRequestURI());
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DataViolationException.class)
	public ResponseEntity<?> dataViolationException(DataViolationException ex, HttpServletRequest request) {
		logger.error("||**TRACE**||  of the encountered ERROR :");
		ErrorDetails errorDetails = new ErrorDetails(ex.getMessage(), HttpStatus.METHOD_NOT_ALLOWED, request.getRequestURI());
		return new ResponseEntity<>(errorDetails, HttpStatus.METHOD_NOT_ALLOWED);
	}
}