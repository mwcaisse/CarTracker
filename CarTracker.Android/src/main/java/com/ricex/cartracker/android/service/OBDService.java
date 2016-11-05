package com.ricex.cartracker.android.service;

import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteBaseService;
import com.ricex.cartracker.android.R;
import com.ricex.cartracker.android.data.util.DatabaseHelper;
import com.ricex.cartracker.android.model.OBDReading;
import com.ricex.cartracker.android.service.persister.DatabasePersister;
import com.ricex.cartracker.android.service.persister.Persister;
import com.ricex.cartracker.android.service.persister.webservice.WebServicePersister;
import com.ricex.cartracker.android.service.reader.gps.GoogleGPSReader;
import com.ricex.cartracker.android.service.task.OBDServiceTask;
import com.ricex.cartracker.android.settings.CarTrackerSettings;
import com.ricex.cartracker.android.view.MainActivity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mitchell on 1/30/2016.
 */
public class OBDService extends OrmLiteBaseService<DatabaseHelper> {

    private CarTrackerSettings settings;

    private static final String LOG_TAG = "ODBSERVICE";

    private OBDServiceTask task;
    private Thread thread;
    private OBDServiceBinder binder;

    private Persister persister;
    private Thread persisterThread;

    public static final int FOREGROUND_NOTIFICATION_ID = 15;


    public List<OBDServiceListener> listeners;

    public OBDService() {
        listeners = new ArrayList<OBDServiceListener>();
        binder = new OBDServiceBinder(this);    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public void onCreate() {
        super.onCreate();
        initializeSettings();

        try {
            getHelper().initializeDaosManagers();
        }
        catch (SQLException e) {
            Log.e(LOG_TAG, "Couldn't initialize daos + managers!", e);
        }
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        startService();
        return START_STICKY;
    }

    protected void startService() {
        //if we haven't created the thread before, create it
        if (persisterThread == null) {
            //persister = new WebServicePersister(settings);
            persister = new DatabasePersister(settings, getHelper());
            persisterThread = new Thread(persister);
        }
        if (thread == null) {
            task = new OBDServiceTask(this, settings, persister, new GoogleGPSReader(this));
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


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_media_play)
                .setContentTitle("Car Tracker Collecting")
                .setContentText("Collecting car telemetrics data");

        Intent resultIntent = new Intent(this, MainActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        notificationBuilder.setContentIntent(pendingIntent);

        startForeground(FOREGROUND_NOTIFICATION_ID,notificationBuilder.build());
    }

    public void onDestroy() {
        Log.i(LOG_TAG, "OBDService onDestroy calling task.stop");
        task.stop();
        Log.i(LOG_TAG, "OBDService onDestroy calling persister.stop");
        persister.stop();
        Log.i(LOG_TAG, "OBDService onDestroy calling super.onDestroy");
        super.onDestroy();
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
