package com.ricex.cartracker.android;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by Mitchell on 1/29/2016.
 */
public class SettingsFragment extends PreferenceFragment {

    public SettingsFragment() {

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
    }



}
