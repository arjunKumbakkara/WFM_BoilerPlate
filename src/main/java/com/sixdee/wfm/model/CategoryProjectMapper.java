
package com.sixdee.wfm.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.sixdee.wfm.common.Constants;
import com.sixdee.wfm.common.TableNames;

import io.swagger.annotations.ApiModelProperty;
/*
@author
Nalini N

Date:14-Mar-2019*/

@Entity
@Table(name = TableNames.CategoryProjectMapping)
@EntityListeners(AuditingEntityListener.class)
public class CategoryProjectMapper {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "category_project_mapping_id")
	@ApiModelProperty(hidden = true)
	private Long categoryProjectMappingId;

	@Column(name = "category_id")
	@ApiModelProperty(required = true)
	@JsonProperty(access = Access.WRITE_ONLY) // Only for POST Request show the level id and for GET request ignore this field in Skill
	private int categoryId;

	@Column(name = "sub_category_name", unique = true)
	@ApiModelProperty(required = true)
	private String subCategoryName;

	@Column(name = "sub_category_desc")
	private String subCategoryDesc;

	@Column(name = "question_id")
	@ApiModelProperty(required = true)
	@JsonProperty(access = Access.WRITE_ONLY) // Only for POST Request show the level id and for GET request ignore this field in Skill
	private int questionId;

	@Column(name = "project_id")
	@ApiModelProperty(required = true)
	@JsonProperty(access = Access.WRITE_ONLY) // Only for POST Request show the level id and for GET request ignore this field in Skill
	private int projectId;

	@OneToOne
	@JoinColumn(name = "question_id", nullable = false, insertable = false, updatable = false)
	private FAQ question;

	@OneToOne
	@JoinColumn(name = "project_id", nullable = false, insertable = false, updatable = false)
	private Project project;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "category_id", nullable = false, insertable = false, updatable = false)
	private Category category;

	public Category getCategory() {
		return category;
	}

	@ApiModelProperty(hidden = true)
	public void setCategory(Category category) {
		this.category = category;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	@Column(name = "create_date")
	@CreatedDate
	private Date createDate;

	public FAQ getQuestion() {
		return question;
	}

	@ApiModelProperty(hidden = true)
	public void setQuestion(FAQ question) {
		this.question = question;
	}

	public Project getProject() {
		return project;
	}

	@ApiModelProperty(hidden = true)
	public void setProject(Project project) {
		this.project = project;
	}

	@Column(name = "update_date")

	@LastModifiedDate
	private Date updateDate;

	public Long getCategoryProjectMappingId() {
		return categoryProjectMappingId;
	}

	public void setCategoryProjectMappingId(Long categoryProjectMappingId) {
		this.categoryProjectMappingId = categoryProjectMappingId;
	}

	public String getSubCategoryName() {
		return subCategoryName;
	}

	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}

	public String getSubCategoryDesc() {
		return subCategoryDesc;
	}

	public void setSubCategoryDesc(String subCategoryDesc) {
		this.subCategoryDesc = subCategoryDesc;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
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
