package com.apm.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.apm.Mappings;
import com.apm.repos.OrganizationRepository;
import com.apm.repos.models.Division;
import com.apm.repos.models.Organization;
import com.apm.repos.models.Role;
import com.apm.utils.DivisionExistsException;
import com.apm.utils.OrganizationExistsException;

@RestController
@ExposesResourceFor(Role.class)
@RequestMapping(Mappings.API_ORGANIZATION_PATH)
public class OrganizationService {

	public static final String API_ORGANIZATION_PATH = Mappings.API_ORGANIZATION_PATH;
	//public static final String API_ORGANIZATION_ROLES_PATH = API_ORGANIZATION_PATH + "/{orgId}/roles";
	//public static final String API_ORGANIZATION_ROLE_USERS_PATH = API_ORGANIZATION_ROLES_PATH + "/{roleId}/users";
	public static final String API_ORGANIZATION_DIVISIONS_PATH = "/{orgId}/divisions";
	public static final String API_ORGANIZATION_DIVISION_ROLES_PATH = API_ORGANIZATION_DIVISIONS_PATH
			+ "/{divId}/roles";
	public static final String API_ORGANIZATION_DIVISION_ROLE_USERS_PATH = API_ORGANIZATION_DIVISION_ROLES_PATH
			+ "/{roleId}/users";
	public static final String API_ORGANIZATION_DIVISION_CAPABILITIES_PATH = API_ORGANIZATION_DIVISIONS_PATH
			+ "/{divId}/capabilities";

	@Autowired
	private OrganizationRepository orgRepo;

	// GET all Organizations
	@RequestMapping(value = "", produces = "application/json")
	public List<Organization> findAll() {
		return orgRepo.findAll();
	}

	// GET Org
	@RequestMapping(value = "{orgId}", produces = "application/json")
	public Organization getOrgById(@PathVariable(value = "orgId") Long orgId) {
		return orgRepo.findOne(orgId);
	}

	// ADD Org
	@RequestMapping(value = "", produces = "application/json", method = RequestMethod.PUT)
	public Organization createNewOrganization(@RequestBody Organization org) throws OrganizationExistsException {
		if (organizationNameExist(org.getOrgName()))
			throw new OrganizationExistsException("Organization with this name already exist");
		return orgRepo.save(org);
	}

	// UPDATE Org
	@RequestMapping(value = "", produces = "application/json", method = RequestMethod.POST)
	public Organization updateOrganization(@RequestBody Organization org) {
		return orgRepo.save(org);
	}

	// DELETE Org
	@RequestMapping(value = "", produces = "application/json", method = RequestMethod.DELETE)
	public String deleteOrganization(@RequestBody Organization org) throws Exception {
		try {
			orgRepo.delete(org);
		} catch (Exception e) {
			throw new Exception("Exception occured while deleting the Organization", e);
		}
		return "ORG_DELETED";

	}
/*
	// GET Roles of the Organization
	@RequestMapping(value = API_ORGANIZATION_ROLES_PATH, produces = "application/json")
	public List<Role> getRolesByOrgId(@PathVariable(value = "orgId") Long orgId) {
		return orgRepo.findRolesByOrgId(orgId);
	}

	// ADD Role of the Organization
	@RequestMapping(value = API_ORGANIZATION_ROLES_PATH, produces = "application/json", method = RequestMethod.PUT)
	public Organization addRoleByOrgId(@PathVariable(value = "orgId") Long orgId, @RequestBody Role role)
			throws RoleExistsException {
		if (roleNameExist(orgId, role.getRoleName()))
			throw new RoleExistsException("Role already exist in this Organization");
		
		Organization orgObject = addRoleToOrgObject(orgId, role);
		return orgRepo.save(orgObject);
	}

	
	// UPDATE Role of the Organization
	@RequestMapping(value = API_ORGANIZATION_ROLES_PATH, produces = "application/json", method = RequestMethod.POST )
	public Organization updateRoleByOrgId(@PathVariable(value = "orgId") Long orgId, @RequestBody Role role){
		Organization orgObject = addRoleToOrgObject(orgId, role);
		return orgRepo.save(orgObject);
	}
	
	// DELETE Role of the Organization
	@RequestMapping(value = API_ORGANIZATION_ROLES_PATH, produces = "application/json", method = RequestMethod.DELETE)
	public Organization deleteRoleByOrgId(@PathVariable(value = "orgId") Long orgId, @PathVariable(value = "roleId") Long roleId) {
		Organization orgObject = removeRoleFromOrgObject(orgId, roleId);
		return orgRepo.save(orgObject);
	}

	// GET Organization->Roles->Users
	@RequestMapping(value = API_ORGANIZATION_ROLE_USERS_PATH, produces = "application/json")
	public List<APMUser> getUsersByRoleByOrgId(@PathVariable(value = "orgId") Long orgId,
			@PathVariable(value = "roleId") Long roleId) {
		return orgRepo.findUsersByRoleByOrgId(orgId, roleId);
	}
*/
	
