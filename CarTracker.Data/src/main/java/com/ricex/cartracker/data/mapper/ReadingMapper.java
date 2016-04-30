package com.ricex.cartracker.data.mapper;

import java.util.List;

import com.ricex.cartracker.common.entity.Reading;

public interface ReadingMapper extends EntityMapper<Reading> {
	
	/** Gets a list of all readings for the given trip
	 * 
	 * @param tripId The trip id
	 * @return The list of trips for the reading
	 */
	public List<Reading> getForTrip(long tripId);

}
