package com.ricex.cartracker.android;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

/**
 *
 *  Debug / PoC Fragment for displaying stats / readings from the OBD-II reader
 *
 * Created by Mitchell on 1/30/2016.
 */
public class DebugFragment extends Fragment {

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

    public DebugFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.debug_fragment_layout, container, false);
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

}

