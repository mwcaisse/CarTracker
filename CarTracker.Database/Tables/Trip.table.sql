CREATE TABLE TRIP (
	ID BIGINT NOT NULL AUTO_INCREMENT,
	START_DATE DATETIME NOT NULL,
	END_DATE DATETIME NULL,
	NAME VARCHAR(250) NULL,
	CAR_ID BIGINT NOT NULL,
	CREATE_DATE DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	MODIFIED_DATE DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (ID),
	FOREIGN KEY (CAR_ID) REFERENCES CAR(ID)
);
