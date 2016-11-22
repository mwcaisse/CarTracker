package com.ricex.cartracker.android.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.Settings;

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
    private static final String TRIGGER_BLUETOOTH_DEVICE_ADDRESS_KEY = "pref_triggerBluetoothDevice";
    private static final String OBD_READER_TYPE_KEY = "pref_obdReaderType";
    private static final String OBD_READING_INTERVAL_KEY = "pref_obdReadingInterval";
    private static final String ODB_DEVICE_PIN = "pref_obdDevicePin";

    private static final String USERNAME_KEY = "pref_username";
    private static final String AUTHENTICATION_TOKEN_KEY = "pref_auth_token";

    private boolean serviceEnabled;
    private boolean locationEnabled;
    private boolean savingLocallyEnabled;
    private String serverAddress;
    private String bluetoothDeviceAddress;
    private String triggerBluetoothDeviceAddress;
    private OBDReaderType obdReaderType;
    private double obdReadingInterval;
    private String username;
    private String authenticationToken;
    private String obdDevicePin;


    /** The context the settings were created om */
    private Context context;

    private SharedPreferences preferences;

    public CarTrackerSettings(Context context) {
        this.context = context;
        updateSettings();
    }

    /** Updates the settings from the Shared Preferences Object
     *
     */

    public void updateSettings() {
        loadPreferences();

        serviceEnabled = preferences.getBoolean(SERVICE_ENABLED_KEY, true);
        locationEnabled = preferences.getBoolean(LOCATION_ENABLED_KEY, true);
        savingLocallyEnabled = preferences.getBoolean(SAVE_LOCALLY_ENABLED_KEY, true);
        serverAddress = preferences.getString(SERVER_ADDRESS_KEY, "");
        bluetoothDeviceAddress = preferences.getString(BLUETOOTH_DEVICE_ADDRESS_KEY, "");
        triggerBluetoothDeviceAddress = preferences.getString(TRIGGER_BLUETOOTH_DEVICE_ADDRESS_KEY, "");
        obdReaderType = OBDReaderType.valueOf(preferences.getString(OBD_READER_TYPE_KEY, OBDReaderType.BLUETOOTH_READER.toString()));
        try {
            obdReadingInterval = Double.parseDouble(preferences.getString(OBD_READING_INTERVAL_KEY, "15.0"));
        }
        catch (NumberFormatException e) {
            obdReadingInterval = 15.0;
        }
        obdDevicePin = preferences.getString(ODB_DEVICE_PIN, "0000");

        username = preferences.getString(USERNAME_KEY, "");
        authenticationToken = preferences.getString(AUTHENTICATION_TOKEN_KEY, "");

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
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    @Override
    public String getAuthToken() {
        return authenticationToken;
    }

    public String getBluetoothDeviceAddress() {
        return bluetoothDeviceAddress;
    }

    public String getTriggerBluetoothDeviceAddress() {
        return triggerBluetoothDeviceAddress;
    }

    public double getODBReadingInterval() {
        return obdReadingInterval;
    }

    public OBDReaderType getOBDReaderType() {
        return obdReaderType;
    }


    public String getUsername() {
        return username;
    }

    public boolean setUsername(String username) {
        return setStringValue(USERNAME_KEY, username);
    }

    public boolean setAuthenticationToken(String authenticationToken) {
        return setStringValue(AUTHENTICATION_TOKEN_KEY, authenticationToken);
    }

    public String getObdDevicePin() {
        return obdDevicePin;

    }
    private boolean setStringValue(String key, String value) {
        if (null == preferences) {
            loadPreferences();
        }
        return preferences.edit().putString(key, value).commit();
    }

    private void loadPreferences() {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

}
