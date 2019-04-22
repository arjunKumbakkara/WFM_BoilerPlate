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
import com.sixdee.wfm.model.State;

/**
 * @author Nalini N
 *
 *         Date : 11-Mar-2019
 */
@Repository
public interface StateRepository extends GlobalRepository<State, Long> {

	List<State> findAll(Specification<State> searchString);

	@Query(value = "SELECT * FROM " + TableNames.State + " where country_id = ?1", nativeQuery = true)
	List<State> findStateByCountryId(Integer id);

}
