package com.apm.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apm.repos.models.PasswordProfile;

public interface PasswordProfileRepository extends JpaRepository<PasswordProfile, Long> {
}
