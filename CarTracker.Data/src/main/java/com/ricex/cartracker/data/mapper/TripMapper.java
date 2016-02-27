package com.ricex.cartracker.data.mapper;

import java.util.List;

import com.ricex.cartracker.data.entity.Trip;

/** Trip Mapper for fetching Trip data
 * 
 * @author Mitchell Caisse
 *
 */

public interface TripMapper {

	/** Gets a trip by id
	 * 
	 * @param id Id of the trip
	 * @return The trip with the given id or null if none exists
	 */
	public Trip get(long id);
	
	/** Gets all of the trips
	 * 
	 * @return all of the trips
	 */
	public List<Trip> getAll();
	
	/** Gets all of the trips for the given car
	 * 
	 * @param carId The car's id
	 * @return All of the Trips for the given car
	 */
	public List<Trip> getForCar(long carId);
	
	/** Creates the given trip
	 * 
	 * @param trip The trip to create
	 */
	public void create(Trip trip);
	
	/** Updates the given trip
	 * 
	 * @param trip The trip to update
	 */
	public void update(Trip trip);
	
	
}
