package com.ricex.cartracker.android.view;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ricex.cartracker.android.model.OBDReading;
import com.ricex.cartracker.android.R;
import com.ricex.cartracker.android.service.OBDServiceListener;

/**
 *
 *  Debug / PoC Fragment for displaying stats / readings from the OBD-II reader
 *
 * Created by Mitchell on 1/30/2016.
 */
public class DebugFragment extends Fragment implements OBDServiceListener {

    private String airIntakeTemp;
    private String ambientAirTemp;
    private String engineCoolantTemp;
    private String oilTemp;

    private String engineRPM;
    private String speed;
    private String maf;

    private String throttlePosition;
    private String fuelLevel;
    private String fuelType;

    private String vin;


    private TextView tvAirIntakeTemp;
    private TextView tvAmbientAirTemp;
    private TextView tvEngineCoolantTemp;
    private TextView tvOilTemp;

    private TextView tvEngineRPM;
    private TextView tvSpeed;
    private TextView tvMAF;

    private TextView tvThrottlePosition;
    private TextView tvFuelLevel;
    private TextView tvFuelType;

    private TextView tvVIN;

    private Handler handler;

    public DebugFragment() {
        createHandler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.debug_fragment_layout, container, false);
    }

    protected void createHandler() {
        handler = new Handler(Looper.getMainLooper()) {

            @Override
            public void handleMessage(Message message) {
                OBDReading data = (OBDReading)message.obj;
                updateFromData(data);
            };

        };
    }


    public void updateFromData(OBDReading data) {
        updateAirIntakeTemp(data.getAirIntakeTemp());
        updateAmbientAirTemp(data.getAmbientAirTemp());
        updateEngineCoolantTemp(data.getEngineCoolantTemp());
        updateEngineRPM(data.getEngineRPM());

        updateSpeed(data.getSpeed());
        updateMAF(data.getMaf());
        updateThrottlePosition(data.getThrottlePosition());
        updateFuelLevel(data.getFuelLevel());
        updateFuelType(data.getFuelType());

        updateVIN(data.getVin());
    }

    public void updateAirIntakeTemp(String airIntakeTemp) {
        this.airIntakeTemp = airIntakeTemp;
        tvAirIntakeTemp.setText(airIntakeTemp);
    }

    public void updateAmbientAirTemp(String val) {
        ambientAirTemp = val;
        tvAmbientAirTemp.setText(ambientAirTemp);
    }

    public void updateEngineCoolantTemp(String val) {
        engineCoolantTemp = val;
        tvEngineCoolantTemp.setText(val);
    }

    public void updateOilTemp(String val) {
        oilTemp = val;
        tvOilTemp.setText(val);
    }

    public void updateEngineRPM(String val) {
        engineRPM = val;
        tvEngineRPM.setText(val);
    }

    public void updateSpeed(String val) {
        speed = val;
        tvSpeed.setText(val);
    }

    public void updateMAF(String val) {
        maf = val;
        tvMAF.setText(val);
    }

    public void updateThrottlePosition(String val) {
        throttlePosition = val;
        tvThrottlePosition.setText(val);
    }

    public void updateFuelType(String val) {
        fuelType = val;
        tvFuelType.setText(val);
    }

    public void updateFuelLevel(String val) {
        fuelLevel = val;
        tvFuelLevel.setText(val);
    }

    public void updateVIN(String val) {
        vin = val;
        tvVIN.setText(val);
    }

    @Override
    public void obdDataRead(OBDReading data) {
        Message message = handler.obtainMessage(1, data);
        message.sendToTarget();
    }
}

