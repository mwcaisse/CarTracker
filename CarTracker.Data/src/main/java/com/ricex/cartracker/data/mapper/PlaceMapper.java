package com.ricex.cartracker.data.mapper;

import com.ricex.cartracker.common.entity.Place;

public interface PlaceMapper extends EntityMapper<Place> {

	/** Fetches the place with the given google id
	 * 
	 * @param googlePlaceId The google place id
	 * @return The place with the given id or null if non exists
	 */
	public Place getByGoogleId(String googlePlaceId);
	
}
