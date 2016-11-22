package com.ricex.cartracker.android.service.reader;

/**
 * Created by Mitchell on 2016-11-21.
 */
public class BluetoothDeviceNotPairedException extends Exception {

    public BluetoothDeviceNotPairedException(String message) {
        super(message);
    }

    public BluetoothDeviceNotPairedException(String message, Throwable cause) {
        super(message, cause);
    }

}
