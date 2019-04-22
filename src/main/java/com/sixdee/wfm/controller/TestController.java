package com.sixdee.wfm.controller;

import com.sixdee.wfm.model.Department;
import com.sixdee.wfm.repository.DepartmentRepository;

import io.swagger.annotations.Api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sixdee.wfm.common.TableNames;
import com.sixdee.wfm.exception.ResourceNotFoundException;
import javax.validation.Valid;

import java.util.Collection;
import java.util.List;
/**
* @author Arjun Kumbakkara[2019@sixdee|WFM|v1.0.0] 
*/
@RestController
@RequestMapping("/test")
@Api(value = "Dummy", description = "Test APIs", tags = { "Test" })
public class TestController {
	public static Logger logger = LoggerFactory.getLogger(TestController.class);
    @Autowired
    DepartmentRepository departmentRepository;
    @GetMapping("/departments")
    public Collection<Department> getAllDepartments() {
    	//List<Department> lister =departmentRepository.findAll();
    	//List<Department> lister=departmentRepository.findAllActiveUsersNative();
    	Collection<Department> lister=departmentRepository.findAllActiveUsersNative();
    	logger.info("Size"+lister.size());
        return lister;
    }
    @PostMapping("/departments")
    public Department createDepartment(@Valid @RequestBody Department department) {
    	logger.info("Creating Deparments");
        return departmentRepository.save(department);
    }
    @GetMapping("/departments/{id}")
    public Department getDepartmentById(@PathVariable(value = "id") Long departmentId) {
        return departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department", "id", departmentId));
    }
    @PutMapping("/departments/{id}")
    public Department updateDepartment(@PathVariable(value = "id") Long departmentId,
                                           @Valid @RequestBody Department departmentDetails) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department", "id", departmentId));
        department.setDepartmentDesc(departmentDetails.getDepartmentDesc());
        department.setDepartmentName(departmentDetails.getDepartmentName());
        Department updatedDepartment = departmentRepository.save(department);
        return updatedDepartment;
    }
    @DeleteMapping("/departments/{id}")
    public ResponseEntity<?> deleteDepartment(@PathVariable(value = "id") Long departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department", "id", departmentId));
        departmentRepository.delete(department);
        return ResponseEntity.ok().build();
    }
}
