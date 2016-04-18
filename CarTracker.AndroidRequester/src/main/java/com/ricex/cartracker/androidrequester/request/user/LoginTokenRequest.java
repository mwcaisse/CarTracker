package com.ricex.cartracker.androidrequester.request.user;

import com.ricex.cartracker.androidrequester.request.AFTResponse;
import com.ricex.cartracker.androidrequester.request.AbstractRequest;
import com.ricex.cartracker.androidrequester.request.ApplicationPreferences;
import com.ricex.cartracker.androidrequester.request.exception.RequestException;
import com.ricex.cartracker.common.auth.AuthToken;
import com.ricex.cartracker.common.viewmodel.BooleanResponse;

/** A Server Request to login to the server to obtain a Session Token.
 * 
 * 	The login is performed with a previously aquired Authentication Token
 * 
 * @author Mitchell Caisse
 *
 */

public class LoginTokenRequest extends AbstractRequest<BooleanResponse> {
	
	/** The user's authentication token */
	private String token;
	
	/** Creates a new Instance of Password Request
	 * 
	 * @param token The authorization token to use to login
	 */
	public LoginTokenRequest(ApplicationPreferences applicationPreferences, String token) {		
		super(applicationPreferences);
		this.token  = token;
	}

	/** Executes the request 
	 * 
	 * @return The AFTResponse representing the results of the request
	 * @throws RequestException If an error occurred while making the request 
	 */
	
	protected AFTResponse<BooleanResponse> executeRequest() throws RequestException {
		AuthToken authToken = new AuthToken(token, getDeviceUID());
		return postForObject(serverAddress + "user/login/token", authToken, BooleanResponse.class);
	}
	
}
