package com.mmdh.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mmdh.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	
	Role findByRoleName(String roleName);

}
