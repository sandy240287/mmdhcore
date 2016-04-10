package com.apm.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.apm.repos.models.Division;
import com.apm.repos.models.Organization;

public interface DivisionRepository extends JpaRepository<Division, Long> {

	List<Division> findDivisionsByOrganization(@Param("organization") Organization organization);
	Division findDivisionByOrganizationAndDivisionName(@Param("organization") Organization organization, @Param("divisionName") String divisionName);

}
