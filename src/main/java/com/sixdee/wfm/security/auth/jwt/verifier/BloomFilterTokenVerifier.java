package com.sixdee.wfm.security.auth.jwt.verifier;


/**
 * @author Arjun Kumbakkara
 * @version 1.0.0
 * @sixdee
 * Feel free to modify or sophisticate this class as it does not stand much significance now and has been put up for abiding to the pattern
 */


import org.springframework.stereotype.Component;
@Component
public class BloomFilterTokenVerifier implements TokenVerifier {
    @Override
    public boolean verify(String jti) {
        return true;
    }
}
