/**
 * 
	
 */
package com.sixdee.wfm.repository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.sixdee.wfm.base.GlobalRepository;
import com.sixdee.wfm.model.CategoryProjectMapper;

/**
 * @author Nalini N
 *
 *         Date : 11-Mar-2019
 */
@Repository
public interface CategoryProjectMapperRepository extends GlobalRepository<CategoryProjectMapper, Long> {

	List<CategoryProjectMapper> findAll(Specification<CategoryProjectMapper> searchString);
}
