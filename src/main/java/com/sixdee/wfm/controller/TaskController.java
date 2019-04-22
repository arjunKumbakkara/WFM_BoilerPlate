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
import com.sixdee.wfm.model.Task;
import com.sixdee.wfm.service.ConsumerKeyAuthService;
import com.sixdee.wfm.service.TaskService;

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
@Api(value = "Tasks", description = " APIs for Tasks", tags = { "Tasks" })
public class TaskController {
	public static Logger logger = LoggerFactory.getLogger(TaskController.class);

	@Autowired
	private ConsumerKeyAuthService esAuthService;
	@Autowired
	private TaskService taskService;

	@PostMapping(value = "/api/admin/v1/task", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Add a new Task with details", notes = "This is a public API with admin right", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = HttpServletResponse.SC_CREATED, message = "A new task has been added successfully"),
			@ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Invalid Consumer Key"), @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Invalid Request") })
	ResponseEntity createTask(@RequestHeader(name = "Consumer-Key", required = false) String key, @Valid @RequestBody Task task) {
		boolean isAuth = true;
		if (key != null) {
			if (!this.esAuthService.isAdmin(key)) {
				isAuth = false;
			}
		}
		if (isAuth) {
			Boolean result = false;
			Task response = this.taskService.createTask(task);
			if (response != null)
				result = true;
			return new ResponseEntity(result, (result) ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST);

		} else
			return new ResponseEntity("{\"Status\":\"Access Denied\",\"Cause\":\"Invalid Consumer Key\"}", HttpStatus.UNAUTHORIZED);

	}

	@GetMapping(value = "/api/public/v1/task", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Return all Task with details", notes = "This is a public API", response = List.class)
	@ApiResponse(code = HttpServletResponse.SC_OK, message = "Success")
	@CustomResponse
	ResponseEntity getAllTasks(@RequestParam(value = "page", required = false) String page, @RequestParam(value = "pageSize", required = false) String pageSize,
			@RequestParam(value = "sortOrder", required = false) String sortOrder, @RequestParam(value = "sortKey", required = false) String sortKey,
			@RequestParam(value = "search", required = false) String search) {
		Page<Task> task = null;
		List<Task> taskAllLister = null;
		if (page != null && page.equalsIgnoreCase("All")) {
			taskAllLister = this.taskService.getAllTask(search);
			return new ResponseEntity(taskAllLister, HttpStatus.OK);
		} else if (page != null && pageSize != null) {
			task = this.taskService.getAllTask(page, pageSize, sortOrder, sortKey, search);
			return new ResponseEntity(task, HttpStatus.OK);
		} else {
			taskAllLister = this.taskService.getAllTask(search);
			return new ResponseEntity(taskAllLister, HttpStatus.OK);
		}
	}

	@GetMapping(value = "/api/public/v1/task/{id}")
	@ApiOperation(value = "Return a specific task", notes = "This is a public API with public right", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = HttpServletResponse.SC_NO_CONTENT, message = "The task has been returned successfully"),
			@ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Invalid Consumer Key"), @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "The task does not exist") })
	@CustomResponse
	ResponseEntity getTaskById(@RequestHeader(name = "Consumer-Key", required = false) String key, @PathVariable(value = "id") Long taskId) {
		logger.info("Recieved Task :findbyId" + taskId);
		Task task = this.taskService.findById(taskId);
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@PutMapping(value = "/admin/v1/task/{taskId}", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Update a specific task.", notes = "This is an admin API", response = String.class)
	@ApiResponse(code = HttpServletResponse.SC_OK, message = "Success")
	@CustomResponse
	ResponseEntity updateTask(@RequestHeader(name = "Consumer-Key", required = false) String key, @PathVariable(value = "taskId") Long taskId, @Valid @RequestBody Task task) {
		boolean isAuth = true;
		if (key != null) {
			if (!this.esAuthService.isAdmin(key)) {
				isAuth = false;
			}
		}
		if (isAuth) {
			Boolean result = false;

			Task response = this.taskService.updateTask(key, taskId, task);
			if (response != null)
				result = true;
			return new ResponseEntity(result, (result) ? HttpStatus.OK : HttpStatus.NOT_FOUND);

		} else
			return new ResponseEntity("{\"Status\":\"Access Denied\",\"Cause\":\"Invalid Consumer Key\"}", HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping(value = "/admin/v1/task/{taskId}", produces = { MediaType.APPLICATION_JSON_VALUE }/* , consumes = { MediaType.APPLICATION_JSON_VALUE } */)
	@ApiOperation(value = " Delete a Task", notes = "This is a public API", response = String.class)
	@ApiResponse(code = HttpServletResponse.SC_OK, message = "Success")
	@CustomResponse
	ResponseEntity deleteTask(@RequestHeader(name = "Consumer-Key", required = false) String key, @PathVariable(value = "taskId") Long taskId) {
		boolean isAuth = true;
		if (key != null) {
			if (!this.esAuthService.isAdmin(key)) {
				isAuth = false;
			}
		}
		if (isAuth) {
			this.taskService.deleteTask(key, taskId);
			return new ResponseEntity(HttpStatus.OK);

		} else
			return new ResponseEntity("{\"Status\":\"Access Denied\",\"Cause\":\"Invalid Consumer Key\"}", HttpStatus.UNAUTHORIZED);

	}

}
