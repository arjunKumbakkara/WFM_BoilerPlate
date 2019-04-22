package com.sixdee.wfm.security.model.token;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.security.authentication.BadCredentialsException;

import com.sixdee.wfm.security.exceptions.JwtExpiredTokenException;
import com.sixdee.wfm.security.model.Scopes;
import com.sixdee.wfm.security.model.UserContext;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
/**
* @author Arjun Kumbakkara[2019@sixdee|WFM|v1.0.0] 
*/

@SuppressWarnings("unchecked")
public class RefreshToken implements JwtToken {
    private Jws<Claims> claims;

    private RefreshToken(Jws<Claims> claims) {
        this.claims = claims;
    }

    
    
    //OLD LOGIC
/*    public static Optional<RefreshToken> create(RawAccessJwtToken token, String signingKey) {
        Jws<Claims> claims = token.parseClaims(signingKey);

        List<String> scopes = claims.getBody().get("scopes", List.class);
        if (scopes == null || scopes.isEmpty() 
                || !scopes.stream().filter(scope -> Scopes.REFRESH_TOKEN.authority().equals(scope)).findFirst().isPresent()) {
            return Optional.empty();
        }

        return Optional.of(new RefreshToken(claims));
    }*/
    
    //New Logic
    public static Optional<RefreshToken> create(RawAccessJwtToken token, String signingKey) {
    	
    	
        Jws<Claims> claims = token.parseClaims(signingKey);
        
        String subject = claims.getBody().getSubject();
        String audience = claims.getBody().getAudience();

        //List<String> scopes = claims.getBody().get("scopes", List.class);
        
        if (subject == null || subject.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(new RefreshToken(claims));
        
        
        
    }
    
    /*public JwtToken createRefreshToken(UserContext userContext) {
        if (StringUtils.isBlank(userContext.getUsername())) {
            throw new IllegalArgumentException("Cannot create JWT Token without username");
        }

        DateTime currentTime = new DateTime();

        Claims claims = Jwts.claims().setSubject(userContext.getUsername());
        
        //Below code suspended as Authority feature not implemented.
        claims.put("scopes", Arrays.asList(Scopes.REFRESH_TOKEN.authority()));
        
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
    }*/
    

    @Override
    public String getToken() {
        return null;
    }

    public Jws<Claims> getClaims() {
        return claims;
    }
    
    public String getJti() {
        return claims.getBody().getId();
    }
    
    public String getSubject() {
        return claims.getBody().getSubject();
    }
}
