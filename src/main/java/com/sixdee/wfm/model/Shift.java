/**
 * 
	
 */
package com.sixdee.wfm.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sixdee.wfm.common.Constants;
import com.sixdee.wfm.common.TableNames;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Arjun Kumbakkara[2019@sixdee|WFM|v1.0.0]
 */
@Entity
@Table(name = TableNames.Shifts)
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "create_date", "update_date" }, allowGetters = true)
public class Shift {

	@Id
	@ApiModelProperty(hidden = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "shift_id", updatable = false, nullable = false)
	private Long shiftId;

	@Column(name = "shift_name", unique = true)
	private String shiftName;

	@Column(name = "shift_desc")
	private String shiftDesc;

	@Column(name = "day_code")
	private String dayCode;

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

	@Column(nullable = false, name = "start_time")
	private String startTime;

	@Column(nullable = false, name = "end_time")
	private String endTime;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "updated_by")
	private String updatedBy;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "shift_id")
	private List<Resource> resources = new ArrayList<Resource>();

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
	 * @return the shiftId
	 */
	public Long getShiftId() {
		return shiftId;
	}

	/**
	 * @param shiftId
	 *            the shiftId to set
	 */
	public void setShiftId(Long shiftId) {
		this.shiftId = shiftId;
	}

	/**
	 * @return the shiftName
	 */
	public String getShiftName() {
		return shiftName;
	}

	/**
	 * @param shiftName
	 *            the shiftName to set
	 */
	public void setShiftName(String shiftName) {
		this.shiftName = shiftName;
	}

	/**
	 * @return the shiftDesc
	 */
	public String getShiftDesc() {
		return shiftDesc;
	}

	/**
	 * @param shiftDesc
	 *            the shiftDesc to set
	 */
	public void setShiftDesc(String shiftDesc) {
		this.shiftDesc = shiftDesc;
	}

	/**
	 * @return the dayCode
	 */
	public String getDayCode() {
		return dayCode;
	}

	/**
	 * @param dayCode
	 *            the dayCode to set
	 */
	public void setDayCode(String dayCode) {
		this.dayCode = dayCode;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the resources
	 */
	public List<Resource> getResources() {
		return resources;
	}

	/**
	 * Logic: its simple.In any object.If a particular param is to be hidden whilst POST (serialization) and shown whilst GET (Deserialization).Then add a apiModelProperty as hidden true fro Swagger
	 * and JsonIgnore for JSON
	 */
	@ApiModelProperty(hidden = true)
	/* @JsonIgnore */
	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}

	@JsonFormat(pattern = Constants.RESPONSE_DATE_FORMAT)
	public Date getUpdateDate() {
		return updateDate;
	}

	@ApiModelProperty(hidden = true)
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@JsonFormat(pattern = Constants.RESPONSE_DATE_FORMAT)
	public Date getCreateDate() {
		return createDate;
	}

	@ApiModelProperty(hidden = true)
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
