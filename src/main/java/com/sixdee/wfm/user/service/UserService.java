package com.sixdee.wfm.user.service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.sixdee.wfm.common.ContextUserDTO;
import com.sixdee.wfm.common.TableNames;
import com.sixdee.wfm.configuration.ConstantsLoader;
import com.sixdee.wfm.configuration.Globals;
import com.sixdee.wfm.model.User;
import com.sixdee.wfm.security.PasswordEncoderGenerator;
import com.sixdee.wfm.user.entity.UserDTO;
/**
* @author Arjun Kumbakkara[2019@sixdee|WFM|v1.0.0] 
*/

@Service
public class UserService {
	private static final Logger logger = LogManager.getLogger(UserService.class);
	
	@Autowired private  ConstantsLoader errorCode;
	
	@Autowired
	com.sixdee.wfm.repository.UserRepository userRepository;
	@Autowired
	Globals globals;
	
	
	
	@Autowired
	PasswordEncoderGenerator BcryptEncoder;
	
	@PersistenceUnit	
	@Qualifier(value = "entityManagerFactoryOne") 
	EntityManagerFactory em;
	
	
	/**@a The attemptee user is verified here.At all iterations of the JWT processes.*/
	public User getByUsername(String username) {
		User user =null;
		try{
			user=findTheUserBean_native(username);
		}catch(Exception e){
			 e.printStackTrace();
		 }
		return user;
	}
	
	public User findTheUserBean_native(String username) throws Exception {
		
		//String query="select LM.USER_ID,LM.LOGINNAME,LM.PASSWORD,LM.MOBILE_NUM,LM.STATUS,IS_FIRST_TIME,FIRST_LOGIN,LM.PSWD_ATTEMPT,LM.START_TIME,DATE_FORMAT(LM.END_TIME, '%d%m%Y%H%i%s') END_TIME,BLOCK_STATUS,CHANGE_PASSWORD_DATE,ROLE_ID,AUTH_TYPE from "+TableNames.USER_MASTER+" UM ,"+TableNames.LOGIN_MASTER+" LM where UM.USER_ID = LM.USER_ID and LM.LOGINNAME = '" + username.trim() + "' ";
		logger.info("Native Query for finding the qualified bean.");
		User userbean=null;
		EntityManager emt = em.createEntityManager();
		Query query = emt.createNativeQuery("select UM.user_name,LM.user_id,UM.is_super_user,UM.user_address,UM.end_date,UM.start_date,LM.is_force_password,UM.email_id,UM.city,UM.customer_id,UM.state,UM.postal,UM.country,UM.state,UM.contact_name,UM.contact_num,UM.fax,LM.login_name,LM.login_password,LM.security_question,LM.block_date,LM.answer,LM.app_id,LM.mobile_num,LM.status,is_first_time,first_login,LM.password_attempt,LM.start_time,DATE_FORMAT(LM.end_time, '%d%m%Y%H%i%s') end_time,block_status,role_id,auth_type from "+TableNames.Users+" UM ,"+TableNames.Logins+" LM where UM.user_id = LM.user_id and LM.login_name =?", User.class);
		query.setParameter(1,username);
		List resultList = query.getResultList();
		logger.debug("User Bean details: ", resultList);
		if(resultList!=null && resultList.size()==1) {
			userbean=(User)resultList.get(0);
		}else {
			throw new Exception("Storage data has been fiddled with.Please examine.");
		}

		return userbean;
	}
	
	

	/**wrong Attempts Blocker:   Wrong Attemtps to Denial of Service*/
	public boolean blockUserSinceWrongAttemptsExceeded(String userName) throws Exception {/*
	boolean flag=false;
	try{
	String sqlBlock = "UPDATE " + TableNames.USER_MASTER +" SET BLOCK_STATUS= 1  WHERE USERNAME = '"+userName+"'";
		int x=jt.update(sqlBlock);
	if(x>0){
		logger.info("User Blocked Temporarily.");
	logger.info("Database transaction successful : returned value of x::"+x);
	}	
	} catch (Exception e){
	e.printStackTrace();
	logger.error("Error logged from UserRepository.blockUserSinceWrongAttemptsExceeded | E ::"+e.getMessage());
	} finally {
	}
	*/
	return true;
	}
}
