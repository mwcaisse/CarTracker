package com.ricex.cartracker.android.service.persister;

import com.ricex.cartracker.android.model.OBDReading;

/**
 * Created by Mitchell on 2/18/2016.
 */
public interface Persister extends Runnable {

    public void persist(OBDReading reading);

    public void stop();


}
