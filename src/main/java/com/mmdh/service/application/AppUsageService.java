package com.mmdh.service.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.mmdh.Mappings;
import com.mmdh.models.application.AppUsage;
import com.mmdh.repos.application.AppUsageRepository;
import com.mmdh.repos.application.ApplicationRepository;
import com.mmdh.utils.MMDHResponse;
import com.mmdh.utils.JSONView;
import com.mmdh.utils.exception.RecordExistsException;

@RestController
@ExposesResourceFor(AppUsage.class)
@RequestMapping(Mappings.API_BASE_PATH)
public class AppUsageService {

	public static final String API_APPUSAGE_PATH = "organizations/{orgId}/applications/{applicationId}/appusage";

	@Autowired
	private ApplicationRepository applicationRepo;

	@Autowired
	private AppUsageRepository appUsageRepo;

	// GET AppUsage
	@JsonView(JSONView.ParentObject.class)
	@RequestMapping(value = API_APPUSAGE_PATH, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public AppUsage getAppUsage(@PathVariable(value = "orgId") Long orgId,
			@PathVariable(value = "applicationId") Long applicationId) {
		return appUsageRepo.findAppUsageByApplication(applicationRepo.getOne(applicationId));
	}

	// GET AppUsage with children
	@JsonView(JSONView.ParentObjectWithChildren.class)
	@RequestMapping(value = API_APPUSAGE_PATH + "/with-children", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public AppUsage getAppUsageWithChildren(@PathVariable(value = "orgId") Long orgId,
			@PathVariable(value = "applicationId") Long applicationId) {
		return appUsageRepo.findAppUsageByApplication(applicationRepo.getOne(applicationId));
	}

	// ADD AppUsage to the Application
	@RequestMapping(value = API_APPUSAGE_PATH, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public @ResponseBody MMDHResponse addAppUsageByApplication(@PathVariable(value = "orgId") Long orgId,
			@PathVariable(value = "applicationId") Long applicationId, @RequestBody AppUsage appUsage)
			throws RecordExistsException {

		// save the appUsage object
		appUsageRepo.save(appUsage);
		return new MMDHResponse("APPUSAGE_CREATED", "AppUsage is added successfully").success();

	}

	// UPDATE AppUsage of the Application
	@RequestMapping(value = API_APPUSAGE_PATH, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public @ResponseBody MMDHResponse updateAppUsageByApplication(@PathVariable(value = "orgId") Long orgId,
			@PathVariable(value = "applicationId") Long applicationId, @RequestBody AppUsage appUsage) {

		// save the AppUsage
		appUsageRepo.save(appUsage);
		return new MMDHResponse("APPUSAGE_UPDATED", "AppUsage is updated successfully").success();
	}

	// DELETE AppUsage of the Application
	@RequestMapping(value = API_APPUSAGE_PATH, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public @ResponseBody MMDHResponse deleteAppUsage(@PathVariable(value = "orgId") Long orgId,
			@PathVariable(value = "applicationId") Long applicationId) {

		// get AppUsage with the supplied functionId
		AppUsage appUsage = appUsageRepo.findAppUsageByApplication(applicationRepo.getOne(applicationId));

		// now delete the function
		appUsageRepo.delete(appUsage);
		return new MMDHResponse("APPUSAGE_DELETED", "AppUsage is deleted successfully").success();
	}

}
