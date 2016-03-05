package com.apm.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.apm.repos.LoginUserDetails;
import com.apm.service.LoginUserDetailsService;

@Component
public class APIAuthenticationProvider implements AuthenticationProvider, UserDetailsChecker {
	protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
	
	@Autowired
	private LoginUserDetailsService userDetailsService;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// Determine username
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();

		LoginUserDetails userdetails = null;
		
		if(StringUtils.hasLength(username) && StringUtils.hasLength(password)){
			userdetails = userDetailsService.authticateUserAndGetDetails(username, password);
		if (userdetails != null) {
				return new UsernamePasswordAuthenticationToken(username, password, userdetails.getAuthorities());
			} else {
				userdetails = userDetailsService.loadUserByUsername(username);
				if (userdetails != null)
					throw new BadCredentialsException("BAD_PASSWORD");
				else
					throw new UsernameNotFoundException("NOT_FOUND");
			}
		}
		throw new UsernameNotFoundException("NOT_PROVIDED");
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

	@Override
	public void check(UserDetails user) {
		System.err.println("Inside check");
		if (!user.isAccountNonLocked()) {
			throw new LockedException(
					messages.getMessage("AbstractUserDetailsAuthenticationProvider.locked", "User account is locked"));
		}

		if (!user.isEnabled()) {
			throw new DisabledException(
					messages.getMessage("AbstractUserDetailsAuthenticationProvider.disabled", "User is disabled"));
		}
	}
}