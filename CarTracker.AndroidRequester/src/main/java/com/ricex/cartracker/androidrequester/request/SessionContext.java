package com.ricex.cartracker.androidrequester.request;

import org.apache.commons.lang3.StringUtils;

/** The context for handling the session with the webserver
 * 
 * @author Mitchell Caisse
 *
 */

public enum SessionContext {
	
	/** The singleton instance of the session context */
	INSTANCE;	
	
	/** The session token to use when making requests */
	private String sessionToken;	
	
	/** Creates a new instance of Session Context 
	 */
	private SessionContext() {
		sessionToken = "";
	}
	
	/** Checks if we currently have a session token
	 * 
	 * @return True if we have a session token, false otherwise
	 */
	public boolean hasSessionToken() {
		return StringUtils.isNotEmpty(sessionToken);
	}
	
	/** Checks if we need a Session token
	 * 
	 * @return True if a session token is needed, false otherwise
	 */
	public boolean needSessionToken() {
		return StringUtils.isEmpty(sessionToken);
	}
	
	/** Invalidates the current session token
	 * 
	 */
	
	public void invalidateSessionToken() {
		sessionToken = null;
	}
	
	/**
	 * @return the session token
	 */
	
	public String getSessionToken() {
		return sessionToken;
	}
	
	/**
	 * 
	 * @param sessionToken The sesion token to set
	 */
	
	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}

}
