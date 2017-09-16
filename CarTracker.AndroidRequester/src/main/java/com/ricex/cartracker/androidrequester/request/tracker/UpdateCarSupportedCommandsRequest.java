package com.ricex.cartracker.androidrequester.request.tracker;

import com.ricex.cartracker.androidrequester.request.AbstractRequest;
import com.ricex.cartracker.androidrequester.request.ApplicationPreferences;
import com.ricex.cartracker.androidrequester.request.RequestResponse;
import com.ricex.cartracker.androidrequester.request.exception.RequestException;
import com.ricex.cartracker.androidrequester.request.type.BooleanResponseType;
import com.ricex.cartracker.common.entity.CarSupportedCommands;

/**
 * Created by Mitchell on 2017-09-15.
 */

public class UpdateCarSupportedCommandsRequest extends AbstractRequest<Boolean> {


    private String vin;
    private CarSupportedCommands supportedCommands;

    /**
     * Creates a new instance of AbstractRequest. Initializes the required fields
     *
     * @param applicationPreferences
     */
    public UpdateCarSupportedCommandsRequest(ApplicationPreferences applicationPreferences,
                                             String vin,
                                             CarSupportedCommands supportedCommands) {
        super(applicationPreferences);

        this.vin = vin;
        this.supportedCommands = supportedCommands;
    }

    @Override
    protected RequestResponse<Boolean> executeRequest() throws RequestException {
        return postForObject(serverAddress + "car/{vin}/supportedCommands/", supportedCommands,
                new BooleanResponseType(), vin);
    }
}
