package com.ricex.cartracker.androidrequester.request.user;

import android.util.Log;

import com.ricex.cartracker.androidrequester.request.AbstractRequest;
import com.ricex.cartracker.androidrequester.request.ApplicationPreferences;
import com.ricex.cartracker.androidrequester.request.RequestResponse;
import com.ricex.cartracker.androidrequester.request.exception.RequestException;
import com.ricex.cartracker.androidrequester.request.type.StringResponseType;

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
	
	protected RequestResponse<String> executeRequest() throws RequestException {
		Log.w("CT_ATR", "Requesting Authentication token from server. Device UID: " + getDeviceUID());
		return postForObject(serverAddress + "user/token", getDeviceUID(), new StringResponseType());
	}

}
