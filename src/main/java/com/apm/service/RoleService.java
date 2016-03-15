package com.apm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apm.repos.RoleRepository;
import com.apm.repos.models.Role;

@RestController
@ExposesResourceFor(Role.class)
@RequestMapping("Roles")
public class RoleService {

	@Autowired
	private RoleRepository roleRepo;

	@RequestMapping(value = "", produces = "application/json")
	public List<Role> findAll() {
		return roleRepo.findAll();
	}

	@RequestMapping(value = "{roleId}", produces = "application/json")
	public Role getRoleById(@PathVariable(value = "roleId") Long roleId) {
		return roleRepo.findOne(roleId);
	}

}
