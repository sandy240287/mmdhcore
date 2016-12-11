package com.mmdh.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mmdh.models.Organization;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {

	Organization findByName(String organizationName);

}
