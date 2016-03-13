package com.ricex.cartracker.data.manager;

import com.ricex.cartracker.data.entity.Reading;
import com.ricex.cartracker.data.mapper.EntityMapper;
import com.ricex.cartracker.data.mapper.ReadingMapper;
import com.ricex.cartracker.data.validation.EntityValidationException;
import com.ricex.cartracker.data.validation.EntityValidator;

public class ReadingManager extends AbstractEntityManager<Reading> {

	public static final String ENTITY_NAME = "Reading";
	
	private ReadingMapper mapper;
	
	
	
	protected ReadingManager(EntityMapper<Reading> entityMapper, EntityValidator<Reading> entityValidator) {
		super(entityMapper, entityValidator);
	}

	@Override
	protected void createValidationLogic(Reading toCreate) throws EntityValidationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void updateValidationLogic(Reading toUpdate) throws EntityValidationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected String getEntityName() {
		return ENTITY_NAME;
	}

}
