package com.ricex.cartracker.android.obd.command;

import com.github.pires.obd.commands.protocol.AvailablePidsCommand;

/**
 * Created by Mitchell on 2017-09-15.
 */

public class AvailablePidsCommand_81_A0 extends AvailablePidsCommand {


    public AvailablePidsCommand_81_A0() {
        super("01 80");
    }

    @Override
    public String getName() {
        return "Available PIDs 81-A0";
    }
}