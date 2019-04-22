package com.sixdee.wfm.filters;

import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonParseException;
import com.sixdee.wfm.common.Constants;
import com.sixdee.wfm.common.ErrorDetails;
import com.sixdee.wfm.common.GeneralValidityContractCheck;
import com.sixdee.wfm.exception.DataViolationException;
import com.sixdee.wfm.exception.ValidationException;

/**
 * @author Arjun Kumbakkara[2019@sixdee|WFM|v1.0.0]
 */

/*
 * @Data
 * 
 * @EqualsAndHashCode(callSuper = false)
 */
@Component
public class CommonRequestInterceptFilter extends OncePerRequestFilter {
	public static Logger logger = LoggerFactory.getLogger(CommonRequestInterceptFilter.class);
	private final String responseHeader;
	private final String mdcTokenKey;
	private final String mdcClientIpKey;
	private final String requestHeader;

	public CommonRequestInterceptFilter() {
		responseHeader = ServletFilterConfig.DEFAULT_RESPONSE_TOKEN_HEADER;
		mdcTokenKey = ServletFilterConfig.DEFAULT_MDC_UUID_TOKEN_KEY;
		mdcClientIpKey = ServletFilterConfig.DEFAULT_MDC_CLIENT_IP_KEY;
		requestHeader = ServletFilterConfig.DEFAULT_MDC_REQUEST_ID_HEADER;
	}

	public CommonRequestInterceptFilter(final String responseHeader, final String mdcTokenKey, final String mdcClientIPKey, final String requestHeader) {
		this.responseHeader = responseHeader;
		this.mdcTokenKey = mdcTokenKey;
		this.mdcClientIpKey = mdcClientIPKey;
		this.requestHeader = requestHeader;
	}

