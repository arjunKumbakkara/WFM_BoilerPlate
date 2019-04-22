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
import com.sixdee.wfm.model.Country;
import com.sixdee.wfm.repository.CountryRepository;
import com.sixdee.wfm.service.CountryService;

/**
 * @author Nalini N
 *
 *         Date : 11-Mar-2019
 */
@Component
public class CountryServiceImpl extends PaginationBaseImpl implements CountryService {
	public static Logger logger = LoggerFactory.getLogger(CountryServiceImpl.class);
	@Autowired
	private CountryRepository countryRepository;

	@Override /* @Pageable */
	public Page<Country> getAllCountry(String page, String pageSize, String sortDirection, String sortKey, String search) {
		Page<Country> lister = countryRepository.findAll(countryRepository.textInAllColumns(search), gotoPageWithConstraints(page, pageSize, sortDirection, sortKey, Country.class));
		return lister;
	}

	@Override
	public List<Country> getAllCountry(String search) {
		List<Country> country = null;
		if (search != null) {
			country = countryRepository.findAll(countryRepository.textInAllColumns(search));
		} else {
			country = countryRepository.findAll();
		}
		return country;
	}

}
