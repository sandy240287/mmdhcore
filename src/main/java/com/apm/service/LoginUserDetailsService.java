package com.apm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.apm.repos.APMUserRepository;
import com.apm.repos.models.APMUser;

@Service
public class LoginUserDetailsService implements UserDetailsService {

	@Autowired
	APMUserRepository repo;

	@Override
	public APMUser loadUserByUsername(String username) throws UsernameNotFoundException {
		return repo.loadUserByUserName(username);
	}
	
	public APMUser authticateUserAndGetDetails(String username, String password) {
		return repo.authticateUserAndGetDetails(username, password);
	}
	
	
}
