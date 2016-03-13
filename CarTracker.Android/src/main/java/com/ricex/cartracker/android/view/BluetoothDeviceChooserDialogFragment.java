package com.ricex.cartracker.android.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/** Fragmnet to show the dialog to select a bluetooth device
 *
 * Created by Mitchell on 1/16/2016.
 */
public class BluetoothDeviceChooserDialogFragment extends DialogFragment {


    /*
        Gets a list of all currently paired bluetooth devices
    */
    protected List<BluetoothDevice> getAvailaleBluetoohDevices() {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = adapter.getBondedDevices();
        return new ArrayList<BluetoothDevice>(pairedDevices);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        List<BluetoothDevice> devices = getAvailaleBluetoohDevices();

        List<String> deviceNames = new ArrayList<String>();
        for (BluetoothDevice device : devices) {
            deviceNames.add(device.getName());
        }
/*
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose a Bluetooth Device");
        builder.setItems(deviceNames.toArray(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        };*/
        return null;

    }




}
