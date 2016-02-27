package com.ricex.cartracker.data.manager;

import com.ricex.cartracker.data.entity.Car;

public class CarManager extends AbstractEntityManager<Car> {

	/** Creates a new car
	 * 
	 * @param car The car to create
	 * @return The created car
	 */
	
	public Car create(Car car) {
		entityMapper.create(car);
		return get(car.getId());
	}
	
}
