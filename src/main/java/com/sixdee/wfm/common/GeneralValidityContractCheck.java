package com.sixdee.wfm.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sixdee.wfm.configuration.Globals;
import com.sixdee.wfm.model.Validate;

/**
 * @author Arjun Kumbakkara
 * @version 1.0.0 | 6thMarch,2019|
 * @author(arjunkumbakkara.github.io)|JSON Parsing/Verification field wise.
 */
@Component
public class GeneralValidityContractCheck {
	private Logger logger = LogManager.getLogger(GeneralValidityContractCheck.class.getName());
	public static final String CHECK_TYPE_HEIRARCHY = "heirarchy";
	public static final String CHECK_TYPE_INJECTION = "injection";
	public static final String CHECK_TYPE_VALIDATION = "validation";

	Validate validateBean = null;

	public HashMap<String, Object> createJsonFieldMapping(String json, String requestMethodType) {
		JsonParser parser = new JsonParser();
		JsonObject object = (JsonObject) parser.parse(json);
		Set<Map.Entry<String, JsonElement>> set = object.entrySet();
		HashMap<String, Object> map = new HashMap<String, Object>();
		Iterator<Map.Entry<String, JsonElement>> iterator = set.iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, JsonElement> entry = iterator.next();
			String key = entry.getKey();
			JsonElement value = entry.getValue();
			if (null != value) {
				logger.debug(value);
				if (!value.isJsonPrimitive()) {
					if (value.isJsonObject()) {
						System.out.println("" + key + "|" + value.toString());
						map.put(key, createJsonFieldMapping(value.toString(), requestMethodType));
					} else if (value.isJsonArray() && value.toString().contains(":")) {
						List<HashMap<String, Object>> list = new ArrayList<>();
						JsonArray array = value.getAsJsonArray();
						if (null != array) {
							for (JsonElement element : array) {
								list.add(createJsonFieldMapping(element.toString(), requestMethodType));
							}
							map.put(key, list);
						}
					} else if (value.isJsonArray() && !value.toString().contains(":")) {
						map.put(key, value.getAsJsonArray());
					}
				} else {
					/******
					 * 1 TODO: ADD THE HEIRARCHY CHECK HERE : In case of incident accountId/userId
					 */

					/******
					 * 2 /*SQL INJECTION/ FORMULAIC INJECTION GO HERE
					 */
					if (checkForSecurityViolation(value.getAsString(), GeneralValidityContractCheck.CHECK_TYPE_INJECTION, "", key, requestMethodType)) {
						map.put(key, value.getAsString());
					} else {
						throw new SecurityContractViolationException(
								"Value to the key" + key + " : " + value.getAsString() + " is found as a Security contract violation(Suspected SQL Injection).Request aborted.");
					}

					/******
					 * 3 /*META PROPERTY VALIDATION GO HERE : MIN/MAX LENGTH , REGEX PATTERN VALIDATION
					 */
					if (checkForSecurityViolation(value.getAsString(), GeneralValidityContractCheck.CHECK_TYPE_VALIDATION, "", key, requestMethodType)) {
						map.put(key, value.getAsString());
					} else {
						throw new SecurityContractViolationException(
								"Value to the key" + key + " : " + value.getAsString() + " is found as a Security contract violation(Suspected SQL Injection).Request aborted.");
					}
				}
			}
		}
		return map;
	}

	public boolean isSecure(String json, HttpServletRequest req) {
		boolean isValid = false;
		/* TODO : Account/UserId from token extraction. */
		// String accountIdForHeirarchyCheck_fromToken=((AuthBean)req.getAttribute("tokenObject")).getAccountId();
		// createJsonFieldMapping(json,accountIdForHeirarchyCheck_fromToken);
		createJsonFieldMapping(json, req.getMethod());
		return isValid = true;

	}

	public boolean checkForSecurityViolation(String valueToBeChecked, String type, String accountFromToken, String key, String reqMethodType) {
		boolean isValid = true;
		if (type.equalsIgnoreCase(GeneralValidityContractCheck.CHECK_TYPE_INJECTION)) {
			if (containsAnyFromTheList(valueToBeChecked, Globals.SECURITY_LAYER_CHECK_CONSTANTS)) {
				isValid = false;
			}
		} else if (type.equalsIgnoreCase(GeneralValidityContractCheck.CHECK_TYPE_HEIRARCHY)) {
			isValid = true;
			/* TODO: Heirarchy Level Validation goes here */
			// Token Check and privilege identification.
		} else if (type.equalsIgnoreCase(GeneralValidityContractCheck.CHECK_TYPE_VALIDATION)) {
			validateBean = new Validate();
			isValid = validateBean.valdiate(key, valueToBeChecked, reqMethodType);
			validateBean = null;
		}
		return isValid;
	}

	public boolean containsAnyFromTheList(String inputStr, String[] securityConstants) {
		return Arrays.stream(securityConstants).parallel().anyMatch(inputStr::contains);
	}

	public boolean isJSON(String potentJson) {
		JSONObject jsonify = null;
		boolean isJSON = false;
		try {
			jsonify = new JSONObject(potentJson);
			isJSON = true;
		} catch (JSONException e) {
			logger.info("The attempted String is not a valid JSON");
			throw new SecurityContractViolationException("Attempted String is not a valid JSON");
		} finally {
			jsonify = null;
		}

		return isJSON;
	}
}