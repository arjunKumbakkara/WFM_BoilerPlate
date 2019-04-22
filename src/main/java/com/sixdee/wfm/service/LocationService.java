/**
 * 
	
 */
package com.sixdee.wfm.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.sixdee.wfm.model.Location;

/**
 * @author Nalini N
 *
 *         Date : 11-Mar-2019
 */
@Service
public interface LocationService {

	public Location createLocation(Location location);

	public Page<Location> getAllLocation(String page, String pageSize, String sortDirection, String sortKey, String search);

	public List<Location> getAllLocation(String search);

	public Location findById(Long Id);

	public Location updateLocation(String key, Long Id, Location updateLocation);

	public void deleteLocation(String key, Long Id);
}
