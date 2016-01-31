package com.ricex.cartracker.android.service;

import android.os.Binder;

/**
 * Created by Mitchell on 1/30/2016.
 */
public class OBDServiceBinder extends Binder {

    private OBDService service;

    public OBDServiceBinder(OBDService service) {
        this.service = service;
    }

    public OBDService getService() {
        return service;
    }
}
