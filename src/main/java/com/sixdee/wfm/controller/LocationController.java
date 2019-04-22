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
import com.sixdee.wfm.model.City;
import com.sixdee.wfm.model.Community;
import com.sixdee.wfm.model.Country;
import com.sixdee.wfm.model.Location;
import com.sixdee.wfm.model.State;
import com.sixdee.wfm.service.CityService;
import com.sixdee.wfm.service.CommunityService;
import com.sixdee.wfm.service.ConsumerKeyAuthService;
import com.sixdee.wfm.service.CountryService;
import com.sixdee.wfm.service.LocationService;
import com.sixdee.wfm.service.StateService;

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
@Api(value = "Locations", description = "APIs for Locations/Community/Country/State/City .The Ids will have to be supplied as these are valid identifiers for identifying the cities,states,countries.The system on its own cant generate them. ", tags = {
		"Locations" })
public class LocationController {
	public static Logger logger = LoggerFactory.getLogger(LocationController.class);

	@Autowired
	private ConsumerKeyAuthService esAuthService;
	@Autowired
	private LocationService locationService;
	@Autowired
	private CountryService countryService;
	@Autowired
	private StateService stateService;
	@Autowired
	private CityService cityService;
	@Autowired
	private CommunityService communityService;

	/* LOCATION *********************/
	@PostMapping(value = "/api/admin/v1/locations", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Add a new Location with details", notes = "This is a public API with admin right", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = HttpServletResponse.SC_CREATED, message = "A new location has been added successfully"),
			@ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Invalid Consumer Key"), @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Invalid Request") })
	ResponseEntity createLocation(@RequestHeader(name = "Consumer-Key", required = false) String key, @Valid @RequestBody Location location) {
		boolean isAuth = true;
		if (key != null) {
			if (!this.esAuthService.isAdmin(key)) {
				isAuth = false;
			}
		}
		if (isAuth) {
			Boolean result = false;
			Location response = this.locationService.createLocation(location);
			if (response != null)
				result = true;
			return new ResponseEntity(result, (result) ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST);

		} else
			return new ResponseEntity("{\"Status\":\"Access Denied\",\"Cause\":\"Invalid Consumer Key\"}", HttpStatus.UNAUTHORIZED);
	}

	@GetMapping(value = "/api/public/v1/locations", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Return all Location with details", notes = "This is a public API", response = List.class)
	@ApiResponse(code = HttpServletResponse.SC_OK, message = "Success")
	@CustomResponse
	ResponseEntity getAllLocations(@RequestParam(value = "page", required = false) String page, @RequestParam(value = "pageSize", required = false) String pageSize,
			@RequestParam(value = "sortOrder", required = false) String sortOrder, @RequestParam(value = "sortKey", required = false) String sortKey,
			@RequestParam(value = "search", required = false) String search) {
		Page<Location> location = null;
		List<Location> locationAllLister = null;
		if (page != null && page.equalsIgnoreCase("All")) {
			locationAllLister = this.locationService.getAllLocation(search);
			return new ResponseEntity(locationAllLister, HttpStatus.OK);
		} else if (page != null && pageSize != null) {
			location = this.locationService.getAllLocation(page, pageSize, sortOrder, sortKey, search);
			return new ResponseEntity(location, HttpStatus.OK);
		} else {
			locationAllLister = this.locationService.getAllLocation(search);
			return new ResponseEntity(locationAllLister, HttpStatus.OK);
		}
	}

	/*********************/

	/* COUNTRY *********************/
	@GetMapping(value = "/api/public/v1/countries", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Return all Country with details", notes = "This is a public API", response = List.class)
	@ApiResponse(code = HttpServletResponse.SC_OK, message = "Success")
	@CustomResponse
	ResponseEntity getAllCountries(@RequestParam(value = "page", required = false) String page, @RequestParam(value = "pageSize", required = false) String pageSize,
			@RequestParam(value = "sortOrder", required = false) String sortOrder, @RequestParam(value = "sortKey", required = false) String sortKey,
			@RequestParam(value = "search", required = false) String search) {
		Page<Country> country = null;
		List<Country> countryAllLister = null;
		if (page != null && page.equalsIgnoreCase("All")) {
			countryAllLister = this.countryService.getAllCountry(search);
			return new ResponseEntity(countryAllLister, HttpStatus.OK);
		} else if (page != null && pageSize != null) {
			country = this.countryService.getAllCountry(page, pageSize, sortOrder, sortKey, search);
			return new ResponseEntity(country, HttpStatus.OK);
		} else {
			countryAllLister = this.countryService.getAllCountry(search);
			return new ResponseEntity(countryAllLister, HttpStatus.OK);
		}
	}

	/*********************/

	/* STATE *********************/
	@GetMapping(value = "/api/public/v1/states", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Return all State with details", notes = "This is a public API", response = List.class)
	@ApiResponse(code = HttpServletResponse.SC_OK, message = "Success")
	@CustomResponse
	ResponseEntity getAllStates(@RequestParam(value = "page", required = false) String page, @RequestParam(value = "pageSize", required = false) String pageSize,
			@RequestParam(value = "sortOrder", required = false) String sortOrder, @RequestParam(value = "sortKey", required = false) String sortKey,
			@RequestParam(value = "search", required = false) String search) {
		Page<State> state = null;
		List<State> stateAllLister = null;
		if (page != null && page.equalsIgnoreCase("All")) {
			stateAllLister = this.stateService.getAllState(search);
			return new ResponseEntity(stateAllLister, HttpStatus.OK);
		} else if (page != null && pageSize != null) {
			state = this.stateService.getAllState(page, pageSize, sortOrder, sortKey, search);
			return new ResponseEntity(state, HttpStatus.OK);
		} else {
			stateAllLister = this.stateService.getAllState(search);
			return new ResponseEntity(stateAllLister, HttpStatus.OK);
		}
	}

	@GetMapping(value = "/api/public/v1/states/{id}")
	@ApiOperation(value = "Return a specific Countries States", notes = "This is a public API with public right", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = HttpServletResponse.SC_NO_CONTENT, message = "The states has been returned successfully"),
			@ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Invalid Consumer Key"), @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "The State does not exist") })
	@CustomResponse
	ResponseEntity getStateByCountryId(@RequestHeader(name = "Consumer-Key", required = false) String key, @PathVariable(value = "id") Integer countryId) {
		logger.info("Recieved Department :findbyId" + countryId);
		List<State> state = this.stateService.findStateByCountryId(countryId);
		return new ResponseEntity(state, HttpStatus.OK);
	}

	/*********************/

	/* CITY *********************/
	@GetMapping(value = "/api/public/v1/cities", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Return all City with details", notes = "This is a public API", response = List.class)
	@ApiResponse(code = HttpServletResponse.SC_OK, message = "Success")
	@CustomResponse
	ResponseEntity getAllCitys(@RequestParam(value = "page", required = false) String page, @RequestParam(value = "pageSize", required = false) String pageSize,
			@RequestParam(value = "sortOrder", required = false) String sortOrder, @RequestParam(value = "sortKey", required = false) String sortKey,
			@RequestParam(value = "search", required = false) String search) {
		Page<City> city = null;
		List<City> cityAllLister = null;
		if (page != null && page.equalsIgnoreCase("All")) {
			cityAllLister = this.cityService.getAllCity(search);
			return new ResponseEntity(cityAllLister, HttpStatus.OK);
		} else if (page != null && pageSize != null) {
			city = this.cityService.getAllCity(page, pageSize, sortOrder, sortKey, search);
			return new ResponseEntity(city, HttpStatus.OK);
		} else {
			cityAllLister = this.cityService.getAllCity(search);
			return new ResponseEntity(cityAllLister, HttpStatus.OK);
		}
	}

	@GetMapping(value = "/api/public/v1/cities/{id}")
	@ApiOperation(value = "Return a specific States Citites", notes = "This is a public API with public right", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = HttpServletResponse.SC_NO_CONTENT, message = "The citys has been returned successfully"),
			@ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Invalid Consumer Key"), @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "The City does not exist") })
	@CustomResponse
	ResponseEntity getCityByCountryId(@RequestHeader(name = "Consumer-Key", required = false) String key, @PathVariable(value = "id") Integer cityId) {
		logger.info("Recieved Department :findbyId" + cityId);
		List<City> city = this.cityService.findCityByStateId(cityId);
		return new ResponseEntity(city, HttpStatus.OK);
	}

	/**********************/

	/* COMMUNITY *********************/

	@GetMapping(value = "/api/public/v1/communities", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Return all Community with details", notes = "This is a public API", response = List.class)
	@ApiResponse(code = HttpServletResponse.SC_OK, message = "Success")
	@CustomResponse
	ResponseEntity getAllCommunitys(@RequestParam(value = "page", required = false) String page, @RequestParam(value = "pageSize", required = false) String pageSize,
			@RequestParam(value = "sortOrder", required = false) String sortOrder, @RequestParam(value = "sortKey", required = false) String sortKey,
			@RequestParam(value = "search", required = false) String search) {
		Page<Community> community = null;
		List<Community> communityAllLister = null;
		if (page != null && page.equalsIgnoreCase("All")) {
			communityAllLister = this.communityService.getAllCommunity(search);
			return new ResponseEntity(communityAllLister, HttpStatus.OK);
		} else if (page != null && pageSize != null) {
			community = this.communityService.getAllCommunity(page, pageSize, sortOrder, sortKey, search);
			return new ResponseEntity(community, HttpStatus.OK);
		} else {
			communityAllLister = this.communityService.getAllCommunity(search);
			return new ResponseEntity(communityAllLister, HttpStatus.OK);
		}
	}

	@GetMapping(value = "/api/public/v1/communities/{id}")
	@ApiOperation(value = "Return a specific Cities Communities", notes = "This is a public API with public right", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = HttpServletResponse.SC_NO_CONTENT, message = "The communitys has been returned successfully"),
			@ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Invalid Consumer Key"),
			@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "The Community does not exist") })
	@CustomResponse
	ResponseEntity getCommunityByCityId(@RequestHeader(name = "Consumer-Key", required = false) String key, @PathVariable(value = "id") Integer cityId) {
		logger.info("Recieved community :findbyId" + cityId);
		List<Community> community = this.communityService.findCommunityByCityId(cityId);
		return new ResponseEntity(community, HttpStatus.OK);
	}

	@GetMapping(value = "/api/public/v1/location/{id}")
	@ApiOperation(value = "Return a specific location", notes = "This is a public API with public right", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = HttpServletResponse.SC_NO_CONTENT, message = "The location has been returned successfully"),
			@ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Invalid Consumer Key"),
			@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "The location does not exist") })
	@CustomResponse
	ResponseEntity getLocationById(@RequestHeader(name = "Consumer-Key", required = false) String key, @PathVariable(value = "id") Long locationId) {
		logger.info("Recieved Location :findbyId" + locationId);
		Location location = this.locationService.findById(locationId);
		return new ResponseEntity(location, HttpStatus.OK);
	}

	@PutMapping(value = "/admin/v1/location/{locationId}", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Update a specific location.", notes = "This is an admin API", response = String.class)
	@ApiResponse(code = HttpServletResponse.SC_OK, message = "Success")
	@CustomResponse
	ResponseEntity updateLocation(@RequestHeader(name = "Consumer-Key", required = false) String key, @PathVariable(value = "locationId") Long locationId, @Valid @RequestBody Location location) {
		boolean isAuth = true;
		if (key != null) {
			if (!this.esAuthService.isAdmin(key)) {
				isAuth = false;
			}
		}
		if (isAuth) {
			Boolean result = false;

			Location response = this.locationService.updateLocation(key, locationId, location);
			if (response != null)
				result = true;
			return new ResponseEntity(result, (result) ? HttpStatus.OK : HttpStatus.NOT_FOUND);

		} else
			return new ResponseEntity("{\"Status\":\"Access Denied\",\"Cause\":\"Invalid Consumer Key\"}", HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping(value = "/admin/v1/location/{locationId}", produces = { MediaType.APPLICATION_JSON_VALUE }/* , consumes = { MediaType.APPLICATION_JSON_VALUE } */)
	@ApiOperation(value = " Delete a Location", notes = "This is a public API", response = String.class)
	@ApiResponse(code = HttpServletResponse.SC_OK, message = "Success")
	@CustomResponse
	ResponseEntity deleteLocation(@RequestHeader(name = "Consumer-Key", required = false) String key, @PathVariable(value = "locationId") Long locationId) {
		boolean isAuth = true;
		if (key != null) {
			if (!this.esAuthService.isAdmin(key)) {
				isAuth = false;
			}
		}
		if (isAuth) {
			this.locationService.deleteLocation(key, locationId);
			return new ResponseEntity(HttpStatus.OK);

		} else
			return new ResponseEntity("{\"Status\":\"Access Denied\",\"Cause\":\"Invalid Consumer Key\"}", HttpStatus.UNAUTHORIZED);

	}

}
