package com.ricex.cartracker.android.view;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.ricex.cartracker.android.R;

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
