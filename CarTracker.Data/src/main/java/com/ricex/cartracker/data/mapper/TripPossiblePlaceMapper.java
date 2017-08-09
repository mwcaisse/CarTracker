package com.ricex.cartracker.data.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.ricex.cartracker.common.entity.TripPossiblePlace;
import com.ricex.cartracker.common.entity.TripPossiblePlaceType;

/** Trip Possible Place mapper for fetching Trip Possible Place data
 * 
 * @author Mitchell Caisse
 *
 */

public interface TripPossiblePlaceMapper extends EntityMapper<TripPossiblePlace> {

	/** Gets all of possible places for the given trip
	 * 
	 * @param tripId The trip's id
	 * @return All of the trip's possible places
	 */
	public List<TripPossiblePlace> getForTrip(long tripId);
	
	/** Gets all of the possible places for the given trip with paging and sorting
	 *  
	 * @param tripId The trip's id
	 * @param orderBy the sorting string
	 * @param bounds The bounds specifying the offset + limit
	 * @return All of the trip possible places within the bounds.
	 */
	public List<TripPossiblePlace> getForTrip(@Param("tripId") long tripId, 
			@Param("orderBy") String orderBy, RowBounds bounds);
	
	/** Counts the number of possible places for the given trip
	 * 
	 * @param tripId The id of the trip
	 * @return The number of possible places that exist for the trip
	 */
	public long countForTrip(long tripId);
	
	/** Gets all of possible places of the given type for the given trip
	 * 
	 * @param tripId The trip's id
	 * @param type The Possible place type
	 * @return The count
	 */
	public List<TripPossiblePlace> getForTripOfType(long tripId, TripPossiblePlaceType type);
	
	/** Gets all of possible places of the given type for the given trip with paging and sorting
	 * 
	 * @param tripId The trip's id
	 * @param type The Possible place type
	 * @param orderBy the sorting string
	 * @param bounds The bounds specifying the offset + limit
	 * @return The count
	 */	
	public List<TripPossiblePlace> getForTripOfType(@Param("tripId") long tripId, 
			@Param("type") TripPossiblePlaceType type, @Param("orderBy") String orderBy, RowBounds bounds);	
	
	
	/** Counts the number of possible places of the given type for the given trip
	 * 
	 * @param tripId The trip's id
	 * @param type The Possible place type
	 * @return The count
	 */
	public long countForTripOfType(long tripId, TripPossiblePlaceType type);
	
}
