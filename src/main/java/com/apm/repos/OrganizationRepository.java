package com.apm.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apm.models.Organization;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {

	Organization findByOrganizationName(String organizationName);

}
