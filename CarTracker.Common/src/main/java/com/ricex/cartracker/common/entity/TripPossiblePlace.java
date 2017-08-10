package com.ricex.cartracker.common.entity;

public class TripPossiblePlace extends AbstractEntity {

	private long tripId;
	
	private long placeId;
	
	private TripPossiblePlaceType placeType;
	
	private double distance;
	
	private Place place;
	
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
	 * @return the placeId
	 */
	public long getPlaceId() {
		return placeId;
	}

	/**
	 * @param placeId the placeId to set
	 */
	public void setPlaceId(long placeId) {
		this.placeId = placeId;
	}

	
	/**
	 * @return the placeType
	 */
	public TripPossiblePlaceType getPlaceType() {
		return placeType;
	}

	/**
	 * @param placeType the placeType to set
	 */
	public void setPlaceType(TripPossiblePlaceType placeType) {
		this.placeType = placeType;
	}

	
	
	/**
	 * @return the distance
	 */
	public double getDistance() {
		return distance;
	}

	/**
	 * @param distance the distance to set
	 */
	public void setDistance(double distance) {
		this.distance = distance;
	}

	/**
	 * @return the place
	 */
	public Place getPlace() {
		return place;
	}

	/**
	 * @param place the place to set
	 */
	public void setPlace(Place place) {
		this.place = place;
		if (null != place) {
			this.placeId = place.getId();
		}
		else {
			this.placeId = Place.INVALID_ID;
		}
	}
	
	
}
