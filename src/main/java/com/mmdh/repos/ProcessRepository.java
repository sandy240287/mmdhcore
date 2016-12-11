package com.mmdh.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.mmdh.models.Function;
import com.mmdh.models.Process;

public interface ProcessRepository extends JpaRepository<Process, Long> {

	List<Process> findProcessesByFunction(@Param("function") Function function);
	Process findProcessByFunctionAndProcessName(@Param("function") Function function, @Param("processName") String processName);
	
}
