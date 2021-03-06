package com.ricex.cartracker.data.validation;

import java.util.Date;

import com.ricex.cartracker.common.entity.Trip;
import com.ricex.cartracker.data.mapper.TripMapper;
import com.ricex.cartracker.data.query.properties.EntityType;

public class TripValidator extends AbstractEntityValidator<Trip> {

	private final TripMapper mapper;
	
	public TripValidator(TripMapper mapper) {
		super(mapper, EntityType.TRIP);
		this.mapper = mapper;
	}
	
	/**
	 * Checks that the given trip is Valid
	 * 
	 * 		1) The start date must be set
	 * 		2) The start date must be in the past
	 * 		4) If the end date is set, it must be in the past
	 * 		3) The trip must have a car
	 */
	public void validate(Trip entity) throws EntityValidationException {	
		if (entity.getStartDate() == null) {
			throw new EntityValidationException("Start Date cannot be left blank!");
		}
		if (entity.getStartDate().after(new Date())) {
			throw new EntityValidationException("Start Date cannot be in the future!");
		}
		if (null != entity.getEndDate() && entity.getEndDate().after(new Date())) {
			throw new EntityValidationException("End Date cannot be in the future!");
		}
		if (entity.getCarId() <= 0) {
			throw new EntityValidationException("Trip must be associated with a car!");
		}

	}
	
}
