package com.mmdh.service.application;

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

import com.fasterxml.jackson.annotation.JsonView;
import com.mmdh.Mappings;
import com.mmdh.models.Organization;
import com.mmdh.models.application.Application;
import com.mmdh.repos.OrganizationRepository;
import com.mmdh.repos.application.ApplicationRepository;
import com.mmdh.utils.MMDHResponse;
import com.mmdh.utils.JSONView;
import com.mmdh.utils.exception.RecordExistsException;

@RestController
@ExposesResourceFor(Application.class)
@RequestMapping(Mappings.API_BASE_PATH)
public class ApplicationService {

	public static final String API_ORGANIZATION_APPLICATIONS_PATH = "organizations/{orgId}/applications";

	@Autowired
	private ApplicationRepository applicationRepo;
	@Autowired
	private OrganizationRepository orgRepo;

	// GET all Applications By Organization
	@JsonView(JSONView.ParentObject.class)
	@RequestMapping(value = API_ORGANIZATION_APPLICATIONS_PATH, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public List<Application> getApplicationsByOrganization(@PathVariable(value = "orgId") Long orgId) {
		// get organization object from repo first
		Organization organization = orgRepo.getOne(orgId);
		return applicationRepo.findApplicationsByOrganization(organization);
	}

	// GET Application
	@JsonView(JSONView.ParentObject.class)
	@RequestMapping(value = API_ORGANIZATION_APPLICATIONS_PATH
			+ "/{applicationId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Application getApplicationById(@PathVariable(value = "applicationId") Long applicationId) {
		return applicationRepo.findOne(applicationId);
	}

	// GET Application with children
	@JsonView(JSONView.ParentObjectWithChildren.class)
	@RequestMapping(value = API_ORGANIZATION_APPLICATIONS_PATH
			+ "/{applicationId}/with-children", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Application getApplicationByIdWithChildren(@PathVariable(value = "applicationId") Long applicationId) {
		return applicationRepo.findOne(applicationId);
	}

	// ADD Application of the Organization
	@RequestMapping(value = API_ORGANIZATION_APPLICATIONS_PATH, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public @ResponseBody MMDHResponse addApplicationByOrganization(@PathVariable(value = "orgId") Long orgId,
			@RequestBody Application application) throws RecordExistsException {
		if (applicationNameExist(orgId, application.getName()))
			throw new RecordExistsException("APPLICATION_EXISTS",
					"Application with this name already exist in this Organization");

		// attach organization to the application
		Organization organization = orgRepo.getOne(orgId);
		application.setOrganization(organization);

		// now save the application
		applicationRepo.save(application);
		return new MMDHResponse("APPLICATION_CREATED", "Application is added successfully").success();

	}

	// UPDATE Application of the Organization
	@RequestMapping(value = API_ORGANIZATION_APPLICATIONS_PATH+"/{applicationId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public @ResponseBody MMDHResponse updateApplicationByOrgId(@PathVariable(value = "orgId") Long orgId,
			@PathVariable(value = "applicationId") Long applicationId, @RequestBody Application application) throws RecordExistsException {

		// if the application name already exists throw an exception
		if (applicationNameExist(orgId, application.getName()))
			throw new RecordExistsException("APPLICATION_EXISTS",
					"Application with this name already exist in this Organization");
		
		// set the supplied application id
		application.setApplicationId(applicationId);
		// attach organization to the application
		Organization organization = orgRepo.getOne(orgId);
		application.setOrganization(organization);

		applicationRepo.save(application);
		return new MMDHResponse("APPLICATION_UPDATED", "Application is updated successfully").success();
	}

	// DELETE Application of the Organization
	@RequestMapping(value = API_ORGANIZATION_APPLICATIONS_PATH
			+ "/{applicationId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public @ResponseBody MMDHResponse deleteApplicationByOrgId(@PathVariable(value = "orgId") Long orgId,
			@PathVariable(value = "applicationId") Long applicationId) {

		// get application from the repo
		Application application = applicationRepo.getOne(applicationId);
		applicationRepo.delete(application);
		return new MMDHResponse("APPLICATION_DELETED", "Application is deleted successfully").success();
	}

	// Check if the Application with same name already existing in DB
	private boolean applicationNameExist(Long orgId, String divName) {
		// get organization
		Organization organization = orgRepo.getOne(orgId);
		Application application = applicationRepo.findApplicationByOrganizationAndName(organization, divName);
		if (application != null && application.getName().equals(divName)) {
			return true;
		}
		return false;
	}

	@ExceptionHandler(RecordExistsException.class)
	@ResponseBody
	public MMDHResponse applicationExistsResponse(RecordExistsException ex, HttpServletResponse response) {
		response.setStatus(HttpStatus.SC_OK);
		return new MMDHResponse(ex.getCode(), ex.getMessage()).error();
	}

}
