package com.ricex.cartracker.data.validation;

import com.ricex.cartracker.common.entity.auth.RegistrationKeyUse;
import com.ricex.cartracker.data.mapper.auth.RegistrationKeyUseMapper;
import com.ricex.cartracker.data.query.properties.EntityType;

public class RegistrationKeyUseValidator extends AbstractEntityValidator<RegistrationKeyUse> {

	private final RegistrationKeyUseMapper mapper;
	
	public RegistrationKeyUseValidator(RegistrationKeyUseMapper mapper) {
		super(mapper, EntityType.REGISTRATION_KEY_USE);
		
		this.mapper = mapper;
	}

	public void validate(RegistrationKeyUse entity) throws EntityValidationException {
		if (entity.getKeyId() == RegistrationKeyUse.INVALID_ID) {
			throw new EntityValidationException("A Registration Key Id must be specified!");
		}
		if (entity.getUserId() == RegistrationKeyUse.INVALID_ID) {
			throw new EntityValidationException("A User Id must be specified!");
		}
		
	}

}
