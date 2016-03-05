package com.apm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.apm.repos.LoginUserDetails;
import com.apm.repos.LoginUserDetailsRepository;

@Service
public class LoginUserDetailsService implements UserDetailsService {

	@Autowired
	LoginUserDetailsRepository repo;

	@Override
	public LoginUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return repo.loadUserByUserName(username);
	}
	
	public LoginUserDetails authticateUserAndGetDetails(String username, String password) {
		return repo.authticateUserAndGetDetails(username, password);
	}
	
	
}
