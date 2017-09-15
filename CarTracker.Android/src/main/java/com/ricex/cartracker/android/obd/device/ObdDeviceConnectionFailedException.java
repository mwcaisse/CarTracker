package com.ricex.cartracker.android.obd.device;

/**
 * Created by Mitchell on 2017-09-15.
 */

public class ObdDeviceConnectionFailedException extends Exception {

    public ObdDeviceConnectionFailedException(String message) {
        super(message);
    }

    public ObdDeviceConnectionFailedException(String message, Throwable cause) {
        super(message, cause);
    }


}
