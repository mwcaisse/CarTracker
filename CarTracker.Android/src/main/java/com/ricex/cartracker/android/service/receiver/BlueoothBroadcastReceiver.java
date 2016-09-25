package com.ricex.cartracker.android.service.receiver;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.ricex.cartracker.android.service.OBDService;
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

            if (isOurDevice(intent)) {
                if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {
                    startService(context);
                }
                else {
                    stopService(context);
                }
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

    protected boolean isOurDevice(BluetoothDevice device) {
        String triggerDeviceAddress = settings.getTriggerBluetoothDeviceAddress();
        if (StringUtils.isBlank(triggerDeviceAddress)) {
            //fallback to the OBD bluetooth device if a trigger device isn't set
            triggerDeviceAddress = settings.getBluetoothDeviceAddress();
        }
        return StringUtils.equals(triggerDeviceAddress, device.getAddress());
    }

    protected boolean isOurDevice(Intent intent) {
        return isOurDevice(getDeviceFromIntent(intent));
    }

    protected void initializeSettings(Context context) {
        if (null == settings) {
            settings = new CarTrackerSettings(context);
        }
    }
}
