package com.ricex.cartracker.data.manager;

import com.ricex.cartracker.common.entity.Place;
import com.ricex.cartracker.data.mapper.PlaceMapper;
import com.ricex.cartracker.data.query.properties.EntityType;
import com.ricex.cartracker.data.validation.EntityValidationException;
import com.ricex.cartracker.data.validation.PlaceValidator;

public class PlaceManager extends AbstractEntityManager<Place> {

	protected final PlaceMapper mapper;
	protected final PlaceValidator validator;
	
	public PlaceManager(PlaceMapper mapper, PlaceValidator validator) {
		super(mapper, validator, EntityType.PLACE);

		this.mapper = mapper;
		this.validator = validator;
	}

	@Override
	protected void createValidationLogic(Place toCreate) throws EntityValidationException {

	}

	@Override
	protected void updateValidationLogic(Place toUpdate) throws EntityValidationException {
		
	}

}