	// GET Organization->Divisions
	@RequestMapping(value = API_ORGANIZATION_DIVISIONS_PATH, produces = "application/json")
	public List<Division> getDivisionsByOrgId(@PathVariable(value = "orgId") Long orgId) {
		return orgRepo.findDivisionsByOrgId(orgId);
	}

	// ADD Division of the Organization
	@RequestMapping(value = API_ORGANIZATION_DIVISIONS_PATH, produces = "application/json", method = RequestMethod.PUT)
	public Organization addDivisionByOrgId(@PathVariable(value = "orgId") Long orgId, @RequestBody Division division) throws DivisionExistsException{
		if (divNameExist(orgId, division.getDivName()))
			throw new DivisionExistsException("Division already exist in this Organization");
		Organization updatedOrg = addDivisionByOrgId(orgId, division);
		return orgRepo.save(updatedOrg);
	}

	// UPDATE Division of the Organization
	@RequestMapping(value = API_ORGANIZATION_DIVISIONS_PATH, produces = "application/json", method = RequestMethod.POST)
	public Organization updateDivisionByOrgId(@PathVariable(value = "orgId") Long orgId, @RequestBody Division division) {
		Organization updatedOrg = addDivisionToOrgObject(orgId, division);
		return orgRepo.save(updatedOrg);
	}

	// DELETE Division of the Organization
	@RequestMapping(value = API_ORGANIZATION_DIVISIONS_PATH, produces = "application/json", method = RequestMethod.DELETE)
	public Organization deleteDivisionByOrgId(@PathVariable(value = "orgId") Long orgId, @PathVariable(value = "divId") Long divId) {
		Organization orgObject = removeDivisionFromOrgObject(orgId, divId);
		return orgRepo.save(orgObject);
	}

	// Check if the Organization Already existing in DB
	private boolean organizationNameExist(String orgName) {
		Organization org = orgRepo.findByOrgName(orgName);
		if (org != null) {
			return true;
		}
		return false;
	}

/*
	// Check if the Role with same name already existing in DB
	private boolean roleNameExist(Long orgId, String roleName) {
		Role role = orgRepo.findRoleByOrgId(orgId, roleName);
		if (role != null) {
			return true;
		}
		return false;
	}
*/
	
	// Check if the Division with same name already existing in DB
	private boolean divNameExist(Long orgId, String divName) {
		Division div = orgRepo.findDivisionNameByOrgId(orgId, divName);
		if (div != null) {
			return true;
		}
		return false;
	}
/*	
	private Organization addRoleToOrgObject(Long orgId, Role role){
		Organization org = new Organization();
		org.setOrgId(orgId);
		List<Role> roles = new ArrayList<Role>();
		roles.add(role);
		org.setRoleByOrgId(roles);
		return org;
	}
	
	private Organization removeRoleFromOrgObject(Long orgId, Long roleId) {
		List<Role> existingRoles = orgRepo.findRolesByOrgId(orgId);
		Organization updatedOrg = new Organization();
		updatedOrg.setOrgId(orgId);
		for(Role role : existingRoles){
			if(role.getRoleId().equals(roleId)){
				existingRoles.remove(role);
			}
		}
		updatedOrg.setRoleByOrgId(existingRoles);
		return updatedOrg;
	}
*/
	private Organization addDivisionToOrgObject(Long orgId, Division div){
		Organization org = new Organization();
		org.setOrgId(orgId);
		List<Division> divs = new ArrayList<Division>();
		divs.add(div);
		org.setDivisions(divs);
		return org;
	}
	
	private Organization removeDivisionFromOrgObject(Long orgId, Long divId) {
		List<Division> existingDivisions = orgRepo.findDivisionsByOrgId(orgId);
		Organization updatedOrg = new Organization();
		updatedOrg.setOrgId(orgId);
		for(Division div : existingDivisions){
			if(div.geDivId().equals(divId)){
				existingDivisions.remove(div);
			}
		}
		updatedOrg.setDivisions(existingDivisions);
		return updatedOrg;
	}

	
}
