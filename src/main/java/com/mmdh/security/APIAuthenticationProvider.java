package com.mmdh.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.mmdh.models.MMDHUser;
import com.mmdh.service.LoginUserDetailsService;

@Component
public class APIAuthenticationProvider implements AuthenticationProvider {
	protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

	@Autowired
	private LoginUserDetailsService userDetailsService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// Determine username
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();

		MMDHUser userdetails = null;

		if (StringUtils.hasLength(username) && StringUtils.hasLength(password)) {
			userdetails = userDetailsService.authticateUserAndGetDetails(username, password);
			if (userdetails != null) {
				return new UsernamePasswordAuthenticationToken(username, password, userdetails.getAuthorities());
			} else if ((userdetails = userDetailsService.loadUserByUsername(username)) != null) {
				if (!userdetails.isEnabled())
					throw new DisabledException("USER_DISABLED");
				else if (!userdetails.isAccountNonExpired())
					throw new AccountExpiredException("ACCOUNT_EXPIRED");
				else if (!userdetails.isCredentialsNonExpired())
					throw new AccountExpiredException("CREDENTIAL_EXPIRED");
				else
					throw new BadCredentialsException("BAD_PASSWORD");
			}else
				throw new UsernameNotFoundException("NOT_FOUND");
		}else
			throw new UsernameNotFoundException("NOT_PROVIDED");
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
	
}