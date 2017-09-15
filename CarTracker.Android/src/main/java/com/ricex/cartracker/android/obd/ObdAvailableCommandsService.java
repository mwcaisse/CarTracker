package com.ricex.cartracker.android.obd;

import android.util.Log;

import com.github.pires.obd.commands.ObdCommand;
import com.github.pires.obd.commands.control.VinCommand;
import com.github.pires.obd.commands.protocol.AvailablePidsCommand_01_20;
import com.github.pires.obd.commands.protocol.AvailablePidsCommand_21_40;
import com.github.pires.obd.commands.protocol.AvailablePidsCommand_41_60;
import com.github.pires.obd.commands.protocol.EchoOffCommand;
import com.github.pires.obd.commands.protocol.LineFeedOffCommand;
import com.github.pires.obd.commands.protocol.ObdResetCommand;
import com.github.pires.obd.commands.protocol.SelectProtocolCommand;
import com.github.pires.obd.commands.protocol.TimeoutCommand;
import com.github.pires.obd.commands.temperature.AmbientAirTemperatureCommand;
import com.github.pires.obd.enums.ObdProtocols;
import com.ricex.cartracker.android.obd.command.AvailablePidsCommand_61_80;
import com.ricex.cartracker.android.obd.command.AvailablePidsCommand_81_A0;
import com.ricex.cartracker.android.obd.device.BluetoothObdDevice;
import com.ricex.cartracker.android.obd.device.ObdDevice;
import com.ricex.cartracker.android.obd.device.ObdDeviceConnectionFailedException;
import com.ricex.cartracker.android.settings.CarTrackerSettings;
import com.ricex.cartracker.common.entity.CarSupportedCommands;

import java.io.IOException;

/**
 * Created by Mitchell on 2017-09-15.
 */

public class ObdAvailableCommandsService {

    private CarTrackerSettings settings;
    private ObdDevice device;

    public ObdAvailableCommandsService(CarTrackerSettings settings) {
        this.settings = settings;

        device = new BluetoothObdDevice(settings.getBluetoothDeviceAddress());
    }

    /** Determines the available commands and persists them to the web server
     *
     */
    public void determineAndSaveAvailableCommands() {
        try {
            device.connect();
            initializeDevice();

            //Construct the support commands object
            CarSupportedCommands supportedCommands = determineSupportedCommands();


        } catch (Exception e) {
            Log.w("OBDACS", "Failed to Determine Available Commands", e);
        }
    }

    protected CarSupportedCommands determineSupportedCommands() throws IOException, InterruptedException {
        //create the commands
        ObdCommand availablePids0120 = new AvailablePidsCommand_01_20();
        ObdCommand availablePids2140 = new AvailablePidsCommand_21_40();
        ObdCommand availablePids4160 = new AvailablePidsCommand_41_60();
        ObdCommand availablePids6180 = new AvailablePidsCommand_61_80();
        ObdCommand availablePids81A1 = new AvailablePidsCommand_81_A0();
        ObdCommand vinCommand = new VinCommand();

        //send the commands
        runOBDCommand(availablePids0120);
        runOBDCommand(availablePids2140);
        runOBDCommand(availablePids4160);
        runOBDCommand(availablePids6180);
        runOBDCommand(availablePids81A1);
        runOBDCommand(vinCommand);

        //Construct the support commands object
        CarSupportedCommands supportedCommands = new CarSupportedCommands();
        supportedCommands.setPids0120Bitmask(Integer.parseInt(availablePids0120.getFormattedResult(), 16));
        supportedCommands.setPids2140Bitmask(Integer.parseInt(availablePids2140.getFormattedResult(), 16));
        supportedCommands.setPids4160Bitmask(Integer.parseInt(availablePids4160.getFormattedResult(), 16));
        supportedCommands.setPids6180Bitmask(Integer.parseInt(availablePids6180.getFormattedResult(), 16));
        supportedCommands.setPids81A0Bitmask(Integer.parseInt(availablePids81A1.getFormattedResult(), 16));

        return supportedCommands;
    }

    //TODO: Move initialization + run command to a common class
    protected void initializeDevice() {
        try {

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

        }
        catch (IOException | InterruptedException e) {
            return;
        }
    }

    protected boolean runOBDCommand(ObdCommand command) throws IOException, InterruptedException {
        command.run(device.getInputStream(), device.getOutputStream());
        return true;
    }

}
