package com.ricex.cartracker.common.viewmodel;

import java.io.Serializable;

public class PreviousNextTrip implements Serializable {

	private long tripId;
	
	private Long nextTripId;
	
	private Long previousTripId;

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
	 * @return the nextTripId
	 */
	public Long getNextTripId() {
		return nextTripId;
	}

	/**
	 * @param nextTripId the nextTripId to set
	 */
	public void setNextTripId(Long nextTripId) {
		this.nextTripId = nextTripId;
	}

	/**
	 * @return the previousTripId
	 */
	public Long getPreviousTripId() {
		return previousTripId;
	}

	/**
	 * @param previousTripId the previousTripId to set
	 */
	public void setPreviousTripId(Long previousTripId) {
		this.previousTripId = previousTripId;
	}	
	
}
