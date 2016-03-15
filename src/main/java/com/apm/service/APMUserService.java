package com.apm.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpStatus;
import org.hsqldb.lib.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.apm.Mappings;
import com.apm.repos.APMUserRepository;
import com.apm.repos.PasswordProfileRepository;
import com.apm.repos.VerificationTokenRepository;
import com.apm.repos.models.APMUser;
import com.apm.repos.models.PasswordProfile;
import com.apm.repos.models.Role;
import com.apm.repos.models.VerificationToken;
import com.apm.utils.APMResponse;
import com.apm.utils.OnRegistrationCompleteEvent;
import com.apm.utils.exception.InvalidVerificationTokenException;
import com.apm.utils.exception.UserExistsException;
import com.apm.utils.exception.UserNotFoundException;

@RestController
@ExposesResourceFor(APMUser.class)
@RequestMapping(Mappings.API_USERS_PATH)
public class APMUserService {

	public static final String API_USERS_PATH = Mappings.API_USERS_PATH;
	public static final String API_USERS_PASSWORDPROFILE_PATH = "/{userId}/passwordProfile";
	public static final String API_USER_CONFIRM_REGISTRATION = "/{userId}/registrationConfirm";

	@Autowired
	ApplicationEventPublisher eventPublisher;
	@Autowired
	private APMUserRepository userRepo;
	@Autowired
	private PasswordProfileRepository passwordProfileRepo;
	@Autowired
	private VerificationTokenRepository verificationTokenRepo;

	// GET All Users
	// GET User by various Search Operations
	// TODO: Add Like operator
	@RequestMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
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

	// GET User
	@RequestMapping(value = "{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public APMUser getUserById(@PathVariable(value = "userId") Long userId) {
		return userRepo.findOne(userId);
	}

	// ADD or Register a new User
	@RequestMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	@Transactional
	public @ResponseBody APMResponse addUser(@RequestBody APMUser user)
			throws UserExistsException, UserNotFoundException, InvalidUserIdNameCombinationException {
		validateUserExistance("ADD_USER", user);

		// initialize roles too
		user.setRoles(new ArrayList<Role>());

		APMUser addedUser = userRepo.save(user);
		// also create User's Password profile
		passwordProfileRepo.save(new PasswordProfile(user.getUserId()));

		// now publish the event for sending an email to the user for email
		// validation
		if (addedUser != null) {
			eventPublisher.publishEvent(new OnRegistrationCompleteEvent(addedUser, LocaleContextHolder.getLocale()));
		}
		return new APMResponse("USER_CREATED", "User is created successfully").success();
	}

	// UPDATE User
	@RequestMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public @ResponseBody APMResponse updateUser(@RequestBody APMUser user)
			throws UserNotFoundException, InvalidUserIdNameCombinationException, UserExistsException {
		// validate the supplied user first
		validateUserExistance("UPDATE_USER", user);
		userRepo.save(user);
		return new APMResponse("USER_UPDATED", "User is updated successfully").success();
	}

	// DELETE User
	@RequestMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	@Transactional
	public @ResponseBody APMResponse deleteUser(@RequestBody APMUser user)
			throws UserNotFoundException, InvalidUserIdNameCombinationException, UserExistsException {
		// first validate the supplied user
		validateUserExistance("DELETE_USER", user);
		// init mandatory fields
		user.setPassword("");
		user.setFirstName("");
		user.setLastName("");

		VerificationToken token = verificationTokenRepo.findByUser(user);
		verificationTokenRepo.delete(token);
		passwordProfileRepo.delete(user.getUserId());
		userRepo.delete(user);

		return new APMResponse("USER_DELETED", "User is deleted successfully").success();
	}

