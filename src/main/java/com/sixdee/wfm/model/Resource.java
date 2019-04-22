package com.sixdee.wfm.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
/**
* @author Arjun Kumbakkara[2019@sixdee|WFM|v1.0.0] 
*/
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sixdee.wfm.common.TableNames;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = TableNames.Resources)
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "create_date", "update_date" }, allowGetters = true)
public class Resource implements Serializable{
	// For JPA
	Resource() {
	}

	@Id
	@ApiModelProperty(hidden = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "resource_id", updatable = false, nullable = false)
	private Long resourceId;

	@Column(name = "parent_resource_id")
	private Long parentResourceId;
	
	@Column(name = "department_id")
	private Long departmentId;
	
	@Column(name = "resource_type")    /*Values : Manager/Resource/Escalation Manager*/
	private Long resourceType;
	
	@Column(name = "resource_name")
	private String resourceName;
	
	

	@Column(name = "shift_id")
	private Long shiftId;
	
	@Column(name = "skillLevelDesignation_id")
	private Long skillLevelDesignationId;
	
	
	@Column(name = "location_id")
	private Long locationId;
		
	@Column(name = "job_type")
	private Long jobType;
	
	
	
	@Column(name = "resource_email")
	private String resourceEmail;

	@Column(name = "resource_phone")
	private String resourcePhone;

	@Column(name = "resource_time_slot_hr")
	private Long resourceTimeSlot;

	@Column(name = "resource_notification_type")
	private Long resourceNotificationType;

	@Column(name = "resource_mobile_app_id")
	private String resourceMobileAppId;
	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "updated_by")
	private String updatedBy;

	@ApiModelProperty(hidden = true)
	@Column(nullable = false, updatable = false, name = "create_date")
	@Temporal(TemporalType.TIMESTAMP)
	/* @CreatedDate */
	@CreationTimestamp
	private Date createDate;

	@ApiModelProperty(hidden = true)
	@Column(nullable = false, name = "update_date")
	@Temporal(TemporalType.TIMESTAMP)
	/* @LastModifiedDate */
	@UpdateTimestamp
	private Date updateDate;

	
	 
	//@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = TableNames.ResourceToSkillJunction, catalog = "WFM", joinColumns = { 
			@JoinColumn(name = "R_id",referencedColumnName = "resource_id", nullable = false, updatable = false) }, 
			inverseJoinColumns = { @JoinColumn(name = "SLD_id",referencedColumnName = "skillLevelDesignation_id", 
					nullable = false, updatable = false) })
	//private Set<SkillLevelDesignationMapper> resourceSkills = new HashSet<SkillLevelDesignationMapper>();
	/*@JsonIgnore*/
	private Set<SkillLevelDesignationMapper> resourceSkills;
	
	 @Override
	    public String toString() {
	        String result = String.format( "Resource [id=" + resourceId + ", name=" + resourceName + "]");
	       /* if (resourceSkills != null) {
	            for(SkillLevelDesignationMapper resource : resourceSkills) {
	                result += String.format(
	                        "Skill [id=%d, name='%s']%n",
	                        SkillLevelDesignationMapper.getSkillLevelDesignationId(), SkillLevelDesignationMapper.getName());
	            }
	        }*/
	        return result;
	    }

	/**
	 * @return the resourceSkills
	 */
	public Set<SkillLevelDesignationMapper> getResourceSkills() {
		return resourceSkills;
	}

	/**
	 * @param resourceSkills the resourceSkills to set
	 */
	public void setResourceSkills(Set<SkillLevelDesignationMapper> resourceSkills) {
		this.resourceSkills = resourceSkills;
	}

	/**
	 * @return the resourceId
	 */
	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}



	/**
	 * @return the resourceName
	 */
	public String getResourceName() {
		return resourceName;
	}

	/**
	 * @param resourceName
	 *            the resourceName to set
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	/**
	 * @return the skillLevelDesignationId
	 */
	public Long getSkillLevelDesignationId() {
		return skillLevelDesignationId;
	}

	/**
	 * @param skillLevelDesignationId the skillLevelDesignationId to set
	 */
	public void setSkillLevelDesignationId(Long skillLevelDesignationId) {
		this.skillLevelDesignationId = skillLevelDesignationId;
	}

	/**
	 * @return the resourceEmail
	 */
	public String getResourceEmail() {
		return resourceEmail;
	}

	/**
	 * @return the parentResourceId
	 */
	public Long getParentResourceId() {
		return parentResourceId;
	}




	/**
	 * @param parentResourceId the parentResourceId to set
	 */
	public void setParentResourceId(Long parentResourceId) {
		this.parentResourceId = parentResourceId;
	}




	/**
	 * @return the departmentId
	 */
	public Long getDepartmentId() {
		return departmentId;
	}

	/**
	 * @param departmentId the departmentId to set
	 */
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	/**
	 * @return the shiftId
	 */
	public Long getShiftId() {
		return shiftId;
	}




	/**
	 * @param shiftId the shiftId to set
	 */
	public void setShiftId(Long shiftId) {
		this.shiftId = shiftId;
	}




	/**
	 * @return the locationId
	 */
	public Long getLocationId() {
		return locationId;
	}




	/**
	 * @param locationId the locationId to set
	 */
	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}




	/**
	 * @return the jobType
	 */
	public Long getJobType() {
		return jobType;
	}




	/**
	 * @param jobType the jobType to set
	 */
	public void setJobType(Long jobType) {
		this.jobType = jobType;
	}




	/**
	 * @return the resourceType
	 */
	public Long getResourceType() {
		return resourceType;
	}

	/**
	 * @param resourceType the resourceType to set
	 */
	public void setResourceType(Long resourceType) {
		this.resourceType = resourceType;
	}

	/**
	 * @param resourceEmail
	 *            the resourceEmail to set
	 */
	public void setResourceEmail(String resourceEmail) {
		this.resourceEmail = resourceEmail;
	}

	/**
	 * @return the resourcePhone
	 */
	public String getResourcePhone() {
		return resourcePhone;
	}

	/**
	 * @param resourcePhone
	 *            the resourcePhone to set
	 */
	public void setResourcePhone(String resourcePhone) {
		this.resourcePhone = resourcePhone;
	}

	

	/**
	 * @return the resourceTimeSlot
	 */
	public Long getResourceTimeSlot() {
		return resourceTimeSlot;
	}

	/**
	 * @param resourceTimeSlot the resourceTimeSlot to set
	 */
	public void setResourceTimeSlot(Long resourceTimeSlot) {
		this.resourceTimeSlot = resourceTimeSlot;
	}

	/**
	 * @return the resourceNotificationType
	 */
	public Long getResourceNotificationType() {
		return resourceNotificationType;
	}

	/**
	 * @param resourceNotificationType the resourceNotificationType to set
	 */
	public void setResourceNotificationType(Long resourceNotificationType) {
		this.resourceNotificationType = resourceNotificationType;
	}

	/**
	 * @return the resourceMobileAppId
	 */
	public String getResourceMobileAppId() {
		return resourceMobileAppId;
	}

	/**
	 * @param resourceMobileAppId
	 *            the resourceMobileAppId to set
	 */
	public void setResourceMobileAppId(String resourceMobileAppId) {
		this.resourceMobileAppId = resourceMobileAppId;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the updatedBy
	 */
	public String getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy
	 *            the updatedBy to set
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate
	 *            the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate
	 *            the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

/*	@Override
	public String toString() {
		return "Resource [id=" + resourceId + ", name=" + resourceName + "]";
	}*/

}
