package com.mmdh.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.mmdh.models.Division;
import com.mmdh.models.Organization;

public interface DivisionRepository extends JpaRepository<Division, Long> {

	List<Division> findDivisionsByOrganization(@Param("organization") Organization organization);
	Division findDivisionByOrganizationAndDivisionName(@Param("organization") Organization organization, @Param("divisionName") String divisionName);

}
