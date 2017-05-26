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

    public static final String PROPERTY_READ_DATE = "readDate";
    @DatabaseField(canBeNull = false)
    private Date readDate;

    public static final String PROPERTY_TRIP = "trip_id";
    @DatabaseField(foreign = true, canBeNull = false, columnName = "trip_id")
    private RawTrip trip;

    public static final String PROPERTY_LATITUDE = "latitude";
    @DatabaseField
    private double latitude;

    public static final String PROPERTY_LONGITUDE = "longitude";
    @DatabaseField
    private double longitude;

    public static final String PROPERTY_AIR_INTAKE_TEMPERATURE = "airIntakeTemperature";
    @DatabaseField
    private double airIntakeTemperature;

    public static final String PROPERTY_AMBIENT_AIR_TEMPERATURE = "ambientAirTemperature";
    @DatabaseField
    private double ambientAirTemperature;

    public static final String PROPERTY_ENGINE_COOLAND_TEMPERATURE = "engineCoolantTemperature";
    @DatabaseField
    private double engineCoolantTemperature;

    public static final String PROPERTY_OIL_TEMPERATURE = "oilTemperature";
    @DatabaseField
    private double oilTemperature;

    public static final String PROPERTY_ENGINE_RPM = "engineRPM";
    @DatabaseField
    private double engineRPM;

    public static final String PROPERTY_SPEED = "speed";
    @DatabaseField
    private double speed;

    public static final String PROPERTY_MASS_AIR_FLOW = "massAirFlow";
    @DatabaseField
    private double massAirFlow;

    public static final String PROPERTY_THROTTLE_POSITION = "throttlePosition";
    @DatabaseField
    private double throttlePosition;

    public static final String PROPERTY_FUEL_TYPE = "fuelType";
    @DatabaseField
    private String fuelType;

    public static final String PROPERTY_FUEL_LEVEL = "fuelLevel";
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


    public RawTrip getTrip() {
        return trip;
    }

    public void setTrip(RawTrip trip) {
        this.trip = trip;
    }
}
