package com.sixdee.wfm.common;
/**
* @author Arjun Kumbakkara[2019@sixdee|WFM|v1.0.0] 
*/

import java.text.SimpleDateFormat;

public interface Constants {

	final String REQUEST_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	final String RESPONSE_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	final SimpleDateFormat formatter = new SimpleDateFormat(REQUEST_DATE_FORMAT);
	final SimpleDateFormat displayFormatter = new SimpleDateFormat(RESPONSE_DATE_FORMAT);

	final String FAILURE_Missing_Mandatory_Field = "SC0009";
	final String FAILURE_INVALID_MIN_LENGTH = "SC0011";
	final String FAILURE_INVALID_MAX_LENGTH = "SC0012";
	final String FAILURE_ONLY_NUMERIC = "SC0015";

	final int HTTP_METHOD_TYPE_GET = 0;
	final int HTTP_METHOD_TYPE_POST = 1;
	final int HTTP_METHOD_TYPE_PUT = 2;
	final int HTTP_METHOD_TYPE_DELETE = 3;

	final int REQUEST_TYPE_GET = 0;
	final int REQUEST_TYPE_POST = 1;
	final int REQUEST_TYPE_PUT = 2;
	final int REQUEST_TYPE_DELETE = 3;

	final String REG_EXP_ONLY_NUMERIC = "^[0-9]*$";

}
