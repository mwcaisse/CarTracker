package com.ricex.cartracker.data.validation;

import org.apache.commons.lang3.StringUtils;

import com.ricex.cartracker.common.entity.RegistrationKey;
import com.ricex.cartracker.data.mapper.RegistrationKeyMapper;
import com.ricex.cartracker.data.query.properties.EntityType;

public class RegistrationKeyValidator extends AbstractEntityValidator<RegistrationKey> {

	private final RegistrationKeyMapper mapper;
	
	public RegistrationKeyValidator(RegistrationKeyMapper mapper) {
		super(mapper, EntityType.REGISTRATION_KEY);
		
		this.mapper = mapper;
	}

	public void validate(RegistrationKey entity) throws EntityValidationException {
		if (StringUtils.isBlank(entity.getKey())) {
			throw new EntityValidationException("Key cannot be blank!");
		}
	}	

}
