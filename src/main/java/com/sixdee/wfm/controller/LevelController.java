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
import com.sixdee.wfm.model.Level;
import com.sixdee.wfm.service.ConsumerKeyAuthService;
import com.sixdee.wfm.service.LevelService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author Arjun Kumbakkara[2019@sixdee|WFM|v1.0.0]
 */

@RestController
/* @RequestMapping("/wfm") */
@SuppressWarnings({ "rawtypes", "unchecked" })
@Api(value = "Levels", description = "APIs for Levels", tags = { "Levels" })
public class LevelController {
	public static Logger logger = LoggerFactory.getLogger(LevelController.class);

	@Autowired
	private ConsumerKeyAuthService esAuthService;
	@Autowired
	private LevelService levelService;

	@PostMapping(value = "/api/admin/v1/levels", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Add a new Levels with details", notes = "This is a public API with admin right", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = HttpServletResponse.SC_CREATED, message = "A new level has been added successfully"),
			@ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Invalid Consumer Key"), @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Invalid Request") })
	ResponseEntity createLevel(@RequestHeader(name = "Consumer-Key", required = false) String key, @Valid @RequestBody Level level) {
		boolean isAuth = true;
		if (key != null) {
			if (!this.esAuthService.isAdmin(key)) {
				isAuth = false;
			}
		}
		if (isAuth) {
			Boolean result = false;
			Level response = this.levelService.createLevel(level);
			if (response != null)
				result = true;
			return new ResponseEntity(result, (result) ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST);

		} else
			return new ResponseEntity("{\"Status\":\"Access Denied\",\"Cause\":\"Invalid Consumer Key\"}", HttpStatus.UNAUTHORIZED);

	}

	@GetMapping(value = "/api/public/v1/levels", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Return all Levels with details", notes = "This is a public API", response = List.class)
	@ApiResponse(code = HttpServletResponse.SC_OK, message = "Success")
	@CustomResponse
	ResponseEntity getAllLevels(@RequestParam(value = "page", required = false) String page, @RequestParam(value = "pageSize", required = false) String pageSize,
			@RequestParam(value = "sortOrder", required = false) String sortOrder, @RequestParam(value = "sortKey", required = false) String sortKey,
			@RequestParam(value = "search", required = false) String search) {
		Page<Level> levels = null;
		List<Level> levelAllLister = null;
		if (page != null && page.equalsIgnoreCase("All")) {
			levelAllLister = this.levelService.getAllLevel(search);
			return new ResponseEntity(levelAllLister, HttpStatus.OK);
		} else if (page != null && pageSize != null) {
			levels = this.levelService.getAllLevel(page, pageSize, sortOrder, sortKey, search);
			return new ResponseEntity(levels, HttpStatus.OK);
		} else {
			levelAllLister = this.levelService.getAllLevel(search);
			return new ResponseEntity(levelAllLister, HttpStatus.OK);
		}
	}


	@GetMapping(value = "/api/public/v1/levels/{id}")
	@ApiOperation(value = "Return a specific level", notes = "This is a public API with public right", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = HttpServletResponse.SC_NO_CONTENT, message = "The level has been returned successfully"),
			@ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Invalid Consumer Key"), @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "The level does not exist") })
	@CustomResponse
	ResponseEntity getLevelById(@RequestHeader(name = "Consumer-Key", required = false) String key, @PathVariable(value = "id") Long levelId) {
		logger.info("Recieved Level :findbyId" + levelId);
		Level level = this.levelService.findById(levelId);
		return new ResponseEntity(level, HttpStatus.OK);
	}

	@PutMapping(value = "/admin/v1/level/{levelId}", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Update a specific level.", notes = "This is an admin API", response = String.class)
	@ApiResponse(code = HttpServletResponse.SC_OK, message = "Success")
	@CustomResponse
	ResponseEntity updateLevel(@RequestHeader(name = "Consumer-Key", required = false) String key, @PathVariable(value = "levelId") Long levelId, @Valid @RequestBody Level level) {
		boolean isAuth = true;
		if (key != null) {
			if (!this.esAuthService.isAdmin(key)) {
				isAuth = false;
			}
		}
		if (isAuth) {
			Boolean result = false;

			Level response = this.levelService.updateLevel(key, levelId, level);
			if (response != null)
				result = true;
			return new ResponseEntity(result, (result) ? HttpStatus.OK : HttpStatus.NOT_FOUND);

		} else
			return new ResponseEntity("{\"Status\":\"Access Denied\",\"Cause\":\"Invalid Consumer Key\"}", HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping(value = "/admin/v1/level/{levelId}", produces = { MediaType.APPLICATION_JSON_VALUE }/* , consumes = { MediaType.APPLICATION_JSON_VALUE } */)
	@ApiOperation(value = " Delete a Level", notes = "This is a public API", response = String.class)
	@ApiResponse(code = HttpServletResponse.SC_OK, message = "Success")
	@CustomResponse
	ResponseEntity deleteLevel(@RequestHeader(name = "Consumer-Key", required = false) String key, @PathVariable(value = "levelId") Long levelId) {
		boolean isAuth = true;
		if (key != null) {
			if (!this.esAuthService.isAdmin(key)) {
				isAuth = false;
			}
		}
		if (isAuth) {
			this.levelService.deleteLevel(key, levelId);
			return new ResponseEntity(HttpStatus.OK);

		} else
			return new ResponseEntity("{\"Status\":\"Access Denied\",\"Cause\":\"Invalid Consumer Key\"}", HttpStatus.UNAUTHORIZED);

	}

}
