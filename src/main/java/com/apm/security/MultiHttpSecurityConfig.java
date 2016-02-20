package com.apm.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class MultiHttpSecurityConfig {
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("user").password("password").roles("USER").and().withUser("admin")
				.password("password").roles("USER", "ADMIN");
	}

	@Configuration
	@Order(2)
	public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
		protected void configure(HttpSecurity http) throws Exception {
			http.antMatcher("/v1/**").authorizeRequests().anyRequest().hasRole("USER").and().httpBasic();
		}
	}

	@Configuration
	@Order(1)
	public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
		@Autowired
		public void registerGlobalAuthentication(AuthenticationManagerBuilder auth) throws Exception {
			auth.authenticationProvider(customAuthenticationProvider());
		}

		@Bean
		AuthenticationProvider customAuthenticationProvider() {
			CustomAuthenticationProvider impl = new CustomAuthenticationProvider();
			impl.setUserDetailsService(customUserDetailsService());
			return impl;
		}

		@Bean
		UserDetailsService customUserDetailsService() {
			return new CustomUserDetailsService();
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests().anyRequest().authenticated().and().formLogin();
			//.loginPage("/login").permitAll();// enable this for a custom login page url
		
			http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
			
		}
	}
}