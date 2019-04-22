package com.sixdee.wfm.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


/**
 * @author Arjun Kumbakkara[2019@sixdee|WFM|v1.0.0] 
 */

@Configuration
@ConfigurationProperties(prefix = "wfm.security.jwt")
public class JwtSettings {
	private Integer tokenExpirationTime;
	private String tokenIssuer;
	private String tokenSigningKey;
	private Integer refreshTokenExpTime;
	public Integer getRefreshTokenExpTime() {
		return refreshTokenExpTime;
	}
	public void setRefreshTokenExpTime(Integer refreshTokenExpTime) {
		this.refreshTokenExpTime = refreshTokenExpTime;
	}
	public Integer getTokenExpirationTime() {
		return tokenExpirationTime;
	}
	public void setTokenExpirationTime(Integer tokenExpirationTime) {
		this.tokenExpirationTime = tokenExpirationTime;
	}
	public String getTokenIssuer() {
		return tokenIssuer;
	}
	public void setTokenIssuer(String tokenIssuer) {
		this.tokenIssuer = tokenIssuer;
	}
	public String getTokenSigningKey() {
		return tokenSigningKey;
	}
	public void setTokenSigningKey(String tokenSigningKey) {
		this.tokenSigningKey = tokenSigningKey;
	}
}
