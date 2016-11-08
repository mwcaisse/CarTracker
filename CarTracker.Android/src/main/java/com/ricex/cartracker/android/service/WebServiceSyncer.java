package com.ricex.cartracker.android.service;

import android.util.Log;

import com.ricex.cartracker.android.data.entity.RawReading;
import com.ricex.cartracker.android.data.entity.RawTrip;
import com.ricex.cartracker.android.data.entity.ReaderLog;
import com.ricex.cartracker.android.data.manager.RawReadingManager;
import com.ricex.cartracker.android.data.manager.RawTripManager;
import com.ricex.cartracker.android.data.manager.ReaderLogManager;
import com.ricex.cartracker.android.data.util.DatabaseHelper;
import com.ricex.cartracker.android.settings.CarTrackerSettings;
import com.ricex.cartracker.androidrequester.request.exception.RequestException;
import com.ricex.cartracker.androidrequester.request.tracker.BulkUploadReaderLogRequest;
import com.ricex.cartracker.androidrequester.request.tracker.CarTrackerRequestFactory;
import com.ricex.cartracker.common.entity.Trip;
import com.ricex.cartracker.common.viewmodel.BulkUploadResult;
import com.ricex.cartracker.common.viewmodel.ReaderLogUpload;
import com.ricex.cartracker.common.viewmodel.ReadingUpload;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mitchell on 2016-11-01.
 */
public class WebServiceSyncer {

    private static final String LOG_TAG = "CT_WSS";

    private static final int BULK_UPLOAD_SIZE = 50;

    private ReaderLogManager logManager;
    private RawTripManager tripManager;
    private RawReadingManager readingManager;
    private CarTrackerSettings settings;

    private CarTrackerRequestFactory requestFactory;

    public WebServiceSyncer(DatabaseHelper databaseHelper, CarTrackerSettings settings) {
        this.settings = settings;

        this.logManager = databaseHelper.getReaderLogManager();
        this.tripManager = databaseHelper.getTripManager();
        this.readingManager = databaseHelper.getReadingManager();

        this.requestFactory = new CarTrackerRequestFactory(settings);
    }


    public void fullSync() {
        syncLogs();
        syncTrips();
        syncReadings();
    }

    /** Synchronize all of the un-synchronized logs
     *
     */
    public void syncLogs() {
        List<ReaderLog> unsyncedLogs = logManager.getUnsynced();
        Map<Long, ReaderLog> unsyncedLogsMap = new HashMap<Long, ReaderLog>();

        if (null != unsyncedLogs && !unsyncedLogs.isEmpty()) {
            for (ReaderLog log : unsyncedLogs) {
                unsyncedLogsMap.put(log.getId(), log);
            }

            for (int startIndex = 0; startIndex < unsyncedLogs.size(); startIndex += BULK_UPLOAD_SIZE) {
                List<ReaderLog> toUpload = unsyncedLogs.subList(startIndex, Math.min(startIndex + BULK_UPLOAD_SIZE, unsyncedLogs.size()));
                List<ReaderLogUpload> uploads = new ArrayList<ReaderLogUpload>();

                for (ReaderLog log : toUpload) {
                    ReaderLogUpload upload = new ReaderLogUpload();

                    upload.setType(log.getType());
                    upload.setMessage(log.getMessage());
                    upload.setDate(log.getDate());
                    upload.setUuid(Long.toString(log.getId()));

                    uploads.add(upload);

                }
                try {
                    List<BulkUploadResult> results = requestFactory.createBulkUploadReaderLogRequest(uploads).execute();
                    for (BulkUploadResult result : results) {
                        if (result.isSuccessful()) {
                            ReaderLog readerLog = unsyncedLogsMap.get(Long.parseLong(result.getUuid()));
                            if (null != readerLog) {
                                readerLog.setServerId(result.getId());
                                readerLog.setSyncedWithServer(true);
                            }
                        }
                    }
                    //update the logs
                    logManager.update(toUpload);
                }
                catch (RequestException e) {
                    Log.w(LOG_TAG, "Error occured while creating reader logs on the server!", e);
                }
            }
        }
    }

