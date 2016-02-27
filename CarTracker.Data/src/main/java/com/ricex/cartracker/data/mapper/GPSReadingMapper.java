package com.ricex.cartracker.data.mapper;

import com.ricex.cartracker.data.entity.GPSReading;

public interface GPSReadingMapper extends EntityMapper<GPSReading> {
	
	/** Fetches the GPS data for the a reading
	 * 
	 * @param readingId The id of the reading
	 * @return The GPS data for the specified reading
	 */
	public GPSReading getForReading(long readingId);
	
}
