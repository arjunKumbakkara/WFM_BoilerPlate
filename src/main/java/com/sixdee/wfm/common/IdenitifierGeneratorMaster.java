package com.sixdee.wfm.common;
/**
* @author Arjun Kumbakkara[2019@sixdee|WFM|v1.0.0] 
*/
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.sixdee.wfm.configuration.ConstantsLoader;
import com.sixdee.wfm.configuration.Globals;

import java.util.UUID;
import java.util.Random;

/**Notes:
 * Below Class has two implementations : 
 * 1.Random String generated using Java 8 Streaming.
 * 2.UUID generating random but UNIQUE  strings.
 * 3.Generates an alpha numeric String supposed as the Request ID.
 * 4.SKU Id Generator Implemented too.
 */

@Component
public class IdenitifierGeneratorMaster {

@Autowired
ConstantsLoader constantsLoader;

@Autowired
Globals globals;
	
private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//1.Implementation    sample:"VvI4cglwTEAddUser"
	    public static String generateRequestId(String userName,String operationName){
	        
	    	String generatedRequestId=null;
	    	Random random = new Random();
	        //IntStream.range(0,10).forEach(i->System.out.println(generateRandomString(random, 9)));
	        generatedRequestId=generateNumericReqString(random, 10)+operationName;
	         return generatedRequestId; 
	    }

	    private static String generateNumericReqString(Random random, int length){
	        return random.ints(48,122)
	                .filter(i-> (i<57 || i>65) && (i <90 || i>97))
	                .mapToObj(i -> (char) i)
	                .limit(length)
	                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
	                .toString();
	    }
	
	 
	    
	    
	//2.Implementation :generates random UUID string. sample:"e28cb297-3fe6-4b9b-a127-f739004c109dAddUser"
	    
	    public static  String generateRequestIdUUID(String Username,String operationName){
	    	
	    	String reqIdent=null;
	    	
	    	UUID reqId=UUID.randomUUID();
	    	
	    	reqIdent=reqId.toString();

	    	return reqIdent+operationName;
	    	
	    }
	    
	    
	 
	 //3.Implementation  : Random AlphaNumeric String extracting the Charset from the username passed. Sample:"[C@56f9f60cAddUser"
	    
	    public static String randomReqId(String userName,String operationName) {
	    	
	    	int length=10;
	    	//char[] characterSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
	    	char[] characterSet = userName.toCharArray();
	        Random random = new SecureRandom();
	        char[] result = new char[length];
	        for (int i = 0; i < result.length; i++) {
	            // picks a random index out of character set[which is the Admin username we have provided from the context] > random character
	            int randomCharIndex = random.nextInt(characterSet.length);
	            result[i] = characterSet[randomCharIndex];
	        }
	        return new String(result+operationName);
	    }
	    
	 //__________________________________________________________________________________________________________________________________________
	    
	    
	    //Silly Method to generate an AccountId for each Account[please Change the Logic : DONE for pace.]
	    
	    //Sample Generated : "MCON7Arju5887"
	    //MCONEC004572700
	    
	    public static String accountId(String accountName,String mobile,String postalCode,String CustomerType){
	    	String generatedAccountId=null;
	    	String acc = accountName.substring(0,4);
	    	String mob="9898787676";
	    	mob=mobile.substring(mobile.length() - 3, mobile.length());
	    	
	    	String post=null;
	    	if(postalCode.length()<=3){
	    		post = postalCode;
	    	}else{
	    		post = postalCode.substring(postalCode.length()-3, postalCode.length());	
	    	}
	    	
	    	
	    	String randomNo=Integer.toString(randInt(100,999));
	    	if(CustomerType.equalsIgnoreCase("1")){
	    		generatedAccountId="MVNE"+randomNo+acc.replaceAll("\\s+","")+mob+post;	
	    	}else if(CustomerType.equalsIgnoreCase("2")){
	    		generatedAccountId="MVNO"+randomNo+acc.replaceAll("\\s+","")+mob+post;
	    	}else if(CustomerType.equalsIgnoreCase("3")){
	    		generatedAccountId="RESL"+randomNo+acc.replaceAll("\\s+","")+mob+post;
	    	}else if(CustomerType.equalsIgnoreCase("4")){
	    		generatedAccountId="ENTR"+randomNo+acc.replaceAll("\\s+","")+mob+post;
	    	}else if(CustomerType.equalsIgnoreCase("5")){
	    		generatedAccountId="ENDU"+randomNo+acc.replaceAll("\\s+","")+mob+post;
	    	}
	    	System.out.println("<<<<generatedAccountId>>>>>>>>>>>>>"+generatedAccountId);
	    	return  generatedAccountId;
	    }
	    
	    public static int randInt(int min, int max) {
	    	Random rand = new Random();
	        int randomNum = rand.nextInt((max - min) + 1) + min;

	        return randomNum;
	    }
	    
		//Date Position Supplier when fed with Name String
		  public int getMonthPosition(String monthString) throws ParseException{
			  int pos=0;
			  String monthName = monthString; 
				Date date = new SimpleDateFormat("MMMM", Locale.ENGLISH).parse(monthName);
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				pos=cal.get(Calendar.MONTH);
				pos=pos+1;   // [Check the scope and use]
				return pos;
		    	
		    }
		  
