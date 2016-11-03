package com.ricex.cartracker.android.service.persister.webservice;

import android.util.Log;

import com.ricex.cartracker.android.model.OBDReading;
import com.ricex.cartracker.android.service.persister.Persister;
import com.ricex.cartracker.android.settings.CarTrackerSettings;
import com.ricex.cartracker.androidrequester.request.exception.RequestException;
import com.ricex.cartracker.androidrequester.request.tracker.BulkUploadReadingRequest;
import com.ricex.cartracker.androidrequester.request.tracker.EndTripRequest;
import com.ricex.cartracker.androidrequester.request.tracker.StartTripRequest;
import com.ricex.cartracker.common.entity.Trip;
import com.ricex.cartracker.common.viewmodel.BulkUploadResult;
import com.ricex.cartracker.common.viewmodel.ReadingUpload;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Mitchell on 2/18/2016.
 */
public class WebServicePersister implements Persister {

    private static final String LOG_TAG = "ODBWEBSERVICEPERSISTER";

    private static final String CAR_VIN = "JF1VA1E66G9807558";

    private CarTrackerSettings settings;

    private Object monitor;

    private Object waitMonitor;

    private boolean running;

    private Map<String, PersisterReadingUpload> uploads;

    private Trip trip;

    public WebServicePersister(CarTrackerSettings settings) {
        this.settings = settings;

        monitor = new Object();
        waitMonitor = new Object();
        running = false;

        uploads = new HashMap<String, PersisterReadingUpload>();
    }

    @Override
    public void run() {

        running = true;

        startTrip();

        while (running) {
            try {

                synchronized (waitMonitor) {
                    waitMonitor.wait();
                }
                //we were awoke, check if we are still running
                if (!running) {
                    break;
                }

                if (null == trip) {
                    startTrip();
                }

                //we have been notified, most likely uploads is above the limit

                if (uploads.size() > 0) {
                    Log.i(LOG_TAG, "Starting a reading upload!");
                    List<ReadingUpload> readingUploads;
                    synchronized (monitor) {
                        readingUploads = getReadingUploads();
                    }

                    try {
                        List<BulkUploadResult> results = new BulkUploadReadingRequest(settings, trip, readingUploads).execute();

                        synchronized (monitor) {
                            for (BulkUploadResult result : results) {
                                if (result.isSuccessful()) {
                                    uploads.remove(result.getUuid());
                                }
                                else {
                                    PersisterReadingUpload upload = uploads.get(result.getUuid());
                                    if (null != upload) {
                                        upload.setTries(upload.getTries() + 1);
                                    }
                                }
                            }
                        }
                    }
                    catch (RequestException e) {
                        Log.e(LOG_TAG, "Error uploading reading data!", e);
                        continue;
                    }
                }


            }
            catch (InterruptedException e) {
                //eh do nothing?
            }
        }

        endTrip();


    }

    public List<ReadingUpload> getReadingUploads() {
        List<ReadingUpload> readingUploads = new ArrayList<ReadingUpload>();

        for (PersisterReadingUpload upload : uploads.values()) {
            ReadingUpload readingUpload = new ReadingUpload();

            readingUpload.setUuid(upload.getUid());
            readingUpload.setReadDate(upload.getReading().getReadDate());
            readingUpload.setTripId(trip.getId());
            readingUpload.setAirIntakeTemperature(convertStringToDouble(upload.getReading().getAirIntakeTemp()));
            readingUpload.setAmbientAirTemperature(convertStringToDouble(upload.getReading().getAmbientAirTemp()));
            readingUpload.setEngineCoolantTemperature(convertStringToDouble(upload.getReading().getEngineCoolantTemp()));
            readingUpload.setOilTemperature(convertStringToDouble(upload.getReading().getOilTemp()));
            readingUpload.setEngineRPM(convertStringToDouble(upload.getReading().getEngineRPM()));
            readingUpload.setSpeed(convertStringToDouble(upload.getReading().getSpeed()));
            readingUpload.setMassAirFlow(convertStringToDouble(upload.getReading().getMaf()));
            readingUpload.setThrottlePosition(convertStringToDouble(upload.getReading().getThrottlePosition()));
            readingUpload.setFuelType(upload.getReading().getFuelType());
            readingUpload.setFuelLevel(convertStringToDouble(upload.getReading().getFuelLevel()));

            //if there is a location associated with the reading, add it to the upload
            if (null != upload.getReading().getLocation()) {
                readingUpload.setLatitude(upload.getReading().getLocation().getLatitude());
                readingUpload.setLongitude(upload.getReading().getLocation().getLongitude());
            }

            readingUploads.add(readingUpload);
        }

        return readingUploads;
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
        try {
            trip = new StartTripRequest(settings, CAR_VIN).execute();
        }
        catch (RequestException e) {
            Log.e(LOG_TAG, "Error starting trip!", e);
        }
    }

    public void endTrip() {
        if (null != trip) {
            try {
                new EndTripRequest(settings, trip).execute();
            } catch (RequestException e) {
                Log.e(LOG_TAG, "Error ending trip!", e);
            }
        }
    }

    /** Persists the given OBDReading to the Web Service
     *
     * @param reading The reading to persist
     */
    public void persist(OBDReading reading) {
        PersisterReadingUpload upload = new PersisterReadingUpload();
        upload.setUid(UUID.randomUUID().toString());
        upload.setReading(reading);
        upload.setTries(0);
        upload.setStatus(0);

        synchronized (monitor) {
            uploads.put(upload.getUid(), upload);
            if (uploads.size() > 20) {
                synchronized (waitMonitor) {
                    waitMonitor.notify();
                }
            }
        }
    }

    public void stop() {
        running = false;
        synchronized (waitMonitor) {
            waitMonitor.notify();
        }
    }
}
