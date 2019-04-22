/**
 * 
	
 */
package com.sixdee.wfm.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.sixdee.wfm.common.TableNames;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Arjun Kumbakkara[2019@sixdee|WFM|v1.0.0]
 */

@Entity
@Table(name = TableNames.Designations)
@EntityListeners(AuditingEntityListener.class)
public class Designation {
	/*
	 * This view is for UI purpose public interface SkillView extends LevelView { To avoid Designations in skill view added a new jsonView }
	 * 
	 * This view is for Level and to avoid skill_cost,revenue unit etc public interface LevelView { }
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "designation_id")
	@ApiModelProperty(hidden = true)
	private int designationId;

	@Column(name = "designation_name", unique = true)
	private String designationName;

	@Column(name = "designation_desc")
	private String designationDesc;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "updated_by")
	private String updatedBy;

	@ApiModelProperty(hidden = true)
	@Column(nullable = false, updatable = false, name = "create_date")
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	/* @CreationTimestamp */
	private Date createDate;

	@ApiModelProperty(hidden = true)
	@Column(nullable = false, name = "update_date")
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	/* @UpdateTimestamp */
	private Date updateDate;

	/**
	 * @return the designationId
	 */
	public int getDesignationId() {
		return designationId;
	}

	/**
	 * @param designationId
	 *            the designationId to set
	 */
	public void setDesignationId(int designationId) {
		this.designationId = designationId;
	}

	/**
	 * @return the designationName
	 */
	public String getDesignationName() {
		return designationName;
	}

	/**
	 * @param designationName
	 *            the designationName to set
	 */
	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}

	/**
	 * @return the designationDesc
	 */
	public String getDesignationDesc() {
		return designationDesc;
	}

	/**
	 * @param designationDesc
	 *            the designationDesc to set
	 */
	public void setDesignationDesc(String designationDesc) {
		this.designationDesc = designationDesc;
	}

	/**
	 * @return the createDate
	 */

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

}
