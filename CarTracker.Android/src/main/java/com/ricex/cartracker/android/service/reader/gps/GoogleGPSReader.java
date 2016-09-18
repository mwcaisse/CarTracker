package com.ricex.cartracker.android.service.reader.gps;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.ricex.cartracker.android.model.GPSLocation;

/**
 * Created by Mitchell on 2016-05-21.
 */
public class GoogleGPSReader implements GPSReader, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient googleApiClient;

    private final Context context;

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

    /** Gets the users current location
     *
     * @return Object representing the user's current location, or null if no permission granted to
     *      access location
     */
    public GPSLocation getCurrentLocation() {
        try {
            Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            return convertAndroidLocation(lastLocation);
        }
        catch (SecurityException e) {
            return null;
        }
    }

    public GPSLocation convertAndroidLocation(Location location) {
        GPSLocation loc = new GPSLocation();
        loc.setLatitude(location.getLatitude());
        loc.setLongitude(location.getLongitude());
        return loc;
    }

    @Override
    public void onConnected(Bundle bundle) {
        //TODO: Implement this
    }

    @Override
    public void onConnectionSuspended(int i) {
        //TODO: Implement this
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        //TODO: Implement this
    }
}