		//Method to Generate a coma seperated string of the fetched IDs.
			public String getIdListCommaSeperated(List<Map<String, Object>> list){
				
				String commaSeperatedString=null;
				
				System.out.println(":::::::::::::::::::List TOString"+list.toString());
				
				if(list!=null  && list.size()>0){
					
					for (int i = 0; list != null && i < list.size(); i++) {
						if (commaSeperatedString != null) {
							commaSeperatedString = commaSeperatedString + ",'" + list.get(i).get("FEATURE_ID")
									+ "'";
						} else {
							commaSeperatedString = "'" + list.get(i).get("FEATURE_ID") + "'";
							System.out.println("Formed value"+list.get(i).get("FEATURE_ID"));
						}
					}
				}
				return commaSeperatedString;
			}
		  
			public String generatePassword() {
				int length = 6;
				boolean useLetters = true;
				boolean useNumbers = true;
				String generatedString = RandomStringUtils.random(length, useLetters,useNumbers);
				return generatedString;
			}
			
			/*FETCHES THE IP and NOT the LOOP BACK ADDRESS*/
			
			  public static String getThisMachineNonLoopBackIP() throws SocketException
			  {
			    Enumeration<NetworkInterface> ifaces = NetworkInterface.getNetworkInterfaces();
			    while( ifaces.hasMoreElements() )
			    {
			      NetworkInterface iface = ifaces.nextElement();
			      Enumeration<InetAddress> addresses = iface.getInetAddresses();
			      while( addresses.hasMoreElements() )
			      {
			        InetAddress addr = addresses.nextElement();
			        if( addr instanceof Inet4Address && !addr.isLoopbackAddress() )
			        {
			        	System.out.println("Checking if fetched is an instance of Inet4Address ");
			          return addr.getHostAddress();
			        }
			      }
			    }
			    return null;
			  }
				public static boolean fromThisMachine(String remoteAddress) {
					boolean boo=false;
				     try {  
				            Enumeration interfaces = NetworkInterface.getNetworkInterfaces();  
				                   while (interfaces.hasMoreElements()) {   
				                     NetworkInterface networkInterface = (NetworkInterface) interfaces.nextElement();
				                     Enumeration addresses = networkInterface.getInetAddresses();
				                     while (addresses.hasMoreElements()) {
					                     InetAddress inetAddress = (InetAddress) addresses.nextElement();
					                     String hostName = inetAddress.getHostName();
					                     String hostAddr = inetAddress.getHostAddress();
					                     try{
						                     if (hostName.equals(remoteAddress) || hostAddr.equals(remoteAddress)) {
						                    	 boo= true;
						                    	 return boo;
						                     }
					                     }catch(Exception e){
					                    	 e.printStackTrace();
					                     }finally{
					                    	 hostName=null;
					                    	 hostAddr=null;
					                     }
				                     }
				               }
				         } catch (Exception e) {
				                  e.printStackTrace();
				                  boo= false;
				         }
				     return boo;
				}
				     
			
			  /*kept for generating ID for the Entity Types*/
			  	public static String getUniqueEntityTypeId() {
					  long data = System.currentTimeMillis();
						long data2=(int)(Math.random()*1000);
						String dataer=Long.toString(data+data2);
						String cut=dataer.substring(dataer.length() - 4);
					return cut;
				}
			  	
			  	
			  	 public  boolean copyFile(String sourceFileName, String destionFileName) {
					  InputStream in=null;
					  OutputStream out=null;
					  boolean isDone=false;
				        try {
				            System.out.println("Source Reading..." + sourceFileName);
				            System.out.println("destination FileName Reading..." + destionFileName);
				            File sourceFile = new File(sourceFileName);
				            File destinationFile = new File(destionFileName);
				            in = new FileInputStream(sourceFile);
				            out = new FileOutputStream(destinationFile);
				 
				            byte[] buffer = new byte[1024];
				            int length;
				            while ((length = in.read(buffer)) > 0) {
				                out.write(buffer, 0, length);
				            }
				            isDone=true;
				            System.out.println("Copied: " + sourceFileName);
				        } catch (Exception ex) {
				            ex.printStackTrace();
				        }finally{
				        	if(in!=null)
								try {
									in.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
				        	if(out!=null)
								try {
									out.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
				        }
				        return isDone;
				    }
		
				  	
			 	public boolean fromThisMachine2(String remoteAddress) {
					boolean boo=false;
				     try {  
				            Enumeration interfaces = NetworkInterface.getNetworkInterfaces();  
				                   while (interfaces.hasMoreElements()) {   
				                     NetworkInterface networkInterface = (NetworkInterface) interfaces.nextElement();
				                     Enumeration addresses = networkInterface.getInetAddresses();
				                     while (addresses.hasMoreElements()) {
					                     InetAddress inetAddress = (InetAddress) addresses.nextElement();
					                     String hostName = inetAddress.getHostName();
					                     String hostAddr = inetAddress.getHostAddress();
					                     
					                     try{
						                     if (hostName.equals(remoteAddress) || hostAddr.equals(remoteAddress)) {
						                    	 boo= true;
						                    	 return boo;
						                    	 
						                     }
					                     }catch(Exception e){
					                    	 e.printStackTrace();
					                     }finally{
					                    	 hostName=null;
					                    	 hostAddr=null;
					                     }
				                     }
				                     
				                     
				               }
				         } catch (Exception e) {
				                  e.printStackTrace();
				                  boo= false;
				         }
				     return boo;
				}
			  	
			 	public String generateResponsetID() {
			 		long lResponseId = 0;
			 		String strResponseId=null;
			 		try {
			 			lResponseId = System.nanoTime();
			 		} catch (Exception e) {
			 			logger.info("Exception Raised In generateRequestID "
			 					+ e.getMessage());
			 		} finally {
			 		}

			 		strResponseId=String.valueOf(lResponseId);
			 		logger.info("Response ID::"+strResponseId);
			 		return strResponseId;

			 	}  	
}


	
	
