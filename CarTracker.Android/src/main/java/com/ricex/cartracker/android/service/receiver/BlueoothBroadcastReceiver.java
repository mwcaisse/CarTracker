package com.ricex.cartracker.android.service.receiver;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ricex.cartracker.android.settings.CarTrackerSettings;

/**
 * Created by Mitchell on 2016-09-21.
 */
public class BlueoothBroadcastReceiver extends BroadcastReceiver {

    private CarTrackerSettings settings;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action) ||
            BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {

            if (isOurDevice(intent)) {
                if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {
                    startService();
                }
                else {
                    stopService();
                }
            }
        }
    }

    protected void startService() {

    }

    protected void stopService() {

    }

    protected BluetoothDevice getDeviceFromIntent(Intent intent) {
        return intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
    }

    protected boolean isOurDevice(BluetoothDevice device) {
        return settings.getBluetoothDeviceAddress().equals(device.getAddress());
    }

    protected boolean isOurDevice(Intent intent) {
        return isOurDevice(getDeviceFromIntent(intent));
    }
}
