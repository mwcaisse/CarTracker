package com.ricex.cartracker.android.view;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
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

    private TextView tvErrorMessage;

    private Handler handler;

    private static final int MESSAGE_ID_MSG = 1;
    private static final int MESSAGE_ID_OBD_DATA = 2;

    private static final String LOG_TAG = "ODBDebugFragment";

    private View view;

    public DebugFragment() {
        createHandler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.debug_fragment_layout, container, false);
        setFields(view);
        return view;
    }

    public void setFields(View view) {
        tvAirIntakeTemp = (TextView) view.findViewById(R.id.valAirIntakeTemp);
        tvAmbientAirTemp = (TextView) view.findViewById(R.id.valAmbientAirTemp);
        tvEngineCoolantTemp = (TextView) view.findViewById(R.id.valEngineCoolantTemp);
        tvOilTemp = (TextView) view.findViewById(R.id.valOilTemp);
        tvEngineRPM = (TextView) view.findViewById(R.id.valEngineRPM);
        tvSpeed = (TextView) view.findViewById(R.id.valSpeed);
        tvMAF = (TextView) view.findViewById(R.id.valMAF);
        tvThrottlePosition = (TextView) view.findViewById(R.id.valThrottlePosition);
        tvFuelType = (TextView) view.findViewById(R.id.valFuelLevel);
        tvFuelLevel = (TextView) view.findViewById(R.id.valFuelLevel);
        tvVIN = (TextView) view.findViewById(R.id.valVIN);

        tvErrorMessage = (TextView) view.findViewById(R.id.tvMessages);
    }

    protected void createHandler() {
        handler = new Handler(Looper.getMainLooper()) {

            @Override
            public void handleMessage(Message message) {
                switch (message.what) {
                    case MESSAGE_ID_MSG:
                        String msg = message.obj.toString();
                        addMessage(msg);
                        break;

                    case MESSAGE_ID_OBD_DATA:
                        OBDReading data = (OBDReading)message.obj;
                        updateFromData(data);
                        Log.i(LOG_TAG, "Obtained a data message!");
                        break;
                }
            };

        };
    }


    public void updateFromData(OBDReading data) {
        updateAirIntakeTemp(data.getAirIntakeTemp());
        updateAmbientAirTemp(data.getAmbientAirTemp());
        updateEngineCoolantTemp(data.getEngineCoolantTemp());
        updateEngineRPM(data.getEngineRPM());
        updateOilTemp(data.getOilTemp());

        updateSpeed(data.getSpeed());
        updateMAF(data.getMaf());
        updateThrottlePosition(data.getThrottlePosition());
        updateFuelLevel(data.getFuelLevel());
        updateFuelType(data.getFuelType());

        updateVIN(data.getVin());

        //refresh the view
        view.invalidate();
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

    public void addMessage(String message) {
        String curText = tvErrorMessage.getText().toString();
        tvErrorMessage.setText(curText + "\n" + message);
    }

    @Override
    public void obdDataRead(OBDReading data) {
        Message message = handler.obtainMessage(MESSAGE_ID_OBD_DATA, data);
        message.sendToTarget();
    }

    @Override
    public void onMessage(String message) {
        Message msg = handler.obtainMessage(MESSAGE_ID_MSG, message);
        msg.sendToTarget();
    }

    @Override
    public void onServiceStopped() {
        onMessage("OBD Service stoped!");
    }
}

