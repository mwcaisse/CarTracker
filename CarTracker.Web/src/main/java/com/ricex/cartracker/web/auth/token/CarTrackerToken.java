package com.ricex.cartracker.web.auth.token;

import java.util.Date;

import org.springframework.security.core.Authentication;

public class CarTrackerToken implements Token {

	private final String id;
	
	private final String username;
	
	private final Authentication authentication;
	
	private final Date createDate;
	
	private final Date expirationDate;
	
	public CarTrackerToken(String id, String username, Authentication authentication, Date expirationDate) {
		this.id = id;
		this.username = username;
		this.authentication = authentication;
		this.createDate = new Date();
		this.expirationDate = expirationDate;
	}
	
	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public Authentication getAuthentication() {
		return authentication;
	}

	@Override
	public boolean isExpired() {
		return new Date().after(expirationDate);
	}
	
	public Date getCreateDate() {
		return createDate;
	}
	
	public boolean equals(Object o) {
		if (o instanceof Token) {
			Token other = (Token) o;
			return other.getId().equals(this.id);
		}
		return false;
	}
	
	public int hashCode() {
		return id.hashCode();
	}

}
