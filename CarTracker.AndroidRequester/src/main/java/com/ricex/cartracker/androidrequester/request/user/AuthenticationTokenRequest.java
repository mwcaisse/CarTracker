package com.ricex.cartracker.androidrequester.request.user;

import com.ricex.cartracker.androidrequester.request.AFTResponse;
import com.ricex.cartracker.androidrequester.request.AbstractRequest;
import com.ricex.cartracker.androidrequester.request.ApplicationPreferences;
import com.ricex.cartracker.androidrequester.request.exception.RequestException;

/** Request to fetch an Authentication Token from the server, to use for future authentication/login requests
 * 
 * @author Mitchell Caisse
 *
 */

public class AuthenticationTokenRequest extends AbstractRequest<String> {

	/** Creates a new instance of AuthenticationTokenRequest
	 * 
	 */
	
	public AuthenticationTokenRequest(ApplicationPreferences applicationPreferences) {
		super(applicationPreferences);		
	}
	
	/** Executes the request 
	 * 
	 * @return The AFTResponse representing the results of the request
	 * @throws RequestException If an error occurred while making the request
	 */
	
	protected AFTResponse<String> executeRequest() throws RequestException {
		return postForObject(serverAddress + "user/token", getDeviceUID(), String.class);
	}

}