    public void syncTrips() {
        List<RawTrip> unsyncedTrips = tripManager.getUnsynced();

        if (null != unsyncedTrips && !unsyncedTrips.isEmpty()) {
            for (RawTrip unsyncedTrip : unsyncedTrips) {

                Trip trip = new Trip();
                trip.setStartDate(unsyncedTrip.getStartDate());
                trip.setEndDate(unsyncedTrip.getEndDate());
                trip.setCarId(7);
                trip.setStatus(unsyncedTrip.getStatus());

                try {
                    trip = requestFactory.createCreateTripRequest(trip).execute();

                    unsyncedTrip.setServerId(trip.getId());
                    unsyncedTrip.setSyncedWithServer(true);
                    tripManager.update(unsyncedTrip);

                    syncReadings(unsyncedTrip);
                }
                catch (RequestException e) {
                    Log.w(LOG_TAG, "Error occured while creating a trip on the server!", e);
                }

            }
        }
    }

    /** Syncs any unsynced readings for synced trips
     *
     */
    public void syncReadings() {
        List<RawTrip> tripsWithUnscynedReadings = tripManager.getTripsWithUnsyncedReadings();

        if (null != tripsWithUnscynedReadings && !tripsWithUnscynedReadings.isEmpty()) {
            for (RawTrip trip : tripsWithUnscynedReadings) {
                syncReadings(trip);
            }
        }
    }

    /** Syncs the unsynced readings for the given trip
     *
     * @param trip
     */
    protected void syncReadings(RawTrip trip) {
        List<RawReading> unsyncedReadings = readingManager.getUnsyncedForTrip(trip.getId());
        Map<Long, RawReading> unsyncedReadingsMap = new HashMap<Long, RawReading>();

        for (RawReading reading : unsyncedReadings) {
            unsyncedReadingsMap.put(reading.getId(), reading);
        }

        for (int startIndex = 0; startIndex < unsyncedReadings.size(); startIndex += BULK_UPLOAD_SIZE) {
            List<RawReading> toUpload = unsyncedReadings.subList(startIndex, Math.min(startIndex + BULK_UPLOAD_SIZE, unsyncedReadings.size()));
            List<ReadingUpload> uploads = new ArrayList<ReadingUpload>();

            for (RawReading rawReading : toUpload) {
                ReadingUpload upload = new ReadingUpload();

                upload.setUuid(Long.toString(rawReading.getId()));
                upload.setReadDate(rawReading.getReadDate());
                upload.setTripId(trip.getServerId());
                upload.setLatitude(rawReading.getLatitude());
                upload.setLongitude(rawReading.getLongitude());
                upload.setAirIntakeTemperature(rawReading.getAirIntakeTemperature());
                upload.setAmbientAirTemperature(rawReading.getAmbientAirTemperature());
                upload.setEngineCoolantTemperature(rawReading.getEngineCoolantTemperature());
                upload.setOilTemperature(rawReading.getOilTemperature());
                upload.setEngineRPM(rawReading.getEngineRPM());
                upload.setSpeed(rawReading.getSpeed());
                upload.setMassAirFlow(rawReading.getMassAirFlow());
                upload.setThrottlePosition(rawReading.getThrottlePosition());
                upload.setFuelType(rawReading.getFuelType());
                upload.setFuelLevel(rawReading.getFuelLevel());

                uploads.add(upload);
            }

            try {
                List<BulkUploadResult> results = requestFactory.createBulkUploadReadingRequest(trip.getServerId(), uploads).execute();
                for (BulkUploadResult result : results) {
                    if (result.isSuccessful()) {
                        RawReading reading = unsyncedReadingsMap.get(Long.parseLong(result.getUuid()));
                        if (null != reading) {
                            reading.setServerId(result.getId());
                            reading.setSyncedWithServer(true);
                        }
                    }
                }
                //update the readings with thier server id + synced to server flag
                readingManager.update(toUpload);
            }
            catch (RequestException e) {
                Log.w(LOG_TAG, "Error occured while creating readings on the server!", e);
            }


        }

    }


}
