/**
 * 
	
 */
package com.sixdee.wfm.repository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.sixdee.wfm.base.GlobalRepository;
import com.sixdee.wfm.model.Country;

/**
 * @author Nalini N
 *
 *         Date : 11-Mar-2019
 */
@Repository
public interface CountryRepository extends GlobalRepository<Country, Long> {

	List<Country> findAll(Specification<Country> searchString);
}
