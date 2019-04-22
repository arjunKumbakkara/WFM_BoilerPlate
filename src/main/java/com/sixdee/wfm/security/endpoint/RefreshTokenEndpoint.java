package com.sixdee.wfm.security.endpoint;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sixdee.wfm.configuration.ConstantsLoader;
import com.sixdee.wfm.configuration.PropertiesLoader;
import com.sixdee.wfm.model.User;
import com.sixdee.wfm.security.auth.JwtAuthenticationToken;
import com.sixdee.wfm.security.auth.jwt.extractor.TokenExtractor;
import com.sixdee.wfm.security.auth.jwt.verifier.TokenVerifier;
import com.sixdee.wfm.security.config.JwtSettings;
import com.sixdee.wfm.security.exceptions.InvalidJwtToken;
import com.sixdee.wfm.security.model.UserContext;
import com.sixdee.wfm.security.model.token.JwtToken;
import com.sixdee.wfm.security.model.token.JwtTokenFactory;
import com.sixdee.wfm.security.model.token.RawAccessJwtToken;
import com.sixdee.wfm.security.model.token.RefreshToken;
import com.sixdee.wfm.user.entity.UserDTO;
import com.sixdee.wfm.user.service.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
/**
* @author Arjun Kumbakkara[2019@sixdee|WFM|v1.0.0] 
*/


@RestController
public class RefreshTokenEndpoint {
    @Autowired private JwtTokenFactory tokenFactory;
    @Autowired private JwtSettings jwtSettings;
    @Autowired private UserService userService;
    @Autowired private TokenVerifier tokenVerifier;
    @Autowired @Qualifier("jwtHeaderTokenExtractor") private TokenExtractor tokenExtractor;
    

    private final Logger logger = LoggerFactory.getLogger(this.getClass());    //Slj4 used.

    @Autowired private PropertiesLoader confi;
    
    @RequestMapping(value="/api/auth/RefreshToken", method=RequestMethod.GET, produces={ MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody JwtToken refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
    	System.out.println("Gotten the Access Token from Bearer Tag");
    	
    	String tokenPayload = tokenExtractor.extract(request.getHeader(confi.getTokenHeaderParam()));  //before value: WebSecurityConfig.JWT_TOKEN_HEADER_PARAM
    	
    	System.out.println("Printing the tokenPayLoad"+tokenPayload);
    	
        
        //String tokenPayloadforRefresh = request.getHeader(confi.getTokenHeaderParam());   //default value : WebSecurityConfig.JWT_TOKEN_HEADER_PARAM
        /*
        RawAccessJwtToken token = new RawAccessJwtToken(tokenExtractor.extract(tokenPayload));
        return getAuthenticationManager().authenticate(new JwtAuthenticationToken(token));
        */
        
    	RawAccessJwtToken rawToken = new RawAccessJwtToken(tokenPayload);
        RefreshToken refreshToken = RefreshToken.create(rawToken, jwtSettings.getTokenSigningKey()).orElseThrow(() -> new InvalidJwtToken());
        
        System.out.println("Refresh Token (Post Claim Check!)"+refreshToken);
        
        
        Jws<Claims> claims = rawToken.parseClaims(jwtSettings.getTokenSigningKey());
        String audience = claims.getBody().getAudience();
        
        System.out.println("Check if REFRESH and NOT ACCESS!"+audience);
        
        if(audience.equalsIgnoreCase("ACCESS")){
        	throw new Exception("ACCESS FIRED FOR ACCESS : REFRESH EXPECTED FOR ACCESS ");
        }
        

        String jti = refreshToken.getJti();
        if (!tokenVerifier.verify(jti)) {
            throw new InvalidJwtToken();
        }

        String subject = refreshToken.getSubject();
        //Below block of code suspended as we aint implementing the Authorities feature(Roles).
       /* User user = userService.getByUsername(subject).orElseThrow(() -> new UsernameNotFoundException("User not found: " + subject));*/
        //79-93 : Bogous code [change when authorities implemented]
        User user = userService.getByUsername(subject);
        
        if(!user.getUserName().equals(null) && !user.getUserName().equalsIgnoreCase("null") && !user.getUserName().equalsIgnoreCase(" ")){
        	logger.error("NOT-ERROR   >>  PROCEED");
        }else{
        	if(user==null) throw new UsernameNotFoundException("User not found: " + subject);    //Dead code...Make it work
 /*       	Error error = user.orElseThrow(() ->new UsernameNotFoundException("User not found: " + subject));*/
        	logger.error("Class : tokenAuth.src.main.java.com.sixdee.security.auth.ajax.AjaxAuthenticationProvider.java : User not found Exception ");
        }
        
        //Suspended Code as the Authorities feature 
        /*List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getRole().authority()))
                .collect(Collectors.toList());*/

       //bogous
        String permitted= "This is for the Refresh Token Generation flow.Thus no Access granted prior to new Access Token reception.";
        UserContext userContext = UserContext.create(user.getUserName(), permitted);

        return tokenFactory.createAccessJwtToken(userContext);
    }
}
