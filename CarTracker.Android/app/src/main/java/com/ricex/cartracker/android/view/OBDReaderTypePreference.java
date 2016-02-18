package com.ricex.cartracker.android.view;

import android.content.Context;
import android.preference.ListPreference;
import android.util.AttributeSet;

import com.ricex.cartracker.android.settings.OBDReaderType;

/**
 * Created by Mitchell on 2/17/2016.
 */
public class OBDReaderTypePreference extends ListPreference {

    public OBDReaderTypePreference(Context context, AttributeSet attrs) {
        super(context, attrs);

        setListEntries();
    }

    protected void setListEntries() {

        OBDReaderType[] types = OBDReaderType.values();

        CharSequence[] entries = new CharSequence[types.length];
        CharSequence[] entryValues = new CharSequence[types.length];

        for (int i=0; i < types.length; i ++) {
            OBDReaderType type = types[i];

            entries[i] = type.getName();
            entryValues[i] = type.toString();
        }

        setEntries(entries);
        setEntryValues(entryValues);
    }
}
