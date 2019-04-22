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

import com.fasterxml.jackson.annotation.JsonView;
import com.sixdee.wfm.common.CustomResponse;
import com.sixdee.wfm.common.Response;
import com.sixdee.wfm.model.Level;
import com.sixdee.wfm.model.LevelDesignation;
import com.sixdee.wfm.model.Skill;
import com.sixdee.wfm.service.ConsumerKeyAuthService;
import com.sixdee.wfm.service.SkillService;

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
@Api(value = "Skills", description = " APIs for Skills", tags = { "Skills" })
public class SkillController {
	public static Logger logger = LoggerFactory.getLogger(SkillController.class);

	@Autowired
	private ConsumerKeyAuthService esAuthService;
	@Autowired
	private SkillService skillService;

	@PostMapping(value = "/api/admin/v1/skills", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Add a new Skill with details", notes = "This is a public API with admin right", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = HttpServletResponse.SC_CREATED, message = "A new skill has been added successfully"),
			@ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Invalid Consumer Key"), @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Invalid Request") })
	ResponseEntity createSkill(@RequestHeader(name = "Consumer-Key", required = false) String key, @Valid @RequestBody Skill skill) {
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

			Skill response = this.skillService.createSkill(skill);
			if (response != null)
				result = true;
			return new ResponseEntity(result, (result) ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST);

		} else
			return new ResponseEntity("{\"Status\":\"Access Denied\",\"Cause\":\"Invalid Consumer Key\"}", HttpStatus.UNAUTHORIZED);

	}

	@GetMapping(value = "/api/public/v1/skills", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Return all Skill with details", notes = "This is a public API", response = List.class)
	@ApiResponse(code = HttpServletResponse.SC_OK, message = "Success")
	@CustomResponse
	ResponseEntity getAllSkills(@RequestParam(value = "page", required = false) String page, @RequestParam(value = "pageSize", required = false) String pageSize,
			@RequestParam(value = "sortOrder", required = false) String sortOrder, @RequestParam(value = "sortKey", required = false) String sortKey,
			@RequestParam(value = "search", required = false) String search) {
		Page<Skill> skill = null;
		List<Skill> skillAllLister = null;
		if (page != null && page.equalsIgnoreCase("All")) {
			skillAllLister = this.skillService.getAllSkill(search);
			return new ResponseEntity(skillAllLister, HttpStatus.OK);
		} else if (page != null && pageSize != null) {
			skill = this.skillService.getAllSkill(page, pageSize, sortOrder, sortKey, search);
			return new ResponseEntity(skill, HttpStatus.OK);
		} else {
			skillAllLister = this.skillService.getAllSkill(search);
			return new ResponseEntity(skillAllLister, HttpStatus.OK);
		}
	}

	@GetMapping(value = "/api/public/v1/skills/{id}")
	@ApiOperation(value = "Return a specific skill", notes = "This is a public API with public right", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = HttpServletResponse.SC_NO_CONTENT, message = "The skill has been returned successfully"),
			@ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Invalid Consumer Key"), @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "The skill does not exist") })
	@CustomResponse
	ResponseEntity getSkillById(@RequestHeader(name = "Consumer-Key", required = false) String key, @PathVariable(value = "id") Long skillId) {
		logger.info("Recieved Skill :findbyId" + skillId);
		Skill skill = this.skillService.findById(skillId);
		return new ResponseEntity(skill, HttpStatus.OK);
	}

	@PutMapping(value = "/admin/v1/skills/{skillId}", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Update a specific skill.", notes = "This is an admin API", response = String.class)
	@ApiResponse(code = HttpServletResponse.SC_OK, message = "Success")
	@CustomResponse
	ResponseEntity updateSkill(@RequestHeader(name = "Consumer-Key", required = false) String key, @PathVariable(value = "skillId") Long skillId, @Valid @RequestBody Skill skill) {
		boolean isAuth = true;
		if (key != null) {
			if (!this.esAuthService.isAdmin(key)) {
				isAuth = false;
			}
		}
		if (isAuth) {
			Boolean result = false;

			Skill response = this.skillService.updateSkill(key, skillId, skill);
			if (response != null)
				result = true;
			return new ResponseEntity(result, (result) ? HttpStatus.OK : HttpStatus.NOT_FOUND);

		} else
			return new ResponseEntity("{\"Status\":\"Access Denied\",\"Cause\":\"Invalid Consumer Key\"}", HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping(value = "/admin/v1/skills/{skillId}", produces = { MediaType.APPLICATION_JSON_VALUE }/* , consumes = { MediaType.APPLICATION_JSON_VALUE } */)
	@ApiOperation(value = " Delete a Skill", notes = "This is a public API", response = String.class)
	@ApiResponse(code = HttpServletResponse.SC_OK, message = "Success")
	@CustomResponse
	ResponseEntity deleteSkills(@RequestHeader(name = "Consumer-Key", required = false) String key, @PathVariable(value = "skillId") Long skillId) {
		boolean isAuth = true;
		if (key != null) {
			if (!this.esAuthService.isAdmin(key)) {
				isAuth = false;
			}
		}
		if (isAuth) {
			this.skillService.deleteSkill(key, skillId);
			return new ResponseEntity(HttpStatus.OK);

		} else
			return new ResponseEntity("{\"Status\":\"Access Denied\",\"Cause\":\"Invalid Consumer Key\"}", HttpStatus.UNAUTHORIZED);

	}

	/* Only For UI Skill View */
	@GetMapping(value = "/api/public/v1/skillsToLevel/{skillId}")
	@ApiOperation(value = "Return a specific skill", notes = "This is a public API with public right", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = HttpServletResponse.SC_NO_CONTENT, message = "The skill with only level mapping has been returned successfully"),
			@ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Invalid Consumer Key"), @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "The skill does not exist") })
	@CustomResponse
	@JsonView(Level.SkillView.class)
	ResponseEntity getSkillByIdWithLevelGroup(@RequestHeader(name = "Consumer-Key", required = false) String key, @PathVariable(value = "skillId") Integer skillId) {
		logger.info("Recieved Skill :findByIdWithLevelGrouping" + skillId);
		List<Level> level = this.skillService.findByIdWithLevelGrouping(skillId);
		return new ResponseEntity(level, HttpStatus.OK);
	}

	/* Only For UI Skill View */
	@GetMapping(value = "/api/public/v1/levelToDesignation")
	@ApiOperation(value = "Return a specific designation mapped to level and skill", notes = "This is a public API with public right", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = HttpServletResponse.SC_NO_CONTENT, message = "The designation mapped to level and skill has been returned successfully"),
			@ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Invalid Consumer Key"), @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "The skill does not exist") })
	@CustomResponse
	// @JsonView(Designation.SkillView.class)
	ResponseEntity findLevelToDesignation(@RequestHeader(name = "Consumer-Key", required = false) String key, @RequestParam(value = "skillId", required = true) Integer skillId,
			@RequestParam(value = "levelId", required = true) Integer levelId) {
		logger.info("Recieved Skill :findByIdWithLevelGrouping" + skillId);
		List<LevelDesignation> level = this.skillService.findLevelToDesignation(levelId, skillId);
		return new ResponseEntity(level, HttpStatus.OK);
	}

}
