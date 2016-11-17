package com.ricex.cartracker.web.auth.token;

import org.springframework.security.core.Authentication;

public interface Token {

	public String getId();
	
	public String getUsername();
	
	public Authentication getAuthentication();
	
	public boolean isExpired();
	
}
