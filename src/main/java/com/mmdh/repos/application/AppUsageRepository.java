package com.mmdh.repos.application;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.mmdh.models.application.AppUsage;
import com.mmdh.models.application.Application;

public interface AppUsageRepository extends JpaRepository<AppUsage, Long> {

	AppUsage findAppUsageByApplication(@Param("application") Application application);

}
