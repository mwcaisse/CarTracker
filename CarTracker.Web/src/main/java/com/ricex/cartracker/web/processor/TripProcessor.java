package com.ricex.cartracker.web.processor;

import java.util.Date;
import java.util.List;

import com.ricex.cartracker.common.entity.Car;
import com.ricex.cartracker.common.entity.Place;
import com.ricex.cartracker.common.entity.Reading;
import com.ricex.cartracker.common.entity.Trip;
import com.ricex.cartracker.common.entity.TripPossiblePlace;
import com.ricex.cartracker.common.entity.TripPossiblePlaceType;
import com.ricex.cartracker.common.entity.TripStatus;
import com.ricex.cartracker.data.manager.CarManager;
import com.ricex.cartracker.data.manager.PlaceManager;
import com.ricex.cartracker.data.manager.ReadingManager;
import com.ricex.cartracker.data.manager.TripManager;
import com.ricex.cartracker.data.manager.TripPossiblePlaceManager;
import com.ricex.cartracker.data.validation.EntityValidationException;
import com.ricex.cartracker.placesrequester.PlaceRequester;
import com.ricex.cartracker.placesrequester.entity.placesearch.PlaceSearchModel;

public class TripProcessor {

	private final TripManager tripManager;
	
	private final ReadingManager readingManager;
	
	private final CarManager carManager;
	
	private final PlaceManager placeManager;
	
	private final PlaceRequester placeRequester;
	
	private final TripPossiblePlaceManager tripPossiblePlaceManager;
	
	public TripProcessor(TripManager tripManager, ReadingManager readingManager, CarManager carManager, 
			PlaceManager placeManager, PlaceRequester placeRequester, 
			TripPossiblePlaceManager tripPossiblePlaceManager) {
		
		this.tripManager = tripManager;
		this.readingManager = readingManager;
		this.carManager = carManager;
		this.placeManager = placeManager;
		this.placeRequester = placeRequester;
		this.tripPossiblePlaceManager = tripPossiblePlaceManager;
	}
	
	public Trip processTrip(long tripId) throws EntityValidationException {
		return processTrip(tripManager.get(tripId));
	}
	
	/** Processes the given trip
	 * 
	 * @param trip The trip to process
	 * @return The processed trip
	 */
	
