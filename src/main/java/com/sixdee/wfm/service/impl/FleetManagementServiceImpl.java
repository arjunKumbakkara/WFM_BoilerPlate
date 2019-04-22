/**
 * 
	
 */
package com.sixdee.wfm.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.sixdee.wfm.common.PaginationBaseImpl;
import com.sixdee.wfm.exception.DataViolationException;
import com.sixdee.wfm.exception.ResourceNotFoundException;
import com.sixdee.wfm.model.FleetManagement;
import com.sixdee.wfm.repository.FleetManagementRepository;
import com.sixdee.wfm.service.FleetManagementService;

/**
 * @author Nalini N
 *
 *         Date : 11-Mar-2019
 */
@Component
public class FleetManagementServiceImpl extends PaginationBaseImpl implements FleetManagementService {
	public static Logger logger = LoggerFactory.getLogger(FleetManagementServiceImpl.class);
	@Autowired
	private FleetManagementRepository fleetManagementRepository;

	@Autowired
	JdbcTemplate jtemplate_dataSourceOne;

	@Override
	public FleetManagement createFleetManagement(FleetManagement fleetManagement) {
		logger.info("FleetManagementServiceImpl.createFleetManagement()" + fleetManagement);
		return fleetManagementRepository.save(fleetManagement);
	}

	@Override /* @Pageable */
	public Page<FleetManagement> getAllFleetManagement(String page, String pageSize, String sortDirection, String sortKey, String search) {
		Page<FleetManagement> lister = fleetManagementRepository.findAll(fleetManagementRepository.textInAllColumns(search),
				gotoPageWithConstraints(page, pageSize, sortDirection, sortKey, FleetManagement.class));
		return lister;
	}

	@Override
	public List<FleetManagement> getAllFleetManagement(String search) {
		List<FleetManagement> fleetManagements = null;
		if (search != null) {
			fleetManagements = fleetManagementRepository.findAll(fleetManagementRepository.textInAllColumns(search));
		} else {
			fleetManagements = fleetManagementRepository.findAll();
		}
		return fleetManagements;
	}

	@Override
	public FleetManagement findById(Long Id) {
		FleetManagement fleetManagement = fleetManagementRepository.findById(Id).orElseThrow(() -> new ResourceNotFoundException("FleetManagement", "id", Id));
		return fleetManagement;
	}

	@Override
	public FleetManagement updateFleetManagement(String key, Long Id, FleetManagement updateFleetManagement) {

		fleetManagementRepository.findById(Id).orElseThrow(() -> new ResourceNotFoundException("FleetManagement", "id", Id));
		updateFleetManagement.setFleetManagementId(Id);
		return fleetManagementRepository.save(updateFleetManagement);

	}

	@Override
	public void deleteFleetManagement(String key, Long Id) {
		FleetManagement fleetManagement = fleetManagementRepository.findById(Id).orElseThrow(() -> new ResourceNotFoundException("FleetManagement", "id", Id));
		try {
			fleetManagementRepository.delete(fleetManagement);
		} catch (Exception e) {
			logger.error("Exception raised while deleting the entry in table ");
			throw new DataViolationException("FleetManagement");
		}
	}

}
