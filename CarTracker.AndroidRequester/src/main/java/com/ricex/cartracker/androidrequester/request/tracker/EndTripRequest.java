package com.ricex.cartracker.androidrequester.request.tracker;

import com.ricex.cartracker.androidrequester.request.AbstractRequest;
import com.ricex.cartracker.androidrequester.request.ApplicationPreferences;
import com.ricex.cartracker.androidrequester.request.RequestResponse;
import com.ricex.cartracker.androidrequester.request.exception.RequestException;
import com.ricex.cartracker.androidrequester.request.type.BooleanResponseType;
import com.ricex.cartracker.common.entity.Trip;

/** Request to end an ongoing trip
 * 
 * @author Mitchell Caisse
 *
 */
public class EndTripRequest extends AbstractRequest<Boolean> {

	private final long tripId;
	
	/** Creates a new End Trip request to end the given trip
	 * 
	 * @param applicationPreferences
	 * @param trip
	 */
	public EndTripRequest(ApplicationPreferences applicationPreferences, Trip trip) {
		this(applicationPreferences, trip.getId());
	}
	
	/** Creates a new End Trip request to end the given trip
	 * 
	 * @param applicationPreferences
	 * @param trip
	 */
	public EndTripRequest(ApplicationPreferences applicationPreferences, long tripId) {
		super(applicationPreferences);
		this.tripId = tripId;
	}
	

	@Override
	protected RequestResponse<Boolean> executeRequest() throws RequestException {
		return postForObject(serverAddress + "trip/{id}/end", null, new BooleanResponseType(), tripId);
	}

}
