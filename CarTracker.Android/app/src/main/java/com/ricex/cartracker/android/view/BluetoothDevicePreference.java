package com.ricex.cartracker.android.view;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.hardware.camera2.params.BlackLevelPattern;
import android.preference.ListPreference;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Mitchell on 1/29/2016.
 */
public class BluetoothDevicePreference extends ListPreference {


    public BluetoothDevicePreference(Context context, AttributeSet attrs) {
        super(context, attrs);

        setEntriesToBluetoothDevices();
    }

    /** Returns a list of all currently paired / connected bluetooth devices
     *
     * @return
     */
    protected List<BluetoothDevice> getAvailableBluetoothDevices() {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = adapter.getBondedDevices();
        return new ArrayList<BluetoothDevice>(pairedDevices);
    }

    /** Sets the List Entries and Values to Bluetooth Device Names and Mac addresses
     *
     */
    protected void setEntriesToBluetoothDevices() {
        List<BluetoothDevice> devices = getAvailableBluetoothDevices();

        CharSequence[] entries = new CharSequence[devices.size()];
        CharSequence[] entryValues = new CharSequence[devices.size()];

        for (int i = 0; i < devices.size(); i ++) {
            BluetoothDevice device = devices.get(i);

            entries[i] = device.getName();
            entryValues[i] = device.getAddress();
        }

        setEntries(entries);
        setEntryValues(entryValues);
    }
}
