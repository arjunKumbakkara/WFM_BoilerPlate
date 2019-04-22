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
import com.sixdee.wfm.model.Community;

/**
 * @author Nalini N
 *
 *         Date : 11-Mar-2019
 */
@Repository
public interface CommunityRepository extends GlobalRepository<Community, Long> {

	List<Community> findAll(Specification<Community> searchString);

	@Query(value = "SELECT * FROM " + TableNames.Community + " where city_id = ?1", nativeQuery = true)
	List<Community> findCommunityByCityId(Integer id);

}
