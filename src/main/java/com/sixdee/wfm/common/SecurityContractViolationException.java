package com.sixdee.wfm.common;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

/**
* @author Arjun Kumbakkara
* @version 1.0.0
* 
*          <p>
*          <b><u>Development History</u></b><br>
*          <table border="1" width="100%">
*          <tr>
*          <td width="15%"><b>Date</b></td>
*          <td width="20%"><b>Author(arjunkumbakkara.github.io)</b></td>
*          <td><b>This is Runtime  as it is difficult to propagate in the exception propagation.TODO: Any Custom BAD CREDENTIALS scenario can do too.</b></td>
*          </tr>
*          <tr>
*          <td>December 28, 2017</td>
*          <td>Arjun Kumbakkara</td>
*          <td></td>
*          </tr>
*          </table>
*          </p>
*/
public class SecurityContractViolationException extends WebApplicationException {
    public SecurityContractViolationException(String message) {
       
    	super(Response.status(Response.Status.BAD_REQUEST)
            .entity(message).type(MediaType.APPLICATION_JSON).build());
    }
}