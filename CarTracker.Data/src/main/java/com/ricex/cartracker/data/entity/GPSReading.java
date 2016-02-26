package com.ricex.cartracker.data.entity;

import java.util.Date;

public class GPSReading extends AbstractEntity {

	private double latitude;
	
	private double longitude;
	
	private double altitude;
	
	private Date readDate;
	
	private long tripId;
	
	private Trip trip;

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
	 * @return the altitude
	 */
	public double getAltitude() {
		return altitude;
	}

	/**
	 * @param altitude the altitude to set
	 */
	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}

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
	}
	
	
	
}
