package com.ricex.cartracker.common.entity;

import java.util.Date;

import com.ricex.cartracker.common.entity.auth.User;

public class Car extends AbstractEntity {
	
	private String vin;
	
	private String name;

	private long ownerId;
	
	private User owner;
	
	private double mileage;
	
	private Date mileageLastUserSet;
	
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
	 * @return the ownerId
	 */
	public long getOwnerId() {
		return ownerId;
	}

	/**
	 * @param ownerId the ownerId to set
	 */
	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}

	/**
	 * @return the owner
	 */
	public User getOwner() {
		return owner;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(User owner) {
		this.owner = owner;
	}

	/**
	 * @return the mileage
	 */
	public double getMileage() {
		return mileage;
	}

	/**
	 * @param mileage the mileage to set
	 */
	public void setMileage(double mileage) {
		this.mileage = mileage;
	}

	/**
	 * @return the mileageLastUserSet
	 */
	public Date getMileageLastUserSet() {
		return mileageLastUserSet;
	}

	/**
	 * @param mileageLastUserSet the mileageLastUserSet to set
	 */
	public void setMileageLastUserSet(Date mileageLastUserSet) {
		this.mileageLastUserSet = mileageLastUserSet;
	}		
	
}
