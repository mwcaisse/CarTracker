package com.ricex.cartracker.data.manager;

import com.ricex.cartracker.common.entity.Car;
import com.ricex.cartracker.common.entity.CarSupportedCommands;
import com.ricex.cartracker.data.mapper.CarMapper;
import com.ricex.cartracker.data.mapper.CarSupportedCommandsMapper;
import com.ricex.cartracker.data.query.properties.EntityType;
import com.ricex.cartracker.data.validation.CarSupportedCommandsValidator;
import com.ricex.cartracker.data.validation.CarValidator;
import com.ricex.cartracker.data.validation.EntityValidationException;

public class CarSupportedCommandsManager extends AbstractEntityManager<CarSupportedCommands> {
	
	protected final CarSupportedCommandsMapper mapper;
	protected final CarMapper carMapper;
	protected final CarSupportedCommandsValidator validator;
	protected final CarValidator carValidator;
	
	public CarSupportedCommandsManager(CarSupportedCommandsMapper mapper, 
			CarMapper carMapper,
			CarSupportedCommandsValidator validator,
			CarValidator carValidator) {
		super(mapper, validator, EntityType.CAR_SUPPORTED_COMMANDS);
		this.mapper = mapper;
		this.carMapper = carMapper;
		this.validator = validator;
		this.carValidator = carValidator;
	}
	
	public CarSupportedCommands getForCar(long carId) {
		return mapper.getForCar(carId);
	}	
	
	public boolean createOrUpdate(String vin, CarSupportedCommands commands) throws EntityValidationException {
		Car car = carMapper.getByVin(vin);
		if (null == car) {
			throw new EntityValidationException("No car with that VIN exists!");	
		}		
		commands.setCarId(car.getId());
		
		CarSupportedCommands existing = getForCar(car.getId());
		if (null == existing) {
			create(commands);
		}
		else {
			commands.setId(existing.getId());
			update(commands);
		}
		
		return true;
	}

	@Override
	protected void createValidationLogic(CarSupportedCommands toCreate) throws EntityValidationException {
		carValidator.exists(toCreate.getCarId());
	}

	@Override
	protected void updateValidationLogic(CarSupportedCommands toUpdate) throws EntityValidationException {
		carValidator.exists(toUpdate.getCarId());		
	}

}
