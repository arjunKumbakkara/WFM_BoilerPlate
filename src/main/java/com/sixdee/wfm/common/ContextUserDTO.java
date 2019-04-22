package com.sixdee.wfm.common;
/**
* @author Arjun Kumbakkara[2019@sixdee|WFM|v1.0.0] 
*/
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Component
@JsonInclude(Include.NON_NULL)   //Use it to Ignore all null-valued fields,period.
public class ContextUserDTO {

	
	//TODO:All the below int fields are initialized with '0' for :Include.NON_NULL wont exclude it. 
	
	private int userId;
	private String username;
	private String password;
	private String mobileNum;
	private String status;
	private String forcePasswordChange;
	private int changePasswordAttempts;
	private String startDate;
	private String endDate;
	private int blockStatus;
	private String changePwsdDate;
	private int roleId;
	private String email;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String country;
	private String postalCode;
	private String accountId;
	private String level;
    
    
    
    
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMobileNum() {
		return mobileNum;
	}
	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	

	public int getChangePasswordAttempts() {
		return changePasswordAttempts;
	}
	public void setChangePasswordAttempts(int changePasswordAttempts) {
		this.changePasswordAttempts = changePasswordAttempts;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getForcePasswordChange() {
		return forcePasswordChange;
	}
	public void setForcePasswordChange(String forcePasswordChange) {
		this.forcePasswordChange = forcePasswordChange;
	}
	public int getBlockStatus() {
		return blockStatus;
	}
	public void setBlockStatus(int blockStatus) {
		this.blockStatus = blockStatus;
	}
	public String getChangePwsdDate() {
		return changePwsdDate;
	}
	public void setChangePwsdDate(String changePwsdDate) {
		this.changePwsdDate = changePwsdDate;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	
	

	

}
