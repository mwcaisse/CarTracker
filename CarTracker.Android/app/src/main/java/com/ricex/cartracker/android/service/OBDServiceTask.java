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
import com.github.pires.obd.commands.protocol.LineFeedOffCommand;
import com.github.pires.obd.commands.protocol.ObdResetCommand;
import com.github.pires.obd.commands.protocol.SelectProtocolCommand;
import com.github.pires.obd.commands.protocol.TimeoutCommand;
import com.github.pires.obd.commands.temperature.AirIntakeTemperatureCommand;
import com.github.pires.obd.commands.temperature.AmbientAirTemperatureCommand;
import com.github.pires.obd.commands.temperature.EngineCoolantTemperatureCommand;
import com.github.pires.obd.enums.AvailableCommandNames;
import com.github.pires.obd.enums.ObdProtocols;
import com.ricex.cartracker.android.model.OBDReading;
import com.ricex.cartracker.android.settings.CarTrackerSettings;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
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
                    stop();
                }
            }

            //perform the data read
            try {
                OBDReading data = readData();
                service.notifyListeners(data);
                service.addMessage("Recieved data from OBD device! RPM: " + data.getEngineRPM());
            }
            catch (Exception e) {
                service.addMessage("Error occurred while trying to read data: " + e.getMessage());
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
            runOBDCommand(new ObdResetCommand());
            runOBDCommand(new LineFeedOffCommand());
            runOBDCommand(new TimeoutCommand(62));


            runOBDCommand(new SelectProtocolCommand(ObdProtocols.AUTO));
            runOBDCommand(new AmbientAirTemperatureCommand());
        }
        catch (IOException | InterruptedException e) {
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
        HashMap<AvailableCommandNames, ObdCommand> commands = createCommands();
        ObdMultiCommand command = createMultiCommand(commands);

        try {
            command.sendCommands(bluetoothSocket.getInputStream(), bluetoothSocket.getOutputStream());
        }
        catch (IOException e) {
            Log.e(LOG_TAG, "Exception occured opening input/output stream to bluetooth device!", e);
            service.addMessage("Error occured while establishing communication to bluetooth device!");
        }
        catch (InterruptedException e) {
            Log.e(LOG_TAG, "Command was interrupted!", e);
        }

        return readDataFromCommands(commands);
    }

    protected OBDReading readDataFromCommands(HashMap<AvailableCommandNames, ObdCommand> commands) {
        OBDReading data = new OBDReading();

        data.setAirIntakeTemp(commands.get(AvailableCommandNames.AIR_INTAKE_TEMP).getFormattedResult());
        data.setAmbientAirTemp(commands.get(AvailableCommandNames.AMBIENT_AIR_TEMP).getFormattedResult());
        data.setEngineCoolantTemp(commands.get(AvailableCommandNames.ENGINE_COOLANT_TEMP).getFormattedResult());
        data.setOilTemp(commands.get(AvailableCommandNames.ENGINE_OIL_TEMP).getFormattedResult());
        data.setEngineRPM(commands.get(AvailableCommandNames.ENGINE_RPM).getFormattedResult());
        data.setSpeed(commands.get(AvailableCommandNames.SPEED).getFormattedResult());
        data.setMaf(commands.get(AvailableCommandNames.MAF).getFormattedResult());
        data.setThrottlePosition(commands.get(AvailableCommandNames.THROTTLE_POS).getFormattedResult());
        data.setFuelType(commands.get(AvailableCommandNames.FUEL_TYPE).getFormattedResult());
        data.setFuelLevel(commands.get(AvailableCommandNames.FUEL_LEVEL).getFormattedResult());
        data.setVin(commands.get(AvailableCommandNames.VIN).getFormattedResult());

        return data;
    }


    protected HashMap<AvailableCommandNames, ObdCommand> createCommands() {
        HashMap<AvailableCommandNames, ObdCommand> commands = new HashMap<AvailableCommandNames, ObdCommand>();

        commands.put(AvailableCommandNames.AIR_INTAKE_TEMP, new AirIntakeTemperatureCommand());
        commands.put(AvailableCommandNames.AMBIENT_AIR_TEMP, new AmbientAirTemperatureCommand());
        commands.put(AvailableCommandNames.ENGINE_COOLANT_TEMP, new EngineCoolantTemperatureCommand());
        commands.put(AvailableCommandNames.ENGINE_OIL_TEMP, new OilTempCommand());

        commands.put(AvailableCommandNames.ENGINE_RPM, new RPMCommand());
        commands.put(AvailableCommandNames.SPEED, new SpeedCommand());
        commands.put(AvailableCommandNames.MAF, new MassAirFlowCommand());
        commands.put(AvailableCommandNames.THROTTLE_POS, new ThrottlePositionCommand());
        commands.put(AvailableCommandNames.FUEL_TYPE, new FindFuelTypeCommand());
        commands.put(AvailableCommandNames.FUEL_LEVEL, new FuelLevelCommand());
        commands.put(AvailableCommandNames.VIN, new VinCommand());

        return commands;
    }

    protected ObdMultiCommand createMultiCommand(HashMap<AvailableCommandNames, ObdCommand> commands) {
        ObdMultiCommand multiCommand = new ObdMultiCommand();
        for (ObdCommand command : commands.values()) {
            multiCommand.add(command);
        }

        return multiCommand;
    }

    public void stop() {
        synchronized (monitor) {
            cont = false;
        }

        service.onTaskStopped();
    }

}
