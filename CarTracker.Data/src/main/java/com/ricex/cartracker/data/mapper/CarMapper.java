package com.ricex.cartracker.data.mapper;

import java.util.List;

import com.ricex.cartracker.data.entity.Car;

/** Car Mapper for fetching car data
 * 
 * @author Mitchell Caisse
 *
 */

public interface CarMapper {

	/** Returns a list of all Cars
	 * 
	 * @return All of the cars
	 */
	public List<Car> getAll();
	
	/** Returns the Car with the specified Id, or null if none exists
	 * 
	 * @param id Id of the car
	 * @return The car or null if none exists
	 */
	public Car get(long id);
	
	/** Creates a Car
	 * 
	 * @param car The car to create
	 */
	public void create(Car car);
	
	/** Updates a car
	 * 
	 * @param car The car to update
	 */
	public void update(Car car);		
	
}
