package com.ricex.cartracker.data.entity;

import java.util.Date;
import java.util.List;

public class Reading extends AbstractEntity {

	private Date readDate;
	
	private long tripId;
	
	private Trip trip;
	
	private GPSReading gpsReading;
	
	private OBDReading obdReading;

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

	/**
	 * @return the gpsReading
	 */
	public GPSReading getGpsReading() {
		return gpsReading;
	}

	/**
	 * @param gpsReading the gpsReading to set
	 */
	public void setGpsReading(GPSReading gpsReading) {
		this.gpsReading = gpsReading;
	}

	/**
	 * @return the obdReading
	 */
	public OBDReading getObdReading() {
		return obdReading;
	}

	/**
	 * @param obdReading the obdReading to set
	 */
	public void setObdReading(OBDReading obdReading) {
		this.obdReading = obdReading;
	}	
	
}
