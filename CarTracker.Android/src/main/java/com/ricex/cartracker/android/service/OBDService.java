package com.ricex.cartracker.android.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.ricex.cartracker.android.model.OBDReading;
import com.ricex.cartracker.android.service.persister.Persister;
import com.ricex.cartracker.android.service.persister.webservice.WebServicePersister;
import com.ricex.cartracker.android.service.task.OBDServiceTask;
import com.ricex.cartracker.android.settings.CarTrackerSettings;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mitchell on 1/30/2016.
 */
public class OBDService extends Service {

    private CarTrackerSettings settings;

    private static final String LOG_TAG = "ODBSERVICE";

    private OBDServiceTask task;
    private Thread thread;
    private OBDServiceBinder binder;

    private Persister persister;
    private Thread persisterThread;


    public List<OBDServiceListener> listeners;

    public OBDService() {
        listeners = new ArrayList<OBDServiceListener>();
        binder = new OBDServiceBinder(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public void onCreate() {
        initializeSettings();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        startService();
        return START_STICKY;
    }

    protected void startService() {
        //if we haven't created the thread before, create it
        if (persisterThread == null) {
            persister = new WebServicePersister(settings);
            persisterThread = new Thread(persister);
        }
        if (thread == null) {
            task = new OBDServiceTask(this, settings, persister);
            thread = new Thread(task);
        }
        //if the thread isn't running (stopped? or was just created)
        //start it
        if (!thread.isAlive()) {
            Log.i(LOG_TAG, "Starting the OBD Service Task!");
            thread.start();
        }
        if (!persisterThread.isAlive()) {
            Log.i(LOG_TAG, "Starting the Persister Task!");
            persisterThread.start();
        }
    }

    public void onDestroy() {
       task.stop();
        persister.stop();
    }

    public void addListener(OBDServiceListener listener) {
        listeners.add(listener);
    }

    public void removeListener(OBDServiceListener listener) {
        listeners.remove(listener);
    }

    public void notifyListeners(OBDReading data) {
        for (OBDServiceListener listener : listeners) {
            listener.obdDataRead(data);
        }
    }

    public void addMessage(String message) {
        for (OBDServiceListener listener : listeners) {
            listener.onMessage(message);
        }
    }

    public void onTaskStopped() {
        stopSelf();
    }

    protected void initializeSettings() {
        settings = new CarTrackerSettings(this);
    }

}
