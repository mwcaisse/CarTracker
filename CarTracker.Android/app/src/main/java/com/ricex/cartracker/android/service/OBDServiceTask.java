package com.ricex.cartracker.android.service;

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
public class OBDServiceTask implements Runnable {

    private OBDService service;

    private CarTrackerSettings settings;

    private Object monitor;

    private boolean cont;

    private BluetoothSocket bluetoothSocket;

    private static final String BLUETOOTH_SERIAL_CONNECTION_UUID = "00001101-0000-1000-8000-00805F9B34FB";


    private static final String LOG_TAG = "ODBSERVICETASK";

    public OBDServiceTask(OBDService service, CarTrackerSettings settings) {
        this.service = service;
        this.settings = settings;
        monitor = new Object();

        cont = true;
    }

    @Override
    public void run() {
        if (!initiateBluetoothConnection() || !initiateOBDConnection()) {
            //we couldn't initiate the bluetooth connection, or the OBD connection, stop service
            stop();
        }

        service.addMessage("OBB connection established, starting data collection loop..");

        boolean loopOn = cont;

        while (loopOn) {

            if (!isConnected()) {
                if (!initiateBluetoothConnection() || !initiateOBDConnection()) {
                    //we are not connected, and we couldn't establish a connection. stop service
                    Log.i(LOG_TAG, "Stopping service, could not establish connection");
                    service.addMessage("Stopping service.. Could not establish connection!");
                    stop();
                }
            }

            //perform the data read
            try {
                OBDReading data = readData();
                service.notifyListeners(data);
                service.addMessage("Recieved data from OBD device! RPM: " + data.getEngineRPM());
                Log.i(LOG_TAG, "Recieved data from OBD device! RPM: " + data.getEngineRPM());
            }
            catch (Exception e) {
                service.addMessage("Error occurred while trying to read data: " + e.getMessage());
                Log.e(LOG_TAG, "Error Occured while trying to read data!", e);
            }

            //sleep for 5 secconds
            try {
                Thread.sleep(1000 * 5);
            }
            catch (InterruptedException e) {
                //we were interrupted... meh. just go back through loop, no need to re-sleep
            }

            //update the ending condition
            synchronized (monitor) {
                loopOn = cont;
            }
        }

        service.addMessage("OBDTask Loop exiting...");
    }

    protected boolean initiateOBDConnection() {
        try {
            service.addMessage("Initiating the OBD Connection...");

            //initilize the commands
            //copied from OBD Gateway service in OBD Reader
            runOBDCommand(new ObdResetCommand());
            runOBDCommand(new EchoOffCommand());

            //send second time based on tests...
            runOBDCommand(new EchoOffCommand());
            runOBDCommand(new LineFeedOffCommand());
            runOBDCommand(new TimeoutCommand(62));


            runOBDCommand(new SelectProtocolCommand(ObdProtocols.AUTO));
            runOBDCommand(new AmbientAirTemperatureCommand());

            service.addMessage("Initiated the OBD Connection.");
            Log.i(LOG_TAG, "Successfully Initiated the OBD Connection!");
        }
        catch (IOException | InterruptedException e) {
            service.addMessage("Failed to initiate the OBD Connection...: " + e.getMessage());
            Log.e(LOG_TAG, "Failed to initiate the OBD Connection", e);
            return false;
        }
        return true;
    }

    protected boolean runOBDCommand(ObdCommand command) throws IOException, InterruptedException {
        command.run(bluetoothSocket.getInputStream(), bluetoothSocket.getOutputStream());
        return true;
    }

    protected boolean initiateBluetoothConnection() {
        BluetoothDevice device = getBluetoothDevice();
        UUID uuid = UUID.fromString(BLUETOOTH_SERIAL_CONNECTION_UUID);
        try {
            bluetoothSocket = device.createInsecureRfcommSocketToServiceRecord(uuid);
            bluetoothSocket.connect();
            Log.i(LOG_TAG, "Successfully connected to bluetooth device!");
            service.addMessage("Successfuly connected to bluetooth device!");
        }
        catch (Exception e) {
            Log.e(LOG_TAG, "Error opening bluetooth connection to device: " + settings.getBluetoothDeviceAddress() + " trying fallback", e);
            service.addMessage("Error opening bluetooth connection to device: " + settings.getBluetoothDeviceAddress() + " trying fallback method");

            Class<?> clazz = bluetoothSocket.getRemoteDevice().getClass();
            Class<?>[] paramTypes = new Class<?>[]{Integer.TYPE};

            try {
                Method m = clazz.getMethod("createRfcommSocket", paramTypes);
                Object[] params = new Object[]{Integer.valueOf(1)};
                BluetoothSocket sockFallback = (BluetoothSocket) m.invoke(bluetoothSocket.getRemoteDevice(), params);
                sockFallback.connect();
                bluetoothSocket = sockFallback;

                Log.i(LOG_TAG, "Sucessfully connected to bluetooth device using fall back method!");
                service.addMessage("Sucessfully connected to bluetooth device using fall back method!");
            }
            catch (Exception e2) {
                Log.e(LOG_TAG, "Couldn't use fallback socket / connection", e2);
                service.addMessage("Failed to fallback, cannot establish bluetooth connection");
                return false;
            }
        }
        return true;
    }

