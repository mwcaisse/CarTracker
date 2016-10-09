package com.ricex.cartracker.common.entity;

import java.util.Date;

public class Trip extends AbstractEntity {
	
	private Date startDate;
	
	private Date endDate;
	
	private String name;
	
	private long carId;
	
	private Car car;
	
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
	 * @param car the car to set
	 */
	public void setCar(Car car) {
		this.car = car;
		if (null != car) {
			carId = car.getId();
		}
	}	

	public void setNew() {
		super.setNew();
		status = TripStatus.NEW;
	}

	
	
}
