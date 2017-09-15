package com.ricex.cartracker.android.service.reader;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
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

    public static BluetoothDevice getDevice(String deviceAddress) {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        return adapter.getRemoteDevice(deviceAddress);
    }

    public static BluetoothSocket connectToDevice(String deviceAddress) throws BluetoothDeviceNotPairedException {
        return connectToDevice(getDevice(deviceAddress));
    }

    public static BluetoothSocket connectToDevice(BluetoothDevice device) throws BluetoothDeviceNotPairedException {
        if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
            throw new BluetoothDeviceNotPairedException("Cannot connect to device! Device not paired");
        }
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

    /** Disconnect from the Bluetooth Device connected on the given Bluetooth Socket
     *
     * @param deviceSocket The socket the device is connected on
     */

    public static void disconnectFromDevice(BluetoothSocket deviceSocket) {
        try {
            deviceSocket.getInputStream().close();
        }
        catch (IOException e) {
            //fail quietly, we are trying to disconnect
        }
        try {
            deviceSocket.getOutputStream().close();
        }
        catch (IOException e) {
            //fail quietly, we are trying to disconnect
        }
        try {
            deviceSocket.close();
        }
        catch (IOException e) {
            //fail quietly, we are trying to disconnect
        }
    }

    /** Determines if the given bluetooth device is paired or nopt
     *
     * @param device The device to check
     * @return True if the device is paired, false otherwise
     */
    public static boolean isDevicePaired(BluetoothDevice device) {
        return device.getBondState() == BluetoothDevice.BOND_BONDED;
    }

    /** Pairs with the given device, using the given pin
     *
     * @param device The device to pair with
     * @param pin The device's pin
     */
    public static void pairWithDevice(BluetoothDevice device, String pin) {
        device.setPin(pin.getBytes());
        device.createBond();
    }

}
