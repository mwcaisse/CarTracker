package com.ricex.cartracker.data.manager;

import com.ricex.cartracker.data.entity.Car;
import com.ricex.cartracker.data.mapper.CarMapper;

public class CarManager extends AbstractEntityManager<Car> {

	protected CarMapper mapper;
	
	/** Fetches a car by its VIN
	 * 
	 * @param vin The car's VIN
	 * @return The car with that VIN
	 */
	public Car getByVin(String vin) {
		return mapper.getByVin(vin);
	}
	
	public boolean existsByVin(String vin) {
		return null != getByVin(vin);
	}
	
	public void setEntityMapper(CarMapper mapper) {
		super.setEntityMapper(mapper);
		this.mapper = mapper;
	}
	

	
	
}
