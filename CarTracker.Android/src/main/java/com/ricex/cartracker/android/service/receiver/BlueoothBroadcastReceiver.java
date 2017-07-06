package com.ricex.cartracker.android.service.receiver;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.ricex.cartracker.android.data.util.DatabaseHelper;
import com.ricex.cartracker.android.service.OBDService;
import com.ricex.cartracker.android.service.logger.DatabaseLogger;
import com.ricex.cartracker.android.service.logger.ServiceLogger;
import com.ricex.cartracker.android.service.reader.BluetoothManager;
import com.ricex.cartracker.android.settings.CarTrackerSettings;

import org.apache.commons.lang3.StringUtils;

import java.sql.SQLException;

/**
 * Created by Mitchell on 2016-09-21.
 */
public class BlueoothBroadcastReceiver extends BroadcastReceiver {

    private CarTrackerSettings settings;

    private static final String LOG_TAG = "BTBR";

    private ServiceLogger databaseLogger;

    private DatabaseHelper databaseHelper;

    @Override
    public void onReceive(Context context, Intent intent) {
        initializeDatabase(context);

        String action = intent.getAction();

        logInfo(LOG_TAG, "Received a bluetooth broadcast: " + getDeviceName(intent));

        if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action) ||
            BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {

            logInfo(LOG_TAG, "Received device "+ action + " broadcast");

            initializeSettings(context);

            if (isTriggerDevice(intent) || isOBDDevice(intent)) {
                logInfo(LOG_TAG, "Trigger device "+ action + ". Starting/stoping service");
                if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action) && !OBDService.SERVICE_RUNNING) {
                    startService(context);
                }
                else if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action) && OBDService.SERVICE_RUNNING) {
                    stopService(context);
                }
            }
            else {
                logInfo(LOG_TAG, action + " was NOT trigger device.");
            }
        }
        else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
            logInfo(LOG_TAG, "Received bond state changed broadcast, likely we have paired with a new device.");

            //bond state between device has changed, check that the device is teh ODB Device
            // and that we are now paired with the device.
            BluetoothDevice device = getDeviceFromIntent(intent);
            if (isOBDDevice(device) && BluetoothManager.isDevicePaired(device)) {
                logInfo(LOG_TAG, "Paired with OBD Reader");
                if (!OBDService.SERVICE_RUNNING) {
                    //if the service isn't already running, start it.
                    startService(context);
                }
            }
            else {
                logInfo(LOG_TAG, "Bond State Changed was not for ODB device.");
            }
        }

        destroyDatabase();
    }

    protected void logInfo(String tag, String message) {
        Log.i(tag, message);
        if (null != databaseLogger) {
            databaseLogger.info(tag, message);
        }
    }

    protected void initializeDatabase(Context context) {
        databaseHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
        try {
            databaseHelper.initializeDaosManagers();
            databaseLogger = new DatabaseLogger(databaseHelper);
        }
        catch (SQLException e) {
            Log.e(LOG_TAG, "Couldn't initilize databaseHelper, DatabaseLogger not created", e);
        }
    }

    protected void destroyDatabase() {
        OpenHelperManager.releaseHelper(); // we are destroying the database, release the helper
        databaseHelper = null;
        databaseLogger = null;
    }

    protected void startService(Context context) {
        logInfo(LOG_TAG, "Registered Bluetooth device has connected!");
        if (!OBDService.SERVICE_RUNNING) {
            Intent serviceIntent = new Intent(context, OBDService.class);
            context.startService(serviceIntent);
        }
    }

    protected void stopService(Context context) {
        logInfo(LOG_TAG, "Registered Bluetooth device has disconnected!");
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

    protected String getDeviceName(Intent intent) {
        BluetoothDevice device = getDeviceFromIntent(intent);
        return null == device ? "" :  device.getName();
    }
}
