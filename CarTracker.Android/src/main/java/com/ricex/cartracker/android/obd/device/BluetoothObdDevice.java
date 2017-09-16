package com.ricex.cartracker.android.obd.device;

import android.bluetooth.BluetoothSocket;

import com.ricex.cartracker.android.service.reader.BluetoothDeviceNotPairedException;
import com.ricex.cartracker.android.service.reader.BluetoothManager;
import com.ricex.cartracker.android.settings.CarTrackerSettings;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Mitchell on 2017-09-15.
 */

public class BluetoothObdDevice extends ObdDevice {

    private String deviceAddress;

    private BluetoothSocket deviceSocket;


    public BluetoothObdDevice(String deviceAddress) {
        this.deviceAddress = deviceAddress;
        this.deviceSocket = null;
    }

    @Override
    public void connect() throws ObdDeviceConnectionFailedException {
        if (isConnected()) {
            return;
        }

        try {
            deviceSocket = BluetoothManager.connectToDevice(deviceAddress);
        }
        catch (BluetoothDeviceNotPairedException e) {
            throw new ObdDeviceConnectionFailedException("Could not connect to Bluetooth Device ("
                    + deviceAddress + ") Not Paired", e);
        }
        if (null == deviceSocket) {
            throw new ObdDeviceConnectionFailedException("Could not connect to Bluetooth Device: "
                    + deviceAddress);
    }

    }

    @Override
    public boolean isConnected() {
        return null != deviceSocket && deviceSocket.isConnected();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        if (!isConnected()) {
            throw new IOException("Not currently connected!");
        }
        return deviceSocket.getInputStream();
    }

    @Override
    public OutputStream getOutputStream() throws IOException  {
        if (!isConnected()) {
            throw new IOException("Not currently connected!");
        }
        return deviceSocket.getOutputStream();
    }

    @Override
    public void disconnect() {
        BluetoothManager.disconnectFromDevice(deviceSocket);
        deviceSocket = null;
    }
}
