
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

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sixdee.wfm.common.Constants;
import com.sixdee.wfm.common.TableNames;

import io.swagger.annotations.ApiModelProperty;

/*

@author
Nalini N

Date:19-Mar-2019*/

@Entity
@Table(name = TableNames.Project)
@EntityListeners(AuditingEntityListener.class)
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "project_id")
	@ApiModelProperty(hidden = true)
	private Long projectId;

	@Column(name = "project_name", unique = true)
	@ApiModelProperty(required = true)
	private String projectName;

	@Column(name = "project_desc")
	@ApiModelProperty(required = true)
	private String projectDesc;

	@Column(name = "create_date")
	@CreatedDate
	private Date createDate;

	@Column(name = "update_date")
	@LastModifiedDate
	private Date updateDate;

	@Column(name = "project_type")
	@ApiModelProperty(required = true)
	private int projectType;

	@Column(name = "equipment_required")
	@ApiModelProperty(required = true)
	private int equipmentRequired;

	@Column(name = "assign_type")
	@ApiModelProperty(required = true)
	private int assignType;

	@Column(name = "out_sourcing")
	@ApiModelProperty(required = true)
	private int outSourcing;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "project_id")
	private List<ProjectTaskSequence> projectTaskSequence = new ArrayList<ProjectTaskSequence>();

	public List<ProjectTaskSequence> getProjectTaskSequence() {
		return projectTaskSequence;
	}

	public void setProjectTaskSequence(List<ProjectTaskSequence> projectTaskSequence) {
		this.projectTaskSequence = projectTaskSequence;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectDesc() {
		return projectDesc;
	}

	public void setProjectDesc(String projectDesc) {
		this.projectDesc = projectDesc;
	}

	public int getProjectType() {
		return projectType;
	}

	public void setProjectType(int projectType) {
		this.projectType = projectType;
	}

	public int getEquipmentRequired() {
		return equipmentRequired;
	}

	public void setEquipmentRequired(int equipmentRequired) {
		this.equipmentRequired = equipmentRequired;
	}

	public int getAssignType() {
		return assignType;
	}

	public void setAssignType(int assignType) {
		this.assignType = assignType;
	}

	public int getOutSourcing() {
		return outSourcing;
	}

	public void setOutSourcing(int outSourcing) {
		this.outSourcing = outSourcing;
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
