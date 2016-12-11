package com.mmdh.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mmdh.models.MMDHUser;

public interface MMDHUserRepository extends JpaRepository<MMDHUser, Long> {

	MMDHUser findOne(Long userId);
	MMDHUser findByUsername(String username);
	
	List<MMDHUser> findAll();
	List<MMDHUser> findByFirstName(String firstName);
	List<MMDHUser> findByLastName(String lastName);
	List<MMDHUser> findByFirstNameAndLastName(String firstName, String lastName);
	List<MMDHUser> findByFirstNameOrLastName(String firstName, String lastName);
	
	List<MMDHUser> findByFirstNameLike(String firstName);
	List<MMDHUser> findByLastNameLike(String lastName);
	
	@Query("select u from mmdhuser u where u.username = :username")
	MMDHUser loadUserByUserName(@Param("username") String username);

	@Query("select u from mmdhuser u where u.username = :username and u.password=:password and u.isEnabled=true")
	MMDHUser authticateUserAndGetDetails(@Param("username") String username, @Param("password") String password);

}
