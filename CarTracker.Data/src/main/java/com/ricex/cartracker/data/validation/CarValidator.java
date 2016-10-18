package com.ricex.cartracker.data.validation;

import org.apache.commons.lang3.StringUtils;

import com.ricex.cartracker.common.entity.Car;
import com.ricex.cartracker.data.mapper.CarMapper;
import com.ricex.cartracker.data.query.properties.EntityType;

public class CarValidator extends AbstractEntityValidator<Car> {		

	private final CarMapper mapper;
	
	public CarValidator(CarMapper mapper) {
		super(mapper, EntityType.CAR);
		this.mapper = mapper;
	}
	
	/**
	 * Checks that the given car is Valid
	 * 
	 * 		1) The VIN cannot be blank
	 */
	public void validate(Car entity) throws EntityValidationException {	
		if (StringUtils.isBlank(entity.getVin())) {
			throw new EntityValidationException("The VIN cannot be blank!");
		}		

	}
}
