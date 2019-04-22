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
import com.sixdee.wfm.exception.DataViolationException;
import com.sixdee.wfm.exception.ResourceNotFoundException;
import com.sixdee.wfm.model.Location;
import com.sixdee.wfm.repository.LocationRepository;
import com.sixdee.wfm.service.LocationService;

/**
 * @author Nalini N
 *
 *         Date : 11-Mar-2019
 */
@Component
public class LocationServiceImpl extends PaginationBaseImpl implements LocationService {
	public static Logger logger = LoggerFactory.getLogger(LocationServiceImpl.class);
	@Autowired
	private LocationRepository locationRepository;

	@Override
	public Location createLocation(Location location) {
		logger.info("LocationServiceImpl.createLocation()" + location);
		return locationRepository.save(location);
	}

	@Override /* @Pageable */
	public Page<Location> getAllLocation(String page, String pageSize, String sortDirection, String sortKey, String search) {
		Page<Location> lister = locationRepository.findAll(locationRepository.textInAllColumns(search), gotoPageWithConstraints(page, pageSize, sortDirection, sortKey, Location.class));
		return lister;
	}

	@Override
	public List<Location> getAllLocation(String search) {
		List<Location> location = null;
		if (search != null) {
			location = locationRepository.findAll(locationRepository.textInAllColumns(search));
		} else {
			location = locationRepository.findAll();
		}
		return location;
	}

	@Override
	public Location findById(Long Id) {
		Location location = locationRepository.findById(Id).orElseThrow(() -> new ResourceNotFoundException("Location", "id", Id));
		return location;
	}

	@Override
	public Location updateLocation(String key, Long Id, Location updateLocation) {

		locationRepository.findById(Id).orElseThrow(() -> new ResourceNotFoundException("Location", "id", Id));
		updateLocation.setLocationId(Id);
		return locationRepository.save(updateLocation);

	}

	@Override
	public void deleteLocation(String key, Long Id) {
		Location location = locationRepository.findById(Id).orElseThrow(() -> new ResourceNotFoundException("Location", "id", Id));
		try {
			locationRepository.delete(location);
		} catch (Exception e) {
			logger.error("Exception raised while deleting the entry in table ");
			throw new DataViolationException("Locations");
		}
	}

}
