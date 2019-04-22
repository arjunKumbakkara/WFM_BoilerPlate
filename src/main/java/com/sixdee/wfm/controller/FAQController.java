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
import com.sixdee.wfm.model.FAQ;
import com.sixdee.wfm.service.ConsumerKeyAuthService;
import com.sixdee.wfm.service.FAQService;

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
@SuppressWarnings({ "rawtypes", "unchecked" })
@Api(value = "FAQs", description = " APIs for FAQs", tags = { "FAQs" })
public class FAQController {
	public static Logger logger = LoggerFactory.getLogger(FAQController.class);

	@Autowired
	private ConsumerKeyAuthService esAuthService;

	@Autowired
	private FAQService questionService;

	@PostMapping(value = "/api/admin/v1/question", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Add a new Question with details", notes = "This is a public API with admin right", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = HttpServletResponse.SC_CREATED, message = "A new question has been added successfully"),
			@ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Invalid Consumer Key"), @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Invalid Request") })
	ResponseEntity createQuestion(@RequestHeader(name = "Consumer-Key", required = false) String key, @Valid @RequestBody FAQ question) {
		boolean isAuth = true;
		if (key != null) {
			if (!this.esAuthService.isAdmin(key)) {
				isAuth = false;
			}
		}
		if (isAuth) {
			Boolean result = false;
			FAQ response = questionService.createQuestion(question);
			if (response != null)
				result = true;
			return new ResponseEntity(result, (result) ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST);

		} else
			return new ResponseEntity("{\"Status\":\"Access Denied\",\"Cause\":\"Invalid Consumer Key\"}", HttpStatus.UNAUTHORIZED);

	}

	@GetMapping(value = "/api/public/v1/question", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Return all Question with details", notes = "This is a public API", response = List.class)
	@ApiResponse(code = HttpServletResponse.SC_OK, message = "Success")
	@CustomResponse
	ResponseEntity getAllQuestions(@RequestParam(value = "page", required = false) String page, @RequestParam(value = "pageSize", required = false) String pageSize,

			@RequestParam(value = "sortOrder", required = false) String sortOrder, @RequestParam(value = "sortKey", required = false) String sortKey,

			@RequestParam(value = "search", required = false) String search) {
		Page<FAQ> question = null;
		List<FAQ> questionAllLister = null;
		if (page != null && page.equalsIgnoreCase("All")) {
			questionAllLister = this.questionService.getAllQuestion(search);
			return new ResponseEntity(questionAllLister, HttpStatus.OK);
		} else if (page != null && pageSize != null) {
			question = this.questionService.getAllQuestion(page, pageSize, sortOrder, sortKey, search);
			return new ResponseEntity(question, HttpStatus.OK);
		} else {
			questionAllLister = this.questionService.getAllQuestion(search);
			return new ResponseEntity(questionAllLister, HttpStatus.OK);
		}
	}

	@GetMapping(value = "/api/public/v1/question/{id}")
	@ApiOperation(value = "Return a specific question", notes = "This is a public API with public right", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = HttpServletResponse.SC_NO_CONTENT, message = "The question has been returned successfully"),
			@ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Invalid Consumer Key"),
			@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "The question does not exist") })
	@CustomResponse
	ResponseEntity getQuestionById(@RequestHeader(name = "Consumer-Key", required = false) String key, @PathVariable(value = "id") Long questionId) {
		logger.info("Recieved Question :findbyId" + questionId);
		FAQ question = this.questionService.findById(questionId);
		return new ResponseEntity(question, HttpStatus.OK);
	}

	@PutMapping(value = "/admin/v1/question/{questionId}", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Update a specific question.", notes = "This is an admin API", response = String.class)
	@ApiResponse(code = HttpServletResponse.SC_OK, message = "Success")
	@CustomResponse
	ResponseEntity updateQuestion(@RequestHeader(name = "Consumer-Key", required = false) String key, @PathVariable(value = "questionId") Long questionId, @Valid @RequestBody FAQ question) {
		boolean isAuth = true;
		if (key != null) {
			if (!this.esAuthService.isAdmin(key)) {
				isAuth = false;
			}
		}
		if (isAuth) {
			Boolean result = false;

			FAQ response = this.questionService.updateQuestion(key, questionId, question);
			if (response != null)
				result = true;
			return new ResponseEntity(result, (result) ? HttpStatus.OK : HttpStatus.NOT_FOUND);

		} else
			return new ResponseEntity("{\"Status\":\"Access Denied\",\"Cause\":\"Invalid Consumer Key\"}", HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping(value = "/admin/v1/question/{questionId}", produces = { MediaType.APPLICATION_JSON_VALUE }/* , consumes = { MediaType.APPLICATION_JSON_VALUE } */)
	@ApiOperation(value = " Delete a Question", notes = "This is a public API", response = String.class)
	@ApiResponse(code = HttpServletResponse.SC_OK, message = "Success")
	@CustomResponse
	ResponseEntity deleteQuestion(@RequestHeader(name = "Consumer-Key", required = false) String key, @PathVariable(value = "questionId") Long questionId) {
		boolean isAuth = true;
		if (key != null) {
			if (!this.esAuthService.isAdmin(key)) {
				isAuth = false;
			}
		}
		if (isAuth) {
			this.questionService.deleteQuestion(key, questionId);
			return new ResponseEntity(HttpStatus.OK);

		} else
			return new ResponseEntity("{\"Status\":\"Access Denied\",\"Cause\":\"Invalid Consumer Key\"}", HttpStatus.UNAUTHORIZED);

	}

}
