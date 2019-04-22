package com.sixdee.wfm.common;
/**
* @author Arjun Kumbakkara[2019@sixdee|WFM|v1.0.0] 
*/
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
/*
 * Below Class has two implementations based on SPRING MAILER: 
 * 1.SMTP Integration.
 * 2.Password Generation.
 */
@PropertySource("classpath:application.properties")
@Component
public class SpringMailer {
	  
	    @Autowired
	    public JavaMailSenderImpl emailSender;

	    @Value("${spring.mail.from.address}")
		public String fromMcon;
	    
	    @Value("${spring.mail.reply.address}")
		public String replyTo;
	  	boolean mailsendStatus=false;
	  	
	    public boolean sendEmail(String from,String to, String subject, String text) throws MessagingException{
	    	final String subject_static=subject;
			final String message_static=text;
			final String to_static=to; 
			final String replyTo_static=replyTo;
	     if(from==null || from.equalsIgnoreCase("") || from.equalsIgnoreCase(null)){
			 from=fromMcon;
		 }
	     final String from_static=from;
			ExecutorService executorService = Executors.newSingleThreadExecutor();      //Observe,Its made as a single threaded operation ->implies  
			executorService.execute(new Runnable() {                                    //that each mail sending action will be carried out and then 
				public void run() {                                                     //the next,To have it paralleled use Executors.newFixedThreadPool(x) 
			    	try {                                                               //Check implementations below
			    		   MimeMessage mimeMessage = emailSender.createMimeMessage();
			    		     MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			    		     helper.setTo(to_static);
			    		     helper.setSubject(subject_static);
			    		     helper.setFrom(from_static);
			    		     helper.setReplyTo(replyTo_static);
			    		     helper.setText(message_static, true);
			    		     emailSender.send(mimeMessage);
			    		     mailsendStatus=true;
			 
					} catch (MessagingException e) {
						throw new RuntimeException(e);
					}
			    	
			    }
			});
	        return mailsendStatus;
	    }
	    
	 /*   public void setFlag(){
	    	mailsendStatus=true;
	    }
	*/
}