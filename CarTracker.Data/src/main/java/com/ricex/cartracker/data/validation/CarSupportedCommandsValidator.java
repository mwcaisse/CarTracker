package com.ricex.cartracker.data.validation;

import com.ricex.cartracker.common.entity.CarSupportedCommands;
import com.ricex.cartracker.data.mapper.CarSupportedCommandsMapper;
import com.ricex.cartracker.data.query.properties.EntityType;

public class CarSupportedCommandsValidator extends AbstractEntityValidator<CarSupportedCommands> {

	private final CarSupportedCommandsMapper mapper;
	
	public CarSupportedCommandsValidator(CarSupportedCommandsMapper mapper) {
		super(mapper, EntityType.CAR_SUPPORTED_COMMANDS);

		this.mapper = mapper;
	}

	public void validate(CarSupportedCommands entity) throws EntityValidationException {
		if (entity.getCarId() <= 0) {
			throw new EntityValidationException("Car Supported Commands must be associated with a car!");
		}
	}

}
