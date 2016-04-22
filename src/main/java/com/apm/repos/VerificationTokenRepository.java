package com.apm.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apm.models.APMUser;
import com.apm.models.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, String> {
    VerificationToken findByToken(String token);
    VerificationToken findByUser(APMUser user);
}
