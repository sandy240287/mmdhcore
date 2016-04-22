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
import com.apm.models.Function;
import com.apm.repos.DivisionRepository;
import com.apm.repos.FunctionRepository;
import com.apm.utils.APMResponse;
import com.apm.utils.JSONView;
import com.apm.utils.exception.RecordExistsException;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@ExposesResourceFor(Function.class)
@RequestMapping(Mappings.API_BASE_PATH)
public class FunctionService {

	public static final String API_FUNCTIONS_PATH = "organizations/{orgId}/divisions/{divisionId}/functions";

	@Autowired
	private DivisionRepository divisionRepo;

	@Autowired
	private FunctionRepository functionRepo;

	// GET Function
	@JsonView(JSONView.ParentObject.class)
	@RequestMapping(value = API_FUNCTIONS_PATH + "/{functionId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Function getFunctionById(@PathVariable(value = "orgId") Long orgId,
			@PathVariable(value = "divisionId") Long divisionId, @PathVariable(value = "functionId") Long functionId) {
		return functionRepo.findOne(functionId);
	}

	// GET Function with children
	@JsonView(JSONView.ParentObjectWithChildren.class)
	@RequestMapping(value = API_FUNCTIONS_PATH
			+ "/{functionId}/with-children", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Function getFunctionByIdWithChildren(@PathVariable(value = "orgId") Long orgId,
			@PathVariable(value = "divisionId") Long divisionId, @PathVariable(value = "functionId") Long functionId) {
		return functionRepo.findOne(functionId);
	}

	// GET Functions by Division
	@JsonView(JSONView.ParentObject.class)
	@RequestMapping(value = API_FUNCTIONS_PATH, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public List<Function> getFunctionsByDivision(@PathVariable(value = "orgId") Long orgId,
			@PathVariable(value = "divisionId") Long divisionId) {

		// get Division with the supplied divId
		Division division = divisionRepo.getOne(divisionId);
		return functionRepo.findFunctionsByDivision(division);
	}

	// ADD Function to the Division
	@RequestMapping(value = API_FUNCTIONS_PATH, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public @ResponseBody APMResponse addFunctionByDivision(@PathVariable(value = "orgId") Long orgId,
			@PathVariable(value = "divisionId") Long divisionId, @RequestBody Function function)
			throws RecordExistsException {

		// get Division with the supplied divId
		Division division = divisionRepo.getOne(divisionId);

		if (functionNameExist(division, function.getFunctionName()))
			throw new RecordExistsException("FUNCTION_EXISTS",
					"FUNCTION with this name already exist in this Division");

		// now save the function
		function.setDivision(division);
		functionRepo.save(function);
		return new APMResponse("FUNCTION_CREATED", "Function is added successfully").success();

	}

	// UPDATE Function of the Division
	@RequestMapping(value = API_FUNCTIONS_PATH
			+ "/{functionId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public @ResponseBody APMResponse updateFunctionByDivision(@PathVariable(value = "orgId") Long orgId,
			@PathVariable(value = "divisionId") Long divisionId, @PathVariable(value = "functionId") Long functionId,
			@RequestBody Function function) {

		function.setFunctionId(functionId);
		// attach Division to the function
		Division division = divisionRepo.getOne(divisionId);
		function.setDivision(division);

		// now save the Function
		functionRepo.save(function);
		return new APMResponse("FUNCTION_UPDATED", "Function is updated successfully").success();
	}

	// DELETE Function of the Division
	@RequestMapping(value = API_FUNCTIONS_PATH
			+ "/{functionId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public @ResponseBody APMResponse deleteFunctionByOrgId(@PathVariable(value = "orgId") Long orgId,
			@PathVariable(value = "divisionId") Long divisionId, @PathVariable(value = "functionId") Long functionId) {

		// get Function with the supplied functionId
		Function function = functionRepo.getOne(functionId);

		// now delete the function
		functionRepo.delete(function);
		return new APMResponse("FUNCTION_DELETED", "Function is deleted successfully").success();
	}

	// Check if the Function with same name already existing in DB for this
	// division
	private boolean functionNameExist(Division division, String functionName) {
		Function function = functionRepo.findFunctionByDivisionAndFunctionName(division, functionName);
		if (function != null && function.getFunctionName().equals(functionName)) {
			return true;
		}
		return false;
	}

	@ExceptionHandler(RecordExistsException.class)
	@ResponseBody
	public APMResponse functionExistsResponse(RecordExistsException ex, HttpServletResponse response) {
		response.setStatus(HttpStatus.SC_OK);
		return new APMResponse(ex.getCode(), ex.getMessage()).error();
	}

}
