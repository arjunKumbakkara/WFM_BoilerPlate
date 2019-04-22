package com.sixdee.wfm.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
* @author Arjun Kumbakkara[2019@sixdee|WFM|v1.0.0] 
*/

@Component
@PropertySource(value={"classpath:config.properties"})
public class PropertiesLoader {

    @Value("${wfm.JWT_TOKEN_HEADER_PARAM}")
    private String TokenHeaderParam;
 
    @Value("${wfm.FORM_BASED_LOGIN_ENTRY_POINT}")
    private String LoginEntryPoint;
 
    @Value("${wfm.TOKEN_BASED_AUTH_ENTRY_POINT}")
    private String TokenEntryPoint;

    @Value("${wfm.TOKEN_REFRESH_ENTRY_POINT}")
    private String RefreshTokenEntryPoint;
    
    @Value("${wfm.CONFIGURATION_ENTRY_POINT}")
    private String configurationEntryPoint;

    @Value("${wfm.FREEWAY_ENTRY_POINT}")
    private String freeWayEntryPoint;

    
    
	/**
	 * @return the freeWayEntryPoint
	 */
	public String getFreeWayEntryPoint() {
		return freeWayEntryPoint;
	}


	/**
	 * @param freeWayEntryPoint the freeWayEntryPoint to set
	 */
	public void setFreeWayEntryPoint(String freeWayEntryPoint) {
		this.freeWayEntryPoint = freeWayEntryPoint;
	}


	public String getTokenHeaderParam() {
		return TokenHeaderParam;
	}
    
    
	public String getConfigurationEntryPoint() {
		return configurationEntryPoint;
	}


	public void setConfigurationEntryPoint(String configurationEntryPoint) {
		this.configurationEntryPoint = configurationEntryPoint;
	}


	public void setTokenHeaderParam(String tokenHeaderParam) {
		TokenHeaderParam = tokenHeaderParam;
	}

	public String getLoginEntryPoint() {
		return LoginEntryPoint;
	}

	public void setLoginEntryPoint(String loginEntryPoint) {
		LoginEntryPoint = loginEntryPoint;
	}

	public String getTokenEntryPoint() {
		return TokenEntryPoint;
	}

	public void setTokenEntryPoint(String tokenEntryPoint) {
		TokenEntryPoint = tokenEntryPoint;
	}

	public String getRefreshTokenEntryPoint() {
		return RefreshTokenEntryPoint;
	}

	public void setRefreshTokenEntryPoint(String refreshTokenEntryPoint) {
		RefreshTokenEntryPoint = refreshTokenEntryPoint;
	}
 	
	
	
}