    protected BluetoothDevice getBluetoothDevice() {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        BluetoothDevice device = adapter.getRemoteDevice(settings.getBluetoothDeviceAddress());
        return device;
    }

    protected boolean isConnected() {
        return bluetoothSocket != null && bluetoothSocket.isConnected();
    }


    protected OBDReading readData() {
        List<OBDCommandJob> jobs = createJobs();

        for (OBDCommandJob job : jobs) {
            executeOBDJob(job);
        }
        return readDataFromJobs(jobs);
    }

    protected OBDReading readDataFromJobs(List<OBDCommandJob> jobs) {
        OBDReading data = new OBDReading();

        for (OBDCommandJob job : jobs) {
            AvailableCommandNames commandName = AvailableCommandNames.valueOf(job.getCommand().getName());

            String results = "";

            if (job.getStatus() == OBDCommandStatus.FINISHED) {
                results = job.getCommand().getFormattedResult();
            }
            else {
                results = "NO DATA";

                service.addMessage("Failed to retreive data for command: " + commandName);
            }

            switch (commandName) {
                case AIR_INTAKE_TEMP:
                    data.setAirIntakeTemp(results);
                    break;
                case AMBIENT_AIR_TEMP:
                    data.setAmbientAirTemp(results);
                    break;
                case ENGINE_COOLANT_TEMP:
                    data.setEngineCoolantTemp(results);
                    break;
                case ENGINE_OIL_TEMP:
                    data.setOilTemp(results);
                    break;
                case ENGINE_RPM:
                    data.setEngineRPM(results);
                    break;
                case SPEED:
                    data.setSpeed(results);
                    break;
                case MAF:
                    data.setMaf(results);
                    break;
                case THROTTLE_POS:
                    data.setThrottlePosition(results);
                    break;
                case FUEL_TYPE:
                    data.setFuelType(results);
                    break;
                case FUEL_LEVEL:
                    data.setFuelLevel(results);
                    break;
                case VIN:
                    data.setVin(results);
                    break;
            }
        }
        
        return data;
    }

    /**
     *  Executes an ODB Job representing an OBD Command
     * @param job The job to execute
     */
    protected void executeOBDJob(OBDCommandJob job) {
        try {
            if (job.getStatus() == OBDCommandStatus.NEW) {
                job.setStatus(OBDCommandStatus.RUNNING);

                if (bluetoothSocket.isConnected()) {
                    job.getCommand().run(bluetoothSocket.getInputStream(), bluetoothSocket.getOutputStream());
                }
                else {
                    job.setStatus(OBDCommandStatus.EXECUTION_ERROR);
                    //socket is closed..
                }
            }
            else {
                //job wasn't new, can't run a old job
            }
        }
        catch (UnsupportedCommandException e) {
            job.setStatus(OBDCommandStatus.NOT_SUPPORTED);
        }
        catch (Exception e) {
            job.setStatus(OBDCommandStatus.EXECUTION_ERROR);
        }

        job.setStatus(OBDCommandStatus.FINISHED);
    }



    protected List<OBDCommandJob> createJobs() {
        List<OBDCommandJob> jobs = new ArrayList<OBDCommandJob>();

        jobs.add(new OBDCommandJob(new AirIntakeTemperatureCommand()));
        jobs.add(new OBDCommandJob(new AmbientAirTemperatureCommand()));
        jobs.add(new OBDCommandJob(new EngineCoolantTemperatureCommand()));
        jobs.add(new OBDCommandJob(new OilTempCommand()));
        jobs.add(new OBDCommandJob(new RPMCommand()));
        jobs.add(new OBDCommandJob(new SpeedCommand()));
        jobs.add(new OBDCommandJob(new MassAirFlowCommand()));
        jobs.add(new OBDCommandJob(new ThrottlePositionCommand()));
        jobs.add(new OBDCommandJob(new FindFuelTypeCommand()));
        jobs.add(new OBDCommandJob(new FuelLevelCommand()));
        jobs.add(new OBDCommandJob(new  VinCommand()));

        return jobs;
    }

    public void stop() {
        synchronized (monitor) {
            cont = false;
        }

        service.onTaskStopped();
    }

}
