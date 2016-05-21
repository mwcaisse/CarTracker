package com.ricex.cartracker.android.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.ricex.cartracker.androidrequester.request.ApplicationPreferences;

/**
 * Created by Mitchell on 1/29/2016.
 */
public class CarTrackerSettings implements ApplicationPreferences {

    private static final String SERVICE_ENABLED_KEY = "pref_serviceEnabled";
    private static final String LOCATION_ENABLED_KEY = "pref_locationEnabled";
    private static final String SAVE_LOCALLY_ENABLED_KEY = "pref_saveLocallyEnabled";
    private static final String SERVER_ADDRESS_KEY = "pref_serverAddress";
    private static final String BLUETOOTH_DEVICE_ADDRESS_KEY = "pref_bluetoothDevice";
    private static final String OBD_READER_TYPE_KEY = "pref_obdReaderType";
    private static final String OBD_READING_INTERVAL_KEY = "pref_obdReadingInterval";

    private boolean serviceEnabled;
    private boolean locationEnabled;
    private boolean savingLocallyEnabled;
    private String serverAddress;
    private String bluetoothDeviceAddress;
    private OBDReaderType obdReaderType;
    private double obdReadingInterval;

    /** The context the settings were created om */
    private Context context;


    public CarTrackerSettings(Context context) {
        this.context = context;

        updateSettings();
    }

    /** Updates the settings from the Shared Preferences Object
     *
     */

    public void updateSettings() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        serviceEnabled = preferences.getBoolean(SERVICE_ENABLED_KEY, true);
        locationEnabled = preferences.getBoolean(LOCATION_ENABLED_KEY, true);
        savingLocallyEnabled = preferences.getBoolean(SAVE_LOCALLY_ENABLED_KEY, true);
        serverAddress = preferences.getString(SERVER_ADDRESS_KEY, "");
        bluetoothDeviceAddress = preferences.getString(BLUETOOTH_DEVICE_ADDRESS_KEY, "");
        obdReaderType = OBDReaderType.valueOf(preferences.getString(OBD_READER_TYPE_KEY, OBDReaderType.BLUETOOTH_READER.toString()));
        try {
            obdReadingInterval = Double.parseDouble(preferences.getString(OBD_READING_INTERVAL_KEY, "15.0"));
        }
        catch (NumberFormatException e) {
            obdReadingInterval = 15.0;
        }

    }

    public boolean isLocationEnabled() {
        return locationEnabled;
    }

    public boolean isSavingLocallyEnabled() {
        return savingLocallyEnabled;
    }

    public boolean isServiceEnabled() {
        return serviceEnabled;
    }

    public String getServerAddress() {
        //return "https://home.fourfivefire.com/cartracker/";
        //return "http://192.168.1.160:8888/cartracker/";
        return serverAddress;
    }

    @Override
    public String getDeviceUID() {
        return null;
    }

    @Override
    public String getAuthToken() {
        return null;
    }

    public String getBluetoothDeviceAddress() {
        return bluetoothDeviceAddress;
    }

    public double getODBReadingInterval() {
        return obdReadingInterval;
    }

    public OBDReaderType getOBDReaderType() {
        return obdReaderType;
    }
}
