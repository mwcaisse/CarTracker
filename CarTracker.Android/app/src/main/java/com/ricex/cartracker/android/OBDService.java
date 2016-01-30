package com.ricex.cartracker.android;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.github.pires.obd.commands.ObdMultiCommand;
import com.github.pires.obd.commands.SpeedCommand;
import com.github.pires.obd.commands.control.VinCommand;
import com.github.pires.obd.commands.engine.MassAirFlowCommand;
import com.github.pires.obd.commands.engine.OilTempCommand;
import com.github.pires.obd.commands.engine.RPMCommand;
import com.github.pires.obd.commands.engine.ThrottlePositionCommand;
import com.github.pires.obd.commands.fuel.FindFuelTypeCommand;
import com.github.pires.obd.commands.fuel.FuelLevelCommand;
import com.github.pires.obd.commands.temperature.AirIntakeTemperatureCommand;
import com.github.pires.obd.commands.temperature.AmbientAirTemperatureCommand;
import com.github.pires.obd.commands.temperature.EngineCoolantTemperatureCommand;
import com.github.pires.obd.enums.AvailableCommandNames;
import com.ricex.cartracker.android.settings.CarTrackerSettings;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import com.github.pires.obd.commands.ObdCommand;

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

    public List<OBDServiceListener> listeners;

    public OBDService() {
        listeners = new ArrayList<OBDServiceListener>();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        initializeSettings();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
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

        return START_STICKY;
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

    protected void notifyListeners(OBDData data) {
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
        catch (IOException e) {
            Log.e(LOG_TAG, "Error opening bluetooth connection to device: " + settings.getBluetoothDeviceAddress(), e);
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
