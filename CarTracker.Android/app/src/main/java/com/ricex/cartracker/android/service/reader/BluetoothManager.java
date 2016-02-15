package com.ricex.cartracker.android.service.reader;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.lang.reflect.Method;
import java.util.UUID;

/** Manages bluetooth + bluetooth connections
 * Created by Mitchell on 2/13/2016.
 */
public class BluetoothManager {

    /*
   * http://developer.android.com/reference/android/bluetooth/BluetoothDevice.html
   * #createRfcommSocketToServiceRecord(java.util.UUID)
   *
   * "Hint: If you are connecting to a Bluetooth serial board then try using the
   * well-known SPP UUID 00001101-0000-1000-8000-00805F9B34FB. However if you
   * are connecting to an Android peer then please generate your own unique
   * UUID."
   */
    private static final UUID BT_SERIAL_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private static final String LOG_TAG = "BluetoothManager";

    public BluetoothDevice getDevice(String deviceAddress) {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        return adapter.getRemoteDevice(deviceAddress);
    }

    public BluetoothSocket connectToDevice(String deviceAddress) {
        return connectToDevice(getDevice(deviceAddress));
    }

    public BluetoothSocket connectToDevice(BluetoothDevice device) {
        BluetoothSocket socket = null;
        try {
            socket = device.createInsecureRfcommSocketToServiceRecord(BT_SERIAL_UUID);
            socket.connect();
            Log.i(LOG_TAG, "Successfully connected to bluetooth device!");
        }
        catch (Exception e) {
            Log.e(LOG_TAG, "Error opening bluetooth connection to device: " + device.getAddress() + " trying fallback", e);

            Class<?> clazz = socket.getRemoteDevice().getClass();
            Class<?>[] paramTypes = new Class<?>[]{Integer.TYPE};

            try {
                Method m = clazz.getMethod("createRfcommSocket", paramTypes);
                Object[] params = new Object[]{Integer.valueOf(1)};
                BluetoothSocket sockFallback = (BluetoothSocket) m.invoke(socket.getRemoteDevice(), params);
                sockFallback.connect();
                socket = sockFallback;

                Log.i(LOG_TAG, "Sucessfully connected to bluetooth device using fall back method!");
            }
            catch (Exception e2) {
                Log.e(LOG_TAG, "Couldn't use fallback socket / connection", e2);
                return null;
            }
        }

        return socket;
    }

}
