package com.ricex.cartracker.android.service.persister.webservice;

import com.ricex.cartracker.android.model.OBDReading;

/**
 * Created by Mitchell on 2/21/2016.
 */
public class PersisterReadingUpload {

    private String uid;

    private OBDReading reading;

    private int tries;

    private int status;

    public PersisterReadingUpload() {

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public OBDReading getReading() {
        return reading;
    }

    public void setReading(OBDReading reading) {
        this.reading = reading;
    }

    public int getTries() {
        return tries;
    }

    public void setTries(int tries) {
        this.tries = tries;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
