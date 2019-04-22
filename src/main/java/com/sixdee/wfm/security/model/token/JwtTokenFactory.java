package com.sixdee.wfm.security.model.token;

import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sixdee.wfm.security.config.JwtSettings;
import com.sixdee.wfm.security.model.Scopes;
import com.sixdee.wfm.security.model.UserContext;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
/**
* @author Arjun Kumbakkara[2019@sixdee|WFM|v1.0.0] 
*/

@Component
public class JwtTokenFactory {
    private final JwtSettings settings;

    @Autowired
    public JwtTokenFactory(JwtSettings settings) {
        this.settings = settings;
    }

    public AccessJwtToken createAccessJwtToken(UserContext userContext) {
    	
        if (StringUtils.isBlank(userContext.getUsername())) 
            throw new IllegalArgumentException("Cannot create JWT Token without username");

        //Below Block suspended as the authorities feature not implmntd.[do activate when so]
        /*if (userContext.getAuthorities() == null || userContext.getAuthorities().isEmpty()) 
            throw new IllegalArgumentException("User doesn't have any privileges");*/

        Claims claims = Jwts.claims().setSubject(userContext.getUsername());
        claims.setAudience("ACCESS");
        
        //Block suspended as authorities correspond to privileges or Claims.thus!
        /*claims.put("scopes", userContext.getAuthorities().stream().map(s -> s.toString()).collect(Collectors.toList()));*/

        //Bogous CLAIM = PRIVILEGE (to be fetched from the privilege list which is the Authorities,Since no authorities,we send ADMIN just to not let it be empty;the claims segment of the token) [change]
        String admin="ADMIN";
        claims.put("scopes",admin);
        
        
        DateTime currentTime = new DateTime();

        String token = Jwts.builder()
          .setClaims(claims)
          .setIssuer(settings.getTokenIssuer())
          .setIssuedAt(currentTime.toDate())
          .setExpiration(currentTime.plusMinutes(settings.getTokenExpirationTime()).toDate())
          .signWith(SignatureAlgorithm.HS512, settings.getTokenSigningKey())
        .compact();

        return new AccessJwtToken(token, claims);
    }

    
    //This is the stub that really generates a token from the refreshforAccessToken's token.
    public JwtToken createRefreshToken(UserContext userContext) {
        if (StringUtils.isBlank(userContext.getUsername())) {
            throw new IllegalArgumentException("Cannot create JWT Refresh Token without username!!");
        }

        DateTime currentTime = new DateTime();

        Claims claims = Jwts.claims().setSubject(userContext.getUsername());
        
        //Below code suspended as Authority feature not implemented.
      /*  claims.put("scopes", Arrays.asList(Scopes.REFRESH_TOKEN.authority()));*/
        
        //bogous code
        String admin="REFRESH";
        claims.put("scopes",admin);
        
        
        String token = Jwts.builder()
          .setClaims(claims)
          .setIssuer(settings.getTokenIssuer())
          .setId(UUID.randomUUID().toString())
          .setIssuedAt(currentTime.toDate())
          .setExpiration(currentTime.plusMinutes(settings.getRefreshTokenExpTime()).toDate())
          .signWith(SignatureAlgorithm.HS512, settings.getTokenSigningKey())
        .compact();

        return new AccessJwtToken(token, claims);
    }
    

    //This is the token to only generate a new Token!
    public JwtToken createTokenToRefresh(UserContext userContext) {
        if (StringUtils.isBlank(userContext.getUsername())) {
            throw new IllegalArgumentException("Cannot create JWT Refresh Token without username!!");
        }

        DateTime currentTime = new DateTime();

        Claims claims = Jwts.claims().setSubject(userContext.getUsername());
        claims.setAudience("REFRESH");
        
        //Below code suspended as Authority feature not implemented.
      /*  claims.put("scopes", Arrays.asList(Scopes.REFRESH_TOKEN.authority()));*/
        
        //bogous code
        String admin="ADMIN";
        claims.put("scopes",admin);
        
        
        String token = Jwts.builder()
          .setClaims(claims)
          .setIssuer(settings.getTokenIssuer())
          .setId(UUID.randomUUID().toString())
          .setIssuedAt(currentTime.toDate())
          .setExpiration(currentTime.plusMinutes(settings.getRefreshTokenExpTime()).toDate())
          .signWith(SignatureAlgorithm.HS512, settings.getTokenSigningKey())
        .compact();

        return new AccessJwtToken(token, claims);
    }
    
    
}
