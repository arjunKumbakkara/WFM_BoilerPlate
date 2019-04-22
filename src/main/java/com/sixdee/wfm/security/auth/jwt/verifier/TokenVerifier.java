package com.sixdee.wfm.security.auth.jwt.verifier;


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
*          <td>December 24, 2016</td>
*          <td>Arjun Kumbakkara</td>
*          <td></td>
*          </tr>
*          </table>
*          </p>
*          TODO:Customise if need be
*/

public interface TokenVerifier {
    public boolean verify(String jti);
}
