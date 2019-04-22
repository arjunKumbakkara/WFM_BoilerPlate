package com.sixdee.wfm.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import com.sixdee.wfm.model.CategoryProjectMapper;
import com.sixdee.wfm.service.CategoryProjectMapperService;
import com.sixdee.wfm.service.ConsumerKeyAuthService;

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
@Api(value = "CategoryProjectMapper", description = " APIs for CategoryProjectMapper", tags = { "CategoryProjectMapper" })
public class CategoryProjectMapperController {
	public static Logger logger = LogManager.getLogger(CategoryProjectMapperController.class);

	@Autowired
	private ConsumerKeyAuthService esAuthService;
	@Autowired
	private CategoryProjectMapperService categoryProjectMapperService;

	@PostMapping(value = "/api/admin/v1/categoryProjectMapper", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Add a new CategoryProjectMapper with details", notes = "This is a public API with admin right", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = HttpServletResponse.SC_CREATED, message = "A new categoryProjectMapper has been added successfully"),
			@ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Invalid Consumer Key"), @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Invalid Request") })
	ResponseEntity createCategoryProjectMapper(@RequestHeader(name = "Consumer-Key", required = false) String key, @Valid @RequestBody CategoryProjectMapper categoryProjectMapper) {
		boolean isAuth = true;
		if (key != null) {
			if (!this.esAuthService.isAdmin(key)) {
				isAuth = false;
			}
		}
		if (isAuth) {
			Boolean result = false;
			CategoryProjectMapper response = this.categoryProjectMapperService.createCategoryProjectMapper(categoryProjectMapper);
			if (response != null)
				result = true;
			return new ResponseEntity(result, (result) ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST);

		} else
			return new ResponseEntity("{\"Status\":\"Access Denied\",\"Cause\":\"Invalid Consumer Key\"}", HttpStatus.UNAUTHORIZED);

	}

	@GetMapping(value = "/api/public/v1/categoryProjectMapper", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Return all CategoryProjectMapper with details", notes = "This is a public API", response = List.class)
	@ApiResponse(code = HttpServletResponse.SC_OK, message = "Success")
	@CustomResponse
	ResponseEntity getAllCategoryProjectMapper(@RequestParam(value = "page", required = false) String page, @RequestParam(value = "pageSize", required = false) String pageSize,
			@RequestParam(value = "sortOrder", required = false) String sortOrder, @RequestParam(value = "sortKey", required = false) String sortKey,
			@RequestParam(value = "search", required = false) String search) {
		Page<CategoryProjectMapper> categoryProjectMapper = null;
		List<CategoryProjectMapper> categoryProjectMapperAllLister = null;
		if (page != null && page.equalsIgnoreCase("All")) {
			categoryProjectMapperAllLister = this.categoryProjectMapperService.getAllCategoryProjectMapper(search);
			return new ResponseEntity(categoryProjectMapperAllLister, HttpStatus.OK);
		} else if (page != null && pageSize != null) {
			categoryProjectMapper = this.categoryProjectMapperService.getAllCategoryProjectMapper(page, pageSize, sortOrder, sortKey, search);
			return new ResponseEntity(categoryProjectMapper, HttpStatus.OK);
		} else {
			categoryProjectMapperAllLister = this.categoryProjectMapperService.getAllCategoryProjectMapper(search);
			return new ResponseEntity(categoryProjectMapperAllLister, HttpStatus.OK);
		}
	}

	@GetMapping(value = "/api/public/v1/categoryProjectMapper/{id}")
	@ApiOperation(value = "Return a specific categoryProjectMapper", notes = "This is a public API with public right", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = HttpServletResponse.SC_NO_CONTENT, message = "The categoryProjectMapper has been returned successfully"),
			@ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Invalid Consumer Key"),
			@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "The categoryProjectMapper does not exist") })
	@CustomResponse
	ResponseEntity getCategoryProjectMapperById(@RequestHeader(name = "Consumer-Key", required = false) String key, @PathVariable(value = "id") Long categoryProjectMapperId) {
		logger.info("Recieved CategoryProjectMapper :findbyId" + categoryProjectMapperId);
		CategoryProjectMapper categoryProjectMapper = this.categoryProjectMapperService.findById(categoryProjectMapperId);
		return new ResponseEntity(categoryProjectMapper, HttpStatus.OK);
	}

	@PutMapping(value = "/admin/v1/categoryProjectMapper/{categoryProjectMapperId}", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Update a specific categoryProjectMapper.", notes = "This is an admin API", response = String.class)
	@ApiResponse(code = HttpServletResponse.SC_OK, message = "Success")
	@CustomResponse
	ResponseEntity updateCategoryProjectMapper(@RequestHeader(name = "Consumer-Key", required = false) String key, @PathVariable(value = "categoryProjectMapperId") Long categoryProjectMapperId,
			@Valid @RequestBody CategoryProjectMapper categoryProjectMapper) {
		boolean isAuth = true;
		if (key != null) {
			if (!this.esAuthService.isAdmin(key)) {
				isAuth = false;
			}
		}
		if (isAuth) {
			Boolean result = false;

			CategoryProjectMapper response = this.categoryProjectMapperService.updateCategoryProjectMapper(key, categoryProjectMapperId, categoryProjectMapper);
			if (response != null)
				result = true;
			return new ResponseEntity(result, (result) ? HttpStatus.OK : HttpStatus.NOT_FOUND);

		} else
			return new ResponseEntity("{\"Status\":\"Access Denied\",\"Cause\":\"Invalid Consumer Key\"}", HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping(value = "/admin/v1/categoryProjectMapper/{categoryProjectMapperId}", produces = { MediaType.APPLICATION_JSON_VALUE }/* , consumes = { MediaType.APPLICATION_JSON_VALUE } */)
	@ApiOperation(value = " Delete a CategoryProjectMapper", notes = "This is a public API", response = String.class)
	@ApiResponse(code = HttpServletResponse.SC_OK, message = "Success")
	@CustomResponse
	ResponseEntity deleteCategoryProjectMapper(@RequestHeader(name = "Consumer-Key", required = false) String key, @PathVariable(value = "categoryProjectMapperId") Long categoryProjectMapperId) {
		boolean isAuth = true;
		if (key != null) {
			if (!this.esAuthService.isAdmin(key)) {
				isAuth = false;
			}
		}
		if (isAuth) {
			this.categoryProjectMapperService.deleteCategoryProjectMapper(key, categoryProjectMapperId);
			return new ResponseEntity(HttpStatus.OK);

		} else
			return new ResponseEntity("{\"Status\":\"Access Denied\",\"Cause\":\"Invalid Consumer Key\"}", HttpStatus.UNAUTHORIZED);

	}

}
