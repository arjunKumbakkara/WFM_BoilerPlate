package com.sixdee.wfm.repository;
/** Park the User Management logic here
* @author Arjun Kumbakkara[2019@sixdee|WFM|v1.0.0] 
*/
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sixdee.wfm.model.Department;
import com.sixdee.wfm.model.User;
import com.sixdee.wfm.user.entity.UserDTO;
import com.sixdee.wfm.base.GlobalRepository;
import com.sixdee.wfm.common.TableNames;



@Repository
public interface UserRepository extends GlobalRepository<User, Long> {

	
	Optional<User> findById(Long id);
	
	
	/*@Query("SELECT d.id as departement_id, COUNT(m.id) as nbMateriel FROM Departement d LEFT JOIN d.sites s LEFT JOIN s.materiels m WHERE "
	        + "(s.metier.id IN (:metier_id) OR :metier_id IS NULL) AND (s.entite.id IN (:entite_id) OR :entite_id IS NULL) "
	        + "AND (m.materielType.id IN (:materielType_id) OR :materielType_id IS NULL) AND "
	        + "(d.id= :departement_id OR :departement_id IS NULL) "
	        + "AND m.dateLivraison is not null  and (EXTRACT(YEAR FROM m.dateLivraison) < :date_id OR :date_id IS NULL) "
	        + "AND ( m.estHISM =:estHISM OR :estHISM IS NULL OR m.estHISM IS NULL) "
	        + "GROUP BY d.id")
	List<Map<Long, Long>> countByDepartementWithFilter(@Param("metier_id") List<Long> metier_id,@Param("entite_id") List<Long> entite_id,@Param("materielType_id") List<Long> materielType_id,
	        @Param("departement_id") Long departement_id, @Param("date_id") Integer date_id,
	        @Param("estHISM") Boolean estHISM);*/
	
	
	/*@Query(value = "select LM.user_id,LM.login_name,LM.login_password,LM.mobile_num,LM.status,is_first_time,first_login,LM.password_attempt,LM.start_time,DATE_FORMAT(LM.end_time, '%d%m%Y%H%i%s') end_time,block_status,rose_id,auth_type from "+TableNames.Users+" UM ,"+TableNames.Logins+" LM where UM.user_id = LM.user_id and LM.login_name =:userName ")
	Collection<Department> findTheContextUser();

	public Long queryFinancialRecordsOfDept(String deptName, String companyName) { 
        String query = "SELECT d.records " + 
                        "FROM Department d " + 
                         "WHERE d.name = ?1 +  
                          "AND d.company.name = ?2; 
        return em.createQuery(query,Long.class)
                 .setParameter(1 , deptName)
                 .setParameter(2 , companyName)
                 .getSingleResult(); 
    } 
 */

/*	public default void findingTheQualifiedUser() {
		Query query = em.createNativeQuery("select LM.USER_ID,LM.LOGINNAME,LM.PASSWORD,LM.MOBILE_NUM,LM.STATUS,IS_FIRST_TIME,FIRST_LOGIN,LM.PSWD_ATTEMPT,LM.START_TIME,DATE_FORMAT(LM.END_TIME, '%d%m%Y%H%i%s') END_TIME,BLOCK_STATUS,CHANGE_PASSWORD_DATE,ROLE_ID,AUTH_TYPE from "+TableNames.USER_MASTER+" UM ,"+TableNames.LOGIN_MASTER+" LM where UM.USER_ID = LM.USER_ID and LM.LOGINNAME = ? ", User.class);
		query.setParameter(1, 10001L);
		List resultList = query.getResultList();
		logger.info("SELECT * FROM COURSE  where id = ? -> {}", resultList);
	}	*/
	
	
	/*public  UserDTO findByUsername(String username) {
		logger.info("FIND USER BY HITTING!!!!"+username);
		UserDTO user = null;
		List<Map<String, Object>> map=null;
		int firstTimeFlag=0;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		//LocalDateTime localDate = LocalDateTime.now();
		try {
			 //map = jt.queryForList("select LM.USER_ID,LM.LOGINNAME,LM.PASSWORD,LM.MOBILE_NUM,LM.STATUS,IS_FIRST_TIME,FIRST_LOGIN,LM.PSWD_ATTEMPT,LM.START_TIME,LM.END_TIME,BLOCK_STATUS,CHANGE_PASSWORD_DATE,ROLE_ID,AUTH_TYPE from "+TableNames.USER_MASTER+" UM ,"+TableNames.LOGIN_MASTER+" LM where UM.USER_ID = LM.USER_ID and UM.USERNAME = '" + username.trim() + "' ");
			String query="select LM.USER_ID,LM.LOGINNAME,LM.PASSWORD,LM.MOBILE_NUM,LM.STATUS,IS_FIRST_TIME,FIRST_LOGIN,LM.PSWD_ATTEMPT,LM.START_TIME,DATE_FORMAT(LM.END_TIME, '%d%m%Y%H%i%s') END_TIME,BLOCK_STATUS,CHANGE_PASSWORD_DATE,ROLE_ID,AUTH_TYPE from "+TableNames.USER_MASTER+" UM ,"+TableNames.LOGIN_MASTER+" LM where UM.USER_ID = LM.USER_ID and LM.LOGINNAME = '" + username.trim() + "' ";
			map = jt.queryForList(query);
			//jt.getDataSource().getConnection().setTransactionIsolation(2);
			//DataSourceUtils.;
			
			logger.info("map size::::"+map.size());
			logger.info("Query ::::"+query);
			if (map.size() > 0) {
			
				logger.info("System Time : "+dtf.format(LocalDateTime.now()));
				
				//user=null;
				logger.info("map ::::"+map);
				user = new UserDTO();

				user.setUserId(Integer.parseInt(""+map.get(0).get("USER_ID")));
				user.setUsername((String) map.get(0).get("LOGINNAME"));
				//user.setPassword((String) map.get(0).get("PASSWORD"));
				user.setPassword(getPassword(username));
				logger.info("||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||"+getPassword(username));
				//user.setPassword(getPassword(username));
				user.setMobileNum((String) map.get(0).get("MOBILE_NUM"));
				user.setStatus((String) map.get(0).get("STATUS"));
				user.setForcePasswordChange((String)map.get(0).get("IS_FIRST_TIME"));
				user.setFirstLogin(Integer.toString((Integer)map.get(0).get("FIRST_LOGIN")));
			
				
				if(user.getFirstLogin().equalsIgnoreCase("0")){
					logger.info("This is the First Time Login");
					firstTimeFlag=1;
				}else{
					logger.info("This is NOT the First Time Login");
				}
				
				user.setChangePasswordAttempts(Integer.parseInt(""+map.get(0).get("PSWD_ATTEMPT")));
				user.setStartDate(sdf.format((Timestamp) map.get(0).get("START_TIME")));
				
				//user.setEndDate(sdf.format((Timestamp) map.get(0).get("END_TIME")));
				System.out.println(""+map.get(0).get("END_TIME"));
				String convertedDate=""+map.get(0).get("END_TIME");
				//user.setEndDate(sdf.format((Timestamp) map.get(0).get("END_TIME")));
				user.setEndDate(convertedDate);
				
				System.out.println(""+user.getEndDate());
				user.setBlockStatus((Integer) map.get(0).get("BLOCK_STATUS"));
				if(map.get(0).get("CHANGE_PASSWORD_DATE")!=null ){
					user.setChangePwsdDate(sdf.format((Date) map.get(0).get("CHANGE_PASSWORD_DATE")));	
				}
				user.setRoleId((Integer) map.get(0).get("ROLE_ID"));
			  //user.setAuthType((AuthTypeEnum)map.get(0).get("AUTH_TYPE"));
				user.setAuth((String)map.get(0).get("AUTH_TYPE"));

				logger.error("Username Fetched after setting in the User Object   " + user.getUsername()
						+ "   Password::" + user.getPassword());
			}else{
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			map=null;
		}
		
		
		return user;
	}	*/
	
	
}



