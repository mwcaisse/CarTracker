CREATE TABLE OBD_READING (
	ID BIGINT NOT NULL AUTO_INCREMENT,
	AIR_INTAKE_TEMP DOUBLE NULL,
	AMBIENT_AIR_TEMP DOUBLE NULL,
	ENGINE_COOLANT_TEMP DOUBLE NULL,
	OIL_TEMP DOUBLE NULL,
	SPEED DOUBLE NULL,
	MASS_AIR_FLOW DOUBLE NULL,
	THROTTLE_POSITION DOUBLE NULL,
	FUEL_TYPE VARCHAR(250) NULL,
	FUEL_LEVEL DOUBLE NULL,
	READ_DATE DATETIME NOT NULL,	
	TRIP_ID BIGINT NOT NULL,
	CREATE_DATE DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	MODIFIED_DATE DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (ID),
	FOREIGN KEY (TRIP_ID) REFERENCES TRIP(ID)
);
