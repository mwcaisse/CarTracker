package com.ricex.cartracker.data.validation;

import org.apache.commons.lang3.StringUtils;

import com.ricex.cartracker.data.entity.Car;
import com.ricex.cartracker.data.manager.CarManager;

public class CarValidator implements EntityValidator<Car> {

	
	protected CarManager manager;
	
	
	/**
	 * Checks that the given car is Valid
	 * 
	 * 		1) The VIN cannot be blank
	 * 		2) If creating..
	 * 			a) A car with the same VIN cannot already exist
	 * 		3) If updating...
	 * 			a) You cannot change the VIN of the car
	 */
	public void validate(Car entity) throws EntityValidationException {	
		if (StringUtils.isBlank(entity.getVin())) {
			throw new EntityValidationException("The VIN cannot be blank!");
		}
		
		if (entity.isNew()) {
			if (manager.existsByVin(entity.getVin())) {
				throw new EntityValidationException("A Car with this VIN already exists!");
			}
		}
		else {
			Car existing = manager.get(entity.getId());
			if (!StringUtils.equalsIgnoreCase(existing.getVin(), entity.getVin())) {
				throw new EntityValidationException("You cannot change a Car's VIN!");
			}
		}
	}

	/** Sets the manager that this validator will user
	 * 
	 * @param manager The manager to use
	 */
	public void setManager(CarManager manager) {
		this.manager = manager;
	}


}
