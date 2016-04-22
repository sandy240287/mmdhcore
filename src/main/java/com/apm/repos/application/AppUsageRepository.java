package com.apm.repos.application;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.apm.models.application.AppUsage;
import com.apm.models.application.Application;

public interface AppUsageRepository extends JpaRepository<AppUsage, Long> {

	AppUsage findAppUsageByApplication(@Param("application") Application application);

}
