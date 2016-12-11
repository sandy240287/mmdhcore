package com.mmdh.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mmdh.models.PasswordProfile;

public interface PasswordProfileRepository extends JpaRepository<PasswordProfile, Long> {
}
