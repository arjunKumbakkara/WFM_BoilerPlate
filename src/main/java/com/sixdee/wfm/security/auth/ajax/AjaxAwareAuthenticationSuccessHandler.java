package com.sixdee.wfm.security.auth.ajax;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sixdee.wfm.configuration.Globals;
import com.sixdee.wfm.security.model.UserContext;
import com.sixdee.wfm.security.model.token.JwtToken;
import com.sixdee.wfm.security.model.token.JwtTokenFactory;

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
*          <td>December 29, 2016</td>
*          <td>Arjun Kumbakkara</td>
*          <td></td>
*          </tr>
*          </table>
*          </p>
*/


@Component
public class AjaxAwareAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final ObjectMapper mapper;
    private final JwtTokenFactory tokenFactory;

    @Autowired
    public AjaxAwareAuthenticationSuccessHandler(final ObjectMapper mapper, final JwtTokenFactory tokenFactory) {
        this.mapper = mapper;
        this.tokenFactory = tokenFactory;
    }
    
    @Autowired
    Globals global;
    
    

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        UserContext userContext = (UserContext) authentication.getPrincipal();
        
        /*UserAttempt:User logs in Correctly and then again goes wrong about it:Logic to Restart Cycle*/
        global.loginMaxAttempt=global.loginMaxAttempt_Reset;
        
        JwtToken accessToken = tokenFactory.createAccessJwtToken(userContext);
        JwtToken refreshToken = tokenFactory.createTokenToRefresh(userContext);
        
        Map<String, String> tokenMap = new HashMap<String, String>();
        tokenMap.put("token", accessToken.getToken());
        tokenMap.put("refreshToken", refreshToken.getToken());
        tokenMap.put("msg", userContext.getMsg());

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        mapper.writeValue(response.getWriter(), tokenMap);

        clearAuthenticationAttributes(request);
    }

    /**
     * Temporary Data stored
     */
    
    protected final void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            return;
        }

        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}
