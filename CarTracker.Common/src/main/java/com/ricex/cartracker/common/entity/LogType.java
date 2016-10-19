package com.ricex.cartracker.common.entity;

import org.apache.commons.lang3.StringUtils;

public enum LogType {

	DEBUG ("Debug"),
	INFO ("Info"),
	WARN ("Warn"),
	ERROR ("Error");
	
	private final String name;
	
	/** Converts a string to its corresponding Log Type based upon name
	 * 
	 * @param str
	 * @return
	 */
	
	public static LogType fromString(String str) {
		for (LogType type : values()) {
			if (StringUtils.equalsIgnoreCase(type.name, str)) {
				return type;
			}
		}
		return null;
	}
	
	private LogType(String name) {
		this.name = name;
	}
	
	public String toString() {
		return name;
	}
	
}
