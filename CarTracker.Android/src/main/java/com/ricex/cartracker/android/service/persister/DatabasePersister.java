package com.ricex.cartracker.android.service.persister;

import android.util.Log;

import com.ricex.cartracker.android.data.entity.RawReading;
import com.ricex.cartracker.android.data.entity.RawTrip;
import com.ricex.cartracker.android.data.manager.RawReadingManager;
import com.ricex.cartracker.android.data.manager.RawTripManager;
import com.ricex.cartracker.android.data.util.DatabaseHelper;
import com.ricex.cartracker.android.model.OBDReading;
import com.ricex.cartracker.android.settings.CarTrackerSettings;
import com.ricex.cartracker.common.entity.Trip;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Mitchell on 2016-10-29.
 */
public class DatabasePersister implements Persister {

    private static final String LOG_TAG = "CT_DB_PERSISTER";

    private static final String CAR_VIN = "JF1VA1E66G9807558";

    private static final int BUFFER_SIZE = 25;

    private CarTrackerSettings settings;

    private DatabaseHelper databaseHelper;

    private RawTripManager tripManager;

    private RawReadingManager readingManager;

    private Object monitor;

    private Object waitMonitor;

    private boolean running;

    private List<OBDReading> readings;

    private RawTrip trip;

    public DatabasePersister(CarTrackerSettings settings, DatabaseHelper databaseHelper) {
        this.settings = settings;
        this.databaseHelper = databaseHelper;
    }

    @Override
    public void run() {

        running = true;

        readingManager = databaseHelper.getReadingManager();
        tripManager = databaseHelper.getTripManager();

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

                //do this check at the end, that way it still finishes the upload / exist gracefully.
                if (!running) {
                    break;
                }
            }
            catch (InterruptedException e) {
                //do nothing
            }
        }

        endTrip();

    }

    List<RawReading> getRawReadings() {
        List<RawReading> rawReadings = new ArrayList<RawReading>();

        for (OBDReading reading : readings) {
            RawReading rawReading = new RawReading();

            rawReading.setReadDate(reading.getReadDate());
            rawReading.setTripId(trip.getId());
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

        if (!tripManager.create(trip)) {
            Log.w(LOG_TAG, "Could not start trip!");
            running = false;
        }
    }

    public void endTrip() {
        if (null != trip) {
            trip.setEndDate(new Date());

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
        running = false;
        synchronized (waitMonitor) {
            waitMonitor.notify();
        }
    }

}
