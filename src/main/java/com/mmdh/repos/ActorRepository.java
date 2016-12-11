package com.mmdh.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.mmdh.models.Actor;
import com.mmdh.models.Organization;

public interface ActorRepository extends JpaRepository<Actor, Long> {

	List<Actor> findActorsByOrganization(@Param("organization") Organization organization);
	Actor findActorByOrganizationAndActorName(@Param("organization") Organization organization, @Param("actorName") String actorName);

}
