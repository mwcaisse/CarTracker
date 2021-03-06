CREATE TABLE USER (
	ID BIGINT NOT NULL AUTO_INCREMENT,

	USERNAME VARCHAR(50) UNIQUE NOT NULL,
	PASSWORD VARCHAR(60) NOT NULL,
	NAME VARCHAR(500) NOT NULL,
	USER_EMAIL VARCHAR(250) NOT NULL,
	ACTIVE BIT NOT NULL DEFAULT 1,
	LOCKED BIT NOT NULL DEFAULT 0,
	EXPIRATION_DATE DATETIME NULL DEFAULT NULL,
	PASSWORD_EXPIRATION_DATE DATETIME NULL DEFAULT NULL,
	
	CREATE_DATE DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	MODIFIED_DATE DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (ID)
);
