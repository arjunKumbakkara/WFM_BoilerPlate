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
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author Arjun Kumbakkara[2019@sixdee|WFM|v1.0.0]
 */
@RestController
/*@RequestMapping("/wfm")*/
@SuppressWarnings({ "rawtypes", "unchecked" })
@Api(value = "Resources", description = " APIs for Resources", tags = { "Resources" })
public class ResourceController {
	public static Logger logger = LoggerFactory.getLogger(ResourceController.class);
	@Autowired
	private ResourceService resourceService;
	@Autowired
	private ConsumerKeyAuthService esAuthService;
	/*RESOURCES FEATURE---------------------------------------------------------------------------------------------------------------------------------*/

	@GetMapping(value = "/api/public/v1/resources", produces= {MediaType.APPLICATION_JSON_VALUE})
	@ApiOperation(value="Return all Resources with details", notes="This is a public API", response=List.class)
	@ApiResponse(code = HttpServletResponse.SC_OK, message = "Success")
	@CustomResponse
	ResponseEntity getAllResources(@RequestParam(value = "page" , required =false) String page,@RequestParam(value = "pageSize", required =false) String pageSize,@RequestParam(value = "sortOrder", required =false) String sortOrder,@RequestParam(value = "sortKey", required =false) String sortKey,@RequestParam(value = "search", required =false) String search) {
		Page<Resource> resources=null;
		List<Resource> resourcesAllLister=null;
		if(page!=null && page.equalsIgnoreCase("All")) {
			resourcesAllLister=this.resourceService.getAllResources(search);
			return new ResponseEntity(resourcesAllLister, HttpStatus.OK);
		}else if(page!=null && pageSize!=null){
			resources =this.resourceService.getAllResources(page,pageSize,sortOrder,sortKey,search);
			return new ResponseEntity(resources, HttpStatus.OK);
		}else {
			resourcesAllLister=this.resourceService.getAllResources(search);
			return new ResponseEntity(resourcesAllLister, HttpStatus.OK);
		}
	}
	@PostMapping(value = "/api/admin/v1/resources", 
			produces = {MediaType.APPLICATION_JSON_VALUE}, 
			consumes = {MediaType.APPLICATION_JSON_VALUE})
	@ApiOperation(value="Add a new resource with details", notes="This is a public API with admin right", response=Response.class)
	@ApiResponses(value = { 
			@ApiResponse(code = HttpServletResponse.SC_CREATED, message = "A new resource has been added successfully"),
			@ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Invalid Consumer Key")
	})
	@CustomResponse
	ResponseEntity createResource(@RequestHeader(name="Consumer-Key", required=false) String key, @Valid @RequestBody Resource resource) {
		if (this.esAuthService.isAdmin(key)) {Boolean result=false;
		Resource response = this.resourceService.createResource(resource);
		if(response!=null)result=true;
		return new ResponseEntity(result, (result) ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND);}
		else {
			return new ResponseEntity("{\"Status\":\"Access Denied\",\"Cause\":\"Invalid Consumer Key\"}", HttpStatus.UNAUTHORIZED);
		}
	}
	@GetMapping(value = "/api/public/v1/resources/{id}")
	@ApiOperation(value="Return a specific Resource", notes="This is a public API with public right", response=Response.class)
	@ApiResponses(value = { 
			@ApiResponse(code = HttpServletResponse.SC_NO_CONTENT, message = "The resource has been returned successfully"),
			@ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Invalid Consumer Key"),
			@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "The resource does not exist")
	})
	@CustomResponse
	ResponseEntity getResourceById(@RequestHeader(name="Consumer-Key", required=false) String key,@PathVariable(value = "id") Long resourceId) {
		logger.info("Recieved Resource :findbyId"+resourceId);
		Resource resource= this.resourceService.findById(resourceId);
		return new ResponseEntity(resource, HttpStatus.OK);
	}
	@PutMapping(value = "/api/admin/v1/resources/{id}",
			produces= {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
	@ApiOperation(value="Update a specific resource.", notes="This is an admin API", response=String.class)
	@ApiResponse(code = HttpServletResponse.SC_OK, message = "Success")
	@CustomResponse
	ResponseEntity updateResource(@RequestHeader(name="Consumer-Key", required=false) String key, @PathVariable(value = "id") Long resourceId, @Valid @RequestBody Resource resourceDetails) {
		Boolean result =false;
		if (this.esAuthService.isAdmin(key)) {
			Resource response = this.resourceService.updateResource(key,resourceId,resourceDetails);
			if(response!=null)result=true;
			return new ResponseEntity(result, (result) ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND);
		}
		else {
			return new ResponseEntity("{\"Status\":\"Access Denied\",\"Cause\":\"Invalid Consumer Key\"}", HttpStatus.UNAUTHORIZED);
		}
	}
	@DeleteMapping(value = "/api/admin/v1/resources/{id}",
			produces= {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
	@ApiOperation(value=" Delete a resource", notes="This is a public API", response=String.class)
	@ApiResponse(code = HttpServletResponse.SC_OK, message = "Success")
	@CustomResponse
	ResponseEntity deleteResource(@RequestHeader(name="Consumer-Key", required=false) String key, @PathVariable(value = "id") Long resourceId) {
		if (this.esAuthService.isAdmin(key)) {
			this.resourceService.deleteResource(key,resourceId);
			Boolean result=true;
			return new ResponseEntity(result, (result) ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND);
		}
		else {
			return new ResponseEntity("{\"Status\":\"Access Denied\",\"Cause\":\"Invalid Consumer Key\"}", HttpStatus.UNAUTHORIZED);
		}
	}
	//------------------------------------------------------------------------------------------------------------------------------------------------
}
