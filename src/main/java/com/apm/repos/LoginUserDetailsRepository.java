package com.apm.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LoginUserDetailsRepository extends JpaRepository<LoginUserDetails, Long> {

	@Query("select u from loginuserdetails u where u.username = :username")
	LoginUserDetails loadUserByUserName(@Param("username") String username);

	@Query("select u from loginuserdetails u where u.username = :username and u.password=:password")
	LoginUserDetails authticateUserAndGetDetails(@Param("username") String username, @Param("password") String password);

}
