package com.ricex.cartracker.web.processor;

import java.util.Date;
import java.util.List;

import com.ricex.cartracker.common.entity.Reading;
import com.ricex.cartracker.common.entity.Trip;
import com.ricex.cartracker.common.entity.TripStatus;
import com.ricex.cartracker.data.manager.ReadingManager;
import com.ricex.cartracker.data.manager.TripManager;
import com.ricex.cartracker.data.validation.EntityValidationException;

public class TripProcessor {

	private final TripManager tripManager;
	
	private final ReadingManager readingManager;
	
	public TripProcessor(TripManager tripManager, ReadingManager readingManager) {
		this.tripManager = tripManager;
		this.readingManager = readingManager;
	}
	
	public Trip processTrip(long tripId) throws EntityValidationException {
		return processTrip(tripManager.get(tripId));
	}
	
	public Trip processTrip(Trip trip) throws EntityValidationException {	
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
					if (distance == 0) {
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
		}		
		
		trip.setStatus(TripStatus.PROCESSED);
		
		tripManager.update(trip);;
	
		
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
	
}
