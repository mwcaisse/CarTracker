package com.ricex.cartracker.androidrequester.request.tracker;

import com.ricex.cartracker.androidrequester.request.AbstractRequest;
import com.ricex.cartracker.androidrequester.request.ApplicationPreferences;
import com.ricex.cartracker.androidrequester.request.RequestResponse;
import com.ricex.cartracker.androidrequester.request.exception.RequestException;
import com.ricex.cartracker.androidrequester.request.type.CarResponseType;
import com.ricex.cartracker.common.entity.Car;

public class CreateCarRequest extends AbstractRequest<Car> {

	private final Car car;
	
	public CreateCarRequest(ApplicationPreferences applicationPreferences, Car car) {
		super(applicationPreferences);
		this.car = car;
	}

	@Override
	protected RequestResponse<Car> executeRequest() throws RequestException {
		return postForObject(serverAddress + "/car/", car, new CarResponseType());
	}

}
