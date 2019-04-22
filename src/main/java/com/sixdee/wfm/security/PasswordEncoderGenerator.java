package com.sixdee.wfm.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author Arjun Kumbakkara
 * @version 1.0.0
 * @Sixdee 2019
 * @desc All Password Encoding , Matching can be housed here and thus used. 
 */
@Component
public class PasswordEncoderGenerator {
	//BcryptPasswordEncoder > Encoding the password.
	  public String passwordBcryptor(String password){
		  BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		  String hashedPassword = passwordEncoder.encode(password);
		  System.out.println("The Hashed Password > > >   : "+hashedPassword);
		  return hashedPassword;
	  }
	}
