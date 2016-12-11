package com.mmdh.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mmdh.models.MMDHUser;
import com.mmdh.models.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, String> {
    VerificationToken findByToken(String token);
    VerificationToken findByUser(MMDHUser user);
}
