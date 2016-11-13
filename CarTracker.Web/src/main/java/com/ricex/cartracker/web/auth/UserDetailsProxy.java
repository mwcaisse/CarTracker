package com.ricex.cartracker.web.auth;

import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ricex.cartracker.common.entity.User;

public class UserDetailsProxy implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	private final User user;
	
	public UserDetailsProxy(User user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {	
		return null;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		Date expirationDate = user.getExpirationDate();
		return null == expirationDate || new Date().before(expirationDate);
	}

	@Override
	public boolean isAccountNonLocked() {
		return !user.isLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		Date passwordExpirationDate = user.getPasswordExpirationDate();
		return null == passwordExpirationDate || new Date().before(passwordExpirationDate);
	}

	@Override
	public boolean isEnabled() {
		return user.isActive();
	}
	
}
