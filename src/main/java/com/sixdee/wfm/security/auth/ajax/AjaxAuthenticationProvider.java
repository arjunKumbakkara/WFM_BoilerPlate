package com.sixdee.wfm.security.auth.ajax;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.sixdee.wfm.configuration.ConstantsLoader;
import com.sixdee.wfm.model.User;
import com.sixdee.wfm.security.model.UserContext;
import com.sixdee.wfm.user.entity.UserDTO;
import com.sixdee.wfm.user.exception.AttemptLimitExceededException;
import com.sixdee.wfm.user.exception.BlockedUserException;
import com.sixdee.wfm.user.exception.ExpiredUserException;
import com.sixdee.wfm.user.exception.InactiveUserException;
import com.sixdee.wfm.user.exception.NonExistentUserException;
import com.sixdee.wfm.user.service.UserService;

/**
 * @author Arjun Kumbakkara[Feb15,2019@sixdee|WFM] 
 */

@PropertySource("classpath:application.properties")
@Component
public class AjaxAuthenticationProvider implements AuthenticationProvider {
	private final BCryptPasswordEncoder encoder;
	private UserService userService;
	private final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY HH:mm:ss");
	private final SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-YYYY");
	private final SimpleDateFormat sdf3 = new SimpleDateFormat("ddMMyyyyHHmmss");
	private final SimpleDateFormat sdf4 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	//2030-06-17 00:00:00.0

	public static Logger logger = LogManager.getLogger(AjaxAuthenticationProvider.class);
	/*	
	@Value("${ldap.conf}")
	public static String ldapConfig;
	 */	
	@Autowired
	ConstantsLoader con;

	@Autowired
	public AjaxAuthenticationProvider(UserService userService, final BCryptPasswordEncoder encoder) {
		this.userService = userService;
		this.encoder = encoder;
	}

	@SuppressWarnings("unused")
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		Assert.notNull(authentication, "No authentication data provided");
		//logger.info("Authenticity Delegation : Call to authenticate has been initiated.");
		String username = (String) authentication.getPrincipal();
		String password = (String) authentication.getCredentials();
		boolean attemptClear=false;
		boolean antiflowBreaker=true;
		User user =null;
		UserContext userContext =null;
		try{
			/*try {
			attemptClear=userService.loginAttemptClear(user);
		} catch (Exception e1) {
			e1.printStackTrace();
		}*/
			user = userService.getByUsername(username);
			if(user==null){
				throw new NonExistentUserException("Non Existent User Attempted!");
			}
			logger.info("User::::::::"+user);
			/*Storing UserName globally for this request cycle.Only this request though*/
			//TODO: Examine and activate //
			con.setCurrentUserName(username);
			String authType = user.getAuthType();
			
			switch (authType) {
			
			case "JDBC":
				/*JDBC Conventional Authentication*/
				if (user != null) {
					if (user.getStatus() != null && user.getStatus().equalsIgnoreCase("D")) {
						antiflowBreaker=false;
						throw new InactiveUserException("Inactive User!!!!");
					} else if (user.getBlockStatus().equalsIgnoreCase("1")) {
						throw new BlockedUserException("Blocked User!!!!");
					} else
						
						/*if (user.getEndDate() != null && user.getEndDate() != ""
						    							&& new Date().after(sdf1.parse(sdf1.format(sdf.parse(user.getEndDate()))))) {*/
						
						System.out.println("Boolean"+sdf3.parse(""+new Date()).after(sdf3.parse(""+user.getEndDate())));
					System.out.println("End date"+""+user.getEndDate());
					if (user.getEndDate() != null && ""+user.getEndDate()!= ""
							&& new Date().after(sdf3.parse(""+user.getEndDate()))) {
						throw new ExpiredUserException("Expired User!!!!");
					} else {
						logger.info("password::::"+password+"DB pass::::"+user.getLogin_password());
						if (!encoder.matches(password, user.getLogin_password())) {
							antiflowBreaker=false;
							throw new BadCredentialsException("Authentication Failed. Username or Password not valid.");
						}
					}
				} else {
					throw new UsernameNotFoundException("User not found: " + username);
				}
				break;
			case "LDAP":
				/*LDAP Authentication
						        	try {
										if(!ldapauthenticate(user)){
											throw new BadCredentialsException("Authentication Failed. Username or Password not valid.");	
										}else{
											logger.info("LDAP Authentication suggested positive.User Authenticated.");

										}
									} catch (NamingException e) {
										e.printStackTrace();
									}
						        	break;*/
			case "JPA":
				/*JPA based Authentication*/
				if (user != null) {
					if (user.getStatus() != null && user.getStatus().equalsIgnoreCase("D")) {
						antiflowBreaker=false;
						throw new InactiveUserException("Inactive User!!!!");
					} else if (user.getBlockStatus().equalsIgnoreCase("1")) {
						throw new BlockedUserException("Blocked User!!!!");
					} else
						/*if (user.getEndDate() != null && user.getEndDate() != ""
						    							&& new Date().after(sdf1.parse(sdf1.format(sdf.parse(user.getEndDate()))))) {*/
						//Fri Feb 22 20:56:33 IST 2019
						//Mon Jun 19 23:07:00 IST 5
						System.out.println("End date"+""+sdf3.parse(""+sdf3.format(new Date())));
						System.out.println("Boolean"+new Date().after(sdf4.parse(""+user.getEndDate())));
					
					if (user.getEndDate() != null && ""+user.getEndDate()!= ""
							&& new Date().after(sdf4.parse(""+user.getEndDate()))) {
						throw new ExpiredUserException("Expired User!!!!");
					} else {
						logger.info("password::::"+password+"DB pass::::"+user.getLogin_password());
						if (!encoder.matches(password, user.getLogin_password())) {
							antiflowBreaker=false;
							throw new BadCredentialsException("Authentication Failed. Username or Password not valid.");
						}
					}
				} else {
					throw new UsernameNotFoundException("User not found: " + username);
				}
				break;
			default:
				throw new IllegalArgumentException(authType + " is not yet implemented!");
			}

			/*  PASSWORD ATTEMPT COUNTER:For User Block logic
	    	if (TotalAttempts != 0) {
			    if (antiflowBreaker) {
			    } else {
			        TotalAttempts--;
			    }
			}else {
				throw new AttemptLimitExceededException("Maximum number of attempts Exceeded.User moved to Blocked state thus.");
			}*/


			String authenticationLoggerMsg = "You have been Authenticated to access this protected API System.";
			userContext = UserContext.create(username, authenticationLoggerMsg);
			// Catch
			// here
			// is
			// that
		}catch(Exception e){

			if(e instanceof BadCredentialsException){
				throw new BadCredentialsException("Authentication Failed. Username or Password not valid.");
			}else if(e instanceof ExpiredUserException){
				throw new ExpiredUserException("Expired User!!!!");
			}else if(e instanceof BlockedUserException){
				throw new BlockedUserException("Blocked User!!!!");
			}else if(e instanceof InactiveUserException){
				throw new InactiveUserException("Inactive User!!!!");	
			}else if(e instanceof IllegalArgumentException){
				throw new IllegalArgumentException("UserName not being fetched!");	
			}else if(e instanceof NonExistentUserException){
				throw new NonExistentUserException("Non Existent UserName Attempted!");	
			}else {
				e.printStackTrace();
			}

		}finally{
			user=null;
		}
		// credentials
		// are
		// required
		// by
		// the
		// super
		// method
		return new UsernamePasswordAuthenticationToken(userContext, null);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}
}
