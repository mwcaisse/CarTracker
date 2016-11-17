package com.ricex.cartracker.web.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.ricex.cartracker.common.entity.auth.User;
import com.ricex.cartracker.data.manager.auth.UserManager;

public class ProxyUserDetailsService implements UserDetailsService {

	private final UserManager userManager;
	
	public ProxyUserDetailsService(UserManager userManager) {
		this.userManager = userManager;
	}
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userManager.getByUsername(username);		
		if (null == user) {
			throw new UsernameNotFoundException("No user with the username: " + username + " was found!");
		}		
		return new UserDetailsProxy(user);
	}

}
