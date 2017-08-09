package com.ricex.cartracker.common.entity;

import org.apache.commons.lang3.StringUtils;

public enum TripPossiblePlaceType {

	START ("Start"),
	DESTINATION ("Destination");
	
	/** Converts a string to its corresponding Trip Possible Place Type based upon name
	 * 
	 * @param str
	 * @return
	 */
	public static TripPossiblePlaceType fromString(String str) {
		for (TripPossiblePlaceType type : values()) {
			if (StringUtils.equalsIgnoreCase(type.name, str)) {
				return type;
			}			
		}
		
		return null;
	}

	/** The human readable name of the Trip Possible Place Type */
	private final String name;
	
	private TripPossiblePlaceType(String name) {
		this.name = name;
	}
	
	public String toString() {
		return name;
	}
}
