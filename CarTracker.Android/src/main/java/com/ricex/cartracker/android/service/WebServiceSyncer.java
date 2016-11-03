package com.ricex.cartracker.android.service;

import com.ricex.cartracker.android.data.entity.ReaderLog;
import com.ricex.cartracker.android.data.manager.RawReadingManager;
import com.ricex.cartracker.android.data.manager.RawTripManager;
import com.ricex.cartracker.android.data.manager.ReaderLogManager;
import com.ricex.cartracker.android.data.util.DatabaseHelper;
import com.ricex.cartracker.android.settings.CarTrackerSettings;
import com.ricex.cartracker.androidrequester.request.exception.RequestException;
import com.ricex.cartracker.androidrequester.request.tracker.BulkUploadReaderLogRequest;
import com.ricex.cartracker.androidrequester.request.tracker.CarTrackerRequestFactory;
import com.ricex.cartracker.common.viewmodel.BulkUploadResult;
import com.ricex.cartracker.common.viewmodel.ReaderLogUpload;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mitchell on 2016-11-01.
 */
public class WebServiceSyncer {

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


    /** Synchronize all of the un-synchronized logs
     *
     */
    public void syncLogs() {
        List<ReaderLog> unsyncedLogs = logManager.getUnsynced();
        Map<Long, ReaderLog> unsyncedLogsMap = new HashMap<Long, ReaderLog>();

        for (ReaderLog log : unsyncedLogs) {
            unsyncedLogsMap.put(log.getId(), log);
        }

        for (int startIndex = 0; startIndex < unsyncedLogs.size(); startIndex += BULK_UPLOAD_SIZE + 1) {
            List<ReaderLog> toUpload = unsyncedLogs.subList(startIndex, Math.min(startIndex + BULK_UPLOAD_SIZE, unsyncedLogs.size() - 1));
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
                        unsyncedLogsMap.get(Long.parseLong(result.getUuid())).setServerId(result.getId());
                    }
                }
            }
            catch (RequestException e) {

            }
        }

    }


}