	public Trip processTrip(Trip trip) throws EntityValidationException {
		if (TripStatus.PROCESSED.equals(trip.getStatus())) {
			throw new EntityValidationException("Trip has already been processed!");
		}
		
		Car tripCar = trip.getCar();
		
		List<Reading> readings = readingManager.getForTrip(trip.getId());
		
		if (!readings.isEmpty()) {
			
			double maxSpeed = readings.get(0).getSpeed();
			double totalSpeed = 0;
			double totalDistance = 0;
			double maxEngineRPM = readings.get(0).getEngineRPM();
			double totalEngineRPM = 0;
			long idleTime = 0; // milliseconds
			
			Reading previousReading = null;
			for (int i=0; i < readings.size(); i++) {
				Reading reading = readings.get(i);
				
				if (reading.getSpeed() > maxSpeed) {
					maxSpeed = reading.getSpeed();
				}
				if (reading.getEngineRPM() > maxEngineRPM) {
					maxEngineRPM = reading.getEngineRPM();
				}
				
				totalSpeed += reading.getSpeed();
				totalEngineRPM += reading.getEngineRPM();
				
				if (null != previousReading) {				
					double distance = calculateDistanceBetweenReadings(previousReading, reading);
					totalDistance += distance;					
					
					if (previousReading.getSpeed() == 0 &&
							reading.getSpeed() == 0) {
						idleTime += reading.getReadDate().getTime() - previousReading.getReadDate().getTime();
					}	
				}
				
				previousReading = reading;
			}
			
			double averageSpeed = totalSpeed / (readings.size() * 1.0);
			double averageEngineRPM = totalEngineRPM / (readings.size() * 1.0);
			
			trip.setAverageSpeed(averageSpeed);
			trip.setAverageEngineRPM(averageEngineRPM);
			trip.setMaxEngineRPM(maxEngineRPM);
			trip.setMaximumSpeed(maxSpeed);
			trip.setDistanceTraveled(totalDistance);
			trip.setIdleTime(idleTime);			
			
			
			//if the end date of the trip is null, set it to the date of the last reading
			if (null == trip.getEndDate()) {
				Date endDate = readings.get(readings.size() - 1).getReadDate();
				trip.setEndDate(endDate);
			}
			
			if (null != tripCar &&
					(null == tripCar.getMileageLastUserSet() ||
					tripCar.getMileageLastUserSet().before(trip.getEndDate()))) {
				double carMileage = tripCar.getMileage();
				carMileage += totalDistance;
				tripCar.setMileage(carMileage);
			}
		}		
		
		trip.setStatus(TripStatus.PROCESSED);
		
		try {
			tripManager.update(trip);	
			if (null != tripCar && trip.getDistanceTraveled() > 0) {
				carManager.update(tripCar);
			}
			addTripPossiblePlaces(trip, readings);
		}
		catch (EntityValidationException e) {
			Trip originalTrip = tripManager.get(trip.getId());
			
			originalTrip.setStatus(TripStatus.FAILED);
			try {
				tripManager.update(originalTrip);
				
				return originalTrip;
			}
			catch (EntityValidationException ex) {
				//if we get an error here, then we have a bigger issue.
				throw new RuntimeException("TODO: Change this into a logger or better exception", ex);
			}
		}	
		
		
		
		return trip;
	}
	
	
	private double calculateDistanceBetweenReadings(Reading prev, Reading current) {
		double currentLat = Math.toRadians(current.getLatitude());
		double prevLat = Math.toRadians(prev.getLatitude());
		
		if (currentLat == 0 || prevLat == 0) {
			return 0; // if either of the latitudes / longitudes are zero, return 0. 0 is an incorrect reading
			//TODO: Add a more sophisticated handler for this
		}
		
		double radius = 6371.0; // radius of earth in km
		double deltaLat = Math.toRadians(current.getLatitude() - prev.getLatitude());
		double deltaLong = Math.toRadians(current.getLongitude() - prev.getLongitude());		

		
		double a = Math.pow(Math.sin(deltaLat / 2.0), 2.0) + Math.pow(Math.sin(deltaLong / 2.0), 2.0) * Math.cos(prevLat) * Math.cos(currentLat);
		double c = 2.0 * Math.atan2(Math.sqrt(a), Math.sqrt(1.0-a));
		double distance = radius * c;	
		
		return Math.abs(distance);
	}
	
	private void addTripPossiblePlaces(Trip trip, List<Reading> readings) throws EntityValidationException {
		if (readings.isEmpty()) {
			return;
		}
		addTripPossiblePlaces(trip, readings.get(0), TripPossiblePlaceType.START);
		addTripPossiblePlaces(trip, readings.get(readings.size() - 1), TripPossiblePlaceType.DESTINATION);
	}
	
	private void addTripPossiblePlaces(Trip trip, Reading reading, TripPossiblePlaceType type) throws EntityValidationException {
		List<PlaceSearchModel> possiblePlaces = 
				placeRequester.getPlacesNearby(reading.getLatitude(), reading.getLongitude(), 150);
		
		for (PlaceSearchModel model : possiblePlaces) {
			TripPossiblePlace possiblePlace = new TripPossiblePlace();
			
			Place place = createOrFetchPlace(model);
			
			possiblePlace.setTripId(trip.getId());
			possiblePlace.setPlace(place);
			possiblePlace.setPlaceType(type);
			
			tripPossiblePlaceManager.create(possiblePlace);
		}
	}
	
	private Place createOrFetchPlace(PlaceSearchModel model) throws EntityValidationException {
		Place place = placeManager.getByGoogleId(model.getId());
		boolean create = false;
		if (null == place) {
			create = true;
			place = new Place();
			place.setGooglePlaceId(model.getId());
		}
		
		place.setName(model.getName());
		place.setLatitude(model.getGeometry().getLocation().getLat());
		place.setLongitude(model.getGeometry().getLocation().getLng());
		place.setActive(true);
		
		if (create) {
			placeManager.create(place);
		}
		else {
			placeManager.update(place);
		}
		
		return place;
	}
	
}
