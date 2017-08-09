package com.ricex.cartracker.data.validation;

import com.ricex.cartracker.common.entity.TripPossiblePlace;
import com.ricex.cartracker.common.entity.TripPossiblePlaceType;
import com.ricex.cartracker.data.mapper.EntityMapper;
import com.ricex.cartracker.data.mapper.TripPossiblePlaceMapper;
import com.ricex.cartracker.data.query.properties.EntityType;

public class TripPossiblePlaceValidator extends AbstractEntityValidator<TripPossiblePlace> {

	private final TripPossiblePlaceMapper mapper;
	
	public TripPossiblePlaceValidator(TripPossiblePlaceMapper mapper) {
		super(mapper, EntityType.TRIP_POSSIBLE_PLACE);		
		this.mapper = mapper;
	}

	public void validate(TripPossiblePlace entity) throws EntityValidationException {
		if (entity.getPlaceId() <= 0) {
			throw new EntityValidationException("Trip Possible Place must be associated with a place!");
		}
		if (entity.getTripId() <= 0) {
			throw new EntityValidationException("Trip Possible Place must be associated with a trip!");
		}
		if (null == entity.getPlaceType()) {
			throw new EntityValidationException("Trip Possible Place must have a type!");
		}		
	}	
	
}
