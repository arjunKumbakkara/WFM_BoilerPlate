/**
 * 
	
 */
package com.sixdee.wfm.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.sixdee.wfm.model.State;

/**
 * @author Nalini N
 *
 *         Date : 11-Mar-2019
 */
@Service
public interface StateService {

	public Page<State> getAllState(String page, String pageSize, String sortDirection, String sortKey, String search);

	public List<State> getAllState(String search);

	public List<State> findStateByCountryId(Integer Id);
}
