package com.ricex.cartracker.android.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Mitchell on 1/29/2016.
 */
public class CarTrackerSettings {

    private static final String SERVICE_ENABLED_KEY = "pref_serviceEnabled";
    private static final String LOCATION_ENABLED_KEY = "pref_locationEnabled";
    private static final String SAVE_LOCALLY_ENABLED_KEY = "pref_saveLocallyEnabled";
    private static final String SERVER_ADDRESS_KEY = "pref_serverAddress";

    private boolean serviceEnabled;
    private boolean locationEnabled;
    private boolean savingLocallyEnabled;
    private String serverAddress;

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
        return serverAddress;
    }

}
