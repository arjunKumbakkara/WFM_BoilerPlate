/**
 * 
	
 */
package com.sixdee.wfm.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.sixdee.wfm.model.City;

/**
 * @author Nalini N
 *
 *         Date : 11-Mar-2019
 */
@Service
public interface CityService {

	public Page<City> getAllCity(String page, String pageSize, String sortDirection, String sortKey, String search);

	public List<City> getAllCity(String search);

	public List<City> findCityByStateId(Integer Id);
}