	@Override
	protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain)
			throws java.io.IOException, ServletException, WebApplicationException, JsonParseException {
		boolean isValidAndSafe = false;
		String requestBody = null;
		GeneralValidityContractCheck generalValidityContractCheck = null;
		try {
			generalValidityContractCheck = new GeneralValidityContractCheck();
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			logger.info("||***FILTER***||CommonRequestInterceptFilter|doFilterInternal: The request has been intercepted.");
			logger.info("||***FILTER***||CommonRequestInterceptFilter|doFilterInternal :  SECURITY LAYER VALIDATION [ FORMULAIC INJECTION |SQL INJECTION |  (only in case of native Queries)]");
			if (request.getMethod().equals("POST") || request.getMethod().equals("PUT")) {
				// We clone the incoming request object for now.
				HttpServletRequest currentRequest = (HttpServletRequest) request;
				MultiReadHttpServletRequest wrappedRequest = new MultiReadHttpServletRequest(currentRequest);
				requestBody = wrappedRequest.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
				logger.info("||***FILTER***||Request Body Recieved >>> " + requestBody);
				if (requestBody != null && generalValidityContractCheck.isJSON(requestBody)) {
					isValidAndSafe = generalValidityContractCheck.isSecure(requestBody, wrappedRequest);
				} else {
					throw new JsonParseException("UNPARSEABLE BODY . FLOW TERMINATES");
				}
				if (isValidAndSafe) {
					try {
						final String requestLifeCycleID = extractUUID(wrappedRequest);
						final String clientIP = extractClientIP(wrappedRequest);
						MDC.put(mdcClientIpKey, clientIP);
						MDC.put(mdcTokenKey, requestLifeCycleID);
						logger.info("METHOD: POST/PUT |*****Current Request Identification ID*****|  :" + requestLifeCycleID);
						logger.info("METHOD: POST/PUT |*****Current Request BODY*****|");
						logger.info("METHOD: POST/PUT |============================================|");
						System.out.println("\n |");
						System.out.println("Request Body:  " + requestBody);
						System.out.println("\n |");
						logger.info("METHOD: POST/PUT |============================================|");
						if (!StringUtils.isEmpty(responseHeader)) {
							response.addHeader(responseHeader, requestLifeCycleID);
						}
						chain.doFilter(wrappedRequest, response);
						logger.info("Server Response" + response.getStatus());
						if (response.getStatus() == 204) {
							logger.info("Request fulfilled.");
							response.setStatus(HttpServletResponse.SC_OK);
						}
					} finally {
						MDC.remove(mdcTokenKey);
						MDC.remove(mdcClientIpKey);
					}
				} else {
					logger.info("METHOD: POST/PUT |*****Current Request BODY*****|  \n:" + requestBody);
					throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).build());
				}

			} else if (request.getMethod().equals("GET")) {
				try {

					final String requestLifeCycleID = extractUUID(request);
					final String clientIP = extractClientIP(request);

					MDC.put(mdcClientIpKey, clientIP);
					MDC.put(mdcTokenKey, requestLifeCycleID);
					logger.info("METHOD: GET |*****Current Request Identification ID*****|  :" + requestLifeCycleID);
					logger.info("METHOD: GET |*****Current Request Body*****|  ");
					logger.info("METHOD: GET |============================================|:");
					System.out.println("\n |");
					System.out.println("Request Body:" + request.getRequestURI());
					System.out.println("\n |");

					/* Validating Path Variable in GET Request */
					/*
					 * String pathVariable = request.getRequestURI().substring(request.getRequestURI().lastIndexOf("/") + 1, request.getRequestURI().length()); if
					 * (!pathVariable.matches(Constants.REG_EXP_ONLY_NUMERIC)) { throw new ValidationException("Path Variable ", "", Constants.FAILURE_ONLY_NUMERIC); }
					 */

					logger.info("METHOD: GET |============================================|:");
					if (!StringUtils.isEmpty(responseHeader)) {
						response.addHeader(responseHeader, requestLifeCycleID);
					}
					chain.doFilter(request, response);

					logger.info("Server Response" + response.getStatus());
					if (response.getStatus() == 204) {
						logger.info("Request fulfilled.");
						response.setStatus(HttpServletResponse.SC_OK);
					}
				} finally {
					MDC.remove(mdcTokenKey);
					MDC.remove(mdcClientIpKey);
				}
			} else {
				try {
					final String requestLifeCycleID = extractUUID(request);
					final String clientIP = extractClientIP(request);
					logger.info("METHOD: DELETE/PATCH/HEAD/CONNECT.. |*Current Request Identification ID*|  :" + requestLifeCycleID);
					MDC.put(mdcClientIpKey, clientIP);
					MDC.put(mdcTokenKey, requestLifeCycleID);
					logger.info("METHOD: DELETE/PATCH/HEAD/CONNECT |*****Current Request Identification ID*****|  :" + requestLifeCycleID);
					logger.info("METHOD: DELETE/PATCH/HEAD/CONNECT |*****Current Request Body*****|  ");
					logger.info("METHOD: DELETE/PATCH/HEAD/CONNECT |============================================|:");
					System.out.println("\n |");
					System.out.println("Request Body:" + request.getRequestURI());
					System.out.println("\n |");

					/* Validating Path Variable in GET Request */
					String pathVariable = request.getRequestURI().substring(request.getRequestURI().lastIndexOf("/") + 1, request.getRequestURI().length());
					if (!pathVariable.matches(Constants.REG_EXP_ONLY_NUMERIC)) {
						throw new ValidationException("Path Variable ", "", Constants.FAILURE_ONLY_NUMERIC);
					}

					logger.info("METHOD: DELETE |============================================|:");
					if (!StringUtils.isEmpty(responseHeader)) {
						response.addHeader(responseHeader, requestLifeCycleID);
					}
					chain.doFilter(request, response);
					logger.info("Server Response" + response.getStatus());
					if (response.getStatus() == 204) {
						logger.info("Request fulfilled.");
						response.setStatus(HttpServletResponse.SC_OK);
					}
				} finally {
					MDC.remove(mdcTokenKey);
					MDC.remove(mdcClientIpKey);
				}

			}
		} catch (Exception e) {
			logger.error("Exception raised in CommonRequestInterceptorFilter >> " + e.getMessage());
			if (e instanceof WebApplicationException) {
				ErrorDetails errorResponse = new ErrorDetails(e.getMessage(), HttpStatus.BAD_REQUEST, request.getRequestURI());
				response.setStatus(HttpStatus.BAD_REQUEST.value());
				response.getWriter().write(convertObjectToJson(errorResponse));
			} else if (e instanceof JsonParseException) {
				ErrorDetails errorResponse = new ErrorDetails(e.getMessage(), HttpStatus.BAD_REQUEST, request.getRequestURI());
				response.setStatus(HttpStatus.BAD_REQUEST.value());
				response.getWriter().write(convertObjectToJson(errorResponse));
				// throw new WebApplicationException(Response.status(Response.Status.NOT_ACCEPTABLE).build());
			} else if (e instanceof ValidationException) {
				ErrorDetails errorResponse = new ErrorDetails(e.getMessage(), HttpStatus.BAD_REQUEST, request.getRequestURI());
				response.setStatus(HttpStatus.BAD_REQUEST.value());
				response.getWriter().write(convertObjectToJson(errorResponse));
			} else if (e instanceof DataViolationException) {
				ErrorDetails errorResponse = new ErrorDetails(e.getMessage(), HttpStatus.METHOD_NOT_ALLOWED, request.getRequestURI());
				response.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
				response.getWriter().write(convertObjectToJson(errorResponse));
			} else if (e instanceof RuntimeException) {
				ErrorDetails errorResponse = new ErrorDetails(e.getMessage(), HttpStatus.BAD_REQUEST, request.getRequestURI());
				response.setStatus(HttpStatus.BAD_REQUEST.value());
				response.getWriter().write(convertObjectToJson(errorResponse));
			}
		} finally {
			requestBody = null;
			generalValidityContractCheck = null;
		}
	}

	private String extractUUID(final HttpServletRequest request) {
		final String token;
		if (!StringUtils.isEmpty(requestHeader) && !StringUtils.isEmpty(request.getHeader(requestHeader))) {
			token = request.getHeader(requestHeader);
		} else {
			token = UUID.randomUUID().toString().toUpperCase().replace("-", "");
		}
		return token;
	}

	private String extractClientIP(final HttpServletRequest request) {
		final String clientIP;
		if (request.getHeader("X-Forwarded-For") != null) {
			clientIP = request.getHeader("X-Forwarded-For").split(",")[0];
		} else {
			clientIP = request.getRemoteAddr();
		}
		return clientIP;
	}

	@Override
	protected boolean isAsyncDispatch(final HttpServletRequest request) {
		return false;
	}

	@Override
	protected boolean shouldNotFilterErrorDispatch() {
		return false;
	}

	public String convertObjectToJson(Object object) throws JsonProcessingException {
		if (object == null) {
			return null;
		}
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(object);
	}

}
