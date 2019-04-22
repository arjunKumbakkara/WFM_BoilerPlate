
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
import com.sixdee.wfm.model.Shift;
import com.sixdee.wfm.repository.ShiftRepository;
import com.sixdee.wfm.service.ShiftService;

/**
 * @author Arjun Kumbakkara[2019@sixdee|WFM|v1.0.0]
 */

@Component
public class ShiftServiceImpl extends PaginationBaseImpl implements ShiftService {
	public static Logger logger = LoggerFactory.getLogger(ShiftServiceImpl.class);
	@Autowired
	private ShiftRepository shiftRepository;

	@Override
	public Shift createShift(Shift shift) {
		logger.info("ShiftServiceImpl.createShift()" + shift);
		return shiftRepository.save(shift);
	}

	@Override /* @Pageable */
	public Page<Shift> getAllShift(String page, String pageSize, String sortDirection, String sortKey, String search) {
		Page<Shift> lister = shiftRepository.findAll(shiftRepository.textInAllColumns(search), gotoPageWithConstraints(page, pageSize, sortDirection, sortKey, Shift.class));
		return lister;
	}

	@Override
	public List<Shift> getAllShifts(String search) {
		List<Shift> shift = null;
		if (search != null) {
			shift = shiftRepository.findAll(shiftRepository.textInAllColumns(search));
		} else {
			shift = shiftRepository.findAll();
		}
		return shift;
	}

	@Override
	public Shift findById(Long Id) {
		Shift shift = shiftRepository.findById(Id).orElseThrow(() -> new ResourceNotFoundException("Shift", "id", Id));
		return shift;
	}

	@Override
	public Shift updateShift(String key, Long Id, Shift updateShift) {

		shiftRepository.findById(Id).orElseThrow(() -> new ResourceNotFoundException("Shift", "id", Id));
		updateShift.setShiftId(Id);
		return shiftRepository.save(updateShift);

	}

	@Override
	public void deleteShift(String key, Long Id) {
		Shift shift = shiftRepository.findById(Id).orElseThrow(() -> new ResourceNotFoundException("Shift", "id", Id));
		try {
			shiftRepository.delete(shift);
		} catch (Exception e) {
			logger.error("Exception raised while deleting the entry in table ");
			throw new DataViolationException("Shift");
		}
	}
}
