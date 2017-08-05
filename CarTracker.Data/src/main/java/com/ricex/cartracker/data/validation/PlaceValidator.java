package com.ricex.cartracker.data.validation;

import com.ricex.cartracker.common.entity.Place;
import com.ricex.cartracker.data.mapper.PlaceMapper;
import com.ricex.cartracker.data.query.properties.EntityType;

public class PlaceValidator extends AbstractEntityValidator<Place> {

	private PlaceMapper mapper;
	
	public PlaceValidator(PlaceMapper mapper) {
		super(mapper, EntityType.PLACE);
		
		this.mapper = mapper;
	}

	/**
	 * Checks that a Place is valid
	 */
	public void validate(Place entity) throws EntityValidationException {
		// TODO Auto-generated method stub
		
	}

}
