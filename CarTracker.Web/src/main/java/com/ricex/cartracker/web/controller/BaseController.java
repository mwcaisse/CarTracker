package com.ricex.cartracker.web.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ricex.cartracker.common.entity.auth.User;
import com.ricex.cartracker.web.auth.UserDetailsProxy;

public abstract class BaseController {

	protected BaseController() {
		
	}
	
	public User getCurrentUser() {		
		if (isAuthenticated()) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();			
			UserDetailsProxy userProxy = (UserDetailsProxy)auth.getPrincipal();
			return userProxy.getUser();
		}
		return null;
	}
	
	public boolean isAuthenticated() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (null == auth || auth instanceof AnonymousAuthenticationToken) {
			return false;
		}
		return auth.isAuthenticated();
	}
	
}
