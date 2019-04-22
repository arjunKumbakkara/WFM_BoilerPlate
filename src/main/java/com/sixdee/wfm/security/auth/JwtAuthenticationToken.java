package com.sixdee.wfm.security.auth;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import com.sixdee.wfm.security.model.UserContext;
import com.sixdee.wfm.security.model.token.RawAccessJwtToken;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @author Arjun Kumbakkara[2019@sixdee|WFM|v1.0.0] 
 */
public class JwtAuthenticationToken extends AbstractAuthenticationToken {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private static final long serialVersionUID = 2877954820905567501L;

	private RawAccessJwtToken rawAccessToken;
	private UserContext userContext;

	public JwtAuthenticationToken(RawAccessJwtToken unsafeToken) {
		super(null);
		this.rawAccessToken = unsafeToken;
		this.setAuthenticated(false);
	}

	//Suspended Block of code as Authorities is not being implmntd as of now.
	/*public JwtAuthenticationToken(UserContext userContext, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.eraseCredentials();
        this.userContext = userContext;
        super.setAuthenticated(true);
    }*/
	//OLD PERSIST LOGIC
	public JwtAuthenticationToken(UserContext userContext, Boolean permitted) {
		super(null);      // Temporarily being set as null as the super constructor expects authority.[temp code]
		this.eraseCredentials();
		this.userContext = userContext;
		super.setAuthenticated(permitted);
	}
	@Override
	public void setAuthenticated(boolean authenticated) {
		if (authenticated) {
			throw new IllegalArgumentException(
					"Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
		}
		super.setAuthenticated(false);
	}
	@Override
	public Object getCredentials() {
		return rawAccessToken;
	}
	@Override
	public Object getPrincipal() {
		//logger.error("This prints the UserContext"+this.userContext.toString());
		return this.userContext;
	}
	@Override
	public void eraseCredentials() {        
		super.eraseCredentials();
		this.rawAccessToken = null;
	}
}
