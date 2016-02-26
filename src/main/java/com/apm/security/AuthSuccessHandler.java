package com.apm.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class AuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
     
	@Autowired
	private CustomUserDetailsService userService;
	
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_OK);
 
        super.onAuthenticationSuccess(request, response, authentication);
		String name = authentication.getName();
		UserDetails user = userService.loadUserByUsername(name);
		//user.setInvalidLoginCount(0);
		//user.setLastLoginDate(new Date());
		//userService.update(user);
		
    }
}
