package com.ricex.cartracker.android.service.task;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import com.ricex.cartracker.android.model.OBDReading;
import com.ricex.cartracker.android.service.OBDService;
import com.ricex.cartracker.android.service.ServiceLogger;
import com.ricex.cartracker.android.service.persister.Persister;
import com.ricex.cartracker.android.service.reader.BluetoothOBDReader;
import com.ricex.cartracker.android.service.reader.ConnectionLostException;
import com.ricex.cartracker.android.service.reader.OBDReader;
import com.ricex.cartracker.android.service.reader.TestOBDReader;
import com.ricex.cartracker.android.model.GPSLocation;
import com.ricex.cartracker.android.service.reader.gps.GPSReader;
import com.ricex.cartracker.android.settings.CarTrackerSettings;

/**
 * Created by Mitchell on 1/30/2016.
 */
public class OBDServiceTask extends ServiceTask implements ServiceLogger {

    private OBDService service;

    private CarTrackerSettings settings;

    private BluetoothSocket bluetoothSocket;

    private OBDReader reader;

    private Persister persister;

    private GPSReader gpsReader;

    private static final String LOG_TAG = "ODBSERVICETASK";

    public OBDServiceTask(OBDService service, CarTrackerSettings settings, Persister persister, GPSReader gpsReader) {
        super(settings.getODBReadingInterval());
        this.service = service;
        this.settings = settings;
        this.persister = persister;
        this.gpsReader = gpsReader;
        createReader();
    }

    protected void createReader() {
        info(LOG_TAG, "Creating OBD Reader");
        switch (settings.getOBDReaderType()) {
            case BLUETOOTH_READER:
                reader = new BluetoothOBDReader(this, settings);
                break;
            case TEST_READER:
                reader = new TestOBDReader();
                break;
        }
    }

    public boolean performLoopInitialization() {
        if (! reader.initialize()) {
            warn(LOG_TAG, "Couldn't initialize OBD Reader");
            return false;
        }

        //initialize the gpsReader... This method itself won't return success status
        //it has callbacks, which we should check at some point in time
        gpsReader.start();
        info(LOG_TAG, "OBB connection established, starting data collection loop..");
        return true;
    }

    public boolean performLoopLogic() {
        if (!reader.initialize()) {
            //we are not connected, and we couldn't establish a connection. stop service

            //TODO: Possibly add a retry count? So that if it fails once, it will wait then
            //      try to reconnect after a certian amount of time has passed
            //      up to x times

            info(LOG_TAG, "Stopping service, could not establish connection");
            return false;

        }

        //perform the data read
        try {
            OBDReading data = reader.read();
            if ("-1".equals(data.getEngineRPM())) {
                info(LOG_TAG, "Received -1 from the reader, stopping");
                return false;
            }
            GPSLocation gpsLocation = gpsReader.getCurrentLocation();
            data.setLocation(gpsLocation);
            persister.persist(data);
            service.notifyListeners(data);

            info(LOG_TAG, "Read some data, Engine RPM: " + data.getEngineRPM());
        }
        catch (ConnectionLostException e) {
            info(LOG_TAG, "Connection to ODB Device lost.");
            return false; // we aren't connected anymore, don't continue
        }
        catch (Exception e) {
            error(LOG_TAG, "Error Occured while trying to read data!", e);
        }

        return true;
    }

    public void performLoopFinilization() {
        info(LOG_TAG, "OBD Task Loop existing...");
    }

    @Override
    public void stop() {
        super.stop(); // call the parent stop method

        gpsReader.stop();
        persister.stop();
        service.onTaskStopped();
    }

    @Override
    public void info(String tag, String message) {
        Log.i(tag, message);
        service.addMessage(message);
    }

    @Override
    public void warn(String tag, String message) {
        Log.w(tag, message);
        service.addMessage(message);
    }

    @Override
    public void error(String tag, String message) {
        Log.e(tag, message);
        service.addMessage(message);
    }

    @Override
    public void error(String tag, String message, Exception e) {
        Log.e(tag, message, e);
        service.addMessage(message + " : " + e.getMessage());
    }
}
