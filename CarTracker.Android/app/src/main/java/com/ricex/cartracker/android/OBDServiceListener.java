package com.ricex.cartracker.android;

/**
 * Created by Mitchell on 1/30/2016.
 */
public interface OBDServiceListener {

    /**
     *  Called when new OBD Data has been read
     * @param data
     */
    public void obdDataRead(OBDData data);

}
