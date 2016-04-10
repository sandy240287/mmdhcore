package com.apm.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apm.repos.models.Organization;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {

	Organization findByOrganizationName(String organizationName);

}
