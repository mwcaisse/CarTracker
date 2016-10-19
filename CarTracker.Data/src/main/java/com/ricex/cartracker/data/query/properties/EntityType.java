package com.ricex.cartracker.data.query.properties;

public enum EntityType {

	CAR ("Car"),
	READER_LOG ("Reader Log"),
	READING ("Reading"),
	TRIP ("Trip"),
	USER ("User");
	
	private final String name;
	
	private EntityType(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

}
