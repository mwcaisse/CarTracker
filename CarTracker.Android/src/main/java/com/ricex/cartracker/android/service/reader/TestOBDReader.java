package com.ricex.cartracker.android.service.reader;

import com.ricex.cartracker.android.model.OBDReading;

/**
 * Created by Mitchell on 2/17/2016.
 */
public class TestOBDReader implements OBDReader {


    @Override
    public boolean initialize() {
        return true;
    }

    public String getCarVin() {
        return "TEST_VIN";
    }

    @Override
    public OBDReading read() {
        OBDReading reading = new OBDReading();

        reading.setAirIntakeTemp(getRandomValue());
        reading.setVin(getRandomValue());
        reading.setFuelLevel(getRandomValue());
        reading.setFuelType(getRandomValue());
        reading.setAmbientAirTemp(getRandomValue());
        reading.setEngineCoolantTemp(getRandomValue());
        reading.setEngineRPM(getRandomValue());
        reading.setMaf(getRandomValue());
        reading.setOilTemp(getRandomValue());
        reading.setThrottlePosition(getRandomValue());
        reading.setSpeed(getRandomValue());

        return reading;
    }

    private String getRandomValue() {
        int val = (int)(Math.random() * 2000);
        return val + "";
    }
}
