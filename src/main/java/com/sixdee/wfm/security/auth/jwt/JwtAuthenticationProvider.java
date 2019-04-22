package com.sixdee.wfm.security.auth.jwt;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.sixdee.wfm.security.auth.JwtAuthenticationToken;
import com.sixdee.wfm.security.config.JwtSettings;
import com.sixdee.wfm.security.model.UserContext;
import com.sixdee.wfm.security.model.token.JwtToken;
import com.sixdee.wfm.security.model.token.RawAccessJwtToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

/**
 * 
 * @author Arjun Kumbakkara
 * @version 1.0.0
 * 
 *          <p>
 *          <b><u>Development History</u></b><br>
 *          <table border="1" width="100%">
 *          <tr>
 *          <td width="15%"><b>Date</b></td>
 *          <td width="20%"><b>Author</b></td>
 *          <td><b>Description</b></td>
 *          </tr>
 *          <tr>
 *          <td>February 15, 2019</td>
 *          <td>Arjun Kumbakkara</td>
 *          <td></td>
 *          </tr>
 *          </table>
 *          </p>
 */


@Component
@SuppressWarnings("unchecked")
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final JwtSettings jwtSettings;
    
    @Autowired
    public JwtAuthenticationProvider(JwtSettings jwtSettings) {
        this.jwtSettings = jwtSettings;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        RawAccessJwtToken rawAccessToken = (RawAccessJwtToken) authentication.getCredentials();

        System.out.println("------------------------------PRE:authenticate@JwtAuth------------------------------------");
        Jws<Claims> jwsClaims = null;
        String subject = null;
        String audience=null; 
        try{
        	jwsClaims=rawAccessToken.parseClaims(jwtSettings.getTokenSigningKey());	
            subject = jwsClaims.getBody().getSubject();
            audience = jwsClaims.getBody().getAudience();
        }catch (NullPointerException e){
        	System.out.println("Refresh Token Scenario: Claims CAN NOT be parsed:Thus about to throw explicit Exception");
        	try {
				throw new Exception(" CLAIMS CAN NOT BE PARSED EXCEPTION : RC  : Token Applied must have been a Refresh Token.a REFRESH TOKEN is only employed to fetch another ACCESS TOKEN .");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
        
        System.out.println("------------------------------POST:authenticate@JwtAuth------------------------------------");
        //Suspended for now.
        /*List<String> scopes = jwsClaims.getBody().get("scopes", List.class);
        List<GrantedAuthority> authorities = scopes.stream()
                .map(authority -> new SimpleGrantedAuthority(authority))
                .collect(Collectors.toList());
        */
        
        /*UserContext context = UserContext.create(subject, authorities);*/ //Suspended
        
        //Bogous Code : Change[when the above block activated]
        boolean permitted=false;
        if(audience!=null && audience.equalsIgnoreCase("REFRESH")){
        	permitted=false;
        	System.out.println("Supplied is REFRESH");
        }else if(audience!=null && audience.equalsIgnoreCase("ACCESS")){
        	System.out.println("Supplied is ACCESS");
        	permitted=true;
        }
        
        String permissionMessage="You have been authenticated to access the Protected Content";
        
        UserContext context = UserContext.create(subject,permissionMessage);
        
        
        /*return new JwtAuthenticationToken(context, context.getAuthorities());*/
        
        return new JwtAuthenticationToken(context,permitted);
        
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
