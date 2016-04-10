package com.apm.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.apm.repos.models.Division;
import com.apm.repos.models.Function;

public interface FunctionRepository extends JpaRepository<Function, Long> {

	List<Function> findFunctionsByDivision(@Param("division") Division division);
	Function findFunctionByDivisionAndFunctionName(@Param("division") Division division, @Param("functionName") String functionName);

}
