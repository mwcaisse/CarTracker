package com.ricex.cartracker.androidrequester.request;

import com.ricex.cartracker.androidrequester.request.user.AuthenticationTokenRequest;
import com.ricex.cartracker.androidrequester.request.user.LoginPasswordRequest;
import com.ricex.cartracker.androidrequester.request.user.LoginTokenRequest;

public abstract class AbstractRequestFactory {

	/** The Application Preferences to use for all Requests
	 * 
	 */
	protected ApplicationPreferences applicationPreferences;
	
	/** Creates a new Abstract Request Factory with the given application preferences
	 * 
	 * @param applicationPreferences The application preferences
	 */
	
	public AbstractRequestFactory(ApplicationPreferences applicationPreferences) {
		this.applicationPreferences = applicationPreferences;
	}
	
	/** Creates a new Authentication Token Request
	 * 
	 * @return the request
	 */
	public AuthenticationTokenRequest createAuthenticationTokenRequest() {
		return new AuthenticationTokenRequest(applicationPreferences);
	}
	
	/** Creates a new Login Password Request, with the given username + password
	 * 
	 * @param username The username
	 * @param password The password
	 * @return the request
	 */
	public LoginPasswordRequest createLoginPasswordRequest(String username, String password) {
		return new LoginPasswordRequest(applicationPreferences, username, password);
	}
	
	/** Creates a new Login Token Request
	 * 
	 * @param token The login token
	 * @return The request
	 */
	public LoginTokenRequest createLoginTokenRequest(String token) {
		return new LoginTokenRequest(applicationPreferences, token);
	}
	
}
