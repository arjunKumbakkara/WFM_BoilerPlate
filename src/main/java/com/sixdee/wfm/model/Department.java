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
@Table(name = TableNames.Departments)
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "create_date", "update_date" }, allowGetters = true)
/*
 * @NamedQueries({
 * 
 * @NamedQuery(name = "Department.fetchAll", query = "select departmentId,departmentName,departmentDesc,createDate,updateDate from Department "),
 * 
 * @NamedQuery(name = "Employee.fetchById", query = "select departmentId,departmentName,departmentDesc,createDate,updateDate from Department where  departmentId =:id "),
 * 
 * @NamedQuery(name = "Employee.fetchByLessthan", query = "select departmentId,departmentName,departmentDesc,createDate,updateDate from Department where  departmentId <:length") })
 */
public class Department {

	// For JPA
	Department() {

	}

	@Id
	@ApiModelProperty(hidden = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "department_id", updatable = false, nullable = false)
	private Long departmentId;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "department_name")
	private String departmentName;

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

	@Column(name = "department_desc")
	private String departmentDesc;

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

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "department_id")
	private List<Section> sections = new ArrayList<Section>();

	/**
	 * @return the sections
	 */
	public List<Section> getSections() {
		return sections;
	}

	/**
	 * @param sections
	 *            the sections to set
	 */
	public void setSections(List<Section> sections) {
		this.sections = sections;
	}

	/**
	 * @return the departmentId
	 */
	public Long getDepartmentId() {
		return departmentId;
	}

	/**
	 * @param departmentId
	 *            the departmentId to set
	 */
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	/**
	 * @return the departmentName
	 */
	public String getDepartmentName() {
		return departmentName;
	}

	/**
	 * @param departmentName
	 *            the departmentName to set
	 */
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	/**
	 * @return the departmentDesc
	 */
	public String getDepartmentDesc() {
		return departmentDesc;
	}

	/**
	 * @param departmentDesc
	 *            the departmentDesc to set
	 */
	public void setDepartmentDesc(String departmentDesc) {
		this.departmentDesc = departmentDesc;
	}

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

	/**
	 * @return the updateDate
	 */
	@JsonFormat(pattern = Constants.RESPONSE_DATE_FORMAT)
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate
	 *            the updateDate to set
	 */
	@ApiModelProperty(hidden = true)
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Override
	public String toString() {
		return "Department [id=" + departmentId + ", name=" + departmentName + "]";
	}

}
