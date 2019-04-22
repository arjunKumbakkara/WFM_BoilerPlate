/**
 * 
	
 */
package com.sixdee.wfm.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.sixdee.wfm.common.PaginationBaseImpl;
import com.sixdee.wfm.common.TableNames;
import com.sixdee.wfm.exception.DataViolationException;
import com.sixdee.wfm.exception.ResourceNotFoundException;
import com.sixdee.wfm.model.Level;
import com.sixdee.wfm.model.LevelDesignation;
import com.sixdee.wfm.model.Skill;
import com.sixdee.wfm.repository.LevelRepository;
import com.sixdee.wfm.repository.SkillRepository;
import com.sixdee.wfm.service.SkillService;

/**
 * @author Nalini N
 *
 *         Date : 11-Mar-2019
 */
@Component
public class SkillServiceImpl extends PaginationBaseImpl implements SkillService {
	public static Logger logger = LoggerFactory.getLogger(SkillServiceImpl.class);
	@Autowired
	private SkillRepository skillRepository;

	@Autowired
	private LevelRepository levelRepository;

	@Autowired
	JdbcTemplate jtemplate_dataSourceOne;

	@Override
	public Skill createSkill(Skill skill) {
		logger.info("SkillServiceImpl.createSkill()" + skill);
		return skillRepository.save(skill);
	}

	@Override /* @Pageable */
	public Page<Skill> getAllSkill(String page, String pageSize, String sortDirection, String sortKey, String search) {
		Page<Skill> lister = skillRepository.findAll(skillRepository.textInAllColumns(search), gotoPageWithConstraints(page, pageSize, sortDirection, sortKey, Skill.class));
		return lister;
	}

	@Override
	public List<Skill> getAllSkill(String search) {
		List<Skill> skills = null;
		if (search != null) {
			skills = skillRepository.findAll(skillRepository.textInAllColumns(search));
		} else {
			skills = skillRepository.findAll();
		}
		return skills;
	}

	@Override
	public Skill findById(Long Id) {
		Skill skill = skillRepository.findById(Id).orElseThrow(() -> new ResourceNotFoundException("Skill", "id", Id));
		return skill;
	}

	@Override
	public Skill updateSkill(String key, Long Id, Skill updateSkill) {

		skillRepository.findById(Id).orElseThrow(() -> new ResourceNotFoundException("Skill", "id", Id));
		updateSkill.setSkillId(Id);
		return skillRepository.save(updateSkill);

	}

	@Override
	public void deleteSkill(String key, Long Id) {
		Skill skill = skillRepository.findById(Id).orElseThrow(() -> new ResourceNotFoundException("Skill", "id", Id));
		try {
			skillRepository.delete(skill);
		} catch (Exception e) {
			logger.error("Exception raised while deleting the entry in table ");
			throw new DataViolationException("Skill");
		}
	}

	@Override
	public List<Level> findByIdWithLevelGrouping(Integer Id) {

		List<Level> level = levelRepository.findByIdWithLevelGrouping(Id);
		return level;
	}

	@Override
	public List<LevelDesignation> findLevelToDesignation(Integer levelId, Integer skillId) {

		return jtemplate_dataSourceOne
				.query("Select SL.designation_id,SL.skill_duration,SL.skill_unit,SL.skill_cost,SL.skill_revenue,D.designation_name,D.designation_desc,D.create_date,D.created_by,D.update_date,D.updated_by from "
						+ TableNames.SkillLevelDesignationMapper + " SL," + TableNames.Designations + " D where SL.level_id=" + levelId + " and SL.skill_id=" + skillId
						+ " and SL.designation_id=D.designation_id;", new LevelDesignationRowMapper());

	}

	class LevelDesignationRowMapper implements RowMapper<LevelDesignation> {
		@Override
		public LevelDesignation mapRow(ResultSet rs, int rowNum) throws SQLException {
			LevelDesignation levelDesignation = new LevelDesignation();
			levelDesignation.setDesignationId(rs.getInt("designation_id"));
			levelDesignation.setDesignationName(rs.getString("designation_name"));
			levelDesignation.setDesignationDesc(rs.getString("designation_desc"));
			levelDesignation.setSkillCost(rs.getInt("skill_cost"));
			levelDesignation.setSkillRevenue(rs.getInt("skill_revenue"));
			levelDesignation.setSkillDuration(rs.getInt("skill_duration"));
			levelDesignation.setSkillUnit(rs.getString("skill_unit"));
			return levelDesignation;
		}
	}

}
