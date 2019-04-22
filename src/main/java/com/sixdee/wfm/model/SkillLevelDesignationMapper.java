
package com.sixdee.wfm.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.sixdee.wfm.common.Constants;
import com.sixdee.wfm.common.TableNames;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Nalini N
 *
 *         Date : 11-Mar-2019
 */

@Entity
@Table(name = TableNames.SkillLevelDesignationMapper)
@EntityListeners(AuditingEntityListener.class)
public class SkillLevelDesignationMapper {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "skillLevelDesignation_id")
	@ApiModelProperty(hidden = true)
	private int skillLevelDesignationId;

	@Column(name = "designation_id")
	@ApiModelProperty(required = true)
	private int designationId;
	
	@Column(name = "skill_id")
	@ApiModelProperty(required = true)
	private int skillId;

	@Column(name = "skill_duration")
	@ApiModelProperty(required = true)
	private int skillDuration;

	@Column(name = "skill_unit")
	@ApiModelProperty(required = true)
	private String skillUnit;

	@Column(name = "skill_cost")
	@ApiModelProperty(required = true)
	private int skillCost;

	@Column(name = "skill_revenue")
	@ApiModelProperty(required = true)
	private int skillRevenue;
	
	
	@Column(name = "skill_expertise")
	private String skillExpertise;

	@Column(name = "level_id")
	@ApiModelProperty(required = true)
	@JsonProperty(access = Access.WRITE_ONLY) // Only for POST Request show the level id and for GET request ignore this field in Skill
	private int levelId;

	@ManyToOne
	@JoinColumn(name = "level_id", nullable = false, insertable = false, updatable = false)
	private Level level;

	@Column(name = "create_date")
	@CreatedDate
	private Date createDate;

	@Column(name = "update_date")
	@LastModifiedDate
	private Date updateDate;
	
	
	//@ManyToMany(fetch = FetchType.EAGER, mappedBy = "resourceSkills")
	@ManyToMany( mappedBy = "resourceSkills")
	//private Set<Resource> resources = new HashSet<Resource>();
	@JsonIgnore
	private Set<Resource> resources;

	public Set<Resource> getResources() {
		return resources;
	}
	@ApiModelProperty(hidden = true)
	public void setResources(Set<Resource> resources) {
		this.resources = resources;
	}

	
	
	 /* @Override
	    public String toString() {
	        String result = String.format(
	                "Book [id=%d, name='%s']%n",
	                id, name);
	        if (publishers != null) {
	            for(Publisher publisher : publishers) {
	                result += String.format(
	                        "Publisher[id=%d, name='%s']%n",
	                        publisher.getId(), publisher.getName());
	            }
	        }

	        return result;
	    }
	*/
	
	
	/**
	 * @return the skillExpertise
	 */
	public String getSkillExpertise() {
		return skillExpertise;
	}
	/**
	 * @param skillExpertise the skillExpertise to set
	 */
	public void setSkillExpertise(String skillExpertise) {
		this.skillExpertise = skillExpertise;
	}
	public int getSkillLevelDesignationId() {
		return skillLevelDesignationId;
	}

	public void setSkillLevelDesignationId(int skillLevelDesignationId) {
		this.skillLevelDesignationId = skillLevelDesignationId;
	}

	public int getLevelId() {
		return levelId;
	}

	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}

	public int getDesignationId() {
		return designationId;
	}

	public Level getLevel() {
		return level;
	}

	@ApiModelProperty(hidden = true)
	public void setLevel(Level level) {
		this.level = level;
	}

	public void setDesignationId(int designationId) {
		this.designationId = designationId;
	}

	public int getSkillDuration() {
		return skillDuration;
	}

	public void setSkillDuration(int skillDuration) {
		this.skillDuration = skillDuration;
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

	public String getSkillUnit() {
		return skillUnit;
	}
	/**
	 * @return the skillId
	 */
	public int getSkillId() {
		return skillId;
	}
	/**
	 * @param skillId the skillId to set
	 */
	public void setSkillId(int skillId) {
		this.skillId = skillId;
	}
	public void setSkillUnit(String skillUnit) {
		this.skillUnit = skillUnit;
	}

	public int getSkillCost() {
		return skillCost;
	}

	public void setSkillCost(int skillCost) {
		this.skillCost = skillCost;
	}

	public int getSkillRevenue() {
		return skillRevenue;
	}

	public void setSkillRevenue(int skillRevenue) {
		this.skillRevenue = skillRevenue;
	}

}
