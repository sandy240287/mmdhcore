package com.apm.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(path="employees")
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	@SuppressWarnings("unchecked")
	@Override
	Employee save(Employee e);

	@Override
	void delete(Employee arg0);

}
