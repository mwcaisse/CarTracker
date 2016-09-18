package com.ricex.cartracker.android.service.reader.gps;

import com.ricex.cartracker.android.model.GPSLocation;

/**
 * Created by Mitchell on 2016-05-21.
 */
public interface GPSReader {

    public void initialize();

    public void start();

    public void stop();

    public GPSLocation getCurrentLocation();
}
