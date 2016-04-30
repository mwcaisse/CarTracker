package com.ricex.cartracker.data.validation;

import org.apache.commons.lang3.StringUtils;

import com.ricex.cartracker.common.entity.Car;

public class CarValidator implements EntityValidator<Car> {	
	
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
