package com.ricex.cartracker.data.mapper;

import java.util.List;

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
	
	/** Gets all of the trips for the given car with paging
	 * 
	 * @param carId The car's id
	 * @param bounds The bounds specifiying  the offset + limit
	 * @return ALl of the trips within the bounds
	 */
	public List<Trip> getForCar(long carId, RowBounds bounds);
	
}
