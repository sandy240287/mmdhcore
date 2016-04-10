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
import com.apm.repos.FunctionRepository;
import com.apm.repos.ProcessRepository;
import com.apm.repos.models.Function;
import com.apm.repos.models.Process;
import com.apm.utils.APMResponse;
import com.apm.utils.JSONView;
import com.apm.utils.exception.RecordExistsException;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@ExposesResourceFor(Process.class)
@RequestMapping(Mappings.API_BASE_PATH)
public class ProcessService {

	public static final String API_PROCESSES_PATH = "organizations/{orgId}/divisions/{divisionId}/functions/{functionId}/processes";

	@Autowired
	private ProcessRepository processRepo;

	@Autowired
	private FunctionRepository functionRepo;

	// GET Process
	@JsonView(JSONView.ParentObject.class)
	@RequestMapping(value = API_PROCESSES_PATH + "/{processId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Process getProcessById(@PathVariable(value = "orgId") Long orgId,
			@PathVariable(value = "divisionId") Long divisionId, @PathVariable(value = "functionId") Long functionId,
			@PathVariable(value = "processId") Long processId) {
		return processRepo.findOne(processId);
	}

	// GET Process with children
	@JsonView(JSONView.ParentObjectWithChildren.class)
	@RequestMapping(value = API_PROCESSES_PATH
			+ "/{processId}/with-children", produces = MediaType.APPLICATION_JSON_VALUE)
	public Process getProcessByIdWithChildren(@PathVariable(value = "orgId") Long orgId,
			@PathVariable(value = "divisionId") Long divisionId, @PathVariable(value = "functionId") Long functionId,
			@PathVariable(value = "processId") Long processId) {
		return processRepo.findOne(processId);
	}

	// GET Processes by Function
	@JsonView(JSONView.ParentObject.class)
	@RequestMapping(value = API_PROCESSES_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Process> getProcesssByFunction(@PathVariable(value = "orgId") Long orgId,
			@PathVariable(value = "divisionId") Long divisionId, @PathVariable(value = "functionId") Long functionId) {

		// get Function with the supplied functionId
		Function function = functionRepo.getOne(functionId);
		return processRepo.findProcessesByFunction(function);
	}

	// ADD Process to the Function
	@RequestMapping(value = API_PROCESSES_PATH, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public @ResponseBody APMResponse addProcessByFunction(@PathVariable(value = "orgId") Long orgId,
			@PathVariable(value = "divisionId") Long divisionId, @PathVariable(value = "functionId") Long functionId,
			@RequestBody Process process) throws RecordExistsException {

		// get Function with the supplied functionId
		Function function = functionRepo.getOne(functionId);

		if (processNameExist(function, process.getProcessName()))
			throw new RecordExistsException("PROCESS_EXISTS", "PROCESS with this name already exist in this Function");

		// now save the process
		process.setFunction(function);
		processRepo.save(process);
		return new APMResponse("PROCESS_CREATED", "Process is added successfully").success();

	}

	// UPDATE Process of the Function
	@RequestMapping(value = API_PROCESSES_PATH
			+ "/{processId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public @ResponseBody APMResponse updateProcessByFunction(@PathVariable(value = "orgId") Long orgId,
			@PathVariable(value = "divisionId") Long divisionId, @PathVariable(value = "functionId") Long functionId,
			@PathVariable(value = "processId") Long processId, @RequestBody Process process) {

		process.setProcessId(processId);
		// attach Function to the Process
		Function function = functionRepo.getOne(functionId);
		process.setFunction(function);

		// now save the Process
		processRepo.save(process);
		return new APMResponse("PROCESS_UPDATED", "Process is updated successfully").success();
	}

	// DELETE Process of the Function
	@RequestMapping(value = API_PROCESSES_PATH
			+ "/{processId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public @ResponseBody APMResponse deleteProcessByOrgId(@PathVariable(value = "orgId") Long orgId,
			@PathVariable(value = "divisionId") Long divisionId, @PathVariable(value = "functionId") Long functionId,
			@PathVariable(value = "processId") Long processId) {

		// get Process with the supplied processId
		Process process = processRepo.getOne(processId);

		// now delete the process
		processRepo.delete(process);
		return new APMResponse("PROCESS_DELETED", "Process is deleted successfully").success();
	}

	// Check if the Process with same name already existing in DB for this
	// division
	private boolean processNameExist(Function division, String processName) {
		Process process = processRepo.findProcessByFunctionAndProcessName(division, processName);
		if (process != null && process.getProcessName().equals(processName)) {
			return true;
		}
		return false;
	}

	@ExceptionHandler(RecordExistsException.class)
	@ResponseBody
	public APMResponse processExistsResponse(RecordExistsException ex, HttpServletResponse response) {
		response.setStatus(HttpStatus.SC_OK);
		return new APMResponse(ex.getCode(), ex.getMessage()).error();
	}

}
