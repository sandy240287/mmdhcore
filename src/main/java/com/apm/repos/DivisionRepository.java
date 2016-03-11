package com.apm.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.apm.repos.models.Capability;
import com.apm.repos.models.Division;
import com.apm.repos.models.Role;

public interface DivisionRepository extends JpaRepository<Division, Long> {
	
	List<Role> findRolesByDivId(@Param("divId") Long divId);
	List<Capability> findCapabilitiesByDivId(@Param("divId") Long divId);

}
