package com.ricex.cartracker.androidrequester.request.tracker;

import com.ricex.cartracker.androidrequester.request.AbstractRequest;
import com.ricex.cartracker.androidrequester.request.ApplicationPreferences;
import com.ricex.cartracker.androidrequester.request.RequestResponse;
import com.ricex.cartracker.androidrequester.request.exception.RequestException;
import com.ricex.cartracker.androidrequester.request.type.BooleanResponseType;

/** Check if a car is registered by its VIN
 * 
 * @author Mitchell Caisse
 *
 */

public class IsCarRegisteredRequest extends AbstractRequest<Boolean>{

	private final String vin;
	
	public IsCarRegisteredRequest(ApplicationPreferences applicationPreferences, String vin) {
		super(applicationPreferences);
		this.vin = vin;
	}

	@Override
	protected RequestResponse<Boolean> executeRequest() throws RequestException {
		return getForObject(serverAddress + "/registered/{vin}", new BooleanResponseType(), vin);
	}

}
