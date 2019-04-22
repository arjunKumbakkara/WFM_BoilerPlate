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
import com.sixdee.wfm.model.Category;

/**
 * @author Nalini N
 *
 *         Date : 11-Mar-2019
 */
@Repository
public interface CategoryRepository extends GlobalRepository<Category, Long> {

	List<Category> findAll(Specification<Category> searchString);

	@Query(value = "SELECT * FROM " + TableNames.Category + " C where C.parent_category_id IS NULL OR C.parent_category_id=0", nativeQuery = true)
	List<Category> findAll();
}
