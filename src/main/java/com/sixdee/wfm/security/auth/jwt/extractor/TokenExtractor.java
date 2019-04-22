package com.sixdee.wfm.security.auth.jwt.extractor;

/**
* @author Arjun Kumbakkara[2019@sixdee|WFM|v1.0.0] 
*/
public interface TokenExtractor {
    public String extract(String payload);
}
