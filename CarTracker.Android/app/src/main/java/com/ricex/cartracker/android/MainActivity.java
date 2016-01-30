package com.ricex.cartracker.android;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {


    protected static final int REQUEST_ENABLE_BT = 5598;

    protected boolean triedEnableBluetooth;

    private DebugFragment debugFragment;

    private OBDService service;

    private boolean bound;

    public MainActivity() {
        triedEnableBluetooth = false;
        bound = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        /*
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();*/

        debugFragment = new DebugFragment();

        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, debugFragment)
                .commit();
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = new Intent(this, OBDService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

    }

    @Override
    protected void onStop() {
        super.onStop();

        if (bound) {
            service.removeListener(debugFragment);
            unbindService(serviceConnection);
            bound = false;
        }
    }


    protected void onResume(Bundle savedInstanceState) {

    }

    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder srv) {
            OBDServiceBinder binder = (OBDServiceBinder) srv;
            service = binder.getService();
            bound = true;

            service.addListener(debugFragment);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bound = false;
        }
    };

}
