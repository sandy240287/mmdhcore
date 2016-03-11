package com.apm.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

//import com.apm.repos.models.APMUser;
import com.apm.repos.models.Division;
import com.apm.repos.models.Organization;
//import com.apm.repos.models.Role;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
	
	Organization findByOrgName(String orgName);
	
	@SuppressWarnings("unchecked")
	Organization save(Organization org);
	
	void delete(Organization org);
	
//	List<Role> findRolesByOrgId(@Param("orgId") Long orgId);
	List<Division> findDivisionsByOrgId(@Param("orgId") Long orgId);

//	List<APMUser> findUsersByRoleByOrgId(@Param("orgId") Long orgId, @Param("roleId") Long roleId);

//	Role findRoleByOrgId(Long orgId, String roleName);

	Division findDivisionNameByOrgId(Long orgId, String divName);

}
