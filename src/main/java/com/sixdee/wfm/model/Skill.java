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

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sixdee.wfm.common.Constants;
import com.sixdee.wfm.common.TableNames;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Nalini N
 *
 *         Date : 11-Mar-2019
 */
@Entity
@Table(name = TableNames.Skills)
@EntityListeners(AuditingEntityListener.class)
public class Skill {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "skill_id")
	@ApiModelProperty(hidden = true)
	private Long skillId;

	@Column(name = "skill_name", unique = true)
	@ApiModelProperty(required = true)
	private String skillName;

	@Column(name = "skill_desc")
	private String skillDesc;

	@Column(name = "create_date")
	@CreatedDate
	private Date createDate;

	@Column(name = "update_date")
	@LastModifiedDate
	private Date updateDate;

	@JoinColumn(name = "skill_id")
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<SkillLevelDesignationMapper> skillLevelDesignations = new ArrayList<SkillLevelDesignationMapper>();

	/**
	 * @return the skillLevelDesignations
	 */
	public List<SkillLevelDesignationMapper> getSkillLevelDesignations() {
		return skillLevelDesignations;
	}

	/**
	 * @param skillLevelDesignations
	 *            the skillLevelDesignations to set
	 */
	public void setSkillLevelDesignations(List<SkillLevelDesignationMapper> skillLevelDesignations) {
		this.skillLevelDesignations = skillLevelDesignations;
	}

	public Long getSkillId() {
		return skillId;
	}

	public void setSkillId(Long skillId) {
		this.skillId = skillId;
	}

	public String getSkillName() {
		return skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	public String getSkillDesc() {
		return skillDesc;
	}

	public void setSkillDesc(String skillDesc) {
		this.skillDesc = skillDesc;
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
