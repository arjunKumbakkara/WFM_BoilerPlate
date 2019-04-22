package com.sixdee.wfm.service.impl;
/**
 * @author Arjun Kumbakkara[2019@sixdee|WFM|v1.0.0] 
 */

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.sixdee.wfm.common.PaginationBaseImpl;
import com.sixdee.wfm.common.WfmBeanUtils;
import com.sixdee.wfm.exception.DataViolationException;
import com.sixdee.wfm.exception.ResourceNotFoundException;
import com.sixdee.wfm.model.Department;
import com.sixdee.wfm.repository.DepartmentRepository;
import com.sixdee.wfm.service.DepartmentService;

@Component /* ALL LOGIC GO HERE */
public class DepartmentServiceImpl extends PaginationBaseImpl implements DepartmentService {
	public static Logger logger = LoggerFactory.getLogger(DepartmentServiceImpl.class);

	/** Author Note: All Custom implementations go here and DepartmentService its abstraction. */
	@Autowired
	private DepartmentRepository departmentRepo;
	@Autowired
	private WfmBeanUtils wfmBeanUtils;

	@Override /* @Pageable */
	public Page<Department> getAllDepartments(String page, String pageSize, String sortDirection, String sortKey, String search) {
		Page<Department> lister = departmentRepo.findAll(departmentRepo.textInAllColumns(search), gotoPageWithConstraints(page, pageSize, sortDirection, sortKey, Department.class));
		return lister;
	}

	@Override
	public List<Department> getAllDepartments(String search) {
		List<Department> departments = null;
		if (search != null) {
			departments = departmentRepo.findAll(departmentRepo.textInAllColumns(search));
		} else {
			departments = departmentRepo.findAll();
		}
		return departments;
	}

	@Override
	public Department createDepartment(Department department) {
		return this.departmentRepo.save(this.wfmBeanUtils.map(department, Department.class));
	}

	@Override
	public Department updateDepartment(String key, Long Id, Department departmentDetails) {
		departmentRepo.findById(Id).orElseThrow(() -> new ResourceNotFoundException("Department", "id", Id));
		// department.setDepartmentDesc(departmentDetails.getDepartmentDesc());
		departmentDetails.setDepartmentId(Id);
		Department updatedDepartment = departmentRepo.save(departmentDetails);
		return updatedDepartment;
	}

	@Override
	public void deleteDepartment(String key, Long Id) {
		Department department = departmentRepo.findById(Id).orElseThrow(() -> new ResourceNotFoundException("Department", "id", Id));
		try {
			departmentRepo.delete(department);
		} catch (Exception e) {
			logger.error("Exception raised while deleting the entry in table ");
			throw new DataViolationException("Departemt");
		}
	}

	@Override
	public Department findById(Long Id) {
		Department department = departmentRepo.findById(Id).orElseThrow(() -> new ResourceNotFoundException("Department", "id", Id));
		return department;
	}

}
