package com.ricex.cartracker.data.manager;

import java.util.List;

import com.ricex.cartracker.data.entity.Reading;
import com.ricex.cartracker.data.mapper.ReadingMapper;
import com.ricex.cartracker.data.validation.EntityValidationException;
import com.ricex.cartracker.data.validation.ReadingValidator;

public class ReadingManager extends AbstractEntityManager<Reading> {

	public static final String ENTITY_NAME = "Reading";
	
	private ReadingMapper mapper;
	
	private ReadingValidator validator;
	
	private TripManager tripManager;
	
	public ReadingManager(ReadingMapper mapper, TripManager tripManager) {
		this (mapper, tripManager, new ReadingValidator());
	}
	
	protected ReadingManager(ReadingMapper mapper, TripManager tripManager, ReadingValidator validator) {
		super(mapper, validator);
		
		this.mapper = mapper;
		this.validator = validator;
		this.tripManager = tripManager;
	}
	
	public List<Reading> getForTrip(long tripId) {
		return mapper.getForTrip(tripId);
	}

	@Override
	protected void createValidationLogic(Reading toCreate) throws EntityValidationException {
		if (!tripManager.exists(toCreate.getTripId())) {
			throw new EntityValidationException(String.format("The specified {0} does not exist!", tripManager.ENTITY_NAME));
		}
	}

	@Override
	protected void updateValidationLogic(Reading toUpdate) throws EntityValidationException {
		createValidationLogic(toUpdate);
	}

	@Override
	protected String getEntityName() {
		return ENTITY_NAME;
	}

}
