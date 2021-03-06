package com.ricex.cartracker.data.manager.auth;

import com.ricex.cartracker.common.entity.auth.RegistrationKeyUse;
import com.ricex.cartracker.data.manager.AbstractEntityManager;
import com.ricex.cartracker.data.mapper.auth.RegistrationKeyUseMapper;
import com.ricex.cartracker.data.query.properties.EntityType;
import com.ricex.cartracker.data.validation.EntityValidationException;
import com.ricex.cartracker.data.validation.auth.RegistrationKeyUseValidator;

public class RegistrationKeyUseManager extends AbstractEntityManager<RegistrationKeyUse> {

	private final RegistrationKeyUseMapper mapper;
	
	private final RegistrationKeyUseValidator validator;
	
	public RegistrationKeyUseManager(RegistrationKeyUseMapper mapper, RegistrationKeyUseValidator validator) {
		super(mapper, validator, EntityType.REGISTRATION_KEY_USE);

		this.mapper = mapper;
		this.validator = validator;
	}

	@Override
	protected void createValidationLogic(RegistrationKeyUse toCreate) throws EntityValidationException {
		
	}

	@Override
	protected void updateValidationLogic(RegistrationKeyUse toUpdate) throws EntityValidationException {
		
	}

}
