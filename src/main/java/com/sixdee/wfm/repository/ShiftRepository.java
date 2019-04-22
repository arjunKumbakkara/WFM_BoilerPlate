/**
 * 
	
 */
package com.sixdee.wfm.repository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.sixdee.wfm.base.GlobalRepository;
import com.sixdee.wfm.model.CategoryProjectMapper;
import com.sixdee.wfm.model.Level;
import com.sixdee.wfm.model.Shift;
/**
 * @author Arjun Kumbakkara[2019@sixdee|WFM|v1.0.0]
 */

@Repository
public interface ShiftRepository extends GlobalRepository<Shift, Long> {

	List<Shift> findAll(Specification<Shift> searchString);
}
