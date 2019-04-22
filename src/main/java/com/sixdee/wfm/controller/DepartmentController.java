package com.sixdee.wfm.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sixdee.wfm.WFMApplication;
import com.sixdee.wfm.common.CustomResponse;
import com.sixdee.wfm.common.Response;
import com.sixdee.wfm.model.Department;
import com.sixdee.wfm.model.Resource;
import com.sixdee.wfm.model.Skill;
import com.sixdee.wfm.service.ConsumerKeyAuthService;
import com.sixdee.wfm.service.DepartmentService;
import com.sixdee.wfm.service.ResourceService;
import com.sixdee.wfm.service.SkillService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author Arjun Kumbakkara[2019@sixdee|WFM|v1.0.0]
 */
@RestController
/*@RequestMapping("/wfm")*/
@SuppressWarnings({ "rawtypes", "unchecked" })
@Api(value = "Depatments", description = "APIs for Department", tags = { "Departments" })
public class DepartmentController {
	public static Logger logger = LoggerFactory.getLogger(DepartmentController.class);
	
	@Autowired private DepartmentService departmentService;
	@Autowired private ConsumerKeyAuthService esAuthService;
	/* DEPARTMENT FEATURE--------------------------------------------------------------------------------------------------------------------------------- */
	@GetMapping(value = "/api/public/v1/departments", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Get paginated list of Departments" ,tags = { "Departments" }, notes = "This is a public API", response = List.class)
	@ApiResponse(code = HttpServletResponse.SC_OK, message = "Success")
	@CustomResponse
	@ApiImplicitParams(
		      @ApiImplicitParam(name="departmentName", value="asdasdasda") 
		  )
	ResponseEntity getAllDepartments(@RequestParam(value = "page", required = false) String page, @RequestParam(value = "pageSize", required = false) String pageSize,
			@RequestParam(value = "sortOrder", required = false) String sortOrder, @RequestParam(value = "sortKey", required = false) String sortKey,
			@RequestParam(value = "search", required = false) String search) {
		Page<Department> departments = null;
		List<Department> departmentsAllLister = null;
		if (page != null && page.equalsIgnoreCase("All")) {
			departmentsAllLister = this.departmentService.getAllDepartments(search);
			return new ResponseEntity(departmentsAllLister, HttpStatus.OK);
		} else if (page != null && pageSize != null) {
			departments = this.departmentService.getAllDepartments(page, pageSize, sortOrder, sortKey, search);
			return new ResponseEntity(departments, HttpStatus.OK);
		} else {
			departmentsAllLister = this.departmentService.getAllDepartments(search);
			return new ResponseEntity(departmentsAllLister, HttpStatus.OK);
		}
	}

	@PostMapping(value = "/api/admin/v1/departments", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Add a new Department with details", notes = "This is a public API with admin right", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = HttpServletResponse.SC_CREATED, message = "A new department has been added successfully"),
			@ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Invalid Consumer Key") })
	@CustomResponse
	ResponseEntity createDepartment(@RequestHeader(name = "Consumer-Key", required = false) String key, @Valid @RequestBody Department department) {
		if (this.esAuthService.isAdmin(key)) {
			Boolean result = false;
			Department response = this.departmentService.createDepartment(department);
			if (response != null)
				result = true;
			return new ResponseEntity(result, (result) ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity("{\"Status\":\"Access Denied\",\"Cause\":\"Invalid Consumer Key\"}", HttpStatus.UNAUTHORIZED);
		}
	}

	@GetMapping(value = "/api/public/v1/departments/{id}")
	@ApiOperation(value = "Return a specific Department", notes = "This is a public API with public right", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = HttpServletResponse.SC_NO_CONTENT, message = "The department has been returned successfully"),
			@ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Invalid Consumer Key"),
			@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "The Department does not exist") })
	@CustomResponse
	ResponseEntity getDepartmentById(@RequestHeader(name = "Consumer-Key", required = false) String key, @PathVariable(value = "id") Long departmentId) {
		logger.info("Recieved Department :findbyId" + departmentId);
		Department department = this.departmentService.findById(departmentId);
		return new ResponseEntity(department, HttpStatus.OK);
	}

	@PutMapping(value = "/api/admin/v1/departments/{id}", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Update a specific department.", notes = "This is an admin API", response = String.class)
	@ApiResponse(code = HttpServletResponse.SC_OK, message = "Success")
	@CustomResponse
	ResponseEntity updateDepartment(@RequestHeader(name = "Consumer-Key", required = false) String key, @PathVariable(value = "id") Long departmentId,
			@Valid @RequestBody Department departmentDetails) {
		Boolean result = false;
		if (this.esAuthService.isAdmin(key)) {
			Department response = this.departmentService.updateDepartment(key, departmentId, departmentDetails);
			if (response != null)
				result = true;
			return new ResponseEntity(result, (result) ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity("{\"Status\":\"Access Denied\",\"Cause\":\"Invalid Consumer Key\"}", HttpStatus.UNAUTHORIZED);
		}
	}

	@DeleteMapping(value = "/api/admin/v1/departments/{id}", produces = { MediaType.APPLICATION_JSON_VALUE }/*, consumes = { MediaType.APPLICATION_JSON_VALUE }*/)
	@ApiOperation(value = " Delete a department", notes = "This is a public API", response = String.class)
	@ApiResponse(code = HttpServletResponse.SC_OK, message = "Success")
	@CustomResponse
	ResponseEntity deleteDepartment(@RequestHeader(name = "Consumer-Key", required = false) String key, @PathVariable(value = "id") Long departmentId) {
		if (this.esAuthService.isAdmin(key)) {
			this.departmentService.deleteDepartment(key, departmentId);
			Boolean result = true;
			return new ResponseEntity(result, (result) ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity("{\"Status\":\"Access Denied\",\"Cause\":\"Invalid Consumer Key\"}", HttpStatus.UNAUTHORIZED);
		}
	}
	//------------------------------------------------------------------------------------------------------------------------------------------------
}
