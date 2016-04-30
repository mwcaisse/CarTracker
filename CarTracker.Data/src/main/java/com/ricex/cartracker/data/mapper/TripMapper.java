package com.ricex.cartracker.data.mapper;

import java.util.List;

import com.ricex.cartracker.common.entity.Trip;

/** Trip Mapper for fetching Trip data
 * 
 * @author Mitchell Caisse
 *
 */

public interface TripMapper extends EntityMapper<Trip> {
	
	/** Gets all of the trips for the given car
	 * 
	 * @param carId The car's id
	 * @return All of the Trips for the given car
	 */
	public List<Trip> getForCar(long carId);	
	
}
