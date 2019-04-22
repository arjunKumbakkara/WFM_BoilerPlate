package com.sixdee.wfm.security;

/**
* @author Arjun Kumbakkara[2019@sixdee|WFM|v1.0.0] 
*/

public enum AuthTypeEnum {

   JDBC(1),
   LDAP(2),
   SSO(3);                     //TODO:Single Sign On..Check www.arjunkumbakkara.github.io for the Implementation GIST.
	private int value;
	private AuthTypeEnum(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
    //setter makes no sense.Just for testing
    public void setValue(int value) {
        this.value = value;
    }
}