	// User Email Validation
	@RequestMapping(value = API_USER_CONFIRM_REGISTRATION, method = RequestMethod.GET)
	public RedirectView confirmRegistration(@RequestParam("token") String token)
			throws InvalidVerificationTokenException {
		VerificationToken verificationToken = verificationTokenRepo.findByToken(token);
		if (verificationToken == null) {
			throw new InvalidVerificationTokenException("INVALID_TOKEN_ID", "Invalid Verification Token Id");
		}

		APMUser user = verificationToken.getUser();
		Calendar cal = Calendar.getInstance();
		if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
			throw new InvalidVerificationTokenException("TOKEN_EXPIRED", "Verification Token has expired");
		}

		user.setEnabled(true);
		userRepo.save(user);
		return new RedirectView(Mappings.REDIRECT_URL_AFTER_REGISTRATION_CONFIRMATION + "?uservalidated=true&username="
				+ user.getUsername() + "&firstname=" + user.getFirstName() + "&lastname=" + user.getLastName());
	}

	// GET User's Password Profile
	@RequestMapping(value = API_USERS_PASSWORDPROFILE_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
	public PasswordProfile getUserPasswordProfileById(@PathVariable(value = "userId") Long userId) {
		return passwordProfileRepo.findOne(userId);
	}

	// POST User's Password Profile
	@RequestMapping(value = API_USERS_PASSWORDPROFILE_PATH, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public @ResponseBody APMResponse updateUserPasswordProfile(@PathVariable(value = "userId") Long userId,
			@RequestBody PasswordProfile passwordProfile) {
		 passwordProfileRepo.save(passwordProfile);
		 return new APMResponse("PROFILE_UPDATED", "Password Profile is updated successfully").success();
	}

	private boolean validateUserExistance(String action, APMUser suppliedUser)
			throws UserNotFoundException, InvalidUserIdNameCombinationException, UserExistsException {
		boolean proceed = true;

		if (suppliedUser.getUsername() == null || StringUtil.isEmpty(suppliedUser.getUsername()))
			throw new UserNotFoundException("USERNAME_NOT_SUPPLIED", "Username is mandatory field in the request");

		APMUser user = userRepo.findByUsername(suppliedUser.getUsername());
		if (user != null) {
			// case of user add
			if (action.equals("ADD_USER"))
				throw new UserExistsException("USER_EXISTS",
						"There is already an account with email address: " + suppliedUser.getUsername());
			// case of User update/delete
			else if (!action.equals("ADD_USER") && user.getUserId() != suppliedUser.getUserId())
				throw new InvalidUserIdNameCombinationException("USERID_USERNAME_COMBINATION_DOES_NOT_MATCH",
						"UserId and Username combination does not match");
		}else if(!action.equals("ADD_USER"))
			throw new UserNotFoundException("USER_NOT_FOUND",
					"No account found using this email address: " + suppliedUser.getUsername());

		return proceed;
	}

	@ExceptionHandler(UserExistsException.class)
	@ResponseBody
	public APMResponse userExistsResponse(UserExistsException ex, HttpServletResponse response) {
		response.setStatus(HttpStatus.SC_OK);
		return new APMResponse(ex.getCode(), ex.getMessage()).error();
	}

	@ExceptionHandler(InvalidVerificationTokenException.class)
	@ResponseBody
	public APMResponse invalidTokenResponse(InvalidVerificationTokenException ex, HttpServletResponse response) {
		response.setStatus(HttpStatus.SC_OK);
		return new APMResponse(ex.getCode(), ex.getMessage()).error();
	}

	@ExceptionHandler(UserNotFoundException.class)
	@ResponseBody
	public APMResponse userNotFoundResponse(UserNotFoundException ex, HttpServletResponse response) {
		response.setStatus(HttpStatus.SC_OK);
		return new APMResponse(ex.getCode(), ex.getMessage()).error();
	}
	
	@ExceptionHandler(InvalidUserIdNameCombinationException.class)
	@ResponseBody
	public APMResponse invalidUserIdNameCombinationResponse(InvalidUserIdNameCombinationException ex, HttpServletResponse response) {
		response.setStatus(HttpStatus.SC_OK);
		return new APMResponse(ex.getCode(), ex.getMessage()).error();
	}

}
