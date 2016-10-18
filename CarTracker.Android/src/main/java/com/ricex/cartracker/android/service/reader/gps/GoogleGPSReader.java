package com.ricex.cartracker.android.service.reader.gps;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.ricex.cartracker.android.model.GPSLocation;

/**
 * Created by Mitchell on 2016-05-21.
 */
public class GoogleGPSReader implements GPSReader, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleApiClient googleApiClient;

    private final Context context;

    private Location lastLocation;

    public GoogleGPSReader(Context context) {
        this.context = context;
    }

    public void initialize() {
        if (null == googleApiClient) {
            googleApiClient = new GoogleApiClient.Builder(context)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    public void start() {
        if (null == googleApiClient) {
            initialize();
        }
        googleApiClient.connect();
    }

    public void stop() {
        if (null != googleApiClient) {
            googleApiClient.disconnect();
        }
    }

    protected LocationRequest createLocationRequest() {
        LocationRequest request = new LocationRequest();
        request.setInterval(500);
        request.setFastestInterval(200);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setMaxWaitTime(2000);
        return request;
    }

    protected void requestLocationUpdates() {
        final LocationRequest locationRequest = createLocationRequest();
        final LocationListener locationListener = this;
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates states = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        try {
                            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, locationListener);
                        }
                        catch (SecurityException e) {
                            Log.e("CT-GPS", " SECURITY EXCEPTION. WTF IS GOING ON", e);
                        }
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.w("CT-GPS", "GPS Settings Request NEEDS RESOLUTION");
                        break;

                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.w("CT-GPS", "GPS Settings Request FAILED AND NO RESOLUTION AVAILABLE");
                        break;
                }

            }
        });

    }

    /** Gets the users current location
     *
     * @return Object representing the user's current location, or null if no permission granted to
     *      access location
     */
    public GPSLocation getCurrentLocation() {
        return convertAndroidLocation(lastLocation);
    }

    public GPSLocation convertAndroidLocation(Location location) {
        GPSLocation loc = new GPSLocation();
        if (null != location) {
            loc.setLatitude(location.getLatitude());
            loc.setLongitude(location.getLongitude());
        }
        return loc;
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.i("CT-GPS","CONNECTED TO GOOGLE GPS");
        requestLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {
        //TODO: Implement this
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i("CT-GPS","FAILED TO CONNECT TO GOOGLE GPS " + connectionResult.getErrorMessage());

    }

    @Override
    public void onLocationChanged(Location location) {
        if (null != location) {
            lastLocation = location;
        }
    }
}
