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
import com.sixdee.wfm.model.City;
import com.sixdee.wfm.repository.CityRepository;
import com.sixdee.wfm.service.CityService;

/**
 * @author Nalini N
 *
 *         Date : 11-Mar-2019
 */
@Component
public class CityServiceImpl extends PaginationBaseImpl implements CityService {
	public static Logger logger = LoggerFactory.getLogger(CityServiceImpl.class);
	@Autowired
	private CityRepository cityRepository;

	@Override /* @Pageable */
	public Page<City> getAllCity(String page, String pageSize, String sortDirection, String sortKey, String search) {
		Page<City> lister = cityRepository.findAll(cityRepository.textInAllColumns(search), gotoPageWithConstraints(page, pageSize, sortDirection, sortKey, City.class));
		return lister;
	}

	@Override
	public List<City> getAllCity(String search) {
		List<City> city = null;
		if (search != null) {
			city = cityRepository.findAll(cityRepository.textInAllColumns(search));
		} else {
			city = cityRepository.findAll();
		}
		return city;
	}

	@Override
	public List<City> findCityByStateId(Integer Id) {
		List<City> city = cityRepository.findCityByStateId(Id);
		return city;
	}

}
