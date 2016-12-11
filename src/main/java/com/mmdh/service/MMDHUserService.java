package com.mmdh.service;

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

import com.fasterxml.jackson.annotation.JsonView;
import com.mmdh.Mappings;
import com.mmdh.models.MMDHUser;
import com.mmdh.models.Organization;
import com.mmdh.models.PasswordProfile;
import com.mmdh.models.Role;
import com.mmdh.models.VerificationToken;
import com.mmdh.repos.MMDHUserRepository;
import com.mmdh.repos.OrganizationRepository;
import com.mmdh.repos.PasswordProfileRepository;
import com.mmdh.repos.RoleRepository;
import com.mmdh.repos.VerificationTokenRepository;
import com.mmdh.utils.MMDHResponse;
import com.mmdh.utils.JSONView;
import com.mmdh.utils.OnRegistrationCompleteEvent;
import com.mmdh.utils.exception.InvalidUserIdNameCombinationException;
import com.mmdh.utils.exception.InvalidVerificationTokenException;
import com.mmdh.utils.exception.MissingMandatoryDataException;
import com.mmdh.utils.exception.RecordExistsException;
import com.mmdh.utils.exception.RecordNotFoundException;

@RestController
@ExposesResourceFor(MMDHUser.class)
@RequestMapping(Mappings.API_BASE_PATH)
public class MMDHUserService {

	public static final String API_USERS_PASSWORDPROFILE_PATH = "/users/{userId}/passwordProfile";
	public static final String API_USER_CONFIRM_REGISTRATION = "/users/{userId}/registrationConfirm";

	@Autowired
	ApplicationEventPublisher eventPublisher;
	@Autowired
	private MMDHUserRepository userRepo;
	@Autowired
	private PasswordProfileRepository passwordProfileRepo;
	@Autowired
	private VerificationTokenRepository verificationTokenRepo;
	@Autowired
	private OrganizationRepository orgRepo;
	@Autowired
	private RoleRepository roleRepo;

	// GET All Users
	// GET User by various Search Operations
	// Following operators are supported
	// AND, OR, LIKE
	//
	@JsonView(JSONView.ParentObject.class)
	@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public List<MMDHUser> findAll(@RequestParam(value = "searchByOperator", required = false) String searchByOperator,
			@RequestParam(value = "firstName", required = false) String firstName,
			@RequestParam(value = "lastName", required = false) String lastName) {

		if (StringUtils.hasLength(searchByOperator) && StringUtils.hasLength(firstName)
				&& StringUtils.hasLength(lastName)) {
			if (searchByOperator.equals("AND"))
				return userRepo.findByFirstNameAndLastName(firstName, lastName);
			else if (searchByOperator.equals("OR"))
				return userRepo.findByFirstNameOrLastName(firstName, lastName);
		} else if (StringUtils.hasLength(firstName) && !StringUtils.hasLength(lastName)) {
			if (StringUtils.hasLength(searchByOperator) && searchByOperator.equals("LIKE"))
				return userRepo.findByFirstNameLike(firstName + "%");
			return userRepo.findByFirstName(firstName);
		} else if (!StringUtils.hasLength(firstName) && StringUtils.hasLength(lastName)) {
			if (StringUtils.hasLength(searchByOperator) && searchByOperator.equals("LIKE"))
				return userRepo.findByLastNameLike(lastName + "%");
			return userRepo.findByLastName(lastName);
		}
		return userRepo.findAll();
	}

	// GET User By Id
	@JsonView(JSONView.ParentObject.class)
	@RequestMapping(value = "/users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public MMDHUser getUserById(@PathVariable(value = "userId") Long userId) {
		return userRepo.findOne(userId);
	}

