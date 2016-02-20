package com.apm.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "user", path = "user")
public interface UserDetailsRepository extends JpaRepository<CustomUserDetails, String> {

	@Query("select u from CustomUserDetails u where u.username = :username")
	CustomUserDetails loadByUserName(@Param("username") String username);

	@Query("select u from CustomUserDetails u where u.username = :username and u.password=:password")
	CustomUserDetails authticateUserAndGetDetails(@Param("username") String username, @Param("password") String password);

}
