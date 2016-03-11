package com.apm.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apm.repos.models.APMUser;
import com.apm.repos.models.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, String> {
	 
    VerificationToken findByToken(String token);
 
    VerificationToken findByUser(APMUser user);
    
    @SuppressWarnings("unchecked")
	VerificationToken save(VerificationToken token);
}
