package com.ricex.cartracker.android.service.reader;

/**
 * Created by Mitchell on 2016-09-24.
 */
public class ConnectionLostException extends Exception {

    public ConnectionLostException(String message) {
        super(message);
    }

    public ConnectionLostException(String message, Throwable cause) {
        super(message, cause);
    }
}
