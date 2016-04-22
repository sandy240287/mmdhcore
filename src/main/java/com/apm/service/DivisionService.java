package com.apm.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.apm.Mappings;
import com.apm.models.Division;
import com.apm.models.Organization;
import com.apm.repos.DivisionRepository;
import com.apm.repos.OrganizationRepository;
import com.apm.utils.APMResponse;
import com.apm.utils.JSONView;
import com.apm.utils.exception.RecordExistsException;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@ExposesResourceFor(Division.class)
@RequestMapping(Mappings.API_BASE_PATH)
public class DivisionService {

	public static final String API_ORGANIZATION_DIVISIONS_PATH = "organizations/{orgId}/divisions";

	@Autowired
	private DivisionRepository divisionRepo;
	@Autowired
	private OrganizationRepository orgRepo;

	// GET all Divisions By Organization
	@JsonView(JSONView.ParentObject.class)
	@RequestMapping(value = API_ORGANIZATION_DIVISIONS_PATH, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public List<Division> getDivisionsByOrganization(@PathVariable(value = "orgId") Long orgId) {
		// get organization object from repo first
		Organization organization = orgRepo.getOne(orgId);
		return divisionRepo.findDivisionsByOrganization(organization);
	}

	// GET Division
	@JsonView(JSONView.ParentObject.class)
	@RequestMapping(value = API_ORGANIZATION_DIVISIONS_PATH
			+ "/{divisionId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Division getDivisionById(@PathVariable(value = "divisionId") Long divisionId) {
		return divisionRepo.findOne(divisionId);
	}

	// GET Division with children
	@JsonView(JSONView.ParentObjectWithChildren.class)
	@RequestMapping(value = API_ORGANIZATION_DIVISIONS_PATH
			+ "/{divisionId}/with-children", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Division getDivisionByIdWithChildren(@PathVariable(value = "divisionId") Long divisionId) {
		return divisionRepo.findOne(divisionId);
	}

	// ADD Division of the Organization
	@RequestMapping(value = API_ORGANIZATION_DIVISIONS_PATH, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public @ResponseBody APMResponse addDivisionByOrganization(@PathVariable(value = "orgId") Long orgId,
			@RequestBody Division division) throws RecordExistsException {
		if (divNameExist(orgId, division.getDivisionName()))
			throw new RecordExistsException("DIVISION_EXISTS",
					"Division with this name already exist in this Organization");

		// attach organization to the division
		Organization organization = orgRepo.getOne(orgId);
		division.setOrganization(organization);

		// now save the division
		divisionRepo.save(division);
		return new APMResponse("DIVISION_CREATED", "Division is added successfully").success();

	}

	// UPDATE Division of the Organization
	@RequestMapping(value = API_ORGANIZATION_DIVISIONS_PATH+"/{divisionId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public @ResponseBody APMResponse updateDivisionByOrgId(@PathVariable(value = "orgId") Long orgId,
			@PathVariable(value = "divisionId") Long divisionId, @RequestBody Division division) throws RecordExistsException {

		// if the division name already exists throw an exception
		if (divNameExist(orgId, division.getDivisionName()))
			throw new RecordExistsException("DIVISION_EXISTS",
					"Division with this name already exist in this Organization");
		
		// set the supplied division id
		division.setDivisionId(divisionId);
		// attach organization to the division
		Organization organization = orgRepo.getOne(orgId);
		division.setOrganization(organization);

		divisionRepo.save(division);
		return new APMResponse("DIVISION_UPDATED", "Division is updated successfully").success();
	}

	// DELETE Division of the Organization
	@RequestMapping(value = API_ORGANIZATION_DIVISIONS_PATH
			+ "/{divisionId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public @ResponseBody APMResponse deleteDivisionByOrgId(@PathVariable(value = "orgId") Long orgId,
			@PathVariable(value = "divisionId") Long divisionId) {

		// get division from the repo
		Division division = divisionRepo.getOne(divisionId);
		divisionRepo.delete(division);
		return new APMResponse("DIVISION_DELETED", "Division is deleted successfully").success();
	}

	// Check if the Division with same name already existing in DB
	private boolean divNameExist(Long orgId, String divName) {
		// get organization
		Organization organization = orgRepo.getOne(orgId);
		Division division = divisionRepo.findDivisionByOrganizationAndDivisionName(organization, divName);
		if (division != null && division.getDivisionName().equals(divName)) {
			return true;
		}
		return false;
	}

	@ExceptionHandler(RecordExistsException.class)
	@ResponseBody
	public APMResponse divisionExistsResponse(RecordExistsException ex, HttpServletResponse response) {
		response.setStatus(HttpStatus.SC_OK);
		return new APMResponse(ex.getCode(), ex.getMessage()).error();
	}

}
