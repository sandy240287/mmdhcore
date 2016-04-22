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
import com.apm.models.Actor;
import com.apm.models.Organization;
import com.apm.repos.ActorRepository;
import com.apm.repos.OrganizationRepository;
import com.apm.utils.APMResponse;
import com.apm.utils.JSONView;
import com.apm.utils.exception.RecordExistsException;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@ExposesResourceFor(Actor.class)
@RequestMapping(Mappings.API_BASE_PATH)
public class ActorService {

	public static final String API_ORGANIZATION_ACTORS_PATH = "organizations/{orgId}/actors";

	@Autowired
	private ActorRepository actorRepo;
	@Autowired
	private OrganizationRepository orgRepo;

	// GET all Actors By Organization
	@JsonView(JSONView.ParentObject.class)
	@RequestMapping(value = API_ORGANIZATION_ACTORS_PATH, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public List<Actor> getActorsByOrganization(@PathVariable(value = "orgId") Long orgId) {
		// get organization object from repo first
		Organization organization = orgRepo.getOne(orgId);
		return actorRepo.findActorsByOrganization(organization);
	}

	// GET Actor
	@JsonView(JSONView.ParentObject.class)
	@RequestMapping(value = API_ORGANIZATION_ACTORS_PATH
			+ "/{actorId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Actor getActorById(@PathVariable(value = "actorId") Long actorId) {
		return actorRepo.findOne(actorId);
	}

	// GET Actor with children
	@JsonView(JSONView.ParentObjectWithChildren.class)
	@RequestMapping(value = API_ORGANIZATION_ACTORS_PATH
			+ "/{actorId}/with-children", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Actor getActorByIdWithChildren(@PathVariable(value = "actorId") Long actorId) {
		return actorRepo.findOne(actorId);
	}

	// ADD Actor of the Organization
	@RequestMapping(value = API_ORGANIZATION_ACTORS_PATH, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public @ResponseBody APMResponse addActorByOrganization(@PathVariable(value = "orgId") Long orgId,
			@RequestBody Actor actor) throws RecordExistsException {
		if (actorNameExist(orgId, actor.getActorName()))
			throw new RecordExistsException("ACTOR_EXISTS",
					"Actor with this name already exist in this Organization");

		// attach organization to the actor
		Organization organization = orgRepo.getOne(orgId);
		actor.setOrganization(organization);

		// now save the actor
		actorRepo.save(actor);
		return new APMResponse("ACTOR_CREATED", "Actor is added successfully").success();

	}

	// UPDATE Actor of the Organization
	@RequestMapping(value = API_ORGANIZATION_ACTORS_PATH+"/{actorId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public @ResponseBody APMResponse updateActorByOrgId(@PathVariable(value = "orgId") Long orgId,
			@PathVariable(value = "actorId") Long actorId, @RequestBody Actor actor) throws RecordExistsException {

		// if the actor name already exists throw an exception
		if (actorNameExist(orgId, actor.getActorName()))
			throw new RecordExistsException("ACTOR_EXISTS",
					"Actor with this name already exist in this Organization");
		
		// set the supplied actor id
		actor.setActorId(actorId);
		// attach organization to the actor
		Organization organization = orgRepo.getOne(orgId);
		actor.setOrganization(organization);

		actorRepo.save(actor);
		return new APMResponse("ACTOR_UPDATED", "Actor is updated successfully").success();
	}

	// DELETE Actor of the Organization
	@RequestMapping(value = API_ORGANIZATION_ACTORS_PATH
			+ "/{actorId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public @ResponseBody APMResponse deleteActorByOrgId(@PathVariable(value = "orgId") Long orgId,
			@PathVariable(value = "actorId") Long actorId) {

		// get actor from the repo
		Actor actor = actorRepo.getOne(actorId);
		actorRepo.delete(actor);
		return new APMResponse("ACTOR_DELETED", "Actor is deleted successfully").success();
	}

	// Check if the Actor with same name already existing in DB
	private boolean actorNameExist(Long orgId, String actorName) {
		// get organization
		Organization organization = orgRepo.getOne(orgId);
		Actor actor = actorRepo.findActorByOrganizationAndActorName(organization, actorName);
		if (actor != null && actor.getActorName().equals(actorName)) {
			return true;
		}
		return false;
	}

	@ExceptionHandler(RecordExistsException.class)
	@ResponseBody
	public APMResponse actorExistsResponse(RecordExistsException ex, HttpServletResponse response) {
		response.setStatus(HttpStatus.SC_OK);
		return new APMResponse(ex.getCode(), ex.getMessage()).error();
	}

}
