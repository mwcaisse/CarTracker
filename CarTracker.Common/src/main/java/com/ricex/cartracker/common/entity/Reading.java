package com.ricex.cartracker.common.entity;

import java.util.Date;

public class Reading extends AbstractEntity {

	private Date readDate;
	
	private long tripId;
	
	private Trip trip;
	
	private double latitude;
	
	private double longitude;
	
	private double airIntakeTemperature;
	
	private double ambientAirTemperature;
	
	private double engineCoolantTemperature;
	
	private double oilTemperature;
	
	private double engineRPM;
	
	private double speed;
	
	private double massAirFlow;
	
	private double throttlePosition;
	
	private String fuelType;
	
	private double fuelLevel;

	/**
	 * @return the readDate
	 */
	public Date getReadDate() {
		return readDate;
	}

	/**
	 * @param readDate the readDate to set
	 */
	public void setReadDate(Date readDate) {
		this.readDate = readDate;
	}

	/**
	 * @return the tripId
	 */
	public long getTripId() {
		return tripId;
	}

	/**
	 * @param tripId the tripId to set
	 */
	public void setTripId(long tripId) {
		this.tripId = tripId;
	}

	/**
	 * @return the trip
	 */
	public Trip getTrip() {
		return trip;
	}

	/**
	 * @param trip the trip to set
	 */
	public void setTrip(Trip trip) {
		this.trip = trip;
		if (null != trip) {
			this.tripId = trip.getId();
		}
	}

	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the airIntakeTemperature
	 */
	public double getAirIntakeTemperature() {
		return airIntakeTemperature;
	}

	/**
	 * @param airIntakeTemperature the airIntakeTemperature to set
	 */
	public void setAirIntakeTemperature(double airIntakeTemperature) {
		this.airIntakeTemperature = airIntakeTemperature;
	}

	/**
	 * @return the ambientAirTemperature
	 */
	public double getAmbientAirTemperature() {
		return ambientAirTemperature;
	}

	/**
	 * @param ambientAirTemperature the ambientAirTemperature to set
	 */
	public void setAmbientAirTemperature(double ambientAirTemperature) {
		this.ambientAirTemperature = ambientAirTemperature;
	}

	/**
	 * @return the engineCoolantTemperature
	 */
	public double getEngineCoolantTemperature() {
		return engineCoolantTemperature;
	}

	/**
	 * @param engineCoolantTemperature the engineCoolantTemperature to set
	 */
	public void setEngineCoolantTemperature(double engineCoolantTemperature) {
		this.engineCoolantTemperature = engineCoolantTemperature;
	}

	/**
	 * @return the oilTemperature
	 */
	public double getOilTemperature() {
		return oilTemperature;
	}

	/**
	 * @param oilTemperature the oilTemperature to set
	 */
	public void setOilTemperature(double oilTemperature) {
		this.oilTemperature = oilTemperature;
	}

	/**
	 * @return the engineRPM
	 */
	public double getEngineRPM() {
		return engineRPM;
	}

	/**
	 * @param engineRPM the engineRPM to set
	 */
	public void setEngineRPM(double engineRPM) {
		this.engineRPM = engineRPM;
	}

	/**
	 * @return the speed
	 */
	public double getSpeed() {
		return speed;
	}

	/**
	 * @param speed the speed to set
	 */
	public void setSpeed(double speed) {
		this.speed = speed;
	}

	/**
	 * @return the massAirFlow
	 */
	public double getMassAirFlow() {
		return massAirFlow;
	}

	/**
	 * @param massAirFlow the massAirFlow to set
	 */
	public void setMassAirFlow(double massAirFlow) {
		this.massAirFlow = massAirFlow;
	}

	/**
	 * @return the throttlePosition
	 */
	public double getThrottlePosition() {
		return throttlePosition;
	}

	/**
	 * @param throttlePosition the throttlePosition to set
	 */
	public void setThrottlePosition(double throttlePosition) {
		this.throttlePosition = throttlePosition;
	}

	/**
	 * @return the fuelType
	 */
	public String getFuelType() {
		return fuelType;
	}

	/**
	 * @param fuelType the fuelType to set
	 */
	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}

	/**
	 * @return the fuelLevel
	 */
	public double getFuelLevel() {
		return fuelLevel;
	}

	/**
	 * @param fuelLevel the fuelLevel to set
	 */
	public void setFuelLevel(double fuelLevel) {
		this.fuelLevel = fuelLevel;
	}	
	
	
	
}
