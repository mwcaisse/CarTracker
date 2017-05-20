package com.ricex.cartracker.androidrequester.request.user;

import com.ricex.cartracker.androidrequester.request.AbstractRequest;
import com.ricex.cartracker.androidrequester.request.ApplicationPreferences;
import com.ricex.cartracker.androidrequester.request.RequestResponse;
import com.ricex.cartracker.androidrequester.request.exception.RequestException;
import com.ricex.cartracker.androidrequester.request.type.BooleanResponseType;
import com.ricex.cartracker.common.auth.AuthUser;
import com.ricex.cartracker.common.viewmodel.BooleanResponse;

/** A Server Request to login to the server to obtain a Session Token.
 * 
 * 	The login is performed with a username and password
 * 
 * @author Mitchell Caisse
 *
 */
public class LoginPasswordRequest extends AbstractRequest<Boolean> {

	/** The user's username */
	private final String username;
	
	/** The user's password */
	private final String password;
	
	/** Creates a new Instance of Password Request
	 * 
	 * @param username The username  to login with
	 * @param password The password to login with
	 */
	public LoginPasswordRequest(ApplicationPreferences applicationPreferences, String username, String password) {
		super(applicationPreferences);
		this.username = username;
		this.password = password;
	}

	/** Executes the request 
	 * 
	 * @return The AFTResponse representing the results of the request
	 * @throws RequestException If an error occurred while making the request 
	 */
	
	protected RequestResponse<Boolean> executeRequest() throws RequestException {
		AuthUser user = new AuthUser(username, password);
		return postForObject(getPasswordLoginAddress(), user, new BooleanResponseType());
	}

}
