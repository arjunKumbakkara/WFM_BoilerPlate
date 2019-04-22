/**
 * 
	
 */
package com.sixdee.wfm.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.sixdee.wfm.common.PaginationBaseImpl;
import com.sixdee.wfm.model.Community;
import com.sixdee.wfm.repository.CommunityRepository;
import com.sixdee.wfm.service.CommunityService;

/**
 * @author Nalini N
 *
 *         Date : 11-Mar-2019
 */
@Component
public class CommunityServiceImpl extends PaginationBaseImpl implements CommunityService {
	public static Logger logger = LoggerFactory.getLogger(CommunityServiceImpl.class);
	@Autowired
	private CommunityRepository communityRepository;

	@Override /* @Pageable */
	public Page<Community> getAllCommunity(String page, String pageSize, String sortDirection, String sortKey, String search) {
		Page<Community> lister = communityRepository.findAll(communityRepository.textInAllColumns(search), gotoPageWithConstraints(page, pageSize, sortDirection, sortKey, Community.class));
		return lister;
	}

	@Override
	public List<Community> getAllCommunity(String search) {
		List<Community> community = null;
		if (search != null) {
			community = communityRepository.findAll(communityRepository.textInAllColumns(search));
		} else {
			community = communityRepository.findAll();
		}
		return community;
	}

	@Override
	public List<Community> findCommunityByCityId(Integer Id) {
		List<Community> communities = communityRepository.findCommunityByCityId(Id);
		return communities;
	}

}
