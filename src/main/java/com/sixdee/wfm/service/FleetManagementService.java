/**
 * 
	
 */
package com.sixdee.wfm.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.sixdee.wfm.model.FleetManagement;

/**
 * @author Nalini N
 *
 *         Date : 11-Mar-2019
 */
@Service
public interface FleetManagementService {

	public FleetManagement createFleetManagement(FleetManagement fleetManagement);

	public Page<FleetManagement> getAllFleetManagement(String page, String pageSize, String sortDirection, String sortKey, String search);

	public List<FleetManagement> getAllFleetManagement(String search);

	public FleetManagement findById(Long Id);

	public FleetManagement updateFleetManagement(String key, Long Id, FleetManagement updateFleetManagement);

	public void deleteFleetManagement(String key, Long Id);

}
