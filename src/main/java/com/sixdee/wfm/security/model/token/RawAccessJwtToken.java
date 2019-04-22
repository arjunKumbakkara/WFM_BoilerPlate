package com.sixdee.wfm.security.model.token;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;

import com.sixdee.wfm.security.exceptions.JwtExpiredTokenException;
import com.sixdee.wfm.user.exception.InvalidTokenException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
/**
* @author Arjun Kumbakkara[2019@sixdee|WFM|v1.0.0] 
*/


public class RawAccessJwtToken implements JwtToken {
    private static Logger logger = LoggerFactory.getLogger(RawAccessJwtToken.class);
            
    private String token;
    
    public RawAccessJwtToken(String token) {
        this.token = token;
    }

    public Jws<Claims> parseClaims(String signingKey) {
        try {
            return Jwts.parser().setSigningKey(signingKey).parseClaimsJws(this.token);
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException ex) {
            logger.error("Invalid JWT Token", ex);
           //throw new BadCredentialsException("Invalid JWT token  ", ex);
            throw new InvalidTokenException("Invalid JWT token: Malformed or wrongly constructed");

        }catch (IllegalArgumentException ext) {
        	  logger.error("Illegal Arguement Exception");
              throw new BadCredentialsException(" Illegal Arguement exception  ", ext);
        }
        catch (ExpiredJwtException expiredEx) {
            logger.info("JWT Token is expired", expiredEx);
            throw new JwtExpiredTokenException(this, "JWT Token expired", expiredEx);
        }
    }

    @Override
    public String getToken() {
        return token;
    }
}
