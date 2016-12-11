package com.mmdh.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.mmdh.Mappings;
import com.mmdh.models.MMDHUser;
import com.mmdh.models.Organization;
import com.mmdh.models.Role;
import com.mmdh.repos.MMDHUserRepository;
import com.mmdh.repos.OrganizationRepository;
import com.mmdh.repos.RoleRepository;
import com.mmdh.utils.MMDHResponse;
import com.mmdh.utils.JSONView;
import com.mmdh.utils.exception.RecordExistsException;
import com.mmdh.utils.exception.RecordNotFoundException;

@RestController
@ExposesResourceFor(Organization.class)
@RequestMapping(Mappings.API_BASE_PATH)
public class OrganizationService {

	protected static Logger logger = LoggerFactory.getLogger(OrganizationService.class);
	public static final String API_ORGANIZATION_PATH = "organizations";

	@Autowired
	private OrganizationRepository orgRepo;
	@Autowired
	private MMDHUserRepository userRepo;
	@Autowired
	private RoleRepository roleRepo;

	// GET all Organizations
	@JsonView(JSONView.ParentObject.class)
	@RequestMapping(value = API_ORGANIZATION_PATH, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public List<Organization> getAllOrgs() {
		return orgRepo.findAll();
	}

	// GET Org
	@JsonView(JSONView.ParentObject.class)
	@RequestMapping(value = API_ORGANIZATION_PATH
			+ "/{orgId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Organization getOrgById(@PathVariable(value = "orgId") Long orgId) {
		return orgRepo.findOne(orgId);
	}

	// GET Org with Children
	@JsonView(JSONView.ParentObjectWithChildren.class)
	@RequestMapping(value = API_ORGANIZATION_PATH
			+ "/{orgId}/with-children", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Organization getOrgByIdWithChildren(@PathVariable(value = "orgId") Long orgId) {
		return orgRepo.findOne(orgId);
	}

	// ADD organization
	@RequestMapping(value = API_ORGANIZATION_PATH, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public @ResponseBody MMDHResponse addOrganization(@RequestBody Organization organization)
			throws RecordExistsException {
		if (organizationNameExist(organization.getName()))
			throw new RecordExistsException("ORGANIZATION_EXISTS", "Organization with this name already exist");

		// associate logged in user to the organization as first user
		String loggedInUserName = SecurityContextHolder.getContext().getAuthentication().getName();
		MMDHUser user = userRepo.findByUsername(loggedInUserName);
		user.setOrganization(organization);
		// make this user Admin user
		Role userRole = roleRepo.findByRoleName("ADMIN");
		user.setRole(userRole);

		List<MMDHUser> users = new ArrayList<MMDHUser>();
		users.add(user);
		// and now save organization
		orgRepo.save(organization);
		return new MMDHResponse("ORGANIZATION_CREATED", "Organization is created successfully").success();
	}

	// UPDATE organization
	@RequestMapping(value = API_ORGANIZATION_PATH
			+ "/{organizationId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public @ResponseBody MMDHResponse updateOrganization(@PathVariable(value = "organizationId") Long organizationId,
			@RequestBody Organization organization) throws RecordNotFoundException {
		organization.setOrganizationId(organizationId);
		orgRepo.save(organization);
		return new MMDHResponse("ORGANIZATION_UPDATED", "Organization is updated successfully").success();
	}

	// DELETE organization
	@RequestMapping(value = API_ORGANIZATION_PATH
			+ "/{organizationId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public @ResponseBody MMDHResponse deleteOrganization(@PathVariable(value = "organizationId") Long organizationId)
			throws RecordNotFoundException {
		orgRepo.delete(organizationId);
		return new MMDHResponse("ORGANIZATION_DELETED", "Organization is deleted successfully").success();

	}

	// Check if the Organization Already existing in DB
	private boolean organizationNameExist(String orgName) {
		Organization org = orgRepo.findByName(orgName);
		if (org != null) {
			return true;
		}
		return false;
	}

	@ExceptionHandler(RecordExistsException.class)
	@ResponseBody
	public MMDHResponse organizationExistsResponse(RecordExistsException ex, HttpServletResponse response) {
		response.setStatus(HttpStatus.SC_OK);
		return new MMDHResponse(ex.getCode(), ex.getMessage()).error();
	}

	@ExceptionHandler(RecordNotFoundException.class)
	@ResponseBody
	public MMDHResponse organizationNotFoundResponse(RecordNotFoundException ex, HttpServletResponse response) {
		response.setStatus(HttpStatus.SC_OK);
		return new MMDHResponse(ex.getCode(), ex.getMessage()).error();
	}

}
