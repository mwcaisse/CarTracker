package com.ricex.cartracker.data.entity;

import java.util.Date;

public class OBDReading extends AbstractEntity {

	private double airIntakeTemperature;
	
	private double ambientAirTemperature;
	
	private double engineCoolandTemperature;
	
	private double oilTemperature;
	
	private double engineRMP;
	
	private double speed;
	
	private double massAirFlow;
	
	private double throttlePosition;
	
	private String fuelType;
	
	private double fuelLevel;
	
	private String vin;	
	
	private long readingId;
	
	private Reading reading;

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
	 * @return the engineCoolandTemperature
	 */
	public double getEngineCoolandTemperature() {
		return engineCoolandTemperature;
	}

	/**
	 * @param engineCoolandTemperature the engineCoolandTemperature to set
	 */
	public void setEngineCoolandTemperature(double engineCoolandTemperature) {
		this.engineCoolandTemperature = engineCoolandTemperature;
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
	 * @return the engineRMP
	 */
	public double getEngineRMP() {
		return engineRMP;
	}

	/**
	 * @param engineRMP the engineRMP to set
	 */
	public void setEngineRMP(double engineRMP) {
		this.engineRMP = engineRMP;
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

	/**
	 * @return the vin
	 */
	public String getVin() {
		return vin;
	}

	/**
	 * @param vin the vin to set
	 */
	public void setVin(String vin) {
		this.vin = vin;
	}

	/**
	 * @return the readingId
	 */
	public long getReadingId() {
		return readingId;
	}

	/**
	 * @param readingId the readingId to set
	 */
	public void setReadingId(long readingId) {
		this.readingId = readingId;
	}

	/**
	 * @return the reading
	 */
	public Reading getReading() {
		return reading;
	}

	/**
	 * @param reading the reading to set
	 */
	public void setReading(Reading reading) {
		this.reading = reading;
	}
		
	
}
