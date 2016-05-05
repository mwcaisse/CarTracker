package com.ricex.cartracker.androidrequester.request.tracker;

import com.ricex.cartracker.androidrequester.request.AbstractRequest;
import com.ricex.cartracker.androidrequester.request.ApplicationPreferences;
import com.ricex.cartracker.androidrequester.request.RequestResponse;
import com.ricex.cartracker.androidrequester.request.exception.RequestException;
import com.ricex.cartracker.androidrequester.request.type.CarResponseType;
import com.ricex.cartracker.common.entity.Car;

/** Request to Create/Register a new car
 * 
 * @author Mitchell Caisse
 *
 */

public class CreateCarRequest extends AbstractRequest<Car> {

	private final Car car;
	
	/** Creates a new instance of the request with the car to create
	 * 
	 * @param applicationPreferences
	 * @param car
	 */
	public CreateCarRequest(ApplicationPreferences applicationPreferences, Car car) {
		super(applicationPreferences);
		this.car = car;
	}

	@Override
	protected RequestResponse<Car> executeRequest() throws RequestException {
		return postForObject(serverAddress + "/car/", car, new CarResponseType());
	}

}
