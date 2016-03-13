package com.ricex.cartracker.android.service;

import com.ricex.cartracker.android.model.OBDReading;

/**
 * Created by Mitchell on 1/30/2016.
 */
public interface OBDServiceListener {

    /**
     *  Called when new OBD Data has been read
     * @param data
     */
    public void obdDataRead(OBDReading data);

    /** Handles a message from the service
     *
     * @param message The message
     */
    public void onMessage(String message);

    /** Called when the service stops
     *
     */
    public void onServiceStopped();
}
