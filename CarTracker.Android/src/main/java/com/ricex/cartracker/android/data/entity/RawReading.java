package com.ricex.cartracker.android.data.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.ricex.cartracker.android.data.dao.impl.RawReadingDaoImpl;

import java.util.Date;

/**
 * Created by Mitchell on 2016-10-23.
 */

@DatabaseTable(tableName = "reading", daoClass = RawReadingDaoImpl.class)
public class RawReading extends ServerEntity {

    @DatabaseField(canBeNull = false)
    private Date readDate;

    @DatabaseField(canBeNull = false)
    private long tripId;

    @DatabaseField
    private double latitude;

    @DatabaseField
    private double longitude;

    @DatabaseField
    private double airIntakeTemperature;

    @DatabaseField
    private double ambientAirTemperature;

    @DatabaseField
    private double engineCoolantTemperature;

    @DatabaseField
    private double oilTemperature;

    @DatabaseField
    private double engineRPM;

    @DatabaseField
    private double speed;

    @DatabaseField
    private double massAirFlow;

    @DatabaseField
    private double throttlePosition;

    @DatabaseField
    private String fuelType;

    @DatabaseField
    private double fuelLevel;

    public RawReading() {

    }

    public Date getReadDate() {
        return readDate;
    }

    public void setReadDate(Date readDate) {
        this.readDate = readDate;
    }

    public long getTripId() {
        return tripId;
    }

    public void setTripId(long tripId) {
        this.tripId = tripId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getAirIntakeTemperature() {
        return airIntakeTemperature;
    }

    public void setAirIntakeTemperature(double airIntakeTemperature) {
        this.airIntakeTemperature = airIntakeTemperature;
    }

    public double getAmbientAirTemperature() {
        return ambientAirTemperature;
    }

    public void setAmbientAirTemperature(double ambientAirTemperature) {
        this.ambientAirTemperature = ambientAirTemperature;
    }

    public double getEngineCoolantTemperature() {
        return engineCoolantTemperature;
    }

    public void setEngineCoolantTemperature(double engineCoolantTemperature) {
        this.engineCoolantTemperature = engineCoolantTemperature;
    }

    public double getOilTemperature() {
        return oilTemperature;
    }

    public void setOilTemperature(double oilTemperature) {
        this.oilTemperature = oilTemperature;
    }

    public double getEngineRPM() {
        return engineRPM;
    }

    public void setEngineRPM(double engineRPM) {
        this.engineRPM = engineRPM;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getMassAirFlow() {
        return massAirFlow;
    }

    public void setMassAirFlow(double massAirFlow) {
        this.massAirFlow = massAirFlow;
    }

    public double getThrottlePosition() {
        return throttlePosition;
    }

    public void setThrottlePosition(double throttlePosition) {
        this.throttlePosition = throttlePosition;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public double getFuelLevel() {
        return fuelLevel;
    }

    public void setFuelLevel(double fuelLevel) {
        this.fuelLevel = fuelLevel;
    }
}
