package com.ricex.cartracker.common.auth;

import java.io.Serializable;

public class AuthToken implements Serializable {
	
	/** The authentication token */
	private String authenticationToken;
	
	/** The uid of the device the authentication request is from */
	private String deviceUuid;
	
	/** The username of the user */
	private String username;
	
	/** Creates a new instance of AuthToken
	 * 
	 */
	public AuthToken() {
		
	}
	
	/** Creates a new instance of AuthToken and initializes the fields
	 * 
	 * @param authenticationToken The authorizationToken
	 * @param deviceUid The deviceUid
	 */
	
	public AuthToken(String username, String authenticationToken, String deviceUuid) {
		this.authenticationToken = authenticationToken;
		this.deviceUuid = deviceUuid;
		this.username = username;
	}

	/**
	 * @return the authenticationToken
	 */
	public String getAuthenticationToken() {
		return authenticationToken;
	}

	/**
	 * @param authenticationToken the authenticationToken to set
	 */
	public void setAuthenticationToken(String authenticationToken) {
		this.authenticationToken = authenticationToken;
	}

	/**
	 * @return the deviceUuid
	 */
	public String getDeviceUuid() {
		return deviceUuid;
	}

	/**
	 * @param deviceUid the deviceUid to set
	 */
	public void setDeviceUuid(String deviceUuid) {
		this.deviceUuid = deviceUuid;
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
	
}
