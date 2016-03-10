package com.ricex.cartracker.data.manager;

import com.ricex.cartracker.data.entity.Car;
import com.ricex.cartracker.data.mapper.CarMapper;
import com.ricex.cartracker.data.validation.CarValidator;

public class CarManager extends AbstractEntityManager<Car> {

	protected CarMapper mapper;
	
	public CarManager(CarMapper mapper) {
		super(mapper, new CarValidator());
		this.mapper = mapper;
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
	
}
