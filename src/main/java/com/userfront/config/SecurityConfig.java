package com.userfront.config;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.userfront.service.serviceImpl.UserSecurityService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private Environment env;
	
	@Qualifier("userSecurityService")
	@Autowired
	private UserSecurityService userSecurityService;
	
	private static final String SALT = "QWERRTzxccvvb1234";
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder()  {
		return new BCryptPasswordEncoder(12, new SecureRandom(SALT.getBytes()));
	}
	
	public static final String[] PUBLIC_MATCHERS = {
			"/webjars/**",
			"/css/**",
			"/",
			"/js/**",
			"/images/**",
			"/uploads/**",
			"/fonts/**",
			"/build/**",
			"/dist/**",
			"/pages/**",
			"/login/**",
			"/registration/**",
			"/logout/**",
			"/plugins/**",
			"/forgot-password/**",
			"/about/**",
			"/contact/**",
			"/console/**",
			"/register/**",
			"/error/**",
			"/dashboard/**",
			"/account/**",
			"/robots.txt"
			
	};
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.antMatchers(PUBLIC_MATCHERS).permitAll()
			.anyRequest().authenticated()
			.and()
			.csrf().disable().cors().disable()
			.formLogin().failureUrl("/index?error").defaultSuccessUrl("/dashboard").loginPage("/index").permitAll()
			.and()
			.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/?index")
			.deleteCookies("remember-me")
			.permitAll()
			.and()
			.rememberMe();
	}
	@Bean
	public AuthenticationManager customAuthenticationManager() throws Exception{
		return this.authenticationManager();
	}
	
	
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userSecurityService).passwordEncoder(passwordEncoder());
	}
}
