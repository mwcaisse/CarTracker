package com.ricex.cartracker.common.entity;

import java.util.Date;

public class Trip extends AbstractEntity {
	
	private Date startDate;
	
	private Date endDate;
	
	private String name;
	
	private long carId;
	
	private Car car;
	
	private double averageSpeed;
	
	private double maximumSpeed;
	
	private double averageEngineRPM;
	
	private double maxEngineRPM;
	
	private double distanceTraveled;
	
	private long idleTime;
	
	private Long startPlaceId;
	
	private Long destinationPlaceId;
	
	private Place start;
	
	private Place destination;
	
	private TripStatus status;

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
	 * @return the carId
	 */
	public long getCarId() {
		return carId;
	}

	/**
	 * @param carId the carId to set
	 */
	public void setCarId(long carId) {
		this.carId = carId;
	}

	/**
	 * @return the car
	 */
	public Car getCar() {
		return car;
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
	public long getIdleTime() {
		return idleTime;
	}

	/**
	 * @param idleTime the idleTime to set
	 */
	public void setIdleTime(long idleTime) {
		this.idleTime = idleTime;
	}

	/**
	 * @return the status
	 */
	public TripStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(TripStatus status) {
		this.status = status;
	}
	
	

	/**
	 * @return the startPlaceId
	 */
	public Long getStartPlaceId() {
		return startPlaceId;
	}

	/**
	 * @param startPlaceId the startPlaceId to set
	 */
	public void setStartPlaceId(Long startPlaceId) {
		this.startPlaceId = startPlaceId;
	}

	/**
	 * @return the destinationPlaceId
	 */
	public Long getDestinationPlaceId() {
		return destinationPlaceId;
	}

	/**
	 * @param destinationPlaceId the destinationPlaceId to set
	 */
	public void setDestinationPlaceId(Long destinationPlaceId) {
		this.destinationPlaceId = destinationPlaceId;
	}

	/**
	 * @return the start
	 */
	public Place getStart() {
		return start;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(Place start) {
		this.start = start;
		if (null != start) {
			this.startPlaceId = start.getId();
		}
		else {
			this.startPlaceId = null;
		}
	}

	/**
	 * @return the destination
	 */
	public Place getDestination() {
		return destination;
	}

	/**
	 * @param destination the destination to set
	 */
	public void setDestination(Place destination) {
		this.destination = destination;
		if (null != destination) {
			this.destinationPlaceId = destination.getId();
		}
		else {
			this.destinationPlaceId = null;
		}
	}

	/**
	 * @param car the car to set
	 */
	public void setCar(Car car) {
		this.car = car;
		if (null != car) {
			carId = car.getId();
		}
	}	
	
}
