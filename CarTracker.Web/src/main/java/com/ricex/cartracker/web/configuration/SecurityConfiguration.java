package com.ricex.cartracker.web.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.ricex.cartracker.web.auth.ProxyUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter  {

	private static Logger log = LoggerFactory.getLogger(SecurityConfiguration.class);
	
	@Autowired(required=true)
	public ApplicationConfiguration applicationConfig;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable() // just for now
			.authorizeRequests()
				.antMatchers("/app/**", "/css/**", "/images/**", "/js/**").permitAll()
				.antMatchers("/login", "/register", "/api/user/register").permitAll()
				.anyRequest().authenticated()				
				.and()
			.formLogin()
				.loginPage("/login")
				.permitAll()
				.and()
			.logout()
				.invalidateHttpSession(true)
				.logoutSuccessUrl("/login?logout")
				.logoutUrl("/logout")
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));	
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService())
			.passwordEncoder(applicationConfig.proxyPasswordEncoder());
	}
	
	@Bean
	public UserDetailsService userDetailsService()  {
		try {
			return new ProxyUserDetailsService(applicationConfig.userManager());
		}
		catch (Exception e) {
			log.error("Error loading User Details Service.", e);
			return super.userDetailsService();
		}
	}

}
