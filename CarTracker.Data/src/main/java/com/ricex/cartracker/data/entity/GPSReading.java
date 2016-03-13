package com.ricex.cartracker.data.entity;

import java.util.Date;

public class GPSReading extends AbstractEntity {

	private double latitude;
	
	private double longitude;

	private Reading reading;

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
