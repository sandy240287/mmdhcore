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
import com.apm.repos.OrganizationRepository;
import com.apm.repos.models.Organization;
import com.apm.utils.APMResponse;
import com.apm.utils.JSONView;
import com.apm.utils.exception.RecordExistsException;
import com.apm.utils.exception.RecordNotFoundException;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@ExposesResourceFor(Organization.class)
@RequestMapping(Mappings.API_BASE_PATH)
public class OrganizationService {

	public static final String API_ORGANIZATION_PATH = "organizations";

	@Autowired
	private OrganizationRepository orgRepo;

	// GET all Organizations
	@JsonView(JSONView.ParentObject.class)
	@RequestMapping(value = API_ORGANIZATION_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Organization> getAllOrgs() {
		return orgRepo.findAll();
	}

	// GET Org
	@JsonView(JSONView.ParentObject.class)
	@RequestMapping(value = API_ORGANIZATION_PATH + "/{orgId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Organization getOrgById(@PathVariable(value = "orgId") Long orgId) {
		return orgRepo.findOne(orgId);
	}

	// GET Org with Children
	@JsonView(JSONView.ParentObjectWithChildren.class)
	@RequestMapping(value = API_ORGANIZATION_PATH + "/{orgId}/with-children", produces = MediaType.APPLICATION_JSON_VALUE)
	public Organization getOrgByIdWithChildren(@PathVariable(value = "orgId") Long orgId) {
		return orgRepo.findOne(orgId);
	}

	// ADD organization
	@RequestMapping(value = API_ORGANIZATION_PATH, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public @ResponseBody APMResponse addOrganization(@RequestBody Organization organization) throws RecordExistsException {
		if (organizationNameExist(organization.getOrganizationName()))
			throw new RecordExistsException("ORGANIZATION_EXISTS", "Organization with this name already exist");
		orgRepo.save(organization);
		return new APMResponse("ORGANIZATION_CREATED", "Organization is created successfully").success();
	}

	// UPDATE organization
	@RequestMapping(value = API_ORGANIZATION_PATH+"/{organizationId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public @ResponseBody APMResponse updateOrganization(@PathVariable(value = "organizationId") Long organizationId, @RequestBody Organization organization)
			throws RecordNotFoundException {
		organization.setOrganizationId(organizationId);
		orgRepo.save(organization);
		return new APMResponse("ORGANIZATION_UPDATED", "Organization is updated successfully").success();
	}

	// DELETE organization
	@RequestMapping(value = API_ORGANIZATION_PATH+"/{organizationId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public @ResponseBody APMResponse deleteOrganization(@PathVariable(value = "organizationId") Long organizationId)
			throws RecordNotFoundException {
		orgRepo.delete(organizationId);
		return new APMResponse("ORGANIZATION_DELETED", "Organization is deleted successfully").success();

	}

	// Check if the Organization Already existing in DB
	private boolean organizationNameExist(String orgName) {
		Organization org = orgRepo.findByOrganizationName(orgName);
		if (org != null) {
			return true;
		}
		return false;
	}

	@ExceptionHandler(RecordExistsException.class)
	@ResponseBody
	public APMResponse organizationExistsResponse(RecordExistsException ex, HttpServletResponse response) {
		response.setStatus(HttpStatus.SC_OK);
		return new APMResponse(ex.getCode(), ex.getMessage()).error();
	}

	@ExceptionHandler(RecordNotFoundException.class)
	@ResponseBody
	public APMResponse organizationNotFoundResponse(RecordNotFoundException ex, HttpServletResponse response) {
		response.setStatus(HttpStatus.SC_OK);
		return new APMResponse(ex.getCode(), ex.getMessage()).error();
	}

}
