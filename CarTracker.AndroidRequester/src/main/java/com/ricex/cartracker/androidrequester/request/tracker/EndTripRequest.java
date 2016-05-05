package com.ricex.cartracker.androidrequester.request.tracker;

import com.ricex.cartracker.androidrequester.request.AbstractRequest;
import com.ricex.cartracker.androidrequester.request.ApplicationPreferences;
import com.ricex.cartracker.androidrequester.request.RequestResponse;
import com.ricex.cartracker.androidrequester.request.exception.RequestException;
import com.ricex.cartracker.androidrequester.request.type.BooleanResponseType;
import com.ricex.cartracker.common.entity.Trip;

public class EndTripRequest extends AbstractRequest<Boolean> {

	private final long tripId;
	
	public EndTripRequest(ApplicationPreferences applicationPreferences, Trip trip) {
		this(applicationPreferences, trip.getId());
	}
	
	public EndTripRequest(ApplicationPreferences applicationPreferences, long tripId) {
		super(applicationPreferences);
		this.tripId = tripId;
	}
	

	@Override
	protected RequestResponse<Boolean> executeRequest() throws RequestException {
		return postForObject(serverAddress + "trip/{id}/end", null, new BooleanResponseType(), tripId);
	}

}
