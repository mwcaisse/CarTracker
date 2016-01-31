package com.ricex.cartracker.android.service;

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
import com.github.pires.obd.commands.temperature.AirIntakeTemperatureCommand;
import com.github.pires.obd.commands.temperature.AmbientAirTemperatureCommand;
import com.github.pires.obd.commands.temperature.EngineCoolantTemperatureCommand;
import com.github.pires.obd.enums.AvailableCommandNames;
import com.ricex.cartracker.android.model.OBDReading;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Mitchell on 1/30/2016.
 */
public class OBDServiceTask implements Runnable {

    private OBDService service;

    private Object monitor;

    private boolean cont;

    private BluetoothSocket bluetoothSocket;


    private static final String LOG_TAG = "ODBSERVICETASK";

    public OBDServiceTask(OBDService service, BluetoothSocket socket) {
        this.service = service;
        this.bluetoothSocket = socket;
        monitor = new Object();
    }

    @Override
    public void run() {
        synchronized (monitor) {
            cont = true;
        }


        boolean loopOn = true;


        while (loopOn) {

            //perform the data read
            OBDReading data = readData();
            service.notifyListeners(data);

            //sleep for 30 secconds
            try {
                Thread.sleep(1000 * 30);
            }
            catch (InterruptedException e) {
                //we were interrupted... meh. just go back through loop, no need to re-sleep
            }


            //update the ending condition
            synchronized (monitor) {
                loopOn = cont;
            }
        }
    }


    protected OBDReading readData() {
        HashMap<AvailableCommandNames, ObdCommand> commands = createCommands();
        ObdMultiCommand command = createMultiCommand(commands);

        try {
            command.sendCommands(bluetoothSocket.getInputStream(), bluetoothSocket.getOutputStream());
        }
        catch (IOException e) {
            Log.e(LOG_TAG, "Exception occured opening input/output stream to bluetooth device!", e);
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
    }

}
