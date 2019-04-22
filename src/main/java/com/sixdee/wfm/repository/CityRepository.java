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
import com.sixdee.wfm.model.City;

/**
 * @author Nalini N
 *
 *         Date : 11-Mar-2019
 */
@Repository
public interface CityRepository extends GlobalRepository<City, Long> {

	List<City> findAll(Specification<City> searchString);

	@Query(value = "SELECT * FROM " + TableNames.City + " where state_id = ?1", nativeQuery = true)
	List<City> findCityByStateId(Integer id);

}
