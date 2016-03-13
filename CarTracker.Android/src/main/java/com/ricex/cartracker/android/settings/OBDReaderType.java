package com.ricex.cartracker.android.settings;

/**
 * Created by Mitchell on 2/17/2016.
 */
public enum OBDReaderType {

    BLUETOOTH_READER ("Bluetooth Reader", "Uses the selected bluetooth device to poll for OBD Data."),
    TEST_READER ("Test Reader", "Uses a test reader that generates random reading data. For testing purposes.");

    /** The name of this reader type */
    private final String name;

    /** The description of this reader type */
    private final String description;

    private OBDReaderType(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}
