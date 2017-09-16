package com.ricex.cartracker.android.view;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.SQLException;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v7.app.AlertDialog;
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
import com.ricex.cartracker.android.obd.ObdAvailableCommandsService;
import com.ricex.cartracker.android.service.OBDService;
import com.ricex.cartracker.android.service.OBDServiceBinder;
import com.ricex.cartracker.android.service.WebServiceSyncer;
import com.ricex.cartracker.android.service.logger.DatabaseLogger;
import com.ricex.cartracker.android.settings.CarTrackerSettings;
import com.ricex.cartracker.android.view.login.LoginActivity;
import com.ricex.cartracker.androidrequester.request.tracker.CarTrackerRequestFactory;

import org.apache.commons.lang3.StringUtils;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "CT_MA";

    protected static final int REQUEST_ENABLE_BT = 5598;

    protected static final int REQUEST_LOGIN_CODE = 5599;

    protected boolean triedEnableBluetooth;

    private DebugFragment debugFragment;

    private OBDService service;

    private boolean bound;

    private Intent serviceIntent;

    private DatabaseHelper databaseHelper = null;
    private boolean databaseHelperCreated = false;
    private boolean databaseHelperDestroyed = false;

    private CarTrackerSettings settings;

    private CarTrackerRequestFactory requestFactory;

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
        requestFactory = new CarTrackerRequestFactory(settings);

        //check that we have the user's credentials, if not request them
        checkForLogin();
    }

    @Override
    protected void onStart() {
        super.onStart();

        //if the service is running, then we want to bind to it.
        if (OBDService.SERVICE_RUNNING){
            bindService();
        }

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

            case R.id.action_available_commands:
                new AsyncTask<DatabaseHelper, Void, Void>() {

                    @Override
                    protected Void doInBackground(DatabaseHelper... params) {
                        DatabaseHelper helper = params[0];
                        DatabaseLogger logger = new DatabaseLogger(helper);
                        new ObdAvailableCommandsService(settings, logger).determineAndSaveAvailableCommands();
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void result) {
                        Toast toast = Toast.makeText(context, "Determining Available Commands Complete!", Toast.LENGTH_SHORT);
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
        if (!OBDService.SERVICE_RUNNING) { // only start the service if it isn't running
            startService(serviceIntent);
        }
       bindService();
    }

    protected void bindService() {
        if (null == serviceIntent) {
            serviceIntent = new Intent(this, OBDService.class);
        }
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

    protected void checkForLogin() {
        String authToken = settings.getAuthToken();
        if (StringUtils.isBlank(authToken)) {
            //we don't have an auth token stored,
            requestLogin();
        }
        else {
            //we have an auth token, lets login to get a session token
            String username = settings.getUsername();
            requestFactory.createLoginTokenRequest(username, authToken);
        }
    }

    protected void requestLogin() {
        final Context context = this;

        new AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("Login")
            .setMessage("You are not currently logged in. Do you want to log in now? If not, you can " +
                    "always log in via settings later")
            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(context, LoginActivity.class);
                    startActivityForResult(intent, REQUEST_LOGIN_CODE);
                }
            })
            .setNegativeButton("No", null)
            .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_LOGIN_CODE) {
            if (resultCode == RESULT_OK) {
                //repeat again, to get a session token
                checkForLogin();
            }
            else {
                Log.w(LOG_TAG, "Could not recieve an auth token from user's login!");
            }
        }
    }

}
