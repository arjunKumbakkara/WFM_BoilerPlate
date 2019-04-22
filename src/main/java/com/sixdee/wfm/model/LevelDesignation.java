/**
 * 
	
 */
package com.sixdee.wfm.model;

import javax.persistence.Column;

/**
 * @author Arjun Kumbakkara[2019@sixdee|WFM|v1.0.0]
 */

public class LevelDesignation extends Designation {

	@Column(name = "skill_cost")
	private int skillCost;

	@Column(name = "skill_duration")
	private int skillDuration;

	@Column(name = "skill_revenue")
	private int skillRevenue;

	@Column(name = "skill_unit")
	private String skillUnit;

	public int getSkillCost() {
		return skillCost;
	}

	public void setSkillCost(int skillCost) {
		this.skillCost = skillCost;
	}

	public int getSkillDuration() {
		return skillDuration;
	}

	public void setSkillDuration(int skillDuration) {
		this.skillDuration = skillDuration;
	}

	public int getSkillRevenue() {
		return skillRevenue;
	}

	public void setSkillRevenue(int skillRevenue) {
		this.skillRevenue = skillRevenue;
	}

	public String getSkillUnit() {
		return skillUnit;
	}

	public void setSkillUnit(String skillUnit) {
		this.skillUnit = skillUnit;
	}

}
