package com.ricex.cartracker.web.processor;

public class LocationModel {

	private final double latitude;
	
	private final double longitude;
	
	public LocationModel(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	
	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}
	
	
	
}
