package com.ricex.cartracker.android.service.task;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import com.github.pires.obd.commands.ObdCommand;
import com.github.pires.obd.commands.ObdMultiCommand;
import com.github.pires.obd.commands.SpeedCommand;
import com.github.pires.obd.commands.control.VinCommand;
import com.github.pires.obd.commands.engine.MassAirFlowCommand;
import com.github.pires.obd.commands.engine.OilTempCommand;
import com.github.pires.obd.commands.engine.RPMCommand;
import com.github.pires.obd.commands.engine.ThrottlePositionCommand;
import com.github.pires.obd.commands.fuel.FindFuelTypeCommand;
import com.github.pires.obd.commands.fuel.FuelLevelCommand;
import com.github.pires.obd.commands.protocol.EchoOffCommand;
import com.github.pires.obd.commands.protocol.LineFeedOffCommand;
import com.github.pires.obd.commands.protocol.ObdResetCommand;
import com.github.pires.obd.commands.protocol.SelectProtocolCommand;
import com.github.pires.obd.commands.protocol.TimeoutCommand;
import com.github.pires.obd.commands.temperature.AirIntakeTemperatureCommand;
import com.github.pires.obd.commands.temperature.AmbientAirTemperatureCommand;
import com.github.pires.obd.commands.temperature.EngineCoolantTemperatureCommand;
import com.github.pires.obd.enums.AvailableCommandNames;
import com.github.pires.obd.enums.ObdProtocols;
import com.github.pires.obd.exceptions.UnsupportedCommandException;
import com.ricex.cartracker.android.model.OBDReading;
import com.ricex.cartracker.android.service.OBDCommandJob;
import com.ricex.cartracker.android.service.OBDCommandStatus;
import com.ricex.cartracker.android.service.OBDService;
import com.ricex.cartracker.android.service.ServiceLogger;
import com.ricex.cartracker.android.service.reader.BluetoothOBDReader;
import com.ricex.cartracker.android.service.reader.OBDReader;
import com.ricex.cartracker.android.service.reader.TestOBDReader;
import com.ricex.cartracker.android.settings.CarTrackerSettings;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by Mitchell on 1/30/2016.
 */
public class OBDServiceTask extends ServiceTask implements ServiceLogger {

    private OBDService service;

    private CarTrackerSettings settings;

    private BluetoothSocket bluetoothSocket;

    private OBDReader reader;

    private static final String BLUETOOTH_SERIAL_CONNECTION_UUID = "00001101-0000-1000-8000-00805F9B34FB";


    private static final String LOG_TAG = "ODBSERVICETASK";

    public OBDServiceTask(OBDService service, CarTrackerSettings settings) {
        super(settings.getODBReadingInterval());
        this.service = service;
        this.settings = settings;
        createReader();
    }

    protected void createReader() {
        switch (settings.getOBDReaderType()) {
            case BLUETOOTH_READER:
                reader = new BluetoothOBDReader(this, settings);
                break;
            case TEST_READER:
                reader = new TestOBDReader();
                break;
        }
    }

    public boolean performLoopInitialization() {
        if (! reader.initialize()) {
            return false;
        }
        info(LOG_TAG, "OBB connection established, starting data collection loop..");
        return true;
    }

    public boolean performLoopLogic() {
        if (!reader.initialize()) {
            //we are not connected, and we couldn't establish a connection. stop service

            //TODO: Possibly add a retry count? So that if it fails once, it will wait then
            //      try to reconnect after a certian amount of time has passed
            //      up to x times

            info(LOG_TAG, "Stopping service, could not establish connection");
            return false;

        }

        //perform the data read
        try {
            OBDReading data = reader.read();
            service.notifyListeners(data);
            info(LOG_TAG, "Recieved data from OBD device! RPM: " + data.getEngineRPM());

        }
        catch (Exception e) {
            error(LOG_TAG, "Error Occured whil trying to read data!", e);
        }

        return true;
    }

    public void performLoopFinilization() {
        info(LOG_TAG, "OBD Task Loop existing...");
    }

    @Override
    public void stop() {
        super.stop(); // call the parent stop method

        service.onTaskStopped();
    }

    @Override
    public void info(String tag, String message) {
        Log.i(tag, message);
        service.addMessage(message);
    }

    @Override
    public void warn(String tag, String message) {
        Log.w(tag, message);
        service.addMessage(message);
    }

    @Override
    public void error(String tag, String message) {
        Log.e(tag, message);
        service.addMessage(message);
    }

    @Override
    public void error(String tag, String message, Exception e) {
        Log.e(tag, message, e);
        service.addMessage(message + " : " + e.getMessage());
    }
}
