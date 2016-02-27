package com.ricex.cartracker.data.mapper;

import com.ricex.cartracker.data.entity.OBDReading;

public interface OBDReadingMapper extends EntityMapper<OBDReading> {
	
	/** Fetches the OBD data for a reading
	 * 
	 * @param readingId The id of the reading
	 * @return The OBD data for the specified reading
	 */
	public OBDReading getForReading(long readingId);

}
