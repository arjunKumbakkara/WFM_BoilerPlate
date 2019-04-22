/**
 * 
	
 */

package com.sixdee.wfm.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sixdee.wfm.base.GlobalRepository;
import com.sixdee.wfm.common.TableNames;
import com.sixdee.wfm.model.ValidationModel;

/**
 * @author Nalini N
 *
 *         Date : 11-Mar-2019
 */

@Repository
public interface ValidationRepository extends GlobalRepository<ValidationModel, Long> {

	@Query(value = "SELECT * FROM " + TableNames.Validations + " V where V.field_name= ?2 AND V.method_type=?1", nativeQuery = true)
	ValidationModel findById(Integer requestType, String key);
}
