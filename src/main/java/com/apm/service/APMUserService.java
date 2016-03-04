package com.apm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apm.Mappings;
import com.apm.repos.APMUser;
import com.apm.repos.APMUserRepository;
import com.apm.repos.PasswordProfile;
import com.apm.repos.PasswordProfileRepository;
import com.apm.repos.Role;
import com.apm.repos.RoleRepository;

@RestController
@ExposesResourceFor(APMUser.class)
@RequestMapping(Mappings.API_USERS_PATH)
public class APMUserService {

	@Autowired
	private APMUserRepository userRepo;
	@Autowired
	private RoleRepository roleRepo;
	@Autowired
	private PasswordProfileRepository passwordProfileRepo;

	@RequestMapping(value = "", produces = "application/json")
	public List<APMUser> findAll(
			@RequestParam(value = "fullyLoaded", required = false) String fullyLoaded,
			@RequestParam(value = "searchByOperator", required = false) String searchByOperator,
			@RequestParam(value = "firstName", required = false) String firstName,
			@RequestParam(value = "lastName", required = false) String lastName) {

		if (StringUtils.hasLength(fullyLoaded) && fullyLoaded.trim().equals("TRUE")) {
			List<APMUser> users = userRepo.findAll();
			for (APMUser user : users) {
				loadRolesToUser(user);
				loadPasswordProfileToAPMUser(user);
			}
			return users;
		}

		if (StringUtils.hasLength(firstName) && StringUtils.hasLength(lastName)
				&& StringUtils.hasLength(searchByOperator)) {
			if (searchByOperator.equals("AND"))
				return userRepo.findByFirstNameAndLastName(firstName, lastName);
			else if (searchByOperator.equals("OR"))
				return userRepo.findByFirstNameOrLastName(firstName, lastName);
		} else if (StringUtils.hasLength(firstName) && !StringUtils.hasLength(lastName)) {
			return userRepo.findByFirstName(firstName);
		} else if (!StringUtils.hasLength(firstName) && StringUtils.hasLength(lastName)) {
			return userRepo.findByLastName(lastName);
		}
		return userRepo.findAll();
	}

	@RequestMapping(value = "{userId}", produces = "application/json")
	public APMUser findOne(@PathVariable(value = "userId") Long userId) {
		return userRepo.findOne(userId);
	}

	private APMUser loadRolesToUser(APMUser user) {
		List<Role> roles = roleRepo.findAllByUserId(user.getUserId()); 
		user.setRoles(roles);
		return user;
	}
	private APMUser loadPasswordProfileToAPMUser(APMUser user) {
		PasswordProfile profile = passwordProfileRepo.findOne(101L);
		user.setPasswordProfile(profile);
		return user;
	}

}
