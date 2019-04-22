package com.sixdee.wfm.configuration;
/**
* @author Arjun Kumbakkara[2019@sixdee|WFM|v1.0.0] 
*/
import java.util.List;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Configuration
@PropertySource(value={"classpath:action.properties"})
public class ConstantsLoader {

	private String currentUserName="WFMAdmin";
	/**
	 * @return the currentUserName
	 */
	public String getCurrentUserName() {
		return currentUserName;
	}

	/**
	 * @param currentUserName the currentUserName to set
	 */
	public void setCurrentUserName(String currentUserName) {
		this.currentUserName = currentUserName;
	}

	/*UserName Store*/
	@Value("${SECURITY_LAYER_CHECK_CONSTANTS}")
	private String securityLayerCheckConstants;
	
	/**
	 * @return the securityLayerCheckConstants
	 */
	public String getSecurityLayerCheckConstants() {
		return securityLayerCheckConstants;
	}

	/**
	 * @param securityLayerCheckConstants the securityLayerCheckConstants to set
	 */
	public void setSecurityLayerCheckConstants(String securityLayerCheckConstants) {
		this.securityLayerCheckConstants = securityLayerCheckConstants;
	}

	/**DIRECT CONSTANTS*/
	public static String  LOCAL_IP = "10.0.1.0";


	/**
	 * @return the lOCAL_IP
	 */
	public static String getLOCAL_IP() {
		return LOCAL_IP;
	}

	/**
	 * @param lOCAL_IP the lOCAL_IP to set
	 */
	public static void setLOCAL_IP(String lOCAL_IP) {
		LOCAL_IP = lOCAL_IP;
	}



}

