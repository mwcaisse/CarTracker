package com.ricex.cartracker.android.view;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ricex.cartracker.android.R;
import com.ricex.cartracker.android.service.OBDService;
import com.ricex.cartracker.android.service.OBDServiceBinder;

public class MainActivity extends AppCompatActivity {


    protected static final int REQUEST_ENABLE_BT = 5598;

    protected boolean triedEnableBluetooth;

    private DebugFragment debugFragment;

    private OBDService service;

    private boolean bound;

    private Intent serviceIntent;

    public MainActivity() {
        triedEnableBluetooth = false;
        bound = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        debugFragment = new DebugFragment();

        getFragmentManager().beginTransaction()
                            .add(R.id.fragment_container, debugFragment)
                            .commit();

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:

             getFragmentManager().beginTransaction()
                                        .replace(R.id.fragment_container, new SettingsFragment())
                                        .addToBackStack(null)
                                        .commit();


                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void startService() {
        debugFragment.addMessage("Starting OBD Service...");
        serviceIntent = new Intent(this, OBDService.class);
        startService(serviceIntent);
        bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE);

        Log.i("CarTracker", "Bound to Service");
    }

    protected void stopService() {
        if (bound) {
            debugFragment.addMessage("Stopping OBD Service...");
            service.removeListener(debugFragment);
            unbindService(serviceConnection);
            bound = false;

            stopService(serviceIntent);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopService();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void toggleService(View v) {
        if (bound) {
            stopService();
        }
        else {
            startService();
        }
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder srv) {
            debugFragment.onMessage("Bound to OBD Service");
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
