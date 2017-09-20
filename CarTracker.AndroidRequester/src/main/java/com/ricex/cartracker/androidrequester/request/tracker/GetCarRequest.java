package com.ricex.cartracker.androidrequester.request.tracker;

import com.ricex.cartracker.androidrequester.request.AbstractRequest;
import com.ricex.cartracker.androidrequester.request.ApplicationPreferences;
import com.ricex.cartracker.androidrequester.request.RequestResponse;
import com.ricex.cartracker.androidrequester.request.exception.RequestException;
import com.ricex.cartracker.androidrequester.request.type.CarResponseType;
import com.ricex.cartracker.common.entity.Car;

/**
 * Created by Mitchell on 2017-09-20.
 */

public class GetCarRequest extends AbstractRequest<Car> {

    private final String vin;

    /** Creates a new instance of a request to fetch a car
     *
     * @param applicationPreferences
     */
    public GetCarRequest(ApplicationPreferences applicationPreferences, String vin) {
        super(applicationPreferences);
        this.vin = vin;
    }

    @Override
    protected RequestResponse<Car> executeRequest() throws RequestException {
        return getForObject(serverAddress + "/car/vin/{vin}", new CarResponseType(), vin);
    }


}
