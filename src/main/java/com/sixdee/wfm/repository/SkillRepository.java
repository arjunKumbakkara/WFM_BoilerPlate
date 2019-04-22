/**
 * 
	
 */
package com.sixdee.wfm.repository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.sixdee.wfm.base.GlobalRepository;
import com.sixdee.wfm.model.Skill;

/**
 * @author Nalini N
 *
 *         Date : 11-Mar-2019
 */
@Repository
public interface SkillRepository extends GlobalRepository<Skill, Long> {

	List<Skill> findAll(Specification<Skill> searchString);

	// Skill save(Skill updateSkill, Long id);
}
