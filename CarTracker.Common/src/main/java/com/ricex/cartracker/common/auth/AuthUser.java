package com.ricex.cartracker.common.auth;

import java.io.Serializable;

/** Container for a user's login information, username and password, when logging in through the API.
 * 
 * @author Mitchell Caisse
 *
 */
public class AuthUser implements Serializable {	

	/** The user's username */
	private String username;
	
	/** The user's password */
	private String password;	

	/** Constructs a new AuthUser
	 */
	public AuthUser() {
		
	}
	
	/** Constructs a new AuthUser with the specified username and password
	 * 
	 * @param username the username
	 * @param password the password
	 */	
	public AuthUser(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
}
