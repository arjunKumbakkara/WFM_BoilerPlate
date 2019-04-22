/**
 * 
	
 */
package com.sixdee.wfm.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.sixdee.wfm.model.Community;

/**
 * @author Nalini N
 *
 *         Date : 11-Mar-2019
 */
@Service
public interface CommunityService {

	public Page<Community> getAllCommunity(String page, String pageSize, String sortDirection, String sortKey, String search);

	public List<Community> getAllCommunity(String search);

	public List<Community> findCommunityByCityId(Integer Id);

}
