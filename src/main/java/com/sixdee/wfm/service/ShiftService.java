/**
 * @author Arjun Kumbakkara[2019@sixdee|WFM|v1.0.0]
 */

package com.sixdee.wfm.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.sixdee.wfm.model.Shift;

@Service
public interface ShiftService {

	public Shift createShift(Shift shift);

	public Page<Shift> getAllShift(String page, String pageSize, String sortDirection, String sortKey, String search);

	public List<Shift> getAllShifts(String search);

	public Shift findById(Long Id);

	public Shift updateShift(String key, Long Id, Shift updateShift);

	public void deleteShift(String key, Long Id);
}
