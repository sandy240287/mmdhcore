package com.mmdh.repos.application;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.mmdh.models.Organization;
import com.mmdh.models.application.Application;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

	List<Application> findApplicationsByOrganization(@Param("organization") Organization organization);
	Application findApplicationByOrganizationAndName(@Param("organization") Organization organization, @Param("applicationName") String applicationName);

}