	// GET User By Id with Child objects
	@JsonView(JSONView.ParentObjectWithChildren.class)
	@RequestMapping(value = "/users/{userId}/with-children", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public MMDHUser getUserByIdWithChildren(@PathVariable(value = "userId") Long userId) {
		return userRepo.findOne(userId);
	}

	// ADD or Register a new User
	@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	@Transactional
	public @ResponseBody MMDHResponse addUser(@RequestBody MMDHUser user) throws RecordExistsException,
			RecordNotFoundException, InvalidUserIdNameCombinationException, MissingMandatoryDataException {

		validateUserExistance("ADD_USER", user);

		// validate mandatory fields are supplied
		if (StringUtils.isEmpty(user.getFirstName()) || StringUtils.isEmpty(user.getLastName())) {
			throw new MissingMandatoryDataException("MISSING_MANDATORY_DATA",
					"FirstName and LastName both the mandatory fields");
		}

		// verify and associate child objects i.e. Org, Role and PasswordProfile
		associateChildObjects(user);
		// save the user
		MMDHUser addedUser = userRepo.save(user);
		// also initialize PasswordProfile
		passwordProfileRepo.save(new PasswordProfile(addedUser.getUserId()));
		// now publish the event for sending an email to the user for email
		// validation
		if (addedUser != null) {
			eventPublisher.publishEvent(new OnRegistrationCompleteEvent(addedUser, LocaleContextHolder.getLocale()));
		}
		return new MMDHResponse("USER_CREATED", "User is created successfully").success();
	}

	// UPDATE User
	@RequestMapping(value = "/users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public @ResponseBody MMDHResponse updateUser(@PathVariable(value = "userId") Long userId, @RequestBody MMDHUser user)
			throws InvalidUserIdNameCombinationException, RecordExistsException, RecordNotFoundException, MissingMandatoryDataException {

		// validate the supplied user first
		user.setUserId(userId);
		validateUserExistance("UPDATE_USER", user);
		// validate mandatory fields are supplied
		if (StringUtils.isEmpty(user.getFirstName()) || StringUtils.isEmpty(user.getLastName())) {
			throw new MissingMandatoryDataException("MISSING_MANDATORY_DATA",
					"FirstName and LastName both the mandatory fields");
		}

		// verify and associate child objects i.e. Org, Role and Password
		// Profile
		associateChildObjects(user);
		// save the user
		userRepo.save(user);
		return new MMDHResponse("USER_UPDATED", "User is updated successfully").success();
	}

	// DELETE User
	@RequestMapping(value = "/users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	@Transactional
	public @ResponseBody MMDHResponse deleteUser(@PathVariable(value = "userId") Long userId) {

		MMDHUser user = userRepo.findOne(userId);
		userRepo.delete(user);

		return new MMDHResponse("USER_DELETED", "User is deleted successfully").success();
	}

	// User Email Validation
	@RequestMapping(value = API_USER_CONFIRM_REGISTRATION, method = RequestMethod.GET)
	public RedirectView confirmRegistration(@RequestParam("token") String token)
			throws InvalidVerificationTokenException {
		VerificationToken verificationToken = verificationTokenRepo.findByToken(token);
		if (verificationToken == null) {
			throw new InvalidVerificationTokenException("INVALID_TOKEN_ID", "Invalid Verification Token Id");
		}

		MMDHUser user = verificationToken.getUser();
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
	@RequestMapping(value = API_USERS_PASSWORDPROFILE_PATH, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public PasswordProfile getUserPasswordProfileById(@PathVariable(value = "userId") Long userId) {
		return passwordProfileRepo.findOne(userId);
	}

	// POST User's Password Profile
	@RequestMapping(value = API_USERS_PASSWORDPROFILE_PATH, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public @ResponseBody MMDHResponse updateUserPasswordProfile(@PathVariable(value = "userId") Long userId,
			@RequestBody PasswordProfile passwordProfile) {
		passwordProfile.setUserId(userId);
		passwordProfileRepo.save(passwordProfile);
		return new MMDHResponse("PROFILE_UPDATED", "Password Profile is updated successfully").success();
	}

	private boolean validateUserExistance(String action, MMDHUser suppliedUser)
			throws RecordNotFoundException, InvalidUserIdNameCombinationException, RecordExistsException {
		boolean proceed = true;

		if (suppliedUser.getUsername() == null || StringUtil.isEmpty(suppliedUser.getUsername()))
			throw new RecordNotFoundException("USERNAME_NOT_SUPPLIED", "Username is mandatory field in the request");

		MMDHUser user = userRepo.findByUsername(suppliedUser.getUsername());
		if (user != null) {
			// case of user add
			if (action.equals("ADD_USER"))
				throw new RecordExistsException("USER_EXISTS",
						"User name " + suppliedUser.getUsername() + " already exists");
			// case of User update/delete
			else if (!action.equals("ADD_USER") && user.getUserId() != suppliedUser.getUserId())
				throw new InvalidUserIdNameCombinationException("USERID_USERNAME_COMBINATION_DOES_NOT_MATCH",
						"UserId and Username combination does not match");
		} else if (!action.equals("ADD_USER"))
			throw new RecordNotFoundException("USER_NOT_FOUND",
					"No account found using this User name: " + suppliedUser.getUsername());

		return proceed;
	}

	private void associateChildObjects(MMDHUser user) throws RecordNotFoundException {
		if (user.getOrganization() != null) {
			// check if the organization exists
			Long organizationId = user.getOrganization().getOrganizationId();
			Organization organization = orgRepo.findOne(organizationId);
			if (organization != null)
				user.setOrganization(organization);
			else
				throw new RecordNotFoundException("ORGANIZATION_NOT_FOUND",
						"No Organization found with supplied OrganizationId");
		}

		if (user.getRole() != null) {
			// check if the role exists
			Long roleId = user.getRole().getRoleId();
			Role role = roleRepo.findOne(roleId);
			if (role != null)
				user.setRole(role);
			else
				throw new RecordNotFoundException("ROLE_NOT_FOUND", "No Role found with supplied RoleId");
		}

		if (user.getPasswordProfile() != null) {
			// check if the Password Profile exists
			PasswordProfile passwordProfile = passwordProfileRepo.findOne(user.getUserId());
			if (passwordProfile != null)
				user.setPasswordProfile(passwordProfile);
			else
				throw new RecordNotFoundException("PASSWORDPROFILE_NOT_FOUND",
						"No Password Profile found with supplied UserId");
		}
	}

	@ExceptionHandler(RecordExistsException.class)
	@ResponseBody
	public MMDHResponse recordExistsResponse(RecordExistsException ex, HttpServletResponse response) {
		response.setStatus(HttpStatus.SC_OK);
		return new MMDHResponse(ex.getCode(), ex.getMessage()).error();
	}

	@ExceptionHandler(RecordNotFoundException.class)
	@ResponseBody
	public MMDHResponse recordNotFoundResponse(RecordNotFoundException ex, HttpServletResponse response) {
		response.setStatus(HttpStatus.SC_OK);
		return new MMDHResponse(ex.getCode(), ex.getMessage()).error();
	}

	@ExceptionHandler(InvalidVerificationTokenException.class)
	@ResponseBody
	public MMDHResponse invalidTokenResponse(InvalidVerificationTokenException ex, HttpServletResponse response) {
		response.setStatus(HttpStatus.SC_OK);
		return new MMDHResponse(ex.getCode(), ex.getMessage()).error();
	}

	@ExceptionHandler(InvalidUserIdNameCombinationException.class)
	@ResponseBody
	public MMDHResponse invalidUserIdNameCombinationResponse(InvalidUserIdNameCombinationException ex,
			HttpServletResponse response) {
		response.setStatus(HttpStatus.SC_OK);
		return new MMDHResponse(ex.getCode(), ex.getMessage()).error();
	}

	@ExceptionHandler(MissingMandatoryDataException.class)
	@ResponseBody
	public MMDHResponse missingMandatoryDataResponse(MissingMandatoryDataException ex, HttpServletResponse response) {
		response.setStatus(HttpStatus.SC_OK);
		return new MMDHResponse(ex.getCode(), ex.getMessage()).error();
	}

}
