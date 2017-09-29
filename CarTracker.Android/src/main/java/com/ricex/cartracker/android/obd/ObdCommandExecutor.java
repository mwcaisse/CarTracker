package com.ricex.cartracker.android.obd;

import android.util.Log;

import com.github.pires.obd.commands.ObdCommand;
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
import com.ricex.cartracker.android.obd.device.ObdDevice;
import com.ricex.cartracker.android.obd.device.ObdDeviceConnectionFailedException;
import com.ricex.cartracker.android.service.logger.ServiceLogger;

import java.io.IOException;

/**
 *  Executes OBD Commands
 *
 * Created by Mitchell on 2017-09-16.
 */

public class ObdCommandExecutor {

    private static final String LOG_TAG = "OBDCE";

    private final ObdDevice device;

    private final ServiceLogger logger;

    private boolean initialized;

    public ObdCommandExecutor(ObdDevice device, ServiceLogger logger) {
        this.device = device;
        this.logger = logger;

        initialized = false;
    }

    /** Initialize the OBD Device for sending/retriving data. Must be called
     *      before any commands are executed
     *
     */
    public boolean initialize() {
        if (initialized) {
            return true; // executor is already initialized, no need to call again
        }
        try {
            //connect to the device, if we aren't already connected
            if (!device.isConnected()) {
                device.connect();
            }
            //initilize the commands
            //copied from OBD Gateway service in OBD Reader
            runOBDCommandNoCheck(new ObdResetCommand());
            runOBDCommandNoCheck(new EchoOffCommand());

            //send second time based on tests...
            runOBDCommandNoCheck(new EchoOffCommand());
            runOBDCommandNoCheck(new LineFeedOffCommand());
            runOBDCommandNoCheck(new TimeoutCommand(62));


            runOBDCommandNoCheck(new SelectProtocolCommand(ObdProtocols.AUTO));
            runOBDCommandNoCheck(new AmbientAirTemperatureCommand());

        }
        catch (IOException | InterruptedException | ObdDeviceConnectionFailedException e) {
            logger.error(LOG_TAG, "Failed to initilize command executor.", e);
            return false;
        }

        initialized = true;
        return true;
    }

    /** Executes the given command on the device
     *
     * @param command The command to execute
     * @return True if command executed sucessfully, false if command returned no data
     *  and/or was unsupported
     * @throws IOException
     * @throws InterruptedException
     */

    public boolean runOBDCommand(ObdCommand command) throws IOException, InterruptedException {
        if (!initialized) {
            throw new IOException("Executor has not been initialized yet!");
        }
        return runOBDCommandNoCheck(command);

    }

    private boolean runOBDCommandNoCheck(ObdCommand command) throws IOException, InterruptedException {
        try {
            command.run(device.getInputStream(), device.getOutputStream());
        }
        catch (NoDataException | UnsupportedCommandException | MisunderstoodCommandException e) {
            return false;
        }
        return true;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public boolean isConnected() {
        return device.isConnected();
    }
}
