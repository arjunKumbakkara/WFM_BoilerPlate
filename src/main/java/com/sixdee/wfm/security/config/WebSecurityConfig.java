package com.sixdee.wfm.security.config;

import java.util.Arrays;


/**
 * @author Arjun Kumbakkara[2019@sixdee|WFM|v1.0.0] 
 */

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sixdee.wfm.configuration.PropertiesLoader;
import com.sixdee.wfm.security.RestAuthenticationEntryPoint;
import com.sixdee.wfm.security.auth.ajax.AjaxAuthenticationProvider;
import com.sixdee.wfm.security.auth.ajax.AjaxLoginProcessingFilter;
import com.sixdee.wfm.security.auth.jwt.JwtAuthenticationProvider;
import com.sixdee.wfm.security.auth.jwt.JwtTokenAuthenticationProcessingFilter;
import com.sixdee.wfm.security.auth.jwt.SkipPathRequestMatcher;
import com.sixdee.wfm.security.auth.jwt.extractor.TokenExtractor;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


	public static Logger logger = LogManager.getLogger(WebSecurityConfig.class);
	@Autowired private RestAuthenticationEntryPoint authenticationEntryPoint;
	@Autowired private AuthenticationSuccessHandler successHandler;
	@Autowired private AuthenticationFailureHandler failureHandler;
	@Autowired private AjaxAuthenticationProvider ajaxAuthenticationProvider;
	@Autowired private JwtAuthenticationProvider jwtAuthenticationProvider;
	@Autowired private TokenExtractor tokenExtractor;
	@Autowired private AuthenticationManager authenticationManager;
	@Autowired private ObjectMapper objectMapper;
	@Autowired private PropertiesLoader confi;
	
	@Bean
	protected AjaxLoginProcessingFilter buildAjaxLoginProcessingFilter() throws Exception {
		AjaxLoginProcessingFilter filter = new AjaxLoginProcessingFilter(confi.getLoginEntryPoint(), successHandler, failureHandler, objectMapper);
		filter.setAuthenticationManager(this.authenticationManager);
		return filter;
	}
	@Bean
	protected JwtTokenAuthenticationProcessingFilter buildJwtTokenAuthenticationProcessingFilter() throws Exception {

		List<String> pathsToSkip = Arrays.asList(confi.getRefreshTokenEntryPoint(),confi.getLoginEntryPoint(),confi.getFreeWayEntryPoint());
		SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(pathsToSkip,confi.getTokenEntryPoint());
		JwtTokenAuthenticationProcessingFilter filter 
		= new JwtTokenAuthenticationProcessingFilter(failureHandler, tokenExtractor, matcher);
		filter.setAuthenticationManager(this.authenticationManager);
		return filter;
	}
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	protected void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(ajaxAuthenticationProvider);
		auth.authenticationProvider(jwtAuthenticationProvider);
	}
	@Bean
	protected BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable() // We don't need CSRF for JWT based authentication .....as it is Stateless and no arent using any cookies.
		.exceptionHandling()
		.authenticationEntryPoint(this.authenticationEntryPoint)
		.and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.authorizeRequests()
		.antMatchers(confi.getLoginEntryPoint()).permitAll()
		.antMatchers(confi.getRefreshTokenEntryPoint()).permitAll()
		.antMatchers(confi.getConfigurationEntryPoint()).permitAll() 
		.antMatchers(confi.getFreeWayEntryPoint()).permitAll()
	/*	.antMatchers(
                HttpMethod.GET,
                "/v2/api-docs",
                "/swagger-resources/**",
                "/swagger-ui.html**",
                "/webjars/**",
                "favicon.ico"
        ).permitAll()*/
		.and()
		.authorizeRequests()
		.antMatchers(confi.getTokenEntryPoint()).authenticated() 
		.and()
		.addFilterBefore(buildAjaxLoginProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
		.addFilterBefore(buildJwtTokenAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class);
	}
}
