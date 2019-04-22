package com.sixdee.wfm.security.model.token;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.jsonwebtoken.Claims;

/**
* @author Arjun Kumbakkara[2019@sixdee|WFM|v1.0.0] 
*/

public final class AccessJwtToken implements JwtToken {
    private final String rawToken;
    @JsonIgnore private Claims claims;

    protected AccessJwtToken(final String token, Claims claims) {
        this.rawToken = token;
        this.claims = claims;
    }

    public String getToken() {
        return this.rawToken;
    }

    public Claims getClaims() {
        return claims;
    }
}
