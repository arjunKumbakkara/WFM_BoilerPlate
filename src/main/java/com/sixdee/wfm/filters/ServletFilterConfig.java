package com.sixdee.wfm.filters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties.Env;
/**
* @author Arjun Kumbakkara[2019@sixdee|WFM|v1.0.0] 
*/
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import lombok.Data;
/**
* @author Arjun Kumbakkara[2019@sixdee|WFM|v1.0.0] 
*/

@Configuration
public class ServletFilterConfig {
	
/*	@Autowired
	static	Environment env;
    public static final String DEFAULT_RESPONSE_TOKEN_HEADER = ""+env.getProperty("wfm.logging.default.responseId.header");
    public static final String DEFAULT_MDC_UUID_TOKEN_KEY = ""+env.getProperty("wfm.logging.default.request.uuid");
    public static final String DEFAULT_MDC_CLIENT_IP_KEY = ""+env.getProperty("wfm.logging.default.request.client.ip");
*/
	@Autowired
		Environment env;
	
	//TODO : Make the below configurable(Static fields included.)
	public static final String DEFAULT_RESPONSE_TOKEN_HEADER = "X-Response-ID";
    public static final String DEFAULT_MDC_UUID_TOKEN_KEY = "WFM_THIS_REQUEST.UUID";
    public static final String DEFAULT_MDC_CLIENT_IP_KEY = "127.0.0.1";
    public static final String DEFAULT_MDC_REQUEST_ID_HEADER = "X-Request-ID";
    
    private String responseHeader = DEFAULT_RESPONSE_TOKEN_HEADER;
    private String mdcTokenKey = DEFAULT_MDC_UUID_TOKEN_KEY;
    private String mdcClientIpKey = DEFAULT_MDC_CLIENT_IP_KEY;
    private String requestHeader = DEFAULT_MDC_REQUEST_ID_HEADER;

    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Bean
    public FilterRegistrationBean servletRegistrationBean() {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        final CommonRequestInterceptFilter commonpointfilter = new CommonRequestInterceptFilter(responseHeader, mdcTokenKey, mdcClientIpKey, requestHeader);
        registrationBean.setFilter(commonpointfilter);
        registrationBean.addUrlPatterns("/api/*");
        registrationBean.setOrder(3);
        return registrationBean;
    }
}
