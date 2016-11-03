package com.ricex.cartracker.androidrequester.request.tracker;

import com.ricex.cartracker.androidrequester.request.AbstractRequest;
import com.ricex.cartracker.androidrequester.request.ApplicationPreferences;
import com.ricex.cartracker.androidrequester.request.RequestResponse;
import com.ricex.cartracker.androidrequester.request.exception.RequestException;
import com.ricex.cartracker.androidrequester.request.type.TripResponseType;
import com.ricex.cartracker.common.entity.Trip;

/**
 * Created by Mitchell on 2016-11-02.
 */
public class CreateTripRequest extends AbstractRequest<Trip> {

    private final Trip trip;

    /**
     * Creates a new instance of tje request with the Trip to create
     *
     * @param applicationPreferences
     */
    public CreateTripRequest(ApplicationPreferences applicationPreferences, Trip trip) {
        super(applicationPreferences);
        this.trip = trip;
    }

    @Override
    protected RequestResponse<Trip> executeRequest() throws RequestException {
        return postForObject(serverAddress + "/trip/", trip, new TripResponseType());
    }

}
