/**
 * 
	
 */
package com.sixdee.wfm.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.sixdee.wfm.model.Level;
import com.sixdee.wfm.model.LevelDesignation;
import com.sixdee.wfm.model.Skill;

/**
 * @author Nalini N
 *
 *         Date : 11-Mar-2019
 */
@Service
public interface SkillService {

	public Skill createSkill(Skill skill);

	public Page<Skill> getAllSkill(String page, String pageSize, String sortDirection, String sortKey, String search);

	public List<Skill> getAllSkill(String search);

	public Skill findById(Long Id);

	public Skill updateSkill(String key, Long Id, Skill updateSkill);

	public void deleteSkill(String key, Long Id);

	public List<Level> findByIdWithLevelGrouping(Integer Id);

	public List<LevelDesignation> findLevelToDesignation(Integer levelId, Integer skillId);

}
