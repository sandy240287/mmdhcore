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
import com.apm.models.Role;
import com.apm.repos.RoleRepository;
import com.apm.utils.APMResponse;
import com.apm.utils.JSONView;
import com.apm.utils.exception.RecordExistsException;
import com.apm.utils.exception.RecordNotFoundException;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@ExposesResourceFor(Role.class)
@RequestMapping(Mappings.API_BASE_PATH)
public class RoleService {

	public static final String API_ROLE_PATH = "/roles";

	@Autowired
	private RoleRepository roleRepo;

	// GET all Roles
	@JsonView(JSONView.ParentObject.class)
	@RequestMapping(value = API_ROLE_PATH, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public List<Role> getAllRoles() {
		return roleRepo.findAll();
	}

	// GET Role
	@JsonView(JSONView.ParentObject.class)
	@RequestMapping(value = API_ROLE_PATH + "/{roleId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Role getRoleById(@PathVariable(value = "roleId") Long roleId) {
		return roleRepo.findOne(roleId);
	}

	// GET Role with Children
	@JsonView(JSONView.ParentObjectWithChildren.class)
	@RequestMapping(value = API_ROLE_PATH
			+ "/{roleId}/with-children", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Role getRoleByIdWithChildren(@PathVariable(value = "roleId") Long roleId) {
		return roleRepo.findOne(roleId);
	}

	// ADD role
	@RequestMapping(value = API_ROLE_PATH, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public @ResponseBody APMResponse addRole(@RequestBody Role role) throws RecordExistsException {
		if (roleNameExist(role.getRoleName()))
			throw new RecordExistsException("ROLE_EXISTS", "Role with this name already exist");
		roleRepo.save(role);
		return new APMResponse("ROLE_CREATED", "Role is created successfully").success();
	}

	// UPDATE role
	@RequestMapping(value = API_ROLE_PATH
			+ "/{roleId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public @ResponseBody APMResponse updateRole(@PathVariable(value = "roleId") Long roleId, @RequestBody Role role)
			throws RecordNotFoundException {
		role.setRoleId(roleId);
		roleRepo.save(role);
		return new APMResponse("ROLE_UPDATED", "Role is updated successfully").success();
	}

	// DELETE role
	@RequestMapping(value = API_ROLE_PATH
			+ "/{roleId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public @ResponseBody APMResponse deleteRole(@PathVariable(value = "roleId") Long roleId)
			throws RecordNotFoundException {
		roleRepo.delete(roleId);
		return new APMResponse("ROLE_DELETED", "Role is deleted successfully").success();

	}

	// Check if the Role Already existing in DB
	private boolean roleNameExist(String roleName) {
		Role role = roleRepo.findByRoleName(roleName);
		if (role != null) {
			return true;
		}
		return false;
	}

	@ExceptionHandler(RecordExistsException.class)
	@ResponseBody
	public APMResponse roleExistsResponse(RecordExistsException ex, HttpServletResponse response) {
		response.setStatus(HttpStatus.SC_OK);
		return new APMResponse(ex.getCode(), ex.getMessage()).error();
	}

	@ExceptionHandler(RecordNotFoundException.class)
	@ResponseBody
	public APMResponse roleNotFoundResponse(RecordNotFoundException ex, HttpServletResponse response) {
		response.setStatus(HttpStatus.SC_OK);
		return new APMResponse(ex.getCode(), ex.getMessage()).error();
	}

}
