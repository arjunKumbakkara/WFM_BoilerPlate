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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sixdee.wfm.common.CustomResponse;
import com.sixdee.wfm.common.Response;
import com.sixdee.wfm.model.Project;
import com.sixdee.wfm.service.ConsumerKeyAuthService;
import com.sixdee.wfm.service.ProjectService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author Nalini N
 *
 *         Date : 14-Mar-2019
 */
@RestController
/* @RequestMapping("/wfm") */
@SuppressWarnings({ "rawtypes", "unchecked" })
@Api(value = "Projects", description = "APIs for Projects", tags = { "Projects" })
public class ProjectController {
	public static Logger logger = LoggerFactory.getLogger(FAQController.class);

	@Autowired
	private ConsumerKeyAuthService esAuthService;
	@Autowired
	private ProjectService projectService;

	@PostMapping(value = "/api/admin/v1/project", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Add a new Project with details", notes = "This is a public API with admin right", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = HttpServletResponse.SC_CREATED, message = "A new project has been added successfully"),
			@ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Invalid Consumer Key"), @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Invalid Request") })
	ResponseEntity createProject(@RequestHeader(name = "Consumer-Key", required = false) String key, @Valid @RequestBody Project project) {
		boolean isAuth = true;
		if (key != null) {
			if (!this.esAuthService.isAdmin(key)) {
				isAuth = false;
			}
		}
		if (isAuth) {
			Boolean result = false;
			Project response = this.projectService.createProject(project);
			if (response != null)
				result = true;
			return new ResponseEntity(result, (result) ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST);

		} else
			return new ResponseEntity("{\"Status\":\"Access Denied\",\"Cause\":\"Invalid Consumer Key\"}", HttpStatus.UNAUTHORIZED);

	}

	@GetMapping(value = "/api/public/v1/project", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Return all Project with details", notes = "This is a public API", response = List.class)
	@ApiResponse(code = HttpServletResponse.SC_OK, message = "Success")
	@CustomResponse
	ResponseEntity getAllProjects(@RequestParam(value = "page", required = false) String page, @RequestParam(value = "pageSize", required = false) String pageSize,
			@RequestParam(value = "sortOrder", required = false) String sortOrder, @RequestParam(value = "sortKey", required = false) String sortKey,
			@RequestParam(value = "search", required = false) String search) {
		Page<Project> project = null;
		List<Project> projectAllLister = null;
		if (page != null && page.equalsIgnoreCase("All")) {
			projectAllLister = this.projectService.getAllProject(search);
			return new ResponseEntity(projectAllLister, HttpStatus.OK);
		} else if (page != null && pageSize != null) {
			project = this.projectService.getAllProject(page, pageSize, sortOrder, sortKey, search);
			return new ResponseEntity(project, HttpStatus.OK);
		} else {
			projectAllLister = this.projectService.getAllProject(search);
			return new ResponseEntity(projectAllLister, HttpStatus.OK);
		}
	}

	@GetMapping(value = "/api/public/v1/project/{id}")
	@ApiOperation(value = "Return a specific project", notes = "This is a public API with public right", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = HttpServletResponse.SC_NO_CONTENT, message = "The project has been returned successfully"),
			@ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Invalid Consumer Key"), @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "The project does not exist") })
	@CustomResponse
	ResponseEntity getProjectById(@RequestHeader(name = "Consumer-Key", required = false) String key, @PathVariable(value = "id") Long projectId) {
		logger.info("Recieved Project :findbyId" + projectId);
		Project project = this.projectService.findById(projectId);
		return new ResponseEntity(project, HttpStatus.OK);
	}

	@PutMapping(value = "/admin/v1/project/{projectId}", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Update a specific project.", notes = "This is an admin API", response = String.class)
	@ApiResponse(code = HttpServletResponse.SC_OK, message = "Success")
	@CustomResponse
	ResponseEntity updateProject(@RequestHeader(name = "Consumer-Key", required = false) String key, @PathVariable(value = "projectId") Long projectId, @Valid @RequestBody Project project) {
		boolean isAuth = true;
		if (key != null) {
			if (!this.esAuthService.isAdmin(key)) {
				isAuth = false;
			}
		}
		if (isAuth) {
			Boolean result = false;

			Project response = this.projectService.updateProject(key, projectId, project);
			if (response != null)
				result = true;
			return new ResponseEntity(result, (result) ? HttpStatus.OK : HttpStatus.NOT_FOUND);

		} else
			return new ResponseEntity("{\"Status\":\"Access Denied\",\"Cause\":\"Invalid Consumer Key\"}", HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping(value = "/admin/v1/project/{projectId}", produces = { MediaType.APPLICATION_JSON_VALUE }/* , consumes = { MediaType.APPLICATION_JSON_VALUE } */)
	@ApiOperation(value = " Delete a Project", notes = "This is a public API", response = String.class)
	@ApiResponse(code = HttpServletResponse.SC_OK, message = "Success")
	@CustomResponse
	ResponseEntity deleteProject(@RequestHeader(name = "Consumer-Key", required = false) String key, @PathVariable(value = "projectId") Long projectId) {
		boolean isAuth = true;
		if (key != null) {
			if (!this.esAuthService.isAdmin(key)) {
				isAuth = false;
			}
		}
		if (isAuth) {
			this.projectService.deleteProject(key, projectId);
			return new ResponseEntity(HttpStatus.OK);

		} else
			return new ResponseEntity("{\"Status\":\"Access Denied\",\"Cause\":\"Invalid Consumer Key\"}", HttpStatus.UNAUTHORIZED);

	}

}
