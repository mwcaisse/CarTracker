package com.ricex.cartracker.androidrequester.request.tracker;

import com.ricex.cartracker.androidrequester.request.AbstractRequest;
import com.ricex.cartracker.androidrequester.request.ApplicationPreferences;
import com.ricex.cartracker.androidrequester.request.RequestResponse;
import com.ricex.cartracker.androidrequester.request.exception.RequestException;
import com.ricex.cartracker.androidrequester.request.type.TripResponseType;
import com.ricex.cartracker.common.entity.Car;
import com.ricex.cartracker.common.entity.Trip;

/** A request to start a new trip for a car
 * 
 * @author Mitchell
 *
 */
public class StartTripRequest extends AbstractRequest<Trip> {

	private String vin;
	
	/** Creates a new Start Trip request for the given car
	 * 
	 * @param applicationPreferences
	 * @param car
	 */
	public StartTripRequest(ApplicationPreferences applicationPreferences, Car car) {
		this(applicationPreferences, car.getVin());
	}
	
	/** Creates a new Start Trip request for the given car
	 * 
	 * @param applicationPreferences
	 * @param car
	 */
	public StartTripRequest(ApplicationPreferences applicationPreferences, String vin) {
		super(applicationPreferences);
		this.vin = vin;
	}

	@Override
	protected RequestResponse<Trip> executeRequest() throws RequestException {
		return postForObject(serverAddress + "car/{vin}/trip/start", null, new TripResponseType(), vin);
	}

}
