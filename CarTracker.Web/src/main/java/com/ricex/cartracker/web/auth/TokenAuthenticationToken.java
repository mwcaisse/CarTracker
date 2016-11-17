package com.ricex.cartracker.web.auth;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import com.ricex.cartracker.common.entity.auth.UserAuthenticationToken;

public class TokenAuthenticationToken extends AbstractAuthenticationToken {	
	
	private final UserAuthenticationToken authenticationToken;
	
	private final UserDetails user;
	
	private boolean authenticated;
	
	public TokenAuthenticationToken(UserAuthenticationToken authenticationToken) {
		super(new UserDetailsProxy(authenticationToken.getUser()).getAuthorities());
		this.authenticationToken = authenticationToken; 
		this.user = new UserDetailsProxy(authenticationToken.getUser());
		
		authenticated = false;
	}

	@Override
	public Object getCredentials() {
		return authenticationToken;
	}

	@Override
	public Object getPrincipal() {
		return user;
	}
	
	@Override
	public boolean isAuthenticated() {
		return authenticated;
	}
	
	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		if (!isAuthenticated) {
			throw new IllegalArgumentException("Can not revoke authentication!");
		}
		if (!authenticationToken.isValid()) {
			throw new IllegalArgumentException("Authentication Token is not valid. Cannot be authenticated");
		}
		authenticated = isAuthenticated;
	}	
	
}