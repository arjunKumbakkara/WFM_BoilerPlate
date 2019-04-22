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
import com.sixdee.wfm.model.Shift;
import com.sixdee.wfm.service.ConsumerKeyAuthService;
import com.sixdee.wfm.service.ShiftService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author Arjun Kumbakkara[2019@sixdee|WFM|v1.0.0]
 */

@RestController
@SuppressWarnings({ "rawtypes", "unchecked" })
@Api(value = "Shifts", description = "APIs for Shifts:This API will also supply all Resources To Shift relation", tags = { "Shifts" })
public class ShiftController {
	public static Logger logger = LoggerFactory.getLogger(ShiftController.class);

	@Autowired
	private ConsumerKeyAuthService esAuthService;
	@Autowired
	private ShiftService shiftService;

	@PostMapping(value = "/api/admin/v1/shifts", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Add a new Shift with details", notes = "This is a public API with admin right", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = HttpServletResponse.SC_CREATED, message = "A new shift has been added successfully"),
			@ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Invalid Consumer Key"), @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Invalid Request") })
	ResponseEntity createShift(@RequestHeader(name = "Consumer-Key", required = false) String key, @Valid @RequestBody Shift shift) {
		boolean isAuth = true;
		if (key != null) {
			if (!this.esAuthService.isAdmin(key)) {
				isAuth = false;
			}
		}
		if (isAuth) {
			Boolean result = false;
			Shift response = this.shiftService.createShift(shift);
			if (response != null)
				result = true;
			return new ResponseEntity(result, (result) ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST);

		} else
			return new ResponseEntity("{\"Status\":\"Access Denied\",\"Cause\":\"Invalid Consumer Key\"}", HttpStatus.UNAUTHORIZED);

	}

	@GetMapping(value = "/api/public/v1/shifts", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Return all Levels with details", notes = "This is a public API", response = List.class)
	@ApiResponse(code = HttpServletResponse.SC_OK, message = "Success")
	@CustomResponse
	ResponseEntity getAllShifts(@RequestParam(value = "page", required = false) String page, @RequestParam(value = "pageSize", required = false) String pageSize,
			@RequestParam(value = "sortOrder", required = false) String sortOrder, @RequestParam(value = "sortKey", required = false) String sortKey,
			@RequestParam(value = "search", required = false) String search) {
		Page<Shift> shifts = null;
		List<Shift> shiftAllLister = null;
		if (page != null && page.equalsIgnoreCase("All")) {
			shiftAllLister = this.shiftService.getAllShifts(search);
			return new ResponseEntity(shiftAllLister, HttpStatus.OK);
		} else if (page != null && pageSize != null) {
			shifts = this.shiftService.getAllShift(page, pageSize, sortOrder, sortKey, search);
			return new ResponseEntity(shifts, HttpStatus.OK);
		} else {
			shiftAllLister = this.shiftService.getAllShifts(search);
			return new ResponseEntity(shiftAllLister, HttpStatus.OK);
		}
	}

	@GetMapping(value = "/api/public/v1/shifts/{id}")
	@ApiOperation(value = "Return a specific shift and related resources", notes = "This is a public API with public right", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = HttpServletResponse.SC_NO_CONTENT, message = "The resource has been returned successfully"),
			@ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Invalid Consumer Key"),
			@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "The resource does not exist") })
	@CustomResponse
	ResponseEntity getSkillById(@RequestHeader(name = "Consumer-Key", required = false) String key, @PathVariable(value = "id") Long skillId) {
		logger.info("Recieved Resource :findbyId" + skillId);
		Shift skill = this.shiftService.findById(skillId);
		return new ResponseEntity(skill, HttpStatus.OK);
	}

	@PutMapping(value = "/admin/v1/shift/{shiftId}", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Update a specific shift.", notes = "This is an admin API", response = String.class)
	@ApiResponse(code = HttpServletResponse.SC_OK, message = "Success")
	@CustomResponse
	ResponseEntity updateShift(@RequestHeader(name = "Consumer-Key", required = false) String key, @PathVariable(value = "shiftId") Long shiftId, @Valid @RequestBody Shift shift) {
		boolean isAuth = true;
		if (key != null) {
			if (!this.esAuthService.isAdmin(key)) {
				isAuth = false;
			}
		}
		if (isAuth) {
			Boolean result = false;

			Shift response = this.shiftService.updateShift(key, shiftId, shift);
			if (response != null)
				result = true;
			return new ResponseEntity(result, (result) ? HttpStatus.OK : HttpStatus.NOT_FOUND);

		} else
			return new ResponseEntity("{\"Status\":\"Access Denied\",\"Cause\":\"Invalid Consumer Key\"}", HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping(value = "/admin/v1/shift/{shiftId}", produces = { MediaType.APPLICATION_JSON_VALUE }/* , consumes = { MediaType.APPLICATION_JSON_VALUE } */)
	@ApiOperation(value = " Delete a Shift", notes = "This is a public API", response = String.class)
	@ApiResponse(code = HttpServletResponse.SC_OK, message = "Success")
	@CustomResponse
	ResponseEntity deleteShift(@RequestHeader(name = "Consumer-Key", required = false) String key, @PathVariable(value = "shiftId") Long shiftId) {
		boolean isAuth = true;
		if (key != null) {
			if (!this.esAuthService.isAdmin(key)) {
				isAuth = false;
			}
		}
		if (isAuth) {
			this.shiftService.deleteShift(key, shiftId);
			return new ResponseEntity(HttpStatus.OK);

		} else
			return new ResponseEntity("{\"Status\":\"Access Denied\",\"Cause\":\"Invalid Consumer Key\"}", HttpStatus.UNAUTHORIZED);

	}
}
