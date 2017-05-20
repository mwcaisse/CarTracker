package com.ricex.cartracker.androidrequester.request.user;

import com.ricex.cartracker.androidrequester.request.AbstractRequest;
import com.ricex.cartracker.androidrequester.request.ApplicationPreferences;
import com.ricex.cartracker.androidrequester.request.RequestResponse;
import com.ricex.cartracker.androidrequester.request.exception.RequestException;
import com.ricex.cartracker.androidrequester.request.type.BooleanResponseType;
import com.ricex.cartracker.common.auth.AuthToken;
import com.ricex.cartracker.common.viewmodel.BooleanResponse;

/** A Server Request to login to the server to obtain a Session Token.
 * 
 * 	The login is performed with a previously aquired Authentication Token
 * 
 * @author Mitchell Caisse
 *
 */

public class LoginTokenRequest extends AbstractRequest<Boolean> {

	/** The user's username */
	private final String username;

	/** The user's authentication token */
	private final String token;

	/** Creates a new instance of Login Token Request
	 *
	 * @param applicationPreferences
	 * @param token
     */
	public LoginTokenRequest(ApplicationPreferences applicationPreferences, String token) {
		this(applicationPreferences, applicationPreferences.getUsername(), token);
	}

	/** Creates a new instance of Login Token Request
	 * 
	 * @param token The authorization token to use to login
	 */
	public LoginTokenRequest(ApplicationPreferences applicationPreferences, String username, String token) {
		super(applicationPreferences);
		this.username = username;
		this.token  = token;
	}

	/** Executes the request 
	 * 
	 * @return The AFTResponse representing the results of the request
	 * @throws RequestException If an error occurred while making the request 
	 */
	
	protected RequestResponse<Boolean> executeRequest() throws RequestException {
		AuthToken authToken = new AuthToken(username, token, getDeviceUID());
		return postForObject(getTokenLoginAddress(), authToken, new BooleanResponseType());
	}
	
}
