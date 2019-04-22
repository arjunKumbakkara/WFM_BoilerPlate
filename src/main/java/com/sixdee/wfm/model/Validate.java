package com.sixdee.wfm.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.sixdee.wfm.common.Constants;
import com.sixdee.wfm.configuration.Globals;
import com.sixdee.wfm.exception.ValidationException;

@Component
public class Validate {
	private Logger logger = LogManager.getLogger(Validate.class.getName());

	public boolean valdiate(String key, String value, String requestMethodType) {
		String isMandatory = null;
		String minLen = null;
		String maxLen = null;
		String regExp = null;
		String regExpErrorCode = null;
		String val[] = null;
		boolean isValid = true;
		int reqType = -1;

		logger.debug("Validating Bean >> Key >> " + key + " Value >>> " + value + " Request Method Type >>> " + requestMethodType);

		reqType = (requestMethodType.equalsIgnoreCase("GET")) ? Constants.HTTP_METHOD_TYPE_GET : reqType;
		reqType = (requestMethodType.equalsIgnoreCase("POST")) ? Constants.HTTP_METHOD_TYPE_POST : reqType;
		reqType = (requestMethodType.equalsIgnoreCase("PUT")) ? Constants.HTTP_METHOD_TYPE_PUT : reqType;
		reqType = (requestMethodType.equalsIgnoreCase("DELETE")) ? Constants.HTTP_METHOD_TYPE_DELETE : reqType;

		if (Globals.restAPIFieldValidationMap.containsKey(key)) {
			val = Globals.restAPIFieldValidationMap.get(key).toString().split("\\#", -1);
			if (val != null) {
				isMandatory = val[0];
				minLen = val[1];
				maxLen = val[2];
				regExp = val[3];
				regExpErrorCode = val[4];
			}

			if (value == null || value.equalsIgnoreCase("")) { /* Check If Its Mandatory */
				if (isMandatory != null && !isMandatory.equalsIgnoreCase("") && isMandatory.equalsIgnoreCase("1")) {
					throw new ValidationException(key, "", Constants.FAILURE_Missing_Mandatory_Field);
				}
			} else {
				if (isMandatory != null && !isMandatory.equalsIgnoreCase("") && isMandatory.equalsIgnoreCase("1")) {
					if (value.length() > 0 && value.length() < Integer.parseInt(minLen)) {
						throw new ValidationException(key, minLen, Constants.FAILURE_INVALID_MAX_LENGTH);
					} else if (value.length() > 0 && value.length() > Integer.parseInt(maxLen)) {
						throw new ValidationException(key, maxLen, Constants.FAILURE_INVALID_MIN_LENGTH);
					} else if (regExp != null && !regExp.equalsIgnoreCase("") && !value.matches(regExp)) {
						throw new ValidationException(key, "", regExpErrorCode);
					}
				} else {
					/* Value is not mandatory but checking value minlength,maxlength nd matching regexp */
					if (value.length() > 0 && value.length() < Integer.parseInt(minLen)) {
						throw new ValidationException(key, minLen, Constants.FAILURE_INVALID_MAX_LENGTH);
					} else if (value.length() > 0 && value.length() > Integer.parseInt(maxLen)) {
						throw new ValidationException(key, maxLen, Constants.FAILURE_INVALID_MIN_LENGTH);
					} else if (regExp != null && !regExp.equalsIgnoreCase("") && !value.matches(regExp)) {
						throw new ValidationException(key, "", regExpErrorCode);
					}
				}
			}
		} else {
			logger.debug("Validation Details not found for key >>" + key + " and request method Type >>> " + requestMethodType);
		}

		return isValid;
	}

}
