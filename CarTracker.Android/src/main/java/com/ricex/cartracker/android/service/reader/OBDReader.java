package com.ricex.cartracker.android.service.reader;

import com.ricex.cartracker.android.model.OBDReading;

/**
 * Created by Mitchell on 2/13/2016.
 */
public interface OBDReader {

    /*** Performs any needed initialization before read is called.
     *
     * @return If true continues the loop execution, if false execution stops
     */
    public boolean initialize();

    /** Reads OBD Data
     *
     * @return the OBD data read
     */
    public OBDReading read() throws ConnectionLostException;

}
