/**
 * 
	
 */

package com.sixdee.wfm.repository;

import org.springframework.stereotype.Repository;

import com.sixdee.wfm.base.GlobalRepository;
import com.sixdee.wfm.model.ErrorCodes;

/**
 * @author Nalini N
 *
 *         Date : 11-Mar-2019
 */

@Repository
public interface ErrorCodeRepository extends GlobalRepository<ErrorCodes, Long> {

}
