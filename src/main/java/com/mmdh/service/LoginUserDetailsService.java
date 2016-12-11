package com.mmdh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mmdh.models.MMDHUser;
import com.mmdh.repos.MMDHUserRepository;

@Service
public class LoginUserDetailsService implements UserDetailsService {

	@Autowired
	MMDHUserRepository repo;

	@Override
	public MMDHUser loadUserByUsername(String username) throws UsernameNotFoundException {
		return repo.loadUserByUserName(username);
	}
	
	public MMDHUser authticateUserAndGetDetails(String username, String password) {
		return repo.authticateUserAndGetDetails(username, password);
	}
	
	
}
