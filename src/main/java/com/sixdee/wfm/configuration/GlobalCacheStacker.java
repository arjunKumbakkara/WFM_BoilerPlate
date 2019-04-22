package com.sixdee.wfm.configuration;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import com.sixdee.wfm.model.ErrorCodes;
import com.sixdee.wfm.model.ValidationModel;
import com.sixdee.wfm.repository.ErrorCodeRepository;
import com.sixdee.wfm.repository.ValidationRepository;

@Configuration
@Order(1)
public class GlobalCacheStacker {
	private static final Logger logger = LogManager.getLogger(GlobalCacheStacker.class);

	public static boolean loaded = false;

	@Autowired
	ConstantsLoader constantsLoader;

	/*
	 * @Cacheable("Error") public Map<String, String> errorCodeMapper() { // Error Mappings. //Map<String, String> synchronizedHashMap = Collections.synchronizedMap(new HashMap<String, String>());
	 * HashMap<String, String> synchronizedHashMap = new HashMap<String, String>(); try { List<Map<String, Object>> rows = jtemp .queryForList("select ERROR_CODE,DESCRIPTION from " +
	 * TableNames.ERROR_CODE_MASTER + ""); for (Map row : rows) { synchronizedHashMap.put((String) row.get("ERROR_CODE"), (String) row.get("DESCRIPTION")); } loaded = true;
	 * log.info("Global Synchronized Error:Map Loaded :Size:" + synchronizedHashMap.size() + " Load Status Boolean::" + loaded); } catch (Exception e) { e.printStackTrace(); } finally { } return
	 * synchronizedHashMap; }
	 * 
	 */

	@Autowired
	private ValidationRepository apiFieldValidationRepository;

	@Autowired
	private ErrorCodeRepository errorCodeRepository;

	public void getApiFieldValidationData() {
		String value = null;
		List<ValidationModel> list = null;

		try {

			list = apiFieldValidationRepository.findAll();

			for (ValidationModel validationModel : list) {

				value = validationModel.getIsMandatory() + "#" + validationModel.getMinLength() + "#" + validationModel.getMaxLength() + "#" + validationModel.getRegularExpression().getRegExp() + "#"
						+ validationModel.getRegularExpression().getErrorCodes().getErrorCode();

				Globals.restAPIFieldValidationMap.put(validationModel.getFieldName(), value);
			}
		} catch (Exception e) {
			logger.error("Exception raised in getApiFieldValidationData " + e.getStackTrace());
		} finally {
			value = null;
			list = null;
		}

	}

	public void getErrorCodeMaster() {
		List<ErrorCodes> list = null;

		try {

			list = errorCodeRepository.findAll();

			for (ErrorCodes errorCodes : list) {

				Globals.errorCodeMaster.put(errorCodes.getErrorCode(), errorCodes.getErrorDesc());
			}
		} catch (Exception e) {
			logger.error("Exception raised in getApiFieldValidationData " + e.getStackTrace());
		} finally {
			list = null;
		}

	}

}
