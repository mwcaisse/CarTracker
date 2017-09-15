package com.ricex.cartracker.data.mapper;

import com.ricex.cartracker.common.entity.CarSupportedCommands;

/** Car Supported Commands Mapper for fetching car supported command data
 * 
 * @author Mitchell Caisse
 *
 */

public interface CarSupportedCommandsMapper extends EntityMapper<CarSupportedCommands> {		
	
	/**
	 *  Gets the supported commands for a given car
	 *  
	 * @param carId The id of the car
	 * @return The supported commands for the car
	 */
	public CarSupportedCommands getForCar(long carId);
	
	
}
