package com.apm.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class AuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	
	@Autowired
	private CustomUserDetailsService userService;
    
	@Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        
        String userName = request.getParameter("username");
        UserDetails userDetails = null;
        if(StringUtils.hasLength(userName))
        	userDetails = userService.loadUserByUsername(userName);
		
        if (userDetails != null) {
			//userDetails.setInvalidLoginCount(user.getInvalidLoginCount() + 1);
			//userService.update(userDetails);
		}
		
		PrintWriter writer = response.getWriter();
        writer.write(exception.getMessage());
        writer.flush();
    }
}