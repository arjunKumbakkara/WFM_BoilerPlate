package com.sixdee.wfm.model;
/**
* @author Arjun Kumbakkara[2019@sixdee|WFM|v1.0.0] 
*/
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;


@Entity
@Table(name = "WFM_users")
@EntityListeners(AuditingEntityListener.class)
/*@JsonIgnoreProperties(value = {"create_date", "update_date"},
        allowGetters = true)*/
public class User {
	
	//For JPA
	User(){
		
	}
	/**
	 * @category : WFM_users
	 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @NotBlank
    @Column(name = "user_name")
    private String userName;

    @NotBlank
    @Column(name = "user_address")
    private String userAddress;
    
    @NotBlank
    @Column(name = "email_id")
    private String emailId;

    @NotBlank
    @Column(name = "role_id")
    private String roleId;

    @Column(name = "contact_num")
    private String contactNum;

    
    @Column(name = "customer_id")
    private String customerId;
    @Column(name = "fax")
    private String fax;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "postal")
    private String postal;

    @Column(name = "country")
    private String country;

    @Column(name = "auth_type")
    private String authType;
    
    @Column(name = "status")
    private String status;

    @Column(name = "contact_name")
    private String contactName;
    
    @Column(name = "block_status")
    private String blockStatus;
    
    @Column(name = "is_super_user")
    private String isSuperUser;
    
    @Column(nullable = false, updatable = false,name = "start_date")
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date startDate;

    @Column(nullable = false,name = "end_date")
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date endDate;
    
   /*---------------------------------------------------------------------------------*/
    /**
	 * @category : WFM_logins
	 */
    
    /*@Column(name = "user_id"): Since this acts as the primary key.*/

    @NotBlank
    @Column(name = "login_name")
    private String login_name;

    @NotBlank
    @Column(name = "login_password")
    private String login_password;
    
    
    @Column(name = "is_force_password")
    private String isForcePassword;

    @Column(name = "app_id")
    private String appId;
    
    @Column(name = "security_question")
    private String securityQuestion;
    
    @Column(name = "answer")
    private String answer;
    
    @Column(nullable = false, updatable = false,name = "block_date")
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date blockDate;
    

    
    /*---------------------------------------------------------------------------------*/
    
    
	@Override
    public String toString() {
        return "User [id=" + userId + ", userName=" + userName + "]";
    }



	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}



	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}



	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}



	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}



	/**
	 * @return the userAddress
	 */
	public String getUserAddress() {
		return userAddress;
	}



	/**
	 * @param userAddress the userAddress to set
	 */
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}



	/**
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}



	/**
	 * @param emailId the emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}



	/**
	 * @return the roleId
	 */
	public String getRoleId() {
		return roleId;
	}



	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}



	/**
	 * @return the contactNum
	 */
	public String getContactNum() {
		return contactNum;
	}



	/**
	 * @param contactNum the contactNum to set
	 */
	public void setContactNum(String contactNum) {
		this.contactNum = contactNum;
	}



	/**
	 * @return the customerId
	 */
	public String getCustomerId() {
		return customerId;
	}



	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}



	/**
	 * @return the fax
	 */
	public String getFax() {
		return fax;
	}



	/**
	 * @param fax the fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}



	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}



	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}



	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}



	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}



	/**
	 * @return the postal
	 */
	public String getPostal() {
		return postal;
	}



	/**
	 * @param postal the postal to set
	 */
	public void setPostal(String postal) {
		this.postal = postal;
	}



	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}



	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}



	/**
	 * @return the authType
	 */
	public String getAuthType() {
		return authType;
	}



	/**
	 * @param authType the authType to set
	 */
	public void setAuthType(String authType) {
		this.authType = authType;
	}



	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}



	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}





	/**
	 * @param login_password the login_password to set
	 */
	public void setLogin_password(String login_password) {
		this.login_password = login_password;
	}



	/**
	 * @return the isForcePassword
	 */
	public String getIsForcePassword() {
		return isForcePassword;
	}



	/**
	 * @param isForcePassword the isForcePassword to set
	 */
	public void setIsForcePassword(String isForcePassword) {
		this.isForcePassword = isForcePassword;
	}



	/**
	 * @return the appId
	 */
	public String getAppId() {
		return appId;
	}



	/**
	 * @param appId the appId to set
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	}



	/**
	 * @return the securityQuestion
	 */
	public String getSecurityQuestion() {
		return securityQuestion;
	}



	/**
	 * @param securityQuestion the securityQuestion to set
	 */
	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}



	/**
	 * @return the answer
	 */
	public String getAnswer() {
		return answer;
	}



	/**
	 * @param answer the answer to set
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}



	/**
	 * @return the blockDate
	 */
	public Date getBlockDate() {
		return blockDate;
	}



	/**
	 * @return the contactName
	 */
	public String getContactName() {
		return contactName;
	}



	/**
	 * @param contactName the contactName to set
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}



	/**
	 * @return the blockStatus
	 */
	public String getBlockStatus() {
		return blockStatus;
	}



	/**
	 * @param blockStatus the blockStatus to set
	 */
	public void setBlockStatus(String blockStatus) {
		this.blockStatus = blockStatus;
	}



	/**
	 * @return the isSuperUser
	 */
	public String getIsSuperUser() {
		return isSuperUser;
	}



	/**
	 * @param isSuperUser the isSuperUser to set
	 */
	public void setIsSuperUser(String isSuperUser) {
		this.isSuperUser = isSuperUser;
	}



	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}



	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}



	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}



	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}



	/**
	 * @return the login_name
	 */
	public String getLogin_name() {
		return login_name;
	}



	/**
	 * @param login_name the login_name to set
	 */
	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}



	/**
	 * @return the login_password
	 */
	public String getLogin_password() {
		return login_password;
	}



	/**
	 * @param blockDate the blockDate to set
	 */
	public void setBlockDate(Date blockDate) {
		this.blockDate = blockDate;
	}
    

}
