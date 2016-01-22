package com.ricex.cartracker.android;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {


    protected static final int REQUEST_ENABLE_BT = 5598;

    protected boolean triedEnableBluetooth;

    public MainActivity() {
        triedEnableBluetooth = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    protected void onResume(Bundle savedInstanceState) {

        //check for bluetooth enabled
        if (!isBluetoothSupported()) {
            //device does not support bluetooth, inform user and then exit
            //or just exit for now.
            finish();
        }


        if  (!isBluetoothEnabled()) {
            //check if we tried to enable bluetooth
            if (triedEnableBluetooth) {
                //we did, so it obviously failed, inform the user that application need bluetooth and exit
                finish();
            }
            else {
                promptUserEnableBluetooth();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ENABLE_BT) {
            triedEnableBluetooth = true;
        }
    }


    /**
     *  Checks if bluetooth is supported on this device or not
     * @return True if bluetooth is supported, false otherwise
     */
    protected boolean isBluetoothSupported() {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        return null != adapter;
    }

    /**
     *  Checks if Bluetooth is currently enabled on this device or not
     * @return True if bluetooth is enabled, false otehrwise
     */
    protected boolean isBluetoothEnabled() {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        return adapter.isEnabled();
    }

    /**
     *  Creates the intent to prompt the user to enable bluetooth
     */
    protected void promptUserEnableBluetooth() {
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
    }

}
