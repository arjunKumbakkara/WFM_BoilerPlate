package com.sixdee.wfm.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import com.sixdee.wfm.common.CustomResponse;
import com.sixdee.wfm.common.Response;
import com.sixdee.wfm.model.Category;
import com.sixdee.wfm.service.CategoryService;
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
@Api(value = "Categories", description = "APIs for Categories", tags = { "Categories" })
public class CategoryController {
	public static Logger logger = LoggerFactory.getLogger(CategoryController.class);

	@Autowired
	private ConsumerKeyAuthService esAuthService;
	@Autowired
	private CategoryService categoryService;

	@PostMapping(value = "/api/admin/v1/category", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Add a new Category with details [If Its Parent Category Remove Parent category Id from request]", notes = "This is a public API with admin right", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = HttpServletResponse.SC_CREATED, message = "A new category has been added successfully"),
			@ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Invalid Consumer Key"), @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Invalid Request") })
	ResponseEntity createCategory(@RequestHeader(name = "Consumer-Key", required = false) String key, @Valid @RequestBody Category category) {
		boolean isAuth = true;
		if (key != null) {
			if (!this.esAuthService.isAdmin(key)) {
				isAuth = false;
			}
		}
		if (isAuth) {
			Boolean result = false;
			Category response = this.categoryService.createCategory(category);
			if (response != null)
				result = true;
			return new ResponseEntity(result, (result) ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST);

		} else
			return new ResponseEntity("{\"Status\":\"Access Denied\",\"Cause\":\"Invalid Consumer Key\"}", HttpStatus.UNAUTHORIZED);

	}

	@GetMapping(value = "/api/public/v1/category", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Return all Category with details", notes = "This is a public API", response = List.class)
	@ApiResponse(code = HttpServletResponse.SC_OK, message = "Success")
	@CustomResponse
	ResponseEntity getAllCategorys() {
		// Page<Category> category = null;
		List<Category> categoryAllLister = null;
		/*
		 * if (page != null && page.equalsIgnoreCase("All")) { categoryAllLister = this.categoryService.getAllCategory(search); return new ResponseEntity(categoryAllLister, HttpStatus.OK); } else if
		 * (page != null && pageSize != null) { category = this.categoryService.getAllCategory(page, pageSize, sortOrder, sortKey, search); return new ResponseEntity(category, HttpStatus.OK); } else {
		 */
		categoryAllLister = this.categoryService.getAllCategory();
		return new ResponseEntity(categoryAllLister, HttpStatus.OK);
		/* } */
	}

	@GetMapping(value = "/api/public/v1/category/{id}")
	@ApiOperation(value = "Return a specific category", notes = "This is a public API with public right", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = HttpServletResponse.SC_NO_CONTENT, message = "The category has been returned successfully"),
			@ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Invalid Consumer Key"),
			@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "The category does not exist") })
	@CustomResponse
	ResponseEntity getCategoryById(@RequestHeader(name = "Consumer-Key", required = false) String key, @PathVariable(value = "id") Long categoryId) {
		logger.info("Recieved Category :findbyId" + categoryId);
		Category category = this.categoryService.findById(categoryId);
		return new ResponseEntity(category, HttpStatus.OK);
	}

	@PutMapping(value = "/admin/v1/category/{categoryId}", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Update a specific category.", notes = "This is an admin API", response = String.class)
	@ApiResponse(code = HttpServletResponse.SC_OK, message = "Success")
	@CustomResponse
	ResponseEntity updateCategory(@RequestHeader(name = "Consumer-Key", required = false) String key, @PathVariable(value = "categoryId") Long categoryId, @Valid @RequestBody Category category) {
		boolean isAuth = true;
		if (key != null) {
			if (!this.esAuthService.isAdmin(key)) {
				isAuth = false;
			}
		}
		if (isAuth) {
			Boolean result = false;

			Category response = this.categoryService.updateCategory(key, categoryId, category);
			if (response != null)
				result = true;
			return new ResponseEntity(result, (result) ? HttpStatus.OK : HttpStatus.NOT_FOUND);

		} else
			return new ResponseEntity("{\"Status\":\"Access Denied\",\"Cause\":\"Invalid Consumer Key\"}", HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping(value = "/admin/v1/category/{categoryId}", produces = { MediaType.APPLICATION_JSON_VALUE }/* , consumes = { MediaType.APPLICATION_JSON_VALUE } */)
	@ApiOperation(value = " Delete a Category", notes = "This is a public API", response = String.class)
	@ApiResponse(code = HttpServletResponse.SC_OK, message = "Success")
	@CustomResponse
	ResponseEntity deleteCategory(@RequestHeader(name = "Consumer-Key", required = false) String key, @PathVariable(value = "categoryId") Long categoryId) {
		boolean isAuth = true;
		if (key != null) {
			if (!this.esAuthService.isAdmin(key)) {
				isAuth = false;
			}
		}
		if (isAuth) {
			this.categoryService.deleteCategory(key, categoryId);
			return new ResponseEntity(HttpStatus.OK);

		} else
			return new ResponseEntity("{\"Status\":\"Access Denied\",\"Cause\":\"Invalid Consumer Key\"}", HttpStatus.UNAUTHORIZED);

	}

}
