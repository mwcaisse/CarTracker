package com.ricex.cartracker.data.mapper;

import java.util.List;

import com.ricex.cartracker.data.entity.Car;

/** Car Mapper for fetching car data
 * 
 * @author Mitchell Caisse
 *
 */

public interface CarMapper extends EntityMapper<Car> {		
	
	
	/** Fetches a Car by its VIN
	 * 
	 * @param vin The VIN of the car
	 * @return The car with the given vin, or null if none exists
	 */
	public Car getByVin(String vin);
	
	
}
