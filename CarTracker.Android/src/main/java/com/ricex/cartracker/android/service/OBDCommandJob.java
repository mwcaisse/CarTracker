package com.ricex.cartracker.android.service;

import com.github.pires.obd.commands.ObdCommand;

/**
 * Created by Mitchell on 2/12/2016.
 */
public class OBDCommandJob {

    private ObdCommand command;

    private OBDCommandStatus status;

    public OBDCommandJob(ObdCommand command) {
        if (null == command) {
            throw new IllegalArgumentException("Command cannot be null!");
        }
        this.command = command;
        status = OBDCommandStatus.NEW;
    }

    public OBDCommandStatus getStatus() {
        return status;
    }

    public void setStatus(OBDCommandStatus status) {
        this.status = status;
    }

    public ObdCommand getCommand() {
        return command;
    }

    public void setCommand(ObdCommand command) {
        this.command = command;
    }
}
