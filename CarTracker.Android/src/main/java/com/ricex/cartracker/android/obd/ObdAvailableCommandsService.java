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
import com.github.pires.obd.exceptions.MisunderstoodCommandException;
import com.github.pires.obd.exceptions.NoDataException;
import com.github.pires.obd.exceptions.UnsupportedCommandException;
import com.ricex.cartracker.android.obd.command.AvailablePidsCommand_61_80;
import com.ricex.cartracker.android.obd.command.AvailablePidsCommand_81_A0;
import com.ricex.cartracker.android.obd.device.BluetoothObdDevice;
import com.ricex.cartracker.android.obd.device.ObdDevice;
import com.ricex.cartracker.android.obd.device.ObdDeviceConnectionFailedException;
import com.ricex.cartracker.android.service.logger.DatabaseLogger;
import com.ricex.cartracker.android.settings.CarTrackerSettings;
import com.ricex.cartracker.androidrequester.request.exception.RequestException;
import com.ricex.cartracker.androidrequester.request.tracker.UpdateCarSupportedCommandsRequest;
import com.ricex.cartracker.common.entity.CarSupportedCommands;

import java.io.IOException;

/**
 * Created by Mitchell on 2017-09-15.
 */

public class ObdAvailableCommandsService {

    private CarTrackerSettings settings;
    private ObdDevice device;
    private DatabaseLogger logger;

    public ObdAvailableCommandsService(CarTrackerSettings settings, DatabaseLogger logger) {
        this.settings = settings;
        this.logger = logger;

        device = new BluetoothObdDevice(settings.getBluetoothDeviceAddress());
    }

    /** Determines the available commands and persists them to the web server
     *
     */
    public void determineAndSaveAvailableCommands() {
        try {
            logger.debug("OBDACS", "Connected to device...");
            device.connect();
            logger.debug("OBDACS", "Initilizing device...");
            initializeDevice();


            logger.debug("OBDACS", "Pulling suported commands...");
            //Construct the support commands object
            CarSupportedCommands supportedCommands = determineSupportedCommands();

            logger.debug("OBDACS", "Pulling VIN...");
            String vin = getVin();

            logger.debug("OBDACS", "Sending supported commands to server...");
            //persist the commands to the server
            persistSupportedCommands(vin, supportedCommands);


        } catch (Exception e) {
            Log.w("OBDACS", "Failed to Determine Available Commands", e);
            logger.error("OBDACS", "Failed to Determine Available Commands", e);
        }
    }

    protected CarSupportedCommands determineSupportedCommands() throws IOException, InterruptedException {
        //create the commands
        ObdCommand availablePids0120 = new AvailablePidsCommand_01_20();
        ObdCommand availablePids2140 = new AvailablePidsCommand_21_40();
        ObdCommand availablePids4160 = new AvailablePidsCommand_41_60();
        ObdCommand availablePids6180 = new AvailablePidsCommand_61_80();
        ObdCommand availablePids81A1 = new AvailablePidsCommand_81_A0();

        //Construct the support commands object
        CarSupportedCommands supportedCommands = new CarSupportedCommands();

        //send the commands + populate supported commands object
        if (runOBDCommand(availablePids0120)) {
            //Integer.parseInt, parses into a signed int, 0xFFFFFFFF will throw an error as it is higher than
            //  Integer.MAX_VALUE when signed. The result will always be 4 bytes, we can safely cast back
            //  to an int. Android 8 (API 26) introduced Integer.parseUnsignedInt, targetting Android 7 (API 24)
            supportedCommands.setPids0120Bitmask((int)Long.parseLong(availablePids0120.getFormattedResult(), 16));
        }
        if (runOBDCommand(availablePids2140)) {
            supportedCommands.setPids2140Bitmask((int)Long.parseLong(availablePids2140.getFormattedResult(), 16));
        }
        if (runOBDCommand(availablePids4160)) {
            supportedCommands.setPids4160Bitmask((int)Long.parseLong(availablePids4160.getFormattedResult(), 16));
        }
        if (runOBDCommand(availablePids6180)) {
            supportedCommands.setPids6180Bitmask((int)Long.parseLong(availablePids6180.getFormattedResult(), 16));
        }
        if (runOBDCommand(availablePids81A1)){
            supportedCommands.setPids81A0Bitmask((int)Long.parseLong(availablePids81A1.getFormattedResult(), 16));
        }

        return supportedCommands;
    }

    protected String getVin() throws IOException, InterruptedException {
        ObdCommand vinCommand = new VinCommand();
        runOBDCommand(vinCommand);
        return vinCommand.getCalculatedResult();
    }

    protected void persistSupportedCommands(String vin, CarSupportedCommands supportedCommands)
            throws RequestException {

        UpdateCarSupportedCommandsRequest request =
                new UpdateCarSupportedCommandsRequest(settings, vin, supportedCommands);

        boolean res = request.execute();
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
            Log.e("OBDACS", "Failed to initilize device", e);
            logger.error("OBDACS", "Failed to initilize device", e);
        }
    }

    protected boolean runOBDCommand(ObdCommand command) throws IOException, InterruptedException {
        try {
            command.run(device.getInputStream(), device.getOutputStream());
        }
        catch (NoDataException | UnsupportedCommandException | MisunderstoodCommandException e) {
            logger.warn("OBDACS", "Error occured while running command.", e);
            return false;
        }
        return true;
    }

}
