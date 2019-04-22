/**
 * 
	
 */
package com.sixdee.wfm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.sixdee.wfm.common.PaginationBaseImpl;
import com.sixdee.wfm.model.State;
import com.sixdee.wfm.repository.StateRepository;
import com.sixdee.wfm.service.StateService;

/**
 * @author Nalini N
 *
 *         Date : 11-Mar-2019
 */
@Component
public class StateServiceImpl extends PaginationBaseImpl implements StateService {

	@Autowired
	private StateRepository stateRepository;

	@Override /* @Pageable */
	public Page<State> getAllState(String page, String pageSize, String sortDirection, String sortKey, String search) {
		Page<State> lister = stateRepository.findAll(stateRepository.textInAllColumns(search), gotoPageWithConstraints(page, pageSize, sortDirection, sortKey, State.class));
		return lister;
	}

	@Override
	public List<State> getAllState(String search) {
		List<State> state = null;
		if (search != null) {
			state = stateRepository.findAll(stateRepository.textInAllColumns(search));
		} else {
			state = stateRepository.findAll();
		}
		return state;
	}

	@Override
	public List<State> findStateByCountryId(Integer Id) {
		List<State> state = stateRepository.findStateByCountryId(Id);
		return state;
	}

}
