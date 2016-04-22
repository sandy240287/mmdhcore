package com.apm.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apm.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	
	Role findByRoleName(String roleName);

}
