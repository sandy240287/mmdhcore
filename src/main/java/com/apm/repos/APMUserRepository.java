package com.apm.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.apm.models.APMUser;

public interface APMUserRepository extends JpaRepository<APMUser, Long> {

	APMUser findOne(Long userId);
	APMUser findByUsername(String username);
	
	List<APMUser> findAll();
	List<APMUser> findByFirstName(String firstName);
	List<APMUser> findByLastName(String lastName);
	List<APMUser> findByFirstNameAndLastName(String firstName, String lastName);
	List<APMUser> findByFirstNameOrLastName(String firstName, String lastName);
	
	List<APMUser> findByFirstNameLike(String firstName);
	List<APMUser> findByLastNameLike(String lastName);
	
	@Query("select u from apmuser u where u.username = :username")
	APMUser loadUserByUserName(@Param("username") String username);

	@Query("select u from apmuser u where u.username = :username and u.password=:password and u.isEnabled=true")
	APMUser authticateUserAndGetDetails(@Param("username") String username, @Param("password") String password);

}
