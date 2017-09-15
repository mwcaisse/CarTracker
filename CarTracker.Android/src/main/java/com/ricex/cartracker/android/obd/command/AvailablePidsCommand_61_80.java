package com.ricex.cartracker.android.obd.command;

import com.github.pires.obd.commands.protocol.AvailablePidsCommand;

/**
 * Created by Mitchell on 2017-09-15.
 */

public class AvailablePidsCommand_61_80 extends AvailablePidsCommand {


    public AvailablePidsCommand_61_80() {
        super("01 60");
    }

    @Override
    public String getName() {
        return "Available PIDs 61-80";
    }
}
