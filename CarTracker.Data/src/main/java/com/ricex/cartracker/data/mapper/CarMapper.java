package com.ricex.cartracker.data.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.ricex.cartracker.common.entity.Car;

/** Car Mapper for fetching car data
 * 
 * @author Mitchell Caisse
 *
 */

public interface CarMapper extends EntityMapper<Car> {		
	
	/** Gets all of the cars with paging and sorting
	 * 
	 * @param orderBy The sorting string
	 * @param bounds the bounds specifying the offset + limit
	 * @return All of the cars within the bounds + sorted
	 */
	
	public List<Car> getAll(@Param("orderBy") String orderBy, RowBounds bounds);
	
	/** Fetches a Car by its VIN
	 * 
	 * @param vin The VIN of the car
	 * @return The car with the given vin, or null if none exists
	 */
	public Car getByVin(String vin);
	
	
}
