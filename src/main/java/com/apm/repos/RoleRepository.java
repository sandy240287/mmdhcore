package com.apm.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

//@Repository
//@RepositoryRestResource(path="roles")
public interface RoleRepository extends JpaRepository<Role, Long> {
	
	@Query(value = "SELECT r.role_id, r.audit_id, r.is_active, r.role_name "
			+ "FROM "
			+ "user_role ur, apmuser u, role r "
			+ "WHERE "
			+ "ur.role_id = r.role_id "
			+ "AND u.user_id = ur.user_id "
			+ "AND u.user_id = :userId", nativeQuery = true)
	List<Role> findAllByUserId(@Param("userId") Long userId);
	
	Role findByRoleId(@Param("roleId") Long roleId);
}
