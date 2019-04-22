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

import com.fasterxml.jackson.annotation.JsonFormat;
/**
* @author Arjun Kumbakkara[2019@sixdee|WFM|v1.0.0] 
*/
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sixdee.wfm.common.Constants;
import com.sixdee.wfm.common.TableNames;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Arjun Kumbakkara[2019@sixdee|WFM|v1.0.0]
 */

@Entity
@Table(name = TableNames.Sections)
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "create_date", "update_date" }, allowGetters = true)
public class Section {
	// For JPA
	Section() {

	}

	@Id
	@ApiModelProperty(hidden = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "section_id", updatable = false, nullable = false)
	private Long sectionId;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "section_name")
	private String sectionName;

	@Column(name = "section_desc")
	private String sectionDesc;

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
	 * @return the createDate
	 */
	@JsonFormat(pattern = Constants.RESPONSE_DATE_FORMAT)
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate
	 *            the createDate to set
	 */
	@ApiModelProperty(hidden = true)
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public String toString() {
		return "Section [id=" + sectionId + ", name=" + sectionName + "]";
	}

	/**
	 * @return the sectionId
	 */
	public Long getSectionId() {
		return sectionId;
	}

	/**
	 * @param sectionId
	 *            the sectionId to set
	 */
	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
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
	 * @return the sectionName
	 */
	public String getSectionName() {
		return sectionName;
	}

	/**
	 * @param sectionName
	 *            the sectionName to set
	 */
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	/**
	 * @return the sectionDesc
	 */
	public String getSectionDesc() {
		return sectionDesc;
	}

	/**
	 * @param sectionDesc
	 *            the sectionDesc to set
	 */
	public void setSectionDesc(String sectionDesc) {
		this.sectionDesc = sectionDesc;
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

}
