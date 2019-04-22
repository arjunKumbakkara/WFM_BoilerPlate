package com.sixdee.wfm.user.repository;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class UserAuthentication {
	
	private static Logger logger   = LogManager.getLogger(UserAuthentication.class.getName());
	private static boolean isAuth  =false;
	private static String URL      = "http://10.0.0.91:8093/api/auth/login";
	
	public static boolean checkUser(String user, int i) {

		Client client = Client.create();
		WebResource webResource = client.resource("http://10.0.0.91:8093/api/auth/login");
		
		
		try {

			String simProvisioningJson ="{"+
					
					"\"username\":\"superadmin\","+
					"\"password\":\"admin\""+
					"}";

			ClientResponse response = webResource.type("application/json").get(ClientResponse.class);
			webResource = client.resource("http://10.0.0.91:8093/api/auth/login");
	//		logger.info(user + "::Validating the user M2M URL:" + webResource);

			response = webResource.header("Content-Type", "application/json")
					.header("X-Requested-With", "XMLHttpRequest")
					.header("X-Authorization", user)
					.header("Cache", "no-cache").post(ClientResponse.class,simProvisioningJson)
					;

			System.out.println("++++++++++++++++++DONE+++++++++++ : "+i+" +++++++ : "+response.toString());
			
	//		logger.info(user + "::Validating the user M2M Response:" + response.getStatus());

			if (response.getStatus() == 200) {
				isAuth = true;
			} else {
				isAuth = false;
			}
		}

		catch (Exception e) {
			e.printStackTrace();
			logger.error(user + "::Exception Raised while CallAPIForConfig " + e.getMessage());
		} finally {
			if (client != null) {
				client.destroy();
				client = null;
			}

			webResource = null;
		}

		return isAuth;

	}
	
	
	
}
