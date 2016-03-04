package com.apm.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

//@Repository
//@RepositoryRestResource(path = "users")
public interface APMUserRepository extends JpaRepository<APMUser, Long> {

	APMUser findOne(Long userId);
	
	List<APMUser> findAll();
	List<APMUser> findByFirstName(String firstName);
	List<APMUser> findByLastName(String lastName);
	List<APMUser> findByFirstNameAndLastName(String firstName, String lastName);
	List<APMUser> findByFirstNameOrLastName(String firstName, String lastName);

}
