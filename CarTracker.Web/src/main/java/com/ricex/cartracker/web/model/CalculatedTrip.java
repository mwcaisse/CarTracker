package com.ricex.cartracker.web.model;

import java.util.Date;

public class CalculatedTrip {

	private long tripId;
	
	private String name;
	
	private Date startDate;
	
	private Date endDate;
	
	private double averageSpeed;
	
	private double maximumSpeed;
	
	private double averageEngineRPM;
	
	private double maxEngineRPM;
	
	private double distanceTraveled;
	
	private double idleTime;

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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the averageSpeed
	 */
	public double getAverageSpeed() {
		return averageSpeed;
	}

	/**
	 * @param averageSpeed the averageSpeed to set
	 */
	public void setAverageSpeed(double averageSpeed) {
		this.averageSpeed = averageSpeed;
	}

	/**
	 * @return the maximumSpeed
	 */
	public double getMaximumSpeed() {
		return maximumSpeed;
	}

	/**
	 * @param maximumSpeed the maximumSpeed to set
	 */
	public void setMaximumSpeed(double maximumSpeed) {
		this.maximumSpeed = maximumSpeed;
	}

	/**
	 * @return the averageEngineRPM
	 */
	public double getAverageEngineRPM() {
		return averageEngineRPM;
	}

	/**
	 * @param averageEngineRPM the averageEngineRPM to set
	 */
	public void setAverageEngineRPM(double averageEngineRPM) {
		this.averageEngineRPM = averageEngineRPM;
	}

	/**
	 * @return the maxEngineRPM
	 */
	public double getMaxEngineRPM() {
		return maxEngineRPM;
	}

	/**
	 * @param maxEngineRPM the maxEngineRPM to set
	 */
	public void setMaxEngineRPM(double maxEngineRPM) {
		this.maxEngineRPM = maxEngineRPM;
	}

	/**
	 * @return the distanceTraveled
	 */
	public double getDistanceTraveled() {
		return distanceTraveled;
	}

	/**
	 * @param distanceTraveled the distanceTraveled to set
	 */
	public void setDistanceTraveled(double distanceTraveled) {
		this.distanceTraveled = distanceTraveled;
	}

	/**
	 * @return the idleTime
	 */
	public double getIdleTime() {
		return idleTime;
	}

	/**
	 * @param idleTime the idleTime to set
	 */
	public void setIdleTime(double idleTime) {
		this.idleTime = idleTime;
	}
	
	
}
