package com.ricex.cartracker.data.manager;

import org.apache.commons.lang3.StringUtils;

import com.ricex.cartracker.common.entity.Car;
import com.ricex.cartracker.data.mapper.CarMapper;
import com.ricex.cartracker.data.query.properties.EntityType;
import com.ricex.cartracker.data.validation.CarValidator;
import com.ricex.cartracker.data.validation.EntityValidationException;

public class CarManager extends AbstractEntityManager<Car> {
	
	protected CarMapper mapper;
	protected CarValidator validator;
	
	/** Creates a new Car Manager with the given CarMapper
	 * 
	 * @param mapper The Car Mapper to use for database tasks
	 */
	public CarManager(CarMapper mapper) {
		this(mapper, new CarValidator());
	}
	
	/** Creates a new Car Manager with the given CarMapper and CarValidator
	 * 
	 * @param mapper The mapper to use
	 * @param validator The validator to use
	 */
	public CarManager(CarMapper mapper, CarValidator validator) {
		super(mapper, validator, EntityType.CAR);
		this.mapper = mapper;
		this.validator = validator;
	}
	
	/** Fetches a car by its VIN
	 * 
	 * @param vin The car's VIN
	 * @return The car with that VIN
	 */
	public Car getByVin(String vin) {
		return mapper.getByVin(vin);
	}
	
	/** Determines if a car with the given VIN exists
	 * 
	 * @param vin The VIN to check
	 * @return True if a car exists false otherwise
	 */
	public boolean existsByVin(String vin) {
		return null != getByVin(vin);
	}

	@Override
	protected void createValidationLogic(Car toCreate) throws EntityValidationException {
		if (existsByVin(toCreate.getVin())) {
			throw new EntityValidationException("A " + getEntityName() + " with this VIN already exists!");
		}
		
	}

	@Override
	protected void updateValidationLogic(Car toUpdate) throws EntityValidationException {
		Car existing = get(toUpdate.getId());
		if (!StringUtils.equalsIgnoreCase(existing.getVin(), toUpdate.getVin())) {
			throw new EntityValidationException("You cannot modify a " + getEntityName() + "'s VIN!");
		}
		
	}	
}
