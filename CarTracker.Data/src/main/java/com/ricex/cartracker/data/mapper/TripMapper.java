package com.ricex.cartracker.data.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

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
	
	/** Gets all of the trips for the given car with paging and sorting
	 * 
	 * @param carId The car's id
	 * @param orderBy the sorting string
	 * @param bounds The bounds specifying  the offset + limit
	 * @return ALl of the trips within the bounds
	 */
	public List<Trip> getForCar(@Param("carId") long carId, @Param("orderBy") String orderBy, RowBounds bounds);
	
	/** Counts the number of trips for the given car
	 * 
	 * @param carId The id of the car
	 * @return The number of trips that exist for the car
	 */
	public long countForCar(long carId);
	
	/** Returns a list of all the trips that are unprocessed
	 * 
	 * @return The unprocessed trips
	 */
	public List<Trip> getUnprocessedTrips();
	
	/** Returns the trip after the trip with the given id, for its car
	 * 
	 * @param tripId
	 * @return
	 */
	public Trip getPreviousTripForCar(long tripId);
	
	/** Returns the trip before the trip with the given id, for its car
	 * 
	 * @param tripId
	 * @return
	 */
	public Trip getNextTripForCar(long tripId);
	
	/** Deletes all the trips for the given car
	 * 
	 * @param carId The id of the car
	 */
	public void deleteForCar(long carId);
	
}
