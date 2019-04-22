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
import com.sixdee.wfm.model.FleetManagement;
import com.sixdee.wfm.service.ConsumerKeyAuthService;
import com.sixdee.wfm.service.FleetManagementService;

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
@Api(value = "FleetManagement", description = " APIs for FleetManagement", tags = { "FleetManagement" })
public class FleetManagementController {
	public static Logger logger = LoggerFactory.getLogger(FleetManagementController.class);

	@Autowired
	private ConsumerKeyAuthService esAuthService;
	@Autowired
	private FleetManagementService fleetManagementService;

	@PostMapping(value = "/api/admin/v1/fleetManagement", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Add a new FleetManagement with details", notes = "This is a public API with admin right", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = HttpServletResponse.SC_CREATED, message = "A new fleetManagement has been added successfully"),
			@ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Invalid Consumer Key"), @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Invalid Request") })
	ResponseEntity createFleetManagement(@RequestHeader(name = "Consumer-Key", required = false) String key, @Valid @RequestBody FleetManagement fleetManagement) {
		boolean isAuth = true;
		if (key != null) {
			if (!this.esAuthService.isAdmin(key)) {
				isAuth = false;
			}
		}
		if (isAuth) {
			Boolean result = false;

			// Validate the request body
			// validateBean.validateAPIFileParameters(commonRequestDTO, requestTrackerId);

			FleetManagement response = this.fleetManagementService.createFleetManagement(fleetManagement);
			if (response != null)
				result = true;
			return new ResponseEntity(result, (result) ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST);

		} else
			return new ResponseEntity("{\"Status\":\"Access Denied\",\"Cause\":\"Invalid Consumer Key\"}", HttpStatus.UNAUTHORIZED);

	}

	@GetMapping(value = "/api/public/v1/fleetManagement", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Return all FleetManagement with details", notes = "This is a public API", response = List.class)
	@ApiResponse(code = HttpServletResponse.SC_OK, message = "Success")
	@CustomResponse
	ResponseEntity getAllFleetManagement(@RequestParam(value = "page", required = false) String page, @RequestParam(value = "pageSize", required = false) String pageSize,
			@RequestParam(value = "sortOrder", required = false) String sortOrder, @RequestParam(value = "sortKey", required = false) String sortKey,
			@RequestParam(value = "search", required = false) String search) {
		Page<FleetManagement> fleetManagement = null;
		List<FleetManagement> fleetManagementAllLister = null;
		if (page != null && page.equalsIgnoreCase("All")) {
			fleetManagementAllLister = this.fleetManagementService.getAllFleetManagement(search);
			return new ResponseEntity(fleetManagementAllLister, HttpStatus.OK);
		} else if (page != null && pageSize != null) {
			fleetManagement = this.fleetManagementService.getAllFleetManagement(page, pageSize, sortOrder, sortKey, search);
			return new ResponseEntity(fleetManagement, HttpStatus.OK);
		} else {
			fleetManagementAllLister = this.fleetManagementService.getAllFleetManagement(search);
			return new ResponseEntity(fleetManagementAllLister, HttpStatus.OK);
		}
	}

	@GetMapping(value = "/api/public/v1/fleetManagement/{id}")
	@ApiOperation(value = "Return a specific fleetManagement", notes = "This is a public API with public right", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = HttpServletResponse.SC_NO_CONTENT, message = "The fleetManagement has been returned successfully"),
			@ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Invalid Consumer Key"),
			@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "The fleetManagement does not exist") })
	@CustomResponse
	ResponseEntity getFleetManagementById(@RequestHeader(name = "Consumer-Key", required = false) String key, @PathVariable(value = "id") Long fleetManagementId) {
		logger.info("Recieved FleetManagement :findbyId" + fleetManagementId);
		FleetManagement fleetManagement = this.fleetManagementService.findById(fleetManagementId);
		return new ResponseEntity(fleetManagement, HttpStatus.OK);
	}

	@PutMapping(value = "/admin/v1/fleetManagement/{fleetManagementId}", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Update a specific fleetManagement.", notes = "This is an admin API", response = String.class)
	@ApiResponse(code = HttpServletResponse.SC_OK, message = "Success")
	@CustomResponse
	ResponseEntity updateFleetManagement(@RequestHeader(name = "Consumer-Key", required = false) String key, @PathVariable(value = "fleetManagementId") Long fleetManagementId,
			@Valid @RequestBody FleetManagement fleetManagement) {
		boolean isAuth = true;
		if (key != null) {
			if (!this.esAuthService.isAdmin(key)) {
				isAuth = false;
			}
		}
		if (isAuth) {
			Boolean result = false;

			FleetManagement response = this.fleetManagementService.updateFleetManagement(key, fleetManagementId, fleetManagement);
			if (response != null)
				result = true;
			return new ResponseEntity(result, (result) ? HttpStatus.OK : HttpStatus.NOT_FOUND);

		} else
			return new ResponseEntity("{\"Status\":\"Access Denied\",\"Cause\":\"Invalid Consumer Key\"}", HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping(value = "/admin/v1/fleetManagement/{fleetManagementId}", produces = { MediaType.APPLICATION_JSON_VALUE }/* , consumes = { MediaType.APPLICATION_JSON_VALUE } */)
	@ApiOperation(value = " Delete a FleetManagement", notes = "This is a public API", response = String.class)
	@ApiResponse(code = HttpServletResponse.SC_OK, message = "Success")
	@CustomResponse
	ResponseEntity deleteFleetManagement(@RequestHeader(name = "Consumer-Key", required = false) String key, @PathVariable(value = "fleetManagementId") Long fleetManagementId) {
		boolean isAuth = true;
		if (key != null) {
			if (!this.esAuthService.isAdmin(key)) {
				isAuth = false;
			}
		}
		if (isAuth) {
			this.fleetManagementService.deleteFleetManagement(key, fleetManagementId);
			return new ResponseEntity(HttpStatus.OK);

		} else
			return new ResponseEntity("{\"Status\":\"Access Denied\",\"Cause\":\"Invalid Consumer Key\"}", HttpStatus.UNAUTHORIZED);

	}

}
