package com.ricex.cartracker.common.entity;

import org.apache.commons.lang3.StringUtils;

public enum TripStatus {

	NEW ("New"),
	STARTED ("Started"),
	FINISHED ("Finished"),
	PROCESSED ("Processed"),
	FAILED ("FAILED");
	
	/** Converts a string to its corresponding Trip Status based upon name
	 * 
	 * @param str
	 * @return
	 */
	public static TripStatus fromString(String str) {
		for (TripStatus status : values()) {
			if (StringUtils.equalsIgnoreCase(status.name, str)) {
				return status;
			}			
		}
		
		return null;
	}

	/** The human readable name of the Trip Status */
	private final String name;
	
	private TripStatus(String name) {
		this.name = name;
	}
	
	public String toString() {
		return name;
	}	
	
}
