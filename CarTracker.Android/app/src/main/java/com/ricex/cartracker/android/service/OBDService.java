package com.ricex.cartracker.android.service;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.ricex.cartracker.android.model.OBDReading;
import com.ricex.cartracker.android.settings.CarTrackerSettings;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Mitchell on 1/30/2016.
 */
public class OBDService extends Service {

    private CarTrackerSettings settings;

    private BluetoothSocket bluetoothSocket;

    private static final String BLUETOOTH_SERIAL_CONNECTION_UUID = "00001101-0000-1000-8000-00805F9B34FB";

    private static final String LOG_TAG = "ODBSERVICE";

    private OBDServiceTask task;
    private Thread thread;
    private OBDServiceBinder binder;

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
        if (!isConnected()) {
            initiateBluetoothConnection();
        }

        //if we haven't created the thread before, create it
        if (thread == null) {
            task = new OBDServiceTask(this, bluetoothSocket);
            thread = new Thread(task);
        }

        //if the thread isn't running (stopped? or was just created)
        //start it
        if (!thread.isAlive()) {
            Log.i(LOG_TAG, "Starting the OBD Service Task!");
            thread.start();
        }
    }

    public void onDestroy() {
        try {
            if (bluetoothSocket.isConnected()) {
                bluetoothSocket.close();
            }
        }
        catch (IOException e) {
            Log.e(LOG_TAG, "Error closing connection with bluetooth device: " + settings.getBluetoothDeviceAddress(), e);
        }
    }

    public void addListener(OBDServiceListener listener) {
        listeners.add(listener);
    }

    public void removeListener(OBDServiceListener listener) {
        listeners.remove(listener);
    }

    protected void notifyListeners(OBDReading data) {
        for (OBDServiceListener listener : listeners) {
            listener.obdDataRead(data);
        }
    }

    protected void initializeSettings() {
        settings = new CarTrackerSettings(this);
    }

    protected void initiateBluetoothConnection() {
        BluetoothDevice device = getBluetoothDevice();
        UUID uuid = UUID.fromString(BLUETOOTH_SERIAL_CONNECTION_UUID);
        try {
            bluetoothSocket = device.createInsecureRfcommSocketToServiceRecord(uuid);
            bluetoothSocket.connect();
        }
        catch (Exception e) {
            Log.e(LOG_TAG, "Error opening bluetooth connection to device: " + settings.getBluetoothDeviceAddress() + " trying fallback", e);

            Class<?> clazz = bluetoothSocket.getRemoteDevice().getClass();
            Class<?>[] paramTypes = new Class<?>[]{Integer.TYPE};

            try {
                Method m = clazz.getMethod("createRfcommSocket", paramTypes);
                Object[] params = new Object[]{Integer.valueOf(1)};
                BluetoothSocket sockFallback = (BluetoothSocket) m.invoke(bluetoothSocket.getRemoteDevice(), params);
                sockFallback.connect();
                bluetoothSocket = sockFallback;
            }
            catch (Exception e2) {
                Log.e(LOG_TAG, "Couldn't use fallback socket / connection", e2);
            }
        }
    }

    protected BluetoothDevice getBluetoothDevice() {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        BluetoothDevice device = adapter.getRemoteDevice(settings.getBluetoothDeviceAddress());
        return device;
    }

    protected boolean isConnected() {
        return bluetoothSocket != null && bluetoothSocket.isConnected();
    }

}
