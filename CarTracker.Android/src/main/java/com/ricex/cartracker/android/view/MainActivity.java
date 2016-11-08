package com.ricex.cartracker.android.view;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.SQLException;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.ricex.cartracker.android.R;
import com.ricex.cartracker.android.data.util.DatabaseHelper;
import com.ricex.cartracker.android.service.OBDService;
import com.ricex.cartracker.android.service.OBDServiceBinder;
import com.ricex.cartracker.android.service.WebServiceSyncer;
import com.ricex.cartracker.android.settings.CarTrackerSettings;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "CT_MA";

    protected static final int REQUEST_ENABLE_BT = 5598;

    protected boolean triedEnableBluetooth;

    private DebugFragment debugFragment;

    private OBDService service;

    private boolean bound;

    private Intent serviceIntent;

    private DatabaseHelper databaseHelper = null;
    private boolean databaseHelperCreated = false;
    private boolean databaseHelperDestroyed = false;

    private CarTrackerSettings settings;

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

        if (null == databaseHelper) {
            try {
                databaseHelper = getDatabaseHelperInternal();
                databaseHelperCreated = true;
            }
            catch (java.sql.SQLException e) {
                Log.e(LOG_TAG, "Could not create database helper!", e);
            }
        }

        settings = new CarTrackerSettings(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    protected void onDestroy() {
        super.onDestroy();
        if (null != databaseHelper) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
            databaseHelperDestroyed = true;
        }
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
        final Context context = this;
        switch (item.getItemId()) {
            case R.id.action_settings:

                getFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_container, new SettingsFragment())
                                    .addToBackStack(null)
                                    .commit();


                return true;

            case R.id.action_sync:

                new AsyncTask<DatabaseHelper, Void, Void>() {

                    @Override
                    protected Void doInBackground(DatabaseHelper... params) {
                        DatabaseHelper helper = params[0];
                        WebServiceSyncer syncer = new WebServiceSyncer(helper, settings);
                        syncer.fullSync();
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void result) {
                        Toast toast = Toast.makeText(context, "Web Sync complete!", Toast.LENGTH_SHORT);
                        toast.show();
                    }

                }.execute(getDatabaseHelper());
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
        //stopService();
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



    protected DatabaseHelper getDatabaseHelperInternal() throws java.sql.SQLException {
        DatabaseHelper helper = (DatabaseHelper) OpenHelperManager.getHelper(this, DatabaseHelper.class);
        helper.initializeDaosManagers();
        return helper;
    }

    protected DatabaseHelper getDatabaseHelper() {
        if (null == databaseHelper) {
            if (!databaseHelperCreated) {
                throw new IllegalStateException("A call has not been made to onCreate yet, so databaseHelper is null");
            }
            else if (databaseHelperDestroyed) {
                throw new IllegalStateException("A call to onDestroy has been made and the database helper cannot be used after that point");
            }
            else {
                throw new IllegalStateException("DatabaseHelper is null for an unknown reason!");
            }
        }
        else {
            return databaseHelper;
        }
    }

}
