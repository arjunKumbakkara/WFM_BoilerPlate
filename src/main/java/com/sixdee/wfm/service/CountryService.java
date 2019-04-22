/**
 * 
	
 */
package com.sixdee.wfm.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.sixdee.wfm.model.Country;

/**
 * @author Nalini N
 *
 *         Date : 11-Mar-2019
 */
@Service
public interface CountryService {

	public Page<Country> getAllCountry(String page, String pageSize, String sortDirection, String sortKey, String search);

	public List<Country> getAllCountry(String search);
}
