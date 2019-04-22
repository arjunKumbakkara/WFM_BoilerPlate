package com.sixdee.wfm.security.model;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author Arjun Kumbakkara
 * @version 1.0.0
 * @Sixdee
 * Dec 22nd,2017
 * Implements and holds up the "This" Context inline to the Application
 * Current Accessr:Per se. 	
 */


public class UserContext {
    private final String username;
   // private final List<GrantedAuthority> authorities;
    private String msg;
    
    //Below code suspended as Authorities not being provided
   /* private UserContext(String username, List<GrantedAuthority> authorities) {
        this.username = username;
        this.authorities = authorities;
    }
    */

    //Temporarily added constructor , no athorities expected.
    private UserContext(String username, String msg) {
        this.username = username;
        this.msg = msg;
    }
    
    
    
    //Temporary code suspended as we are not using the Role Management for now.[flowing from AjaxAuthenticationProvider]
    /*public static UserContext create(String username, List<GrantedAuthority> authorities) {
        if (StringUtils.isBlank(username)) throw new IllegalArgumentException("Username is blank: " + username);
        return new UserContext(username, authorities);
    }*/

   // String permitted = "You have been permitted to access the API @api/auth/me Endpoint";
    
    public static UserContext create(String username, String msg) {
        if (StringUtils.isBlank(username)) throw new IllegalArgumentException("NO SUBJECT FOUND Exception: Claims isnt Carrying any Username(Username can not be Blank).Thus threw: " + username);
       // if (StringUtils.isBlank(msg)) throw new IllegalArgumentException("Permissions Can not be granted as this only stands for token resurrection!" + username);
        return new UserContext(username, msg);
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getMsg() {
        return msg;
    }

    //Getter of the Authorities[Temporarily suspended]
    /*public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }*/
}
