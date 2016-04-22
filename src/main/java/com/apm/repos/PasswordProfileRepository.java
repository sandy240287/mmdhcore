package com.apm.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apm.models.PasswordProfile;

public interface PasswordProfileRepository extends JpaRepository<PasswordProfile, Long> {
}
