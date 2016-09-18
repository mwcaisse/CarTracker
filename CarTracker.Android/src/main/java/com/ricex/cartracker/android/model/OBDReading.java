package com.ricex.cartracker.android.model;

import java.util.Date;

/**
 *  POJO for representing OBDReading
 *
 * Created by Mitchell on 1/30/2016.
 */
public class OBDReading {


    private String airIntakeTemp;
    private String ambientAirTemp;
    private String engineCoolantTemp;
    private String oilTemp;

    private String engineRPM;
    private String speed;
    private String maf;

    private String throttlePosition;
    private String fuelLevel;
    private String fuelType;

    private GPSLocation location;

    private String vin;

    private Date readDate;

    public OBDReading() {
        readDate = new Date();
    }

    public String getAirIntakeTemp() {
        return airIntakeTemp;
    }

    public void setAirIntakeTemp(String airIntakeTemp) {
        this.airIntakeTemp = airIntakeTemp;
    }

    public String getAmbientAirTemp() {
        return ambientAirTemp;
    }

    public void setAmbientAirTemp(String ambientAirTemp) {
        this.ambientAirTemp = ambientAirTemp;
    }

    public String getEngineCoolantTemp() {
        return engineCoolantTemp;
    }

    public void setEngineCoolantTemp(String engineCoolantTemp) {
        this.engineCoolantTemp = engineCoolantTemp;
    }

    public String getOilTemp() {
        return oilTemp;
    }

    public void setOilTemp(String oilTemp) {
        this.oilTemp = oilTemp;
    }

    public String getEngineRPM() {
        return engineRPM;
    }

    public void setEngineRPM(String engineRPM) {
        this.engineRPM = engineRPM;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getMaf() {
        return maf;
    }

    public void setMaf(String maf) {
        this.maf = maf;
    }

    public String getThrottlePosition() {
        return throttlePosition;
    }

    public void setThrottlePosition(String throttlePosition) {
        this.throttlePosition = throttlePosition;
    }

    public String getFuelLevel() {
        return fuelLevel;
    }

    public void setFuelLevel(String fuelLevel) {
        this.fuelLevel = fuelLevel;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public Date getReadDate() {
        return readDate;
    }

    public void setReadDate(Date readDate) {
        this.readDate = readDate;
    }

    public GPSLocation getLocation() {
        return location;
    }

    public void setLocation(GPSLocation location) {
        this.location = location;
    }
}
