package com.ricex.cartracker.android.view;

import android.app.Fragment;

import com.ricex.cartracker.android.model.OBDReading;
import com.ricex.cartracker.android.service.OBDServiceListener;

import java.util.Date;

/**
 * Created by Mitchell on 2017-05-28.
 */

public class ServiceStatusFragment extends Fragment implements OBDServiceListener {

    private Date tripStartDate;

    @Override
    public void obdDataRead(OBDReading data) {

    }

    @Override
    public void onMessage(String message) {
        //TOOD: Do nothing on service stop
    }

    @Override
    public void onServiceStopped() {

    }
}
