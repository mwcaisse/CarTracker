package com.ricex.cartracker.android.service.receiver;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.ricex.cartracker.android.service.OBDService;
import com.ricex.cartracker.android.service.reader.BluetoothManager;
import com.ricex.cartracker.android.settings.CarTrackerSettings;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Mitchell on 2016-09-21.
 */
public class BlueoothBroadcastReceiver extends BroadcastReceiver {

    private CarTrackerSettings settings;

    private static final String LOG_TAG = "BTBR";

    private Intent serviceIntent;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action) ||
            BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {

            initializeSettings(context);

            if (isTriggerDevice(intent)) {
                Log.i(LOG_TAG, "Trigger device connected/disconnected. Starting/stoping service");
                if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {
                    startService(context);
                }
                else {
                    stopService(context);
                }
            }
        }
        else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
            //bond state between device has changed, check that the device is teh ODB Device
            // and that we are now paired with the device. If so
            // start the service
            BluetoothDevice device = getDeviceFromIntent(intent);
            if (isOBDDevice(device) && BluetoothManager.isDevicePaired(device)) {

                Log.i(LOG_TAG, "Paired with OBD Reader");
                //startService(context);
            }
        }
    }

    protected void startService(Context context) {
        Log.i(LOG_TAG, "Registered Bluetooth device has connected!");
        serviceIntent = new Intent(context, OBDService.class);
        context.startService(serviceIntent);
    }

    protected void stopService(Context context) {
        Log.i(LOG_TAG, "Registered Bluetooth device has disconnected!");
    }

    protected BluetoothDevice getDeviceFromIntent(Intent intent) {
        return intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
    }

    protected boolean isTriggerDevice(BluetoothDevice device) {
        String triggerDeviceAddress = settings.getTriggerBluetoothDeviceAddress();
        if (StringUtils.isBlank(triggerDeviceAddress)) {
            return isOBDDevice(device);
        }
        return StringUtils.equals(triggerDeviceAddress, device.getAddress());
    }

    protected boolean isTriggerDevice(Intent intent) {
        return isTriggerDevice(getDeviceFromIntent(intent));
    }

    protected boolean isOBDDevice(BluetoothDevice device) {
        String odbDeviceAddress = settings.getBluetoothDeviceAddress();
        return StringUtils.equals(odbDeviceAddress, device.getAddress());
    }

    protected boolean isOBDDevice(Intent intent) {
        return isOBDDevice(getDeviceFromIntent(intent));
    }

    protected void initializeSettings(Context context) {
        if (null == settings) {
            settings = new CarTrackerSettings(context);
        }
    }
}
