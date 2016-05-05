package com.ricex.cartracker.androidrequester.request.tracker;

import com.ricex.cartracker.androidrequester.request.AbstractRequest;
import com.ricex.cartracker.androidrequester.request.ApplicationPreferences;
import com.ricex.cartracker.androidrequester.request.RequestResponse;
import com.ricex.cartracker.androidrequester.request.exception.RequestException;
import com.ricex.cartracker.androidrequester.request.type.TripResponseType;
import com.ricex.cartracker.common.entity.Trip;

public class StartTripRequest extends AbstractRequest<Trip> {

	private String vin;
	
	public StartTripRequest(ApplicationPreferences applicationPreferences, String vin) {
		super(applicationPreferences);
		this.vin = vin;
	}

	@Override
	protected RequestResponse<Trip> executeRequest() throws RequestException {
		return postForObject(serverAddress + "car/" + vin + "/trip/start", null, new TripResponseType());
	}

}
