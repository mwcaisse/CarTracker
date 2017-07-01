package com.ricex.cartracker.android.service.persister;

import android.util.Log;

import com.ricex.cartracker.android.data.entity.RawReading;
import com.ricex.cartracker.android.data.entity.RawTrip;
import com.ricex.cartracker.android.data.manager.RawReadingManager;
import com.ricex.cartracker.android.data.manager.RawTripManager;
import com.ricex.cartracker.android.data.util.DatabaseHelper;
import com.ricex.cartracker.android.model.OBDReading;
import com.ricex.cartracker.android.service.WebServiceSyncer;
import com.ricex.cartracker.android.settings.CarTrackerSettings;
import com.ricex.cartracker.common.entity.Trip;
import com.ricex.cartracker.common.entity.TripStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Mitchell on 2016-10-29.
 */
public class DatabasePersister implements Persister {

    private static final String LOG_TAG = "CT_DB_PERSISTER";

    private static final String CAR_VIN = "JF1VA1E66G9807558";

    private static final int BUFFER_SIZE = 100;

    private CarTrackerSettings settings;

    private DatabaseHelper databaseHelper;

    private RawTripManager tripManager;

    private RawReadingManager readingManager;

    private Object monitor;

    private Object waitMonitor;

    private Object stopMonitor;

    private boolean running;

    private List<OBDReading> readings;

    private RawTrip trip;

    private WebServiceSyncer webServiceSyncer;

    public DatabasePersister(CarTrackerSettings settings, DatabaseHelper databaseHelper) {
        this.settings = settings;
        this.databaseHelper = databaseHelper;
        this.webServiceSyncer = new WebServiceSyncer(databaseHelper, settings);

        monitor = new Object();
        waitMonitor = new Object();
        stopMonitor = new Object();
    }

    @Override
    public void run() {

        running = true;

        readingManager = databaseHelper.getReadingManager();
        tripManager = databaseHelper.getTripManager();

        readings = new ArrayList<OBDReading>();

        startTrip();

        while (running) {
            try {
                synchronized (waitMonitor) {
                    waitMonitor.wait();
                }

                if (null == trip) {
                    startTrip();
                }

                if (readings.size() > BUFFER_SIZE) {
                    persistReadings();
                }

                //do this check at the end, that way it still finishes the upload / exist gracefully.
                if (!running) {
                    Log.i(LOG_TAG, "Database Persister run we aren't running, breaking loop");
                    break;
                }
            }
            catch (InterruptedException e) {
                //do nothing
            }
        }

        //emd tje trip
        endTrip();

        //persist any readings remaining in the buffer
        persistReadings();

        webServiceSyncer.fullSync();

        synchronized (stopMonitor) {
            stopMonitor.notifyAll();
        }

        Log.i(LOG_TAG, "Database Persister run returning.");

    }

    protected void persistReadings() {
        synchronized (monitor) {
            List<RawReading> toCreate = getRawReadings();
            if (readingManager.create(toCreate)) {
                readings.clear();
            }
            else {
                //woop woop, error here
                Log.w(LOG_TAG, "Couldn't persist readings to database!");
            }
        }
    }

    List<RawReading> getRawReadings() {
        List<RawReading> rawReadings = new ArrayList<RawReading>();

        for (OBDReading reading : readings) {
            RawReading rawReading = new RawReading();

            rawReading.setReadDate(reading.getReadDate());
            rawReading.setTrip(trip);
            rawReading.setAirIntakeTemperature(convertStringToDouble(reading.getAirIntakeTemp()));
            rawReading.setAmbientAirTemperature(convertStringToDouble(reading.getAmbientAirTemp()));
            rawReading.setEngineCoolantTemperature(convertStringToDouble(reading.getEngineCoolantTemp()));
            rawReading.setOilTemperature(convertStringToDouble(reading.getOilTemp()));
            rawReading.setEngineRPM(convertStringToDouble(reading.getEngineRPM()));
            rawReading.setSpeed(convertStringToDouble(reading.getSpeed()));
            rawReading.setMassAirFlow(convertStringToDouble(reading.getMaf()));
            rawReading.setThrottlePosition(convertStringToDouble(reading.getThrottlePosition()));
            rawReading.setFuelType(reading.getFuelType());
            rawReading.setFuelLevel(convertStringToDouble(reading.getFuelLevel()));

            if (null != reading.getLocation()) {
                rawReading.setLatitude(reading.getLocation().getLatitude());
                rawReading.setLongitude(reading.getLocation().getLongitude());
            }

            rawReadings.add(rawReading);
        }

        return rawReadings;
    }

    private double convertStringToDouble(String val) {
        val.replaceAll("[^\\d.-]", "");
        try {
            return Double.parseDouble(val);
        }
        catch (NumberFormatException e) {
            return 0;
        }
    }

    public void startTrip() {
        trip = new RawTrip();
        trip.setStartDate(new Date());
        trip.setStatus(TripStatus.STARTED);

        if (!tripManager.create(trip)) {
            Log.w(LOG_TAG, "Could not start trip!");
            running = false;
        }
    }

    public void endTrip() {
        if (null != trip) {
            trip.setEndDate(new Date());
            trip.setStatus(TripStatus.FINISHED);

            if (!tripManager.update(trip)) {
                Log.w(LOG_TAG, "Could not end trip!");
            }
        }
    }

    @Override
    public void persist(OBDReading reading) {
        synchronized (monitor) {
            readings.add(reading);

            if (readings.size() > BUFFER_SIZE) {
                synchronized (waitMonitor) {
                    waitMonitor.notify();
                }
            }
        }
    }

    @Override
    public void stop() {
        if (running) {
            running = false;
            synchronized (waitMonitor) {
                waitMonitor.notify();
            }

            Log.i(LOG_TAG, "Database Persister stop waiting on stopMonitor");
            try {
                synchronized (stopMonitor)  {
                    stopMonitor.wait();
                }
            }
            catch (InterruptedException e) {

            }

            Log.i(LOG_TAG, "Database Persister stop, no longer waiting on stopMonitor");
        }
    }

}
