package com.sixdee.wfm.service;
/**
* @author Arjun Kumbakkara[2019@sixdee|WFM|v1.0.0] 
*/

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.sixdee.wfm.model.Department;

@Service
public interface DepartmentService {
	public Page<Department> getAllDepartments(String page,String pageSize,String sortDirection,String sortKey,String search);
	public List<Department> getAllDepartments(String search);
	public Department createDepartment(Department department);
	public Department updateDepartment(String key,Long Id,Department departmentDetails);
	public void deleteDepartment(String key,Long Id);
	public Department findById(Long Id);
	

}
