package com.apm.service;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apm.Mappings;
import com.apm.repos.APMUserRepository;
import com.apm.repos.PasswordProfileRepository;
import com.apm.repos.RoleRepository;
import com.apm.repos.VerificationTokenRepository;
import com.apm.repos.models.APMUser;
import com.apm.repos.models.PasswordProfile;
import com.apm.repos.models.Role;
import com.apm.repos.models.VerificationToken;
import com.apm.utils.EmailExistsException;
import com.apm.utils.InvalidVerificationTokenException;
import com.apm.utils.OnRegistrationCompleteEvent;

@RestController
@ExposesResourceFor(APMUser.class)
@RequestMapping(Mappings.API_USERS_PATH)
public class APMUserService {
	@Autowired
	ApplicationEventPublisher eventPublisher;
	@Autowired
	private APMUserRepository userRepo;
	@Autowired
	private RoleRepository roleRepo;
	@Autowired
	private PasswordProfileRepository passwordProfileRepo;
	@Autowired
	private VerificationTokenRepository verificationTokenRepo;
	
	@RequestMapping(value = "", produces = "application/json")
	public List<APMUser> findAll(@RequestParam(value = "searchByOperator", required = false) String searchByOperator,
			@RequestParam(value = "firstName", required = false) String firstName,
			@RequestParam(value = "lastName", required = false) String lastName) {

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
	public APMUser getUserById(@PathVariable(value = "userId") Long userId) {
		return userRepo.findOne(userId);
	}

	@RequestMapping(value = Mappings.API_USERS_ROLES_PATH + "/{roleId}", produces = "application/json")
	public Role getUserRoleByRoleId(@PathVariable(value = "roleId") Long roleId) {
		return roleRepo.findByRoleId(roleId);
	}

	@RequestMapping(value = Mappings.API_USERS_ROLES_PATH, produces = "application/json")
	public List<Role> getUserRolesById(@PathVariable(value = "userId") Long userId) {
		return roleRepo.findAllByUserId(userId);
	}

	@RequestMapping(value = Mappings.API_USERS_PASSWORDPROFILE_PATH, produces = "application/json")
	public PasswordProfile getUserPasswordProfileById(@PathVariable(value = "userId") Long userId) {
		return passwordProfileRepo.findOne(userId);
	}

	@RequestMapping(value = "", produces = "application/json", method = RequestMethod.PUT)
	public APMUser registerNewUser(@RequestBody APMUser user) throws EmailExistsException {
		if (userIdExist(user.getUsername())) {
			throw new EmailExistsException("There is an account with that email adress: " + user.getUsername());
		}
		
		APMUser createdUser = userRepo.save(user);
		if(createdUser!=null){
			eventPublisher.publishEvent(new OnRegistrationCompleteEvent(createdUser, LocaleContextHolder.getLocale()));
		}
		return createdUser;
	}
	
	@RequestMapping(value = Mappings.API_USER_CONFIRM_REGISTRATION, method = RequestMethod.GET)
	public String confirmRegistration(@RequestParam("token") String token) throws InvalidVerificationTokenException{
		VerificationToken verificationToken = verificationTokenRepo.findByToken(token);
	    if (verificationToken == null) {
	    	throw new InvalidVerificationTokenException("Invalid Verification Token Id");
	    }
	     
	    APMUser user = verificationToken.getUser();
	    Calendar cal = Calendar.getInstance();
	    if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
	    	throw new InvalidVerificationTokenException("Verification Token has expired");
	    } 
	     
	    user.setEnabled(true); 
	    userRepo.save(user); 
	    return "USER_ENABLED_AFTER_REGISTERATION";
	}

	private boolean userIdExist(String userName) {
		APMUser user = userRepo.findByUsername(userName);
		if (user != null) {
			return true;
		}
		return false;
	}

}
