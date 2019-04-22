/**
 * 
	
 */
package com.sixdee.wfm.repository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sixdee.wfm.base.GlobalRepository;
import com.sixdee.wfm.common.TableNames;
import com.sixdee.wfm.model.Level;

/**
 * @author Arjun Kumbakkara[2019@sixdee|WFM|v1.0.0]
 */

@Repository
public interface LevelRepository extends GlobalRepository<Level, Long> {

	List<Level> findAll(Specification<Level> searchString);

	@Query(value = "SELECT distinct(SL.level_id),L.level_name,L.level_desc,L.create_date,L.created_by,L.update_date,L.updated_by FROM " + TableNames.SkillLevelDesignationMapper + " SL,"
			+ TableNames.Levels + " L where SL.skill_id= ?1 AND SL.level_id=L.level_id", nativeQuery = true)
	List<Level> findByIdWithLevelGrouping(Integer id);
}